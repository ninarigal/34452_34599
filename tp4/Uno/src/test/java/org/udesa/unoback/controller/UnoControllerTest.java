package org.udesa.unoback.controller;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.udesa.unoback.model.*;
import org.udesa.unoback.service.Dealer;
import org.udesa.unoback.service.UnoService;


@SpringBootTest
@AutoConfigureMockMvc
public class UnoControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired UnoService unoService;
    @Autowired ObjectMapper mapper;

    @MockBean private Dealer dealer;

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
    public void test01CanCreateMatchWithTwoPlayers() throws Exception {
        assertNotNull(UUID.fromString(createMatchFor("A", "B").replaceAll("^\"|\"$", "")));
    }

    @Test
    void test02NewMatchFails() throws Exception {
        mockMvc.perform(post("/uno/newmatch"))
                .andDo(print())
                .andExpect(status().is(400));
    }

    @Test
    void test03PlayInNewMatch() throws Exception {
        playerPlay(UUID.fromString(createMatchFor("A", "B").replaceAll("^\"|\"$", "")), "A", red2);

    }

    @Test
    void test04CantPlayNotHisTurn() throws Exception {
        playerPlayFailing(UUID.fromString(createMatchFor("A", "B").replaceAll("^\"|\"$", "")), "B", blue1);

    }

    @Test
    void test05CantPlayNotHisCard() throws Exception {
        playerPlayFailing(UUID.fromString(createMatchFor("A", "B").replaceAll("^\"|\"$", "")), "A", blue1);
    }

    @Test
    void test06DrawCard() throws Exception {
        playerDraw(UUID.fromString(createMatchFor("A", "B").replaceAll("^\"|\"$", "")), "A");

    }

    @Test
    void test07CantDrawCard() throws Exception {
        playerDrawFailing(UUID.fromString(createMatchFor("A", "B").replaceAll("^\"|\"$", "")), "B");
    }

    @Test
    void test08getActiveCard() throws Exception {
        JsonCard card = getActiveCard(UUID.fromString(createMatchFor("A", "B").replaceAll("^\"|\"$", "")));
        assertEquals("Red", card.getColor());
        assertEquals(1, card.getNumber());
    }

    @Test
    void test09getActiveCardFails() throws Exception {
        getActiveCardFailing(UUID.randomUUID());
    }

    @Test
    void test10getPlayerHand() throws Exception {
        List<JsonCard> hand = getPlayerHand(UUID.fromString(createMatchFor("A", "B").replaceAll("^\"|\"$", "")));
        assertEquals(7, hand.size());
    }

    @Test
    void test11getPlayerHandFails() throws Exception {
        getPlayerHandFailing(UUID.randomUUID());
    }


    private String createMatchFor(String player1, String player2) throws Exception {
        return mockMvc.perform(post("/uno/newmatch")
                        .param("players", player1)
                        .param("players", player2))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    private void playerPlay(UUID matchId, String player, Card card) throws Exception {
        mockMvc.perform(post("/uno/play/" + matchId + "/" + player)
                        .contentType( MediaType.APPLICATION_JSON )
                        .content(card.asJson().toString()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    private void playerPlayFailing(UUID matchId, String player, Card card) throws Exception {
        mockMvc.perform(post("/uno/play/" + matchId + "/" + player)
                        .contentType( MediaType.APPLICATION_JSON )
                        .content(card.asJson().toString()))
                .andDo(print())
                .andExpect(status().is(500));
    }

    private void playerDraw(UUID matchId, String player) throws Exception {
        mockMvc.perform(post("/uno/draw/" + matchId + "/" + player)
                        .contentType( MediaType.APPLICATION_JSON )
                        .param("player", player))
                .andDo(print())
                .andExpect(status().isOk());
    }

    private void playerDrawFailing(UUID matchId, String player) throws Exception {
        mockMvc.perform(post("/uno/draw/" + matchId + "/" + player)
                        .contentType( MediaType.APPLICATION_JSON )
                        .param("player", player))
                .andDo(print())
                .andExpect(status().is(500));
    }

    private JsonCard getActiveCard(UUID matchId) throws Exception {
        String json = mockMvc.perform(get("/uno/activecard/" + matchId))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        return mapper.readValue(json, JsonCard.class);
    }

    private void getActiveCardFailing(UUID matchId) throws Exception {
        mockMvc.perform(get("/uno/activecard/" + matchId))
                .andDo(print())
                .andExpect(status().is(500))
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    private List<JsonCard> getPlayerHand(UUID matchId) throws Exception {
        String json = mockMvc.perform(get("/uno/playerhand/" + matchId))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        return mapper.readValue(json, new TypeReference<List<JsonCard>>() {});
    }

    private void getPlayerHandFailing(UUID matchId) throws Exception {
        mockMvc.perform(get("/uno/playerhand/" + matchId))
                .andDo(print())
                .andExpect(status().is(500))
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

}
