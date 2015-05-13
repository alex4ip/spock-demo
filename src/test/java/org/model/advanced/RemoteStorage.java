package org.model.advanced;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public interface RemoteStorage {

    Data saveData(String message);

    List<Data> getShardData(long shardNumber);

    ServerInfo getServerInfo();

    class Data {
        private long id;
        private long shard;
        private String message;

        public long getId() { return id; }

        public long getShard() { return shard; }

        public String getMessage() { return message; }
    }

    class ServerInfo {
        private long shardNumber;
        private List<ServerInfo> shards;

        public long getShardNumber() { return shardNumber; }

        public List<ServerInfo> getShards() { return shards; }
    }

    class DataService {
        RemoteStorage remoteStorage; //suppose it is injected by DI

        public Map<Long, List<Data>> storeSomeUserData(String... messages) {
            //do some validation and business logic
            Map<Long, List<Data>> shardToData = Arrays.stream(messages)
                    .map(remoteStorage::saveData)
                    .collect(groupingBy(Data::getShard));

            //and little more...
            return shardToData;
        }

        public Map<Long, List<Data>> getDataByShards(Predicate<Long> predicate) {
            ServerInfo serverInfo = remoteStorage.getServerInfo();
            List<Long> shardNumbers = serverInfo.getShards().stream()
                    .map(ServerInfo::getShardNumber)
                    .collect(toList());

            shardNumbers.add(serverInfo.getShardNumber());

            return shardNumbers.stream()
                    .filter(predicate)
                    .collect(toMap(i -> i, remoteStorage::getShardData)); //there is post-processing of returned values
        }
    }
}
