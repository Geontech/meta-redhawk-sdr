from _common import RHRuntimeTestCase
from oeqa.core.decorator.depends import OETestDepends
from oeqa.runtime.decorator.package import OEHasPackage

class OmniORB(RHRuntimeTestCase):
    @OEHasPackage(['omniorb-init'])
    def test_omniorb_init(self):
        self.run_command('nameclt list')

    @OEHasPackage(['omniorb'])
    def test_omniorb_user_exists(self):
        self.run_command('id omniORB')
