package org

import org.model.SmokeModel
import spock.lang.Specification

import static org.hamcrest.Matchers.nullValue
import static spock.util.matcher.HamcrestSupport.that

class SpockSmokeSpec extends Specification {
    def "Spock should mock w/o default constructor"() {
        given:
        def object = new SmokeModel(0)
        def mock = Mock(SmokeModel) {
            printSmth() >> 'I can print from mock!'
        }

        expect:
        that object.printSmth(), nullValue()
        mock.printSmth() == 'I can print from mock!'
    }
}