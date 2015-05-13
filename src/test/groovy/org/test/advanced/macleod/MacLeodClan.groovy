package org.test.advanced.macleod

import spock.lang.Specification

class MacLeodClan extends Specification {

    def whoAmI() { 'MacLeod' }

    def cry() { "${whoAmI()}: There can be only one" }
}
