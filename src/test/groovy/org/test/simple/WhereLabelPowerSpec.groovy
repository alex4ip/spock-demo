package org.test.simple

import groovy.sql.Sql
import spock.lang.Specification
import spock.lang.*

//@Unroll("#name:#expected")
class WhereLabelPowerSpec extends Specification {

    @Unroll
    def "array where block"() {
        expect:
        name.length() == expected
        where:
        [name, expected] << [['Kirk', 4], ['McCoy', 5], ['Spock', 5]]
    }

    @Unroll
    def "arrays where block"() {
        expect:
        name.length() == expected
        where:
        name << ['Kirk', 'McCoy', 'Spock']
        expected << [4, 5, 5]
    }

    @Unroll
    def "table where block"() {
        expect:
        name.length() == expected
        where:
        name    || expected
        'Kirk'  || 4
        'McCoy' || 5
        'Spock' || 5
    }

    @Shared def sql

    def setupSpec() {
        sql = Sql.newInstance('jdbc:hsqldb:mem:testDB')
        sql.execute '''
             create table test_crew (
                 id IDENTITY,
                 name varchar(10),
                 expected integer,
             );'''
        sql.execute '''insert into test_crew(name, expected) VALUES('Kirk', 4);'''
        sql.execute '''insert into test_crew(name, expected) VALUES('McCoy', 5);'''
        sql.execute '''insert into test_crew(name, expected) VALUES('Spock', 5);'''
    }

    @Unroll
    def "sql sourced were block; #name"() {
        expect:
        name.length() == expected

        where:
        [name, expected] << sql.rows("select name, expected from test_crew")
    }

    //@Unroll on class







    /**
     ██████╗  █████╗ ██████╗ ████████╗    ██████╗
     ██╔══██╗██╔══██╗██╔══██╗╚══██╔══╝    ╚════██╗
     ██████╔╝███████║██████╔╝   ██║        █████╔╝
     ██╔═══╝ ██╔══██║██╔══██╗   ██║       ██╔═══╝
     ██║     ██║  ██║██║  ██║   ██║       ███████╗
     ╚═╝     ╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝       ╚══════╝
     * {@link org.test.advanced.HamcrestSpec}
     */
}