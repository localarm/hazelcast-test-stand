package example;

import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

public class BadSQLGrammarMain {
    public static void main(String[] args) {
        Config config = new Config();

        MulticastConfig multicastConfig = new MulticastConfig();
        multicastConfig.setEnabled(false);
        AutoDetectionConfig autoDetectionConfig = new AutoDetectionConfig();
        autoDetectionConfig.setEnabled(false);
        JoinConfig joinConfig = new JoinConfig();
        joinConfig.setMulticastConfig(multicastConfig);
        joinConfig.setAutoDetectionConfig(autoDetectionConfig);
        NetworkConfig networkConfig = new NetworkConfig();
        networkConfig.setJoin(joinConfig);
        config.setNetworkConfig(networkConfig);

        MapConfig mapCfg = new MapConfig();
        mapCfg.setName("exampleCache");
        mapCfg.setBackupCount(0);
        mapCfg.setTimeToLiveSeconds(0);
        EvictionConfig evictionConfig = new EvictionConfig();
        evictionConfig.setEvictionPolicy(EvictionPolicy.LRU);
        evictionConfig.setSize(1000);
        evictionConfig.setMaxSizePolicy(MaxSizePolicy.PER_NODE);
        mapCfg.setEvictionConfig(evictionConfig);

        MapStoreConfig mapStoreCfg = new MapStoreConfig();
        mapCfg.setName("exampleCache");
        mapStoreCfg.setImplementation(new BadSQLMapLoader());
        mapStoreCfg.setEnabled(true);
        mapCfg.setMapStoreConfig(mapStoreCfg);
        config.addMapConfig(mapCfg);
        HazelcastInstance testInstance = Hazelcast.newHazelcastInstance(config);
        IMap<Integer, Long> exampleMap = testInstance.getMap("exampleCache");
        //exception here
        exampleMap.get(1);
    }

}
