package com.sebczu.poc.reactive;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Slf4j
@SpringBootTest
public class BufferTest {

  /**
   * --a--b--c--
   * buffer
   * --------[a,b,c]--
   */
  @Test
  public void buffer() {
    Flux<String> publisher = Flux.just("a", "b", "c");

    publisher.buffer()
        .subscribe(buffered -> log.info("buffered: {}", buffered));
  }

  /**
   * --a--b----c--d--------e--
   * buffer
   * ----[a,b]----[c,d]---[e]--
   */
  @Test
  public void buffer2() {
    Flux<String> publisher = Flux.just("a", "b", "c", "d", "e");

    publisher.buffer(2)
            .subscribe(buffered -> log.info("buffered: {}", buffered));
  }

  @Test
  public void buffer3() {
    Flux<String> publisher = Flux.just("a", "b", "c", "d", "e").delayElements(Duration.ofSeconds(1));

    publisher.buffer(Duration.ofMillis(2500))
            .doOnNext(buffered -> log.info("buffered: {}", buffered))
            .blockLast();
  }

  @Test
  public void buffer4() {
    Flux<String> publisher = Flux.just("a", "b", "c", "d", "e").delayElements(Duration.ofSeconds(1));

    publisher.buffer(Duration.ofMillis(700), Duration.ofMillis(1500))
            .doOnNext(buffered -> log.info("buffered: {}", buffered))
            .blockLast();
  }

}
