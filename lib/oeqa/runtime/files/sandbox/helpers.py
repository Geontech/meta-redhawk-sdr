# Instantiate each of the helper "components"
from ossie.utils import sb
import sys

# Note we don't check plots or SoundSink as those imply
# onboard hardware support.
helpers = [
    sb.StreamSource, sb.StreamSink,
    sb.FileSource, sb.FileSink,
    sb.MessageSource, sb.MessageSink,
    sb.PropertyChangeListener,
    ]

failed = False
for helper in helpers:
    try:
        h = helper()
        print "Passed: %s" % type(h)
    except Exception as e:
        print "Failed: %s (%s)" % (type(h), e)
        failed = True

if failed:
    sys.exit(1)
sys.exit(0)
