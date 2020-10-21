package com.sebczu.poc.reactive;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StringSubscriber implements Subscriber<String> {

  @Override
  public void onSubscribe(Subscription subscription) {
    subscription.request(100);
    log.info("on subscribe");
  }

  @Override
  public void onNext(String value) {
    log.info("on next: {}", value);
  }

  @Override
  public void onError(Throwable throwable) {
    log.info("on error", throwable);
  }

  @Override
  public void onComplete() {
    log.info("on complete");
  }

}
