from _common import RHRuntimeTestCase
from oeqa.core.decorator.depends import OETestDepends
from oeqa.runtime.decorator.package import OEHasPackage

class OmniEvents(RHRuntimeTestCase):
    @OETestDepends(['omniorb.OmniORB.test_omniorb_init'])
    @OEHasPackage(['omnievents-init'])
    def test_omnievents_init(self):
        self.run_command('nameclt resolve EventChannelFactory')
