package org.test.simple

import spock.lang.Specification
import spock.lang.Unroll

import java.util.concurrent.atomic.AtomicInteger

class HelloSpockSpec extends Specification {
    def "naturally readable test name"() {
        given: //or setup alias 
        def phrase = 'hello'

        when:
        def actual = phrase + ' Spock'

        then: //or expect alias 
        actual.length() == 11
        actual.contains(' ')
        //conditions 
        //exceptions 
        //interactions (described bellow)  

        expect:
        (phrase + ' Spock').length() == 11

        cleanup:
        actual = null
        //is run even if the method 
        //has produced an exception  

        where: //test parametrisation 
        [a, b] << [[1, 2]]
    }

    static class PhraseBook {
        Map<String, String> content;

        public PhraseBook() {
            this.content = new HashMap<>();
            this.content.put("kirk", "Beam me up, Scotty!");
            this.content.put("mccoy", "He is dead, Jim!");
            this.content.put("scotty", "We need more power, Captain!");
            this.content.put("spock", "Live long and prosper!");
        }

        public String getPhraseOf(String person) {
            return content.getOrDefault(person.toLowerCase(), "<n/a>");
        }
    }

    @Unroll
    def "plain old hello Spock"() {
        given:
        def phraseBook = new PhraseBook()

        expect:
        phraseBook.getPhraseOf(person) == catchphrase

        cleanup:
        println catchphrase

        where:
        person   | catchphrase
        'Kirk'   | 'Beam me up, Scotty!'
        'McCoy'  | 'He is dead, Jim!'
        'Scotty' | 'We need more power, Captain!'
        'Spock'  | 'Live long and prosper!'
        'Gorn'   | '<n/a>'
    }

    /**
     * {@link HelpersSpec}
     * {@link org.test}
     */
}