package org.test.simple

import spock.lang.FailsWith
import spock.lang.Ignore
import spock.lang.IgnoreIf
import spock.lang.IgnoreRest
import spock.lang.Requires
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise
import spock.lang.Timeout
import spock.lang.Unroll

@Stepwise
class AnnotationsSpec extends Specification {

    @Ignore("fails every time :(")
    @Timeout(2)
    def "timeout"() {
        println '1st test'
        expect:
        Thread.sleep(3000) // #Groovy: no try-catch block in tests!
    }

//    @IgnoreRest //ignore other tests in class
//    @IgnoreIf({true}) // #Groovy: dynamic value in annotations
//    @Requires({false})
    def "ignore annotations"() {
        println '2nd test'
        expect:
        true
    }

    @FailsWith(RuntimeException)
    def "fails with"() {
        throw new RuntimeException()
        expect:
        true
    }


    @Unroll
    def "unroll"() { //templates
        given:
        println "I'm the $n test"

        expect:
        true

        where:
        n << ['3rd', '4th']
    }

    /**
     * {@link WhereLabelPowerSpec}
     * {@link org.test}
     */
}