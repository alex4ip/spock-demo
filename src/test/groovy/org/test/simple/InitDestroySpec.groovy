package org.test.simple

import spock.lang.Specification

class InitDestroySpec extends Specification {

    void setupSpec() { println 'setupSpec' } //@BeforeClass

    void setup() { println 'setup' } //@Before

    void cleanup() { println 'cleanup' } //@After

    void cleanupSpec() { println 'cleanupSpec' } //@AfterClass

    def "init-destroy 1"() {
        expect:
        println "test 1";
    }

    def "init-destroy 2"() {
        expect:
        println "test 2";
    }

    /**
     * {@link AnnotationsSpec}
     * {@link org.test}
     */
}