package org.test.advanced

import spock.lang.Specification

import spock.lang.*
import static org.hamcrest.Matchers.*
import static spock.util.matcher.HamcrestSupport.*

class HamcrestSpec extends Specification {
    def "hamcrest support"() {
        given:
        def list = [2, 4]

        when:
        list.add(Integer.MAX_VALUE)

        then:
        expect list[2], notNullValue(Integer) // #Groovy: Optional parenthesis
        expect list, allOf(hasSize(3), hasItem(Integer.MAX_VALUE))

//        expect:
//        that list, everyItem(lessThan(5))
//        list.every { it < 5 } // #jdk8: Stream.allMatch(Predicate<? super T> predicate);
    }

    /**
     * {@link org.test.advanced.macleod.MacLeodClan}
     * {@link org.test}
     */
}