package com.demo.util


import spock.lang.Specification

import java.time.LocalDateTime

class DateTimeUtilTest extends Specification {
    DateTimeUtil dateTimeUtil = new DateTimeUtil()

    def "test get As UTC Time Stamp"() {
        when:
        String result = dateTimeUtil.getAsUTCTimeStamp(LocalDateTime.of(2019, 04, 4, 04, 04, 04))

        then:
        result == "2019-04-04T04:04:04.000Z"
    }
}