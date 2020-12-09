package com.sebczu.poc.reactive;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

import java.util.stream.Stream;

@Slf4j
@SpringBootTest
public class FlatMapTest {

  @Test
  public void flatMap() {
    Flux<String> publisher = Flux.just("a", "bb", "ccc");

    publisher.flatMap(string -> {
      Stream<Character> characters = string.chars()
          .mapToObj(i -> (char) i);

      return Flux.fromStream(characters);
    }).subscribe(character -> log.info("char: {}", character));
  }

}
