package com.demo.logging


import spock.lang.Specification
import spock.lang.Unroll

class DataMaskerTest extends Specification {
    DataMasker dataMasker = new DataMasker()


    @Unroll
    def "test maskJsonFields"() {
        expect:
        dataMasker.maskJsonFields(jsonString) == expectedJson

        where:
        jsonString                                                                   | expectedJson
        '{"customerAccessToken":"xvyz","authorisation":"abcd","doNotChange":"1234"}' | '{"customerAccessToken":"XXX","authorisation":"XXX","doNotChange":"1234"}'
        '{"customerAccessToken":"xvyz"}'                                             | '{"customerAccessToken":"XXX"}'
        '{"authorisation":"xvyz"}'                                                   | '{"authorisation":"XXX"}'
        '{"doNotChange":"xvyz"}'                                                     | '{"doNotChange":"xvyz"}'
        '{"customerAccessToken xvyz"}'                                               | '{"customerAccessToken xvyz"}'
        null                                                                         | 'null'

    }


    @Unroll
    def "test maskHeaderValue"() {
        expect:
        dataMasker.maskHeaderValue(header, value) == expectedValue

        where:
        header          | value                | expectedValue
        'Authorization' | 'to be replaced'     | 'XXX'
        'some header'   | 'not to be replaced' | 'not to be replaced'
        'some header'   | null                 | 'null'
    }
}
