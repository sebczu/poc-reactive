package com.sebczu.poc.reactive;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;

@Slf4j
@SpringBootTest
public class SubscribeTest {


  @Test
  public void subscribe() {
    Mono<String> publisher = Mono.just("test");

    publisher
        .map(string -> {
          log.info("thread: " + Thread.currentThread().getName());
          log.info(string);
          return string;
        })
        .subscribe();
  }

}
