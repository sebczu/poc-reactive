package com.sebczu.poc.reactive;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

@Slf4j
@SpringBootTest
public class MapTest {

  @Test
  public void map() {
    Flux<String> publisher = Flux.just("a", "bb", "ccc");

    publisher.map(String::length)
        .subscribe(length -> log.info("length: {}", length));
  }

}
