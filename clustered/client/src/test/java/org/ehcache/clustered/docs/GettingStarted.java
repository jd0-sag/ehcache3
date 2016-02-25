/*
 * Copyright Terracotta, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ehcache.clustered.docs;

import org.ehcache.clustered.ClusteredCacheManager;
import org.ehcache.clustered.config.ClusteringServiceConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.junit.Test;

import java.net.URI;

/**
 * Samples demonstrating use of a clustered cache.
 *
 * @author Clifford W. Johnson
 *
 * @see org.ehcache.docs.GettingStarted
 */
public class GettingStarted {

  @Test
  public void clusteredCacheManagerExample() throws Exception {
    // tag::clusteredCacheManagerExample
    final CacheManagerBuilder<ClusteredCacheManager> clusteredCacheManagerBuilder =
        CacheManagerBuilder.newCacheManagerBuilder()
            .with(new ClusteringServiceConfiguration(URI.create("http://localhost:9540")))
            .withCache("simple-cache", CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class)
                .withResourcePools(ResourcePoolsBuilder.newResourcePoolsBuilder()
                    .heap(10, EntryUnit.ENTRIES))
                .build());
    final ClusteredCacheManager cacheManager = clusteredCacheManagerBuilder.build(true);

    cacheManager.close();
    // end::clusteredCacheManagerExample
  }
}