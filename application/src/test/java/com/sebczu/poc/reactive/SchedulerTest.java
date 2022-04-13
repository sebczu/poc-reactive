package com.sebczu.poc.reactive;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
@SpringBootTest
public class SchedulerTest {

  @Test
  public void scheduler() {
    Flux<String> publisher = Flux.just("a", "b", "c");

    publisher
      .parallel(3).runOn(Schedulers.newParallel("custom-parallel", 3))
      .map(str -> {
        log.info("(map1) begin in thread: {} string {}", Thread.currentThread().getName(), str);
        sleep(50);
        log.info("(map1) end in thread: {} string {}", Thread.currentThread().getName(), str);
        return str;
      })
      .map(str -> {
        log.info("(map2) begin in thread: {} string {}", Thread.currentThread().getName(), str);
        sleep(75);
        log.info("(map2) end in thread: {} string {}", Thread.currentThread().getName(), str);
        return str;
      })
      .sequential().publishOn(Schedulers.newSingle("custom-single"))
      .map(str -> {
        log.info("(map3) begin in thread: {} string {}", Thread.currentThread().getName(), str);
        sleep(75);
        log.info("(map3) end in thread: {} string {}", Thread.currentThread().getName(), str);
        return str;
      })
      .blockLast();
  }

  @Test
  public void scheduler2() {
    Flux<String> publisher = Flux.just("a", "b", "c");

    publisher
      .map(str -> {
        log.info("(map1) begin in thread: {} string {}", Thread.currentThread().getName(), str);
        sleep(50);
        log.info("(map1) end in thread: {} string {}", Thread.currentThread().getName(), str);
        return str;
      })
      .map(str -> {
        log.info("(map2) begin in thread: {} string {}", Thread.currentThread().getName(), str);
        sleep(75);
        log.info("(map2) end in thread: {} string {}", Thread.currentThread().getName(), str);
        return str;
      })
      .subscribeOn(Schedulers.newSingle("custom-single"))
      .blockLast();
  }

  @SneakyThrows
  private void sleep(long millis) {
    Thread.sleep(millis);
  }

}
