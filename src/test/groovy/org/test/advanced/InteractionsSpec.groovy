package org.test.advanced

import org.spockframework.mock.CannotInvokeRealMethodException
import spock.lang.Specification


class InteractionsSpec extends Specification {
    interface SomeType {
        String getId();

        String setData(Object data);
    }

    def "default syntax"() {
        given:
        def mock1 = Mock(SomeType)
        SomeType mock2 = Mock()

        def spy1 = Spy(SomeType)
        SomeType spy2 = Spy()

        expect:
        mock1.getId() == null
        mock2.getId() == null

        when:
        spy1.getId()

        then:
        thrown(CannotInvokeRealMethodException)
    }

    /**
     * 1 * mock.setData('data')
     * |   |    |        |
     * |   |    |        argument constraint
     * |   |    method constraint
     * |   target constraint
     * cardinality
     */
    def "cardinality and interaction syntax"() {
        given:
        def mock = Mock(SomeType)

        when:
        mock.getId()
        mock.id // #Groovy: property
        mock.setData('data')

        then:
        2 * mock.getId()
        1 * mock.setData('data')
    }

    def "constarint examples"() {
        def mock = Mock(SomeType)

        //cardinality
        1 * mock.setData('hello')      // exactly one call
        0 * mock.setData('hello')      // zero calls
        (1..3) * mock.setData('hello') // between one and three calls (inclusive)
        (1.._) * mock.setData('hello') // at least one call
        (_..3) * mock.setData('hello') // at most three calls
        _ * mock.setData('hello')      // any number of calls, including zero

        //target
        1 * mock.setData('hello') // a call to 'mock'
        1 * _.setData('hello')    // a call to any mock object

        //method
        1 * mock.setData('hello') // a method named 'setData'
        1 * mock./s.*a/('hello')  // a method whose name matches the given regular expression

        //arguments
        1 * mock.setData('hello')           // an argument that is equal to the String "hello"
        1 * mock.setData(!'hello')          // an argument that is unequal to the String "hello"
        1 * mock.setData()                  // the empty argument list (would never match in our example)
        1 * mock.setData(_)                 // any single argument (including null)
        1 * mock.setData(*_)                // any argument list (including the empty argument list)
        1 * mock.setData(!null)             // any non-null argument
        1 * mock.setData(_ as String)       // any non-null argument that is-a String
        1 * mock.setData({ it.size() > 3 }) // an argument that satisfies the given predicate

        //cath them all!
        1 * mock._(*_)  // any method on mock, with any argument list
        1 * mock._      // shortcut for and preferred over the above

        1 * _._         // any method call on any mock object
        1 * _           // shortcut for and preferred over the above

        //all or nothing
        _ * mock._       // allow any interaction with 'mock'
        0 * _            // don't allow any other interaction
    }

    // Stubs

    def "right shift (>>) operator"() {
        given:
        def mock = Mock(SomeType)
        mock.getId() >> 'resultID' //<-- return value isn't a constraint

        expect:
        mock.getId() == 'resultID'
        mock.getId() == 'resultID'
    }

    def "tripple right shift (>>>) operator"() {
        given:
        def mock = Mock(SomeType)
        mock.getId() >>> ['resultID', 'anotherID']

        expect:
        mock.getId() == 'resultID'
        mock.getId() == 'anotherID'
        mock.getId() == 'anotherID'
    }

    def "hybrid stubbing"() {
        given:
        def mock = Mock(SomeType)
        mock.getId() >>> ['resultID', 'anotherID'] >> { throw new RuntimeException() }

        expect:
        mock.getId() == 'resultID'
        mock.getId() == 'anotherID'

        when:
        mock.getId()

        then:
        thrown(RuntimeException)
    }

    /**
     * {@link org.test}
     */
}