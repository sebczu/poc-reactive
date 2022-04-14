package com.sebczu.poc.reactive;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@SpringBootTest
public class LogTest {

  @Test
  public void log() {
    Flux<String> publisher = Flux.just("a", "b", "c", "d");

    publisher
      .log("1")
      .parallel(3)
      .log("2")
      .runOn(Schedulers.newParallel("custom-parallel", 3))
      .log("3")
      .flatMap(str -> {
        log.info("thread {}", Thread.currentThread().getName());
        sleep(50);
        return Mono.just(str);
      })
      .log("4")
      .subscribe(str -> log.info("subscribe {}", Thread.currentThread().getName()),
        error -> {},
        () -> {},
        subscription -> log.info("on subscribe: {}", subscription));

    sleep(2000);
  }

  @SneakyThrows
  private void sleep(long millis) {
    Thread.sleep(millis);
  }

}
