package org.test.advanced.macleod

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target


class ConnorSpec extends MacLeodClan {
    @Override
    def whoAmI() { 'Connor MacLeod' }

    @ConnorMacLeod
    def "Connor MacLeod"() {
        expect:
        println cry()
    }
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface ConnorMacLeod {}

/**
 * {@link org.test.advanced.InteractionsSpec}
 */