package com.undancer.breath.core.util

import spock.lang.Specification

/**
 * Created by undancer on 14-4-17.
 */
class RequestUtilsTest extends Specification {

    def request

    void setup() {
        println "setup"
    }

    def "GetRequest"() {
        when:
        request = RequestUtils.getRequest()
        then:
        request == null
    }

    def "GetStringParameter"() {

    }

    def "SetApplicationContext"() {

    }
}
