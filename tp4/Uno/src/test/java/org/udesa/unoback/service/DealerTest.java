package org.udesa.unoback.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.udesa.unoback.model.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DealerTest {
    private Dealer dealer;

    @BeforeEach
    void setUp() {
        dealer = new Dealer();
    }

    @Test
    void fullDeckShouldHaveCorrectSize() {
        assertEquals(40, dealer.fullDeck().size());
    }

    @Test
    void fullDeckShouldContain4WildCards() {
        List<Card> deck = dealer.fullDeck();
        long wildCount = deck.stream()
                .filter(c -> c instanceof WildCard)
                .count();
        assertEquals(4, wildCount);
    }

    @Test
    void fullDeckShouldContain4ActionCards() {
        List<Card> deck = dealer.fullDeck();
        Map<Class<?>, Long> counts = deck.stream()
                .collect(Collectors.groupingBy(Card::getClass, Collectors.counting()));

        assertEquals(4, counts.getOrDefault(SkipCard.class, 0L));
        assertEquals(4, counts.getOrDefault(Draw2Card.class, 0L));
        assertEquals(4, counts.getOrDefault(ReverseCard.class, 0L));
    }

    @Test
    void fullDeckShouldContain24NumberCards() {
        List<Card> deck = dealer.fullDeck();
        long numberCount = deck.stream()
                .filter(c -> c instanceof NumberCard)
                .count();
        assertEquals(24, numberCount);
    }

}
