package com.demo.api.rest.config.spring;


import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;

public enum CacheCatalog {

    STUDENTS_BY_ID {
        @Override
        public CacheConfiguration getCacheConfiguration() {
            return new CacheConfiguration()
                    .name(CacheCatalog.STUDENTS_BY_ID_NAME)
                    .maxEntriesLocalHeap(100)
                    .maxEntriesLocalDisk(500)
                    .timeToLiveSeconds(30 * 60)
                    .timeToIdleSeconds(20 * 60)
                    .memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.LFU);
        }
    };

    public static final String STUDENTS_BY_ID_NAME = "studentsById";

    public abstract CacheConfiguration getCacheConfiguration();

}
