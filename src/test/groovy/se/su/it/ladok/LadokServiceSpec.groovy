package se.su.it.ladok

import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class LadokServiceSpec extends Specification implements ServiceUnitTest<LadokService>{

    def setup() {
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
