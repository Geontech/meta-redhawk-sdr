# Uses the available redhawk domain to launch a component (arg 1) 
# by way of creating a waveform, and then calls api, start, stop,
# and releaseObject on the waveform.
# All of these methods should work.
from ossie.utils import redhawk, sb, uuid
from ossie.cf import CF
import os
import sys
import shutil
import time

if len(sys.argv) != 2:
    sys.exit("Script takes 1 argument: the component name")

component_name = sys.argv[1]
if not component_name in sb.catalog():
    sys.exit("Component not found in SDRROOT: " + component_name)

sca_dom_path = os.path.join(os.environ['SDRROOT'], 'dom')

wave_name = str(uuid.uuid1())
catalog_name = os.path.join('/', 'waveforms', wave_name, wave_name + '.sad.xml')
waveform_file_path = os.path.abspath(os.path.join(sca_dom_path, catalog_name[1:]))
waveform_dir_path = os.path.dirname(waveform_file_path)

success = True
app = None
try:
    print("Creating waveform %s with %s in it" % (wave_name, component_name))
    sb.launch(component_name)
    waveform_file = sb.generateSADXML(wave_name)
    sb.release()

    print("Creating waveform path: %s" % waveform_dir_path)
    os.makedirs(waveform_dir_path)
    with open(waveform_file_path, 'w') as f:
        print("Installing waveform to %s..." % waveform_file_path)
        f.write(waveform_file)
    print("Finished installing waveform.")
    
    print("Attaching to REDHAWK Domain")
    dom = redhawk.attach()
    has_executables = False
    for d in dom.devices:
        if type(d).__name__ == 'ExecutableDevice':
            has_executables = True
            break
    if not has_executables:
        raise Exception("Domain has no executable devices")

    print("Preparing to create application from waveform.")
    app = None
    for attempt in range(10):
        try:
            app = dom.createApplication(catalog_name)
        except Exception as e:
            print(e)
            print "Unable to create application (attempt %d).  Sleeping 5 seconds..." % (attempt+1)
            time.sleep(5)

    if not app:
        success = False
    else:
        print("Application created.  Testing interfaces...")
        app.api()
        app.start()
        time.sleep(5)
        app.stop()
        app.releaseObject()
        app = None
        print("Application released.")
except Exception as e:
    success = False
    print(e)
finally:
    if app:
        app.releaseObject()

    if os.path.exists(waveform_dir_path) and os.path.isdir(waveform_dir_path):
        print("Uninstalling application.")
        shutil.rmtree(waveform_dir_path)

print("Exiting with success: %s" % success)
sys.exit(not success)
