package com.sebczu.poc.reactive;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.blockhound.BlockHound;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@Slf4j
@SpringBootTest
public class BlockHoundTest {

    @Test
    public void aaa() {
//BlockHound.install();
        Mono<Integer> publisher = Mono.just(1)
                .doOnNext(value -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });

        StepVerifier.create(publisher)
                .expectErrorMatches(error -> {
                    error.printStackTrace();
                    return error.getMessage().contains("Blocking call");
                })
                .verify();
    }

}
