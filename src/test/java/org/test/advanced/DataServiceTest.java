package org.test.advanced;

import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.model.advanced.RemoteStorage;
import org.model.advanced.RemoteStorage.Data;
import org.model.advanced.RemoteStorage.DataService;
import org.model.advanced.RemoteStorage.ServerInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class DataServiceTest {

    @InjectMocks DataService service;

    @Mock RemoteStorage remoteStorage;

    @Before public void setUp() throws Exception { initMocks(this); }

    @Test
    public void testStoreSomeUserData() throws Exception {
        /*setup mocks behaviour*/
        AtomicLong id = new AtomicLong(1L);

        given(remoteStorage.saveData(any(String.class)))
                .willAnswer(invocation -> {
                    String msg = (String) invocation.getArguments()[0];

                    Data result = mock(Data.class);
                    when(result.getId()).thenReturn(id.getAndIncrement());
                    when(result.getShard()).thenReturn(id.get() % 2);
                    when(result.getMessage()).thenReturn(msg);

                    return result;
                });

        /*action to test*/
        Map<Long, List<Data>> actual = service.storeSomeUserData("data1", "data2", "data3");

        /*checks*/
        assertThat(actual.keySet(), both(hasSize(2)).and(containsInAnyOrder(0L, 1L)));
        verify(remoteStorage, times(3)).saveData(any(String.class));
    }

    @Test
    public void testGetDataByShards() throws Exception {
        /*setup mocks behaviour*/
        Predicate<Long> predicate = l -> 0 == l % 2;

        given(remoteStorage.getServerInfo())
                .willAnswer(invocation -> {
                    ServerInfo result = mock(ServerInfo.class);
                    when(result.getShardNumber()).thenReturn(0L);

                    ServerInfo shard1 = mock(ServerInfo.class);
                    when(shard1.getShardNumber()).thenReturn(1L);

                    ServerInfo shard2 = mock(ServerInfo.class);
                    when(shard2.getShardNumber()).thenReturn(2L);

                    List<ServerInfo> shards = new ArrayList<>();
                    shards.add(shard1);
                    shards.add(shard2);

                    when(result.getShards()).thenReturn(shards);

                    return result;
                });

        List<Data> dataFromShard0 = new ArrayList<>();
        given(remoteStorage.getShardData(0L)).willReturn(dataFromShard0);

        List<Data> dataFromShard2 = new ArrayList<>();
        dataFromShard2.add(new Data());
        given(remoteStorage.getShardData(2L)).willReturn(dataFromShard2);

        /*action to test*/
        Map<Long, List<Data>> actual = service.getDataByShards(predicate);

        /*checks*/
        assertThat(actual.keySet(), both(hasSize(2)).and(containsInAnyOrder(0L, 2L)));
        assertThat(actual.get(0L), hasSize(0));
        assertThat(actual.get(2L), hasSize(1));
    }
}