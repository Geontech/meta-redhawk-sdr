# Uses the sandbox to launch a component (arg 1) and then
# call api, start, stop, and releaseObject.
# All of these methods should work.
from ossie.utils import sb
from ossie.cf import CF
import sys

if len(sys.argv) != 2:
    sys.exit("Script takes 1 argument: the component name")

component_name = sys.argv[1]
if not component_name in sb.catalog():
    sys.exit("Component not found in SDRROOT: " + component_name)

component = None
try:
    component = sb.launch(component_name)
    component.ref._set_log_level( CF.LogLevels.DEBUG )
    component.api()
    component.start()
    component.stop()
    component.releaseObject()
    del component
except Exception as e:
    sys.exit(e)

sys.exit(0)
