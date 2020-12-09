package com.sebczu.poc.reactive;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;

@Slf4j
@SpringBootTest
public class DisposableTest {

  @Test
  public void disposable() throws InterruptedException {
    Flux<String> publisher = Flux.just("a", "bb", "ccc")
        .delayElements(Duration.ofSeconds(1));

    Disposable disposable = publisher
        .subscribe(string -> {
          log.info(string);
        }, error -> {
          log.error("error: ", error);
        }, () -> {
          log.info("complete");
        });

    Thread.sleep(1500);
    log.info("dispose");
    disposable.dispose();
  }

}
