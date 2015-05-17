package org.test.simple

import spock.lang.Specification

import java.util.concurrent.atomic.AtomicInteger


class HelpersSpec extends Specification {
    def "how to catch exceptions"() {
        given:
        def ex = new IOException()

        when:
        throw ex

        then:
        def caught = thrown(IOException) // and more...
        caught == ex // #Groovy: equals
        caught.is(ex) // #Groovy: is
    }

    def "compare with init value example"() {
        given:
        def initInt = 42
        def atomicInt = new AtomicInteger(initInt)

        when:
        def actual = atomicInt.incrementAndGet()

        then:
        actual == initInt + 1
    }

    def "helper old"() {
        given:
        def atomicInt = new AtomicInteger(42)

        when:
        atomicInt.incrementAndGet()

        then:
        atomicInt.get() == old(atomicInt.get()) + 1
    }

    /**
     * {@link GroovyFeaturesSpec#rich_asserts()}
     * {@link org.test}
     */
}