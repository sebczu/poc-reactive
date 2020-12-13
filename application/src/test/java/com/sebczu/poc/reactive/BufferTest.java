package com.sebczu.poc.reactive;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

@Slf4j
@SpringBootTest
public class BufferTest {

  /**
   * --a--b--c--
   * buffer
   * --[a,b,c]--
   */
  @Test
  public void buffer() {
    Flux<String> publisher = Flux.just("a", "b", "c");

    publisher.buffer()
        .subscribe(buffered -> log.info("buffered: {}", buffered));
  }

}
