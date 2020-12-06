package com.sebczu.poc.reactive;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.util.context.Context;

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
          log.info(string);
        });
  }

  @Test
  public void subscribe3() {
    Flux<String> publisher = Flux.push(emitter -> {
      emitter.next("test");
      emitter.next("test2");
      emitter.error(new RuntimeException("error"));
    });

    publisher
        .subscribe(string -> {
          log.info(string);
        }, error -> {
          log.error("error: ", error);
        });
  }

  @Test
  public void subscribe4() {
    Flux<String> publisher = Flux.push(emitter -> {
      emitter.next("test");
      emitter.next("test2");
      emitter.error(new RuntimeException("error"));
    });

    publisher
        .subscribe(string -> {
          log.info(string);
        }, error -> {
          log.error("error: ", error);
        });
  }

  //complete() or error() terminate sequence
  @Test
  public void subscribe5() {
    Flux<String> publisher = Flux.push(emitter -> {
      emitter.next("test");
      emitter.next("test2");
      emitter.complete();
    });

    publisher
        .subscribe(string -> {
          log.info(string);
        }, error -> {
          log.error("error: ", error);
        }, () -> {
          log.info("complete");
        });
  }

  @Test
  public void subscribe6() {
    Flux<String> publisher = Flux.push(emitter -> {
      emitter.next("test");
      emitter.next("test2");
      emitter.error(new RuntimeException("error"));
    });

    publisher
        .subscribe(string -> {
          log.info(string);
        }, error -> {
          log.error("error: ", error);
        }, () -> {
          log.info("complete");
        });
  }

  @Test
  public void subscribe7() {
    Flux<String> publisher = Flux.push(emitter -> {
      emitter.next("test");
      emitter.next("test2");
      emitter.error(new RuntimeException("error"));
    });

    publisher
        .doOnEach(signal -> {
          log.info("context: {}", signal.getContext().<String>get("key"));
        })
        .subscribe(string -> {
          log.info(string);
        }, error -> {
          log.error("error: ", error);
        }, () -> {
          log.info("complete");
        }, Context.of("key", "value")); //initial context will be available on each step
  }

  @Test
  public void subscribe8() {
    Flux<String> publisher = Flux.push(emitter -> {
      emitter.next("test");
      emitter.next("test2");
      emitter.error(new RuntimeException("error"));
    });

    publisher
        .subscribe(new Subscriber<>() {
          @Override
          public void onSubscribe(Subscription subscription) {
            subscription.request(10);
          }

          @Override
          public void onNext(String string) {
            log.info(string);
          }

          @Override
          public void onError(Throwable error) {
            log.error("error: ", error);
          }

          @Override
          public void onComplete() {
            log.info("complete");
          }
        });
  }
}
