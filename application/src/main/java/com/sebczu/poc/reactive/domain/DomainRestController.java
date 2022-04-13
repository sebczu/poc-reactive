package com.sebczu.poc.reactive.domain;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping
public class DomainRestController {

  @GetMapping("/flux")
  public Flux<String> flux() {
    return Flux.just("a", "b", "c")
      .map(str -> {
        log.info("(map1) begin in thread: {} string {}", Thread.currentThread().getName(), str);
        sleep(150);
        log.info("(map1) end in thread: {} string {}", Thread.currentThread().getName(), str);
        return str;
      })
      .map(str -> {
        log.info("(map2) begin in thread: {} string {}", Thread.currentThread().getName(), str);
        sleep(200);
        log.info("(map2) end in thread: {} string {}", Thread.currentThread().getName(), str);
        return str;
      });
  }

  @GetMapping("/flux/parallel")
  public Flux<String> fluxParallel() {
    log.info("thread: {}", Thread.currentThread().getName());
    return Flux.just("a", "b", "c")
      .parallel(2).runOn(Schedulers.newParallel("custom-parallel", 2))
      .map(str -> {
        log.info("(map1) begin in thread: {} string {}", Thread.currentThread().getName(), str);
        sleep(50);
        log.info("(map1) end in thread: {} string {}", Thread.currentThread().getName(), str);
        return str;
      })
      .map(str -> {
        log.info("(map2) begin in thread: {} string {}", Thread.currentThread().getName(), str);
        sleep(75);
        log.info("(map2) end in thread: {} string {}", Thread.currentThread().getName(), str);
        return str;
      })
      .sequential().publishOn(Schedulers.newSingle("custom-single"))
      .map(str -> {
        log.info("(map3) begin in thread: {} string {}", Thread.currentThread().getName(), str);
        sleep(75);
        log.info("(map3) end in thread: {} string {}", Thread.currentThread().getName(), str);
        return str;
      });
  }

  @SneakyThrows
  private void sleep(long millis) {
    Thread.sleep(millis);
  }

}
