package com.sebczu.poc.reactive;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

@Slf4j
@SpringBootTest
public class ContextTest {

    @Test
    public void contextWrite() {
        Mono<String> publisher = Mono.just("change");

        publisher
                .flatMap(element -> Mono.deferContextual(ctx -> Mono.just(ctx.get("key"))))
                .doOnNext(element -> log.info("element: " + element))
                .contextWrite(Context.of("key", "value"))
                .subscribe();

    }

    @Test
    public void contextWrite2() {
        Mono<String> publisher = Mono.just("change");

        publisher
                .contextWrite(Context.of("key", "value"))
                .flatMap(element -> Mono.deferContextual(ctx -> Mono.just(ctx.getOrDefault("key", "default"))))
                .doOnNext(element -> log.info("element: " + element))
                .subscribe();
    }

}
