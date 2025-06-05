package org.udesa.unoback.model;

import java.util.ArrayList;
import java.util.List;

public class DeckBuilder {

    public static List<Card> standardUnoDeck() {
        List<Card> deck = new ArrayList<>();

        for (String color : List.of("Red", "Blue", "Green", "Yellow")) {

            for (int num = 0; num <= 9; num++) {
                deck.add(new NumberCard(color, num));
                deck.add(new NumberCard(color, num));
            }
            deck.add(new SkipCard(color));
            deck.add(new SkipCard(color));
            deck.add(new ReverseCard(color));
            deck.add(new ReverseCard(color));
            deck.add(new Draw2Card(color));
            deck.add(new Draw2Card(color));
        }

        for (int i = 0; i < 4; i++) {
            deck.add(new WildCard());
        }

        return deck;
    }
}
