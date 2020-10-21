package com.sebczu.poc.reactive;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

@Slf4j
@SpringBootTest
public class ReactiveTest {

  @Autowired
  private StringSubscriber subscriber;

  @Test
  public void subscribeMono() {
    Mono<String> publisher = Mono.just("test");

    publisher.subscribe(subscriber);
  }

  @Test
  public void twoSubscribeMono() {
    Mono<String> publisher = Mono.just("test");

    publisher.subscribe(value -> log.info("value: {}", value));
    publisher.subscribe(value -> log.info("value: {}", value));
  }

  @Test
  public void doFirstDoFinallyMono() {
    Mono<String> publisher = Mono.just("test");

    publisher
        .doFirst(() -> log.info("do first"))
        .doFinally(signal -> log.info("do finally: " + signal))
        .subscribe(subscriber);
  }

  @Test
  public void disposeMono() {
    Mono<String> publisher = Mono.just("test");

    Disposable disposable = publisher
        .doFirst(() -> log.info("do first"))
        .doFinally(signal -> log.info("do finally: " + signal))
        .subscribe();

    disposable.dispose();
  }

  @Test
  public void subscribeMonoTest() {
    Mono<String> publisher = Mono.just("test");

    StepVerifier
        .create(publisher)
        .expectNext("test")
        .expectComplete()
        .verify();
  }

  @Test
  public void subscribeMonoNeverTest() {
    Mono<Void> publisher = Mono.never();

    StepVerifier
        .create(publisher)
        .expectTimeout(Duration.ofSeconds(1))
        .verify();
  }

  @Test
  public void subscribeMonoEmptyTest() {
    Mono<Void> publisher = Mono.empty();

    StepVerifier
        .create(publisher)
        .expectComplete()
        .verify();
  }

}
