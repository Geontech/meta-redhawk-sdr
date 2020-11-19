from _common import RHRuntimeTestCase, RH_FILES_DIR
from oeqa.core.decorator.depends import OETestDepends
from oeqa.runtime.decorator.package import OEHasPackage
import os

class Sandbox(RHRuntimeTestCase):
    temp_sandbox = '/tmp/sandbox'
    @classmethod
    def setUp(cls):
        src = os.path.join(RH_FILES_DIR, 'sandbox')
        cls.tc.target.run('mkdir -p %s' % Sandbox.temp_sandbox)
        cls.tc.target.copyDirTo(src, Sandbox.temp_sandbox)

    @classmethod
    def tearDown(cls):
        cls.tc.target.run('rm -rf  %s' % Sandbox.temp_sandbox)

    def run_script(self, script):
        self.run_command('bash -lc "python2 %s/%s"' % (Sandbox.temp_sandbox, script))
    
    def run_sandbox_component(self, component):
        self.run_script('component.py ' + component)

    @OEHasPackage(['redhawk-python'])
    def test_basic_import(self):
        self.run_script('basic_import.py')

    @OETestDepends(['redhawk.Sandbox.test_basic_import'])
    @OEHasPackage(['redhawk-python'])
    def test_helpers(self):
        self.run_script('helpers.py')

    @OETestDepends(['redhawk.Sandbox.test_basic_import'])
    @OEHasPackage(['rh-agc'])
    def test_component_agc(self):
        self.run_sandbox_component('rh.agc')

    @OETestDepends(['redhawk.Sandbox.test_basic_import'])
    @OEHasPackage(['rh-amfmpmbasebanddemod'])
    def test_component_amfmpmbasebanddemod(self):
        self.run_sandbox_component('rh.AmFmPmBasebandDemod')

    @OETestDepends(['redhawk.Sandbox.test_basic_import'])
    @OEHasPackage(['rh-arbitraryrateresampler'])
    def test_component_arbitraryrateresampler(self):
        self.run_sandbox_component('rh.ArbitraryRateResampler')

    @OETestDepends(['redhawk.Sandbox.test_basic_import'])
    @OEHasPackage(['rh-autocorrelate'])
    def test_component_autocorrelate(self):
        self.run_sandbox_component('rh.autocorrelate')

    @OETestDepends(['redhawk.Sandbox.test_basic_import'])
    @OEHasPackage(['rh-dataconverter'])
    def test_component_dataconverter(self):
        self.run_sandbox_component('rh.DataConverter')

    @OETestDepends(['redhawk.Sandbox.test_basic_import'])
    @OEHasPackage(['rh-fastfilter'])
    def test_component_fastfilter(self):
        self.run_sandbox_component('rh.fastfilter')

    @OETestDepends(['redhawk.Sandbox.test_basic_import'])
    @OEHasPackage(['rh-filereader'])
    def test_component_filereader(self):
        self.run_sandbox_component('rh.FileReader')

    @OETestDepends(['redhawk.Sandbox.test_basic_import'])
    @OEHasPackage(['rh-filewriter'])
    def test_component_filewriter(self):
        self.run_sandbox_component('rh.FileWriter')

    @OETestDepends(['redhawk.Sandbox.test_basic_import'])
    @OEHasPackage(['rh-hardlimit'])
    def test_component_hardlimit(self):
        self.run_sandbox_component('rh.HardLimit')

    @OETestDepends(['redhawk.Sandbox.test_basic_import'])
    @OEHasPackage(['rh-psd'])
    def test_component_psd(self):
        self.run_sandbox_component('rh.psd')

    @OETestDepends(['redhawk.Sandbox.test_basic_import'])
    @OEHasPackage(['rh-psk-soft'])
    def test_component_psk_soft(self):
        self.run_sandbox_component('rh.psk_soft')

    @OETestDepends(['redhawk.Sandbox.test_basic_import'])
    @OEHasPackage(['rh-rbdsdecoder'])
    def test_component_rbdsdecoder(self):
        self.run_sandbox_component('rh.RBDSDecoder')

    @OETestDepends(['redhawk.Sandbox.test_basic_import'])
    @OEHasPackage(['rh-siggen'])
    def test_component_siggen(self):
        self.run_sandbox_component('rh.SigGen')

    @OETestDepends(['redhawk.Sandbox.test_basic_import'])
    @OEHasPackage(['rh-sinksdds'])
    def test_component_sinksdds(self):
        self.run_sandbox_component('rh.SinkSDDS')

    @OETestDepends(['redhawk.Sandbox.test_basic_import'])
    @OEHasPackage(['rh-sinksocket'])
    def test_component_sinksocket(self):
        self.run_sandbox_component('rh.sinksocket')

    @OETestDepends(['redhawk.Sandbox.test_basic_import'])
    @OEHasPackage(['rh-sinkvita49'])
    def test_component_sinkvita49(self):
        self.run_sandbox_component('rh.SinkVITA49')

    @OETestDepends(['redhawk.Sandbox.test_basic_import'])
    @OEHasPackage(['rh-sourcesdds'])
    def test_component_sourcesdds(self):
        self.run_sandbox_component('rh.SourceSDDS')

    @OETestDepends(['redhawk.Sandbox.test_basic_import'])
    @OEHasPackage(['rh-sourcesocket'])
    def test_component_sourcesocket(self):
        self.run_sandbox_component('rh.sourcesocket')

    @OETestDepends(['redhawk.Sandbox.test_basic_import'])
    @OEHasPackage(['rh-sourcevita49'])
    def test_component_sourcevita49(self):
        self.run_sandbox_component('rh.SourceVITA49')

    @OETestDepends(['redhawk.Sandbox.test_basic_import'])
    @OEHasPackage(['rh-tunefilterdecimate'])
    def test_component_tunefilterdecimate(self):
        self.run_sandbox_component('rh.TuneFilterDecimate')

class Runtime(RHRuntimeTestCase):
    temp_runtime = '/tmp/runtime'

    @classmethod
    def setUp(cls):
        src = os.path.join(RH_FILES_DIR, 'runtime')
        cls.tc.target.run('mkdir -p %s' % Runtime.temp_runtime)
        cls.tc.target.copyDirTo(src, Runtime.temp_runtime)

    @classmethod
    def tearDown(cls):
        cls.tc.target.run('rm -rf  %s' % Runtime.temp_runtime)

    def run_script(self, script):
        self.run_command('bash -lc "python2 %s/%s"' % (Runtime.temp_runtime, script))
    
    def run_component(self, component):
        self.run_script('component.py ' + component)

    @OETestDepends(['omniorb.OmniORB.test_omniorb_init'])
    @OETestDepends(['omnievents.OmniEvents.test_omnievents_init'])
    @OEHasPackage(['domain-init'])
    def test_domain_init(self):
        self.run_command('nameclt list %s' % self.td.get('REDHAWK_DOMAIN'))

    @OEHasPackage(['redhawk-python'])
    def test_basic_import(self):
        self.run_script('basic_import.py')

    @OETestDepends(['redhawk.Runtime.test_basic_import'])
    @OETestDepends(['redhawk.Runtime.test_domain_init'])
    @OEHasPackage(['gpp'])
    @OEHasPackage(['rh-agc'])
    def test_component_agc(self):
        self.run_component('rh.agc')

    @OETestDepends(['redhawk.Runtime.test_basic_import'])
    @OETestDepends(['redhawk.Runtime.test_domain_init'])
    @OEHasPackage(['gpp'])
    @OEHasPackage(['rh-amfmpmbasebanddemod'])
    def test_component_amfmpmbasebanddemod(self):
        self.run_component('rh.AmFmPmBasebandDemod')

    @OETestDepends(['redhawk.Runtime.test_basic_import'])
    @OETestDepends(['redhawk.Runtime.test_domain_init'])
    @OEHasPackage(['gpp'])
    @OEHasPackage(['rh-arbitraryrateresampler'])
    def test_component_arbitraryrateresampler(self):
        self.run_component('rh.ArbitraryRateResampler')

    @OETestDepends(['redhawk.Runtime.test_basic_import'])
    @OETestDepends(['redhawk.Runtime.test_domain_init'])
    @OEHasPackage(['gpp'])
    @OEHasPackage(['rh-autocorrelate'])
    def test_component_autocorrelate(self):
        self.run_component('rh.autocorrelate')

    @OETestDepends(['redhawk.Runtime.test_basic_import'])
    @OETestDepends(['redhawk.Runtime.test_domain_init'])
    @OEHasPackage(['gpp'])
    @OEHasPackage(['rh-dataconverter'])
    def test_component_dataconverter(self):
        self.run_component('rh.DataConverter')

    @OETestDepends(['redhawk.Runtime.test_basic_import'])
    @OETestDepends(['redhawk.Runtime.test_domain_init'])
    @OEHasPackage(['gpp'])
    @OEHasPackage(['rh-fastfilter'])
    def test_component_fastfilter(self):
        self.run_component('rh.fastfilter')

    @OETestDepends(['redhawk.Runtime.test_basic_import'])
    @OETestDepends(['redhawk.Runtime.test_domain_init'])
    @OEHasPackage(['gpp'])
    @OEHasPackage(['rh-filereader'])
    def test_component_filereader(self):
        self.run_component('rh.FileReader')

    @OETestDepends(['redhawk.Runtime.test_basic_import'])
    @OETestDepends(['redhawk.Runtime.test_domain_init'])
    @OEHasPackage(['gpp'])
    @OEHasPackage(['rh-filewriter'])
    def test_component_filewriter(self):
        self.run_component('rh.FileWriter')

    @OETestDepends(['redhawk.Runtime.test_basic_import'])
    @OETestDepends(['redhawk.Runtime.test_domain_init'])
    @OEHasPackage(['gpp'])
    @OEHasPackage(['rh-hardlimit'])
    def test_component_hardlimit(self):
        self.run_component('rh.HardLimit')

    @OETestDepends(['redhawk.Runtime.test_basic_import'])
    @OETestDepends(['redhawk.Runtime.test_domain_init'])
    @OEHasPackage(['gpp'])
    @OEHasPackage(['rh-psd'])
    def test_component_psd(self):
        self.run_component('rh.psd')

    @OETestDepends(['redhawk.Runtime.test_basic_import'])
    @OETestDepends(['redhawk.Runtime.test_domain_init'])
    @OEHasPackage(['gpp'])
    @OEHasPackage(['rh-psk-soft'])
    def test_component_psk_soft(self):
        self.run_component('rh.psk_soft')

    @OETestDepends(['redhawk.Runtime.test_basic_import'])
    @OETestDepends(['redhawk.Runtime.test_domain_init'])
    @OEHasPackage(['gpp'])
    @OEHasPackage(['rh-rbdsdecoder'])
    def test_component_rbdsdecoder(self):
        self.run_component('rh.RBDSDecoder')

    @OETestDepends(['redhawk.Runtime.test_basic_import'])
    @OETestDepends(['redhawk.Runtime.test_domain_init'])
    @OEHasPackage(['gpp'])
    @OEHasPackage(['rh-siggen'])
    def test_component_siggen(self):
        self.run_component('rh.SigGen')

    @OETestDepends(['redhawk.Runtime.test_basic_import'])
    @OETestDepends(['redhawk.Runtime.test_domain_init'])
    @OEHasPackage(['gpp'])
    @OEHasPackage(['rh-sinksdds'])
    def test_component_sinksdds(self):
        self.run_component('rh.SinkSDDS')

    @OETestDepends(['redhawk.Runtime.test_basic_import'])
    @OETestDepends(['redhawk.Runtime.test_domain_init'])
    @OEHasPackage(['gpp'])
    @OEHasPackage(['rh-sinksocket'])
    def test_component_sinksocket(self):
        self.run_component('rh.sinksocket')

    @OETestDepends(['redhawk.Runtime.test_basic_import'])
    @OETestDepends(['redhawk.Runtime.test_domain_init'])
    @OEHasPackage(['gpp'])
    @OEHasPackage(['rh-sinkvita49'])
    def test_component_sinkvita49(self):
        self.run_component('rh.SinkVITA49')

    @OETestDepends(['redhawk.Runtime.test_basic_import'])
    @OETestDepends(['redhawk.Runtime.test_domain_init'])
    @OEHasPackage(['gpp'])
    @OEHasPackage(['rh-sourcesdds'])
    def test_component_sourcesdds(self):
        self.run_component('rh.SourceSDDS')

    @OETestDepends(['redhawk.Runtime.test_basic_import'])
    @OETestDepends(['redhawk.Runtime.test_domain_init'])
    @OEHasPackage(['gpp'])
    @OEHasPackage(['rh-sourcesocket'])
    def test_component_sourcesocket(self):
        self.run_component('rh.sourcesocket')

    @OETestDepends(['redhawk.Runtime.test_basic_import'])
    @OETestDepends(['redhawk.Runtime.test_domain_init'])
    @OEHasPackage(['gpp'])
    @OEHasPackage(['rh-sourcevita49'])
    def test_component_sourcevita49(self):
        self.run_component('rh.SourceVITA49')

    @OETestDepends(['redhawk.Runtime.test_basic_import'])
    @OETestDepends(['redhawk.Runtime.test_domain_init'])
    @OEHasPackage(['gpp'])
    @OEHasPackage(['rh-tunefilterdecimate'])
    def test_component_tunefilterdecimate(self):
        self.run_component('rh.TuneFilterDecimate')