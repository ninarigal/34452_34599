package org.udesa.unoback.service;

import org.springframework.stereotype.Service;
import org.udesa.unoback.model.Card;
import org.udesa.unoback.model.DeckBuilder;
import org.udesa.unoback.model.JsonCard;
import org.udesa.unoback.model.Match;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
public class UnoService {
    private Map<UUID, Match> matches = new HashMap<>();


    public UUID newMatch(List<String> players) {
        UUID matchId = UUID.randomUUID();
        List<Card> deck = DeckBuilder.standardUnoDeck();
        Collections.shuffle(deck);
        Match match = new Match(deck, 7, players);
        matches.put(matchId, match);
        return matchId;
    }

    public void play(UUID matchId, String playerName, JsonCard jsonCard) {
        checkMatchExists(matchId);
        Match match = matches.get(matchId);
        Card carta = jsonCard.asCard();
        match.play(playerName, carta);
        matches.put(matchId, match);
    }

    public void draw(UUID matchId, String playerName) {
        checkMatchExists(matchId);
        Match match = matches.get(matchId);
        match.drawCard(playerName);
        matches.put(matchId, match);
    }

    public JsonCard activeCard(UUID matchId) {
        checkMatchExists(matchId);
        Match match = matches.get(matchId);
        Card head = match.activeCard();
        return head.asJson();
    }

    public List<JsonCard> playerHand(UUID matchId) {
        checkMatchExists(matchId);
        Match match = matches.get(matchId);
        List<Card> mano = match.playerHand();
        return mano.stream()
                .map(Card::asJson)
                .collect(Collectors.toList());
    }

    private void checkMatchExists(UUID matchId) {
        if (!matches.containsKey(matchId)) {
            throw new RuntimeException("Match not found");
        }
    }

    public boolean existsMatch(UUID matchId) {
        return matches.containsKey(matchId);
    }


}
