package org.udesa.unoback.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.udesa.unoback.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UnoServiceTest {
    @Autowired
    private UnoService unoService;

    @MockBean
    private Dealer dealer;

    private List<Card> fixedDeck;

    static Card RedOn( int n ) { return new NumberCard( "Red", n ); }
    static Card BlueOn( int n ) { return new NumberCard( "Blue", n ); }
    static Card YellowOn( int n ) { return new NumberCard( "Yellow", n ); }
    static Card GreenOn( int n ) { return new NumberCard( "Green", n ); }
    static Card red1 = RedOn( 1);
    static Card red2 = RedOn( 2 );
    static Card red3 = RedOn( 3 );
    static Card red4 = RedOn( 4 );
    static Card red5 = RedOn( 5 );
    static Card redDraw2 = new Draw2Card( "Red" );
    static Card redSkip = new SkipCard( "Red" );
    static Card blue1 = BlueOn( 1 );
    static Card blue2 = BlueOn( 2 );
    static Card blue3 = BlueOn( 3 );
    static Card blue4 = BlueOn( 4 );
    static Card green1 = GreenOn( 1 );
    static Card green3() { return GreenOn( 3 ); }
    static Card green5 = GreenOn( 5 );
    static Card yellow2 = YellowOn( 2 );
    static Card yellow3 = YellowOn( 3 );
    static Card yellow5 = YellowOn( 5 );
    static Card yellowReverse = new ReverseCard( "Yellow" );
    static WildCard wildCard() { return new WildCard(); }

    @BeforeEach
    void setUp() {
        fixedDeck = new ArrayList<>();
        fixedDeck.add(red1);
        fixedDeck.addAll(List.of(red1, red2, red3, red4, red5, red1, red2));
        fixedDeck.addAll(List.of(blue1, blue2, blue3, blue4, blue1, blue2, blue3));
        fixedDeck.addAll(List.of(yellow5, yellow2, yellow3));

        when(dealer.fullDeck()).thenReturn(fixedDeck);
    }

    @Test
    public void test01NewMatch(){
        assertDoesNotThrow(() -> {
            assertTrue(unoService.existsMatch(unoService.newMatch(List.of("Nina", "Ana"))));
        });
    }

    @Test
    public void test02NewMatchinitializesCorrectly(){
        UUID matchId = unoService.newMatch(List.of("Nina", "Ana"));
        JsonCard active = unoService.activeCard(matchId);
        assertEquals("Red", active.getColor());
        assertEquals(1, active.getNumber());
    }

    @Test
    public void test03PlayerHand(){
        UUID matchId = unoService.newMatch(List.of("Nina", "Ana"));
        assertEquals(7, unoService.playerHand(matchId).size());
    }

    @Test
    void test04PlayOneCard() {
        UUID matchId = unoService.newMatch(List.of("Nina", "Ana"));
        unoService.play(matchId, "Nina", red1);
        unoService.play(matchId, "Ana", blue1);
        assertEquals(6, unoService.playerHand(matchId).size());
    }

    @Test
    void test05InvalidMatchId() {
        UUID invalid = UUID.randomUUID();
        assertThrows(RuntimeException.class, () -> unoService.activeCard(invalid));
        assertThrows(RuntimeException.class, () -> unoService.playerHand(invalid));
        assertThrows(RuntimeException.class, () -> unoService.play(invalid, "Ana", blue1));
        assertThrows(RuntimeException.class, () -> unoService.draw(invalid, "Nina"));
    }

    @Test
    void test06JuegoFueraDeTurno() {
        UUID matchId = unoService.newMatch(List.of("Nina", "Ana"));
        assertThrows(RuntimeException.class, () -> unoService.play(matchId, "Ana", blue1));
    }

    @Test
    void test07DrawAndPlayOneCard() {
        UUID matchId = unoService.newMatch(List.of("Nina", "Ana"));
        unoService.play(matchId, "Nina", red5);
        unoService.draw(matchId, "Ana");
        unoService.play(matchId, "Ana", yellow5);
        unoService.draw(matchId, "Nina");
        unoService.play(matchId, "Nina", yellow2);
    }
}
