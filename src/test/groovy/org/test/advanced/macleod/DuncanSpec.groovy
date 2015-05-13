package org.test.advanced.macleod

class DuncanSpec extends MacLeodClan {

    @Override
    def whoAmI() { 'Duncan MacLeod' }

    def "Duncan MacLeod"() {
        expect:
        println cry()
    }
}