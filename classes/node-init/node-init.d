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
# Provides:          nodeBooter-NODE_NAME
# Required-Start:    $local_fs $remote_fs $network
# Required-Stop:     $local_fs $remote_fs $network
# Should-Start:      $syslog
# Should-Stop:       $syslog
# Default-Start:     2 3 4 5
# Default-Stop:      0 1 6
# Short-Description: Makes sure the NODE_NAME node exists on boot
# Description:       Starts the NODE_NAME node on boot
#                    
### END INIT INFO
NODENAME=NODE_NAME
DOMAIN=DOMAIN_NAME
PIDFILE=/var/run/redhawk/$NODENAME.pid
LOGFILE=/var/log/redhawk/$NODENAME.log
DAEMON_ARGS="-d /nodes/$NODENAME/DeviceManager.dcd.xml --domain ${DOMAIN} --pidfile $PIDFILE --daemon --user redhawk 2>&1 > $LOGFILE"

do_start() {
  if [ -f $PIDFILE ]; then
    echo "PID file exists. Attemping to stop before starting."
    do_stop
  fi
  mkdir -p /var/run/redhawk
  chown -R redhawk:redhawk /var/run/redhawk
  mkdir -p /var/log/redhawk
  chown -R redhawk:redhawk /var/log/redhawk
  /bin/bash -lc "nodeBooter $DAEMON_ARGS" || do_stop
}

do_stop() {
  if [ -f "${PIDFILE}" ]; then
    echo "Stopping Device Manager on PID $(cat $PIDFILE)"
    if ! kill $(cat $PIDFILE); then
      echo "Standard kill failed to stop device manager, escalating!"
      kill -9 $(cat $PIDFILE)
    fi
    rm -f $PIDFILE
  fi
}

do_status() {
  if [ -f $PIDFILE ]; then
    echo "I think the Device Manager ${NODENAME} is running on PID $(cat $PIDFILE)"
  else
    echo "I do not think ${NODENAME} is running"
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

