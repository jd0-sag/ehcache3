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

package org.ehcache.impl.internal.statistics;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.Status;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.ehcache.config.units.MemoryUnit.MB;

/**
 * @author Henri Tremblay
 */
public class DefaultStatisticsServiceTest {

  private static final String CACHE = "myCache";

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  private final DefaultStatisticsService service = new DefaultStatisticsService();
  private CacheManager cacheManager;

  @Before
  public void before() {
    CacheConfigurationBuilder<Long, String> cacheConfiguration =
      CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class,
        ResourcePoolsBuilder.newResourcePoolsBuilder()
          .heap(1, MB));

    cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
      .using(service)
      .withCache(CACHE, cacheConfiguration)
      .build();
  }

  @Test
  public void startStopStart() throws Exception {
    cacheManager.init();

    assertThat(service.isStarted()).isTrue();

    Cache<Long, String> cache = cacheManager.getCache(CACHE, Long.class, String.class);
    cache.get(2L);
    assertThat(service.getCacheMisses(CACHE)).isEqualTo(1);

    cacheManager.close();

    assertThat(service.isStarted()).isFalse();

    cacheManager.init();

    assertThat(service.isStarted()).isTrue();

    // We expect the stats to be reinitialized after a stop start
    assertThat(service.getCacheMisses(CACHE)).isEqualTo(0);
    cache = cacheManager.getCache(CACHE, Long.class, String.class);
    cache.get(2L);
    assertThat(service.getCacheMisses(CACHE)).isEqualTo(1);
  }

  @Test
  public void startInMaintenance() throws Exception {
    expectedException.expect(IllegalStateException.class);
    service.stateTransition(Status.UNINITIALIZED, Status.MAINTENANCE);
  }

  @Test
  public void clear() throws Exception {

  }

  @Test
  public void getCacheHits() throws Exception {

  }

  @Test
  public void getCacheHitPercentage() throws Exception {

  }

  @Test
  public void getCacheMisses() throws Exception {

  }

  @Test
  public void getCacheMissPercentage() throws Exception {

  }

  @Test
  public void getCacheGets() throws Exception {

  }

  @Test
  public void getCachePuts() throws Exception {

  }

  @Test
  public void getCacheRemovals() throws Exception {

  }

  @Test
  public void getCacheEvictions() throws Exception {

  }

  @Test
  public void getAverageGetTime() throws Exception {

  }

  @Test
  public void getAveragePutTime() throws Exception {

  }

  @Test
  public void getAverageRemoveTime() throws Exception {

  }

}
