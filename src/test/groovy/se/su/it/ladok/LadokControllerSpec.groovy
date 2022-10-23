package se.su.it.ladok

import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification

class LadokControllerSpec extends Specification implements ControllerUnitTest<LadokController> {

    def setup() {
        controller.configurationService = Mock(ConfigurationService)
        controller.ladokService = Mock(LadokService)
    }

    def cleanup() {
    }

    void "test something useful"() {
        given:
        when:
        1+2
        then:
        1<2
    }
}
