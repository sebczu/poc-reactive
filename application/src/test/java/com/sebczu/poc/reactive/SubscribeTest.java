package com.sebczu.poc.reactive;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

@Slf4j
@SpringBootTest
public class SubscribeTest {

  @Test
  public void subscribe() {
    Flux<String> publisher = Flux.just("test", "test2");

    publisher
        .map(string -> {
          log.info("thread: " + Thread.currentThread().getName());
          log.info(string);
          return string;
        })
        .subscribe();
  }

  @Test
  public void subscribe2() {
    Flux<String> publisher = Flux.just("test", "test2");

    publisher
        .subscribe(string -> {
          log.info("thread: " + Thread.currentThread().getName());
          log.info(string);
        });
  }

  @Test
  public void subscribe3() {
    Flux<String> publisher = Flux.just("test", "test2");

    publisher
        .subscribe(string -> {
          log.info("thread: " + Thread.currentThread().getName());
          log.info(string);
        }, error -> {
          log.info("thread: " + Thread.currentThread().getName());
          log.error("error: ", error);
        });
  }

}
