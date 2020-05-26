#!/bin/sh
#
# This file is protected by Copyright. Please refer to the COPYRIGHT file distributed 
# with this source distribution.
#
# This file is part of Geon Technology's meta-redhawk-sdr.
#
# Geon Technology's meta-redhawk-sdr is free software: you can redistribute it and/or 
# modify it under the terms of the GNU Lesser General Public License as published by 
# the Free Software Foundation, either version 3 of the License, or (at your option) 
# any later version.
#
# Geon Technology's meta-redhawk-sdr is distributed in the hope that it will be useful, 
# but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or 
# FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more
# details.
#
# You should have received a copy of the GNU Lesser General Public License
# along with this program.  If not, see http://www.gnu.org/licenses/.
#


### BEGIN INIT INFO
# Provides:          nodeBooter-DOMAIN
# Required-Start:    $local_fs $remote_fs $network
# Required-Stop:     $local_fs $remote_fs $network
# Should-Start:      $syslog
# Should-Stop:       $syslog
# Default-Start:     2 3 4 5
# Default-Stop:      0 1 6
# Short-Description: Makes sure the DOMAIN exists on boot
# Description:       Starts the DOMAIN on boot
#                    
### END INIT INFO

OSSIEHOME=OSSIEHOME_PATH
SDRROOT=SDRROOT_PATH
PYTHONPATH=$OSSIEHOME/lib/python:$OSSIEHOME/lib64/python
LD_LIBRARY_PATH=$OSSIEHOME/lib
PATH=/sbin:/bin:/usr/sbin:/usr/bin:$OSSIEHOME/bin
DOMAIN=DOMAIN_NAME
DOMAIN_PROFILE=$SDRROOT/dom/domain/DomainManager.dmd.xml
PIDFILE=/var/run/$DOMAIN.pid
LOG_CFG_FILE=LOG_CFG_PATH
DAEMON_ARGS="-D $DOMAIN_PROFILE --domain $DOMAIN --pidfile $PIDFILE --daemon --user redhawk -logcfgfile $LOG_CFG_FILE"

do_start() {
  if [ -f $PIDFILE ]; then
    echo "PID file exists. Attemping to stop before starting."
    do_stop
  fi
  mkdir -p /var/log/redhawk
  chown -R redhawk:redhawk /var/log/redhawk
  /bin/bash -lc "nodeBooter $DAEMON_ARGS" || do_stop
}

do_stop() {
  echo "Stopping Domain Manager on PID $(cat $PIDFILE)"
  if ! kill $(cat $PIDFILE); then
    echo "Standard kill failed to stop domain manager, escalating!"
    kill -9 $(cat $PIDFILE)
  fi

  rm -f $PIDFILE
}

do_status() {
  if [ -f $PIDFILE ]; then
    echo "I think the Domain Manager is running on PID $(cat $PIDFILE)"
  else
    echo "I dont think its running"
  fi
}

case "$1" in
  start)
        do_start
        ;;
  stop)
        do_stop
        ;;
  status)
        do_status
        ;;
  reset)
        do_stop
        do_start
        ;;
  *)
        exit 0
        ;;
esac

exit 0

