import os
from oeqa.runtime.case import OERuntimeTestCase

RH_CASES_DIR = os.path.join(os.path.dirname(os.path.abspath(__file__)))
RH_FILES_DIR = os.path.join(os.path.dirname(RH_CASES_DIR), "files")

class RHRuntimeTestCase(OERuntimeTestCase):
    def run_command(self, command):
        status, output = self.target.run(command)
        msg = 'REDHAWK %s test failed, output: %s' % (self.__class__.__name__, output)
        self.assertEqual(status, 0, msg=msg)
        return output
