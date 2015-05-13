package org.test.simple

import spock.lang.Specification


class GroovyFeaturesSpec extends Specification {

    def "groovy truth"() {
        expect:
        !null
        !''
        ![]
        ![:]
        !0
        and:
        new Object()
        'not blank string'
        [1]
        [key: null]
        1
    }

    def "rich asserts"() {
        given:
        def a = 10
        def b = 20
        def c = 30

        expect:
        1000 / ((a**2 + 25) / 10) == b * (c - 10) / 5
    }

    def "multiple assignment"() {
        given:
        def (a, b) = ['A', 'B']

        when:
        (a, b) = [b, a]

        then:
        a == 'B'
        b == 'A'
    }

    List<Float> currentLocation() {
        return [53.54, 27.34] //wikipedia.org/wiki/Minsk
    }

    def "destructuring aka tuple return"() {
        when:
        def (la, lo) = currentLocation()

        then:
        la == 53.54
        lo == 27.34
    }

    //java analog
    def "BigDecimal by default"() {
        expect:
        2.0 - 1.1 == 0.9
        3.0 - 1.1 == 1.9
        1.0.class == BigDecimal
    }

    def "closures"() {
        given:
        def closure = { it > 42 }
        def list = (1..10)*.multiply(10)

        when:
        def actual = list.findAll(closure)

        then:
        actual == (5..10)*.multiply(10)
    }
}