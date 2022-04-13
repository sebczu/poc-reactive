package com.sebczu.poc.reactive.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import javax.annotation.PostConstruct;
import java.time.Duration;

@Component
@Slf4j
public class CronJob {

  @PostConstruct
  public void init() {
    Flux.interval(Duration.ofSeconds(10), Schedulers.newSingle("sub"))
      .log()
      .publishOn(Schedulers.newSingle("pub"))
      .map(counter -> {
        log.info("counter: {}", counter);
        return counter;
      })
      .subscribe();
  }

}
