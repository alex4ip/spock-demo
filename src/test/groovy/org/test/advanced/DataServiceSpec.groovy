package org.test.advanced

import org.model.advanced.RemoteStorage
import org.model.advanced.RemoteStorage.Data;
import org.model.advanced.RemoteStorage.DataService;
import org.model.advanced.RemoteStorage.ServerInfo;
import spock.lang.Specification

import static org.hamcrest.Matchers.*
import static spock.util.matcher.HamcrestSupport.expect

class DataServiceSpec extends Specification {

    def "should validate and store data to correct hard"() {
        given:
        def id = 1L;

        def remoteService = Mock(RemoteStorage)
        def service = new DataService(remoteStorage: remoteService); // #Groovy: Constructors

        when:
        def actual = service.storeSomeUserData('data1', 'data2', 'data3')

        then:
        expect actual.keySet(), both(hasSize(2)).and(containsInAnyOrder(0L, 1L))
        3 * remoteService.saveData(_ as String) >> {
            new Data(id: id++, shard: id % 2, message: it[0])
        }
    }

    def "should return all shards data"() {
        given:
        def predcate = { 0 == it % 2 } // #Groovy: #Jdk8 interoperability
        def remoteService = Mock(RemoteStorage) {
            getServerInfo() >> {
                def shard1 = Mock(ServerInfo) {
                    getShardNumber() >> 1L
                }

                def shard2 = new ServerInfo(shardNumber: 2L)

                new ServerInfo(shards: [shard1, shard2])
            }

            getShardData(0L) >> []
            getShardData(2L) >> [new Data()]

            //region alternative variant
//            getShardData(_ as Long) >> {
//                switch (it[0]) {
//                    case 0L: []; break
//                    case 2L: [new Data()]; break
//                }
//            }
            //endregion
        }
        def service = new DataService(remoteStorage: remoteService);

        when:
        def actual = service.getDataByShards(predcate)

        then:
        expect actual.keySet(), both(hasSize(2)).and(containsInAnyOrder(0L, 2L))
        actual[0L].size() == 0
        actual[2L].size() == 1
    }
}