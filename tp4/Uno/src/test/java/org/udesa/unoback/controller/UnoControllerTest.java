package org.udesa.unoback.controller;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.udesa.unoback.model.*;
import org.udesa.unoback.service.Dealer;
import org.udesa.unoback.service.UnoService;
import org.udesa.unoback.service.UnoServiceTest;


@SpringBootTest
@AutoConfigureMockMvc
public class UnoControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper mapper;

    @MockBean private Dealer dealer;


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
        when(dealer.fullDeck()).thenReturn(UnoServiceTest.fullDeck());
    }

    @Test
    public void test01CanCreateMatchWithTwoPlayers() throws Exception {
        assertNotNull(UUID.fromString(createMatchFor("A", "B")));
    }


    @Test
    void test02NewMatchFailsLessPlayers() throws Exception {
        mockMvc.perform(post("/uno/newmatch")
                        .param("players", "A"))
                .andDo(print())
                .andExpect(status().is(HttpStatus.UNPROCESSABLE_ENTITY.value()));
    }


    @Test
    void test03PlayInNewMatch() throws Exception {
        playerPlay(UUID.fromString(createMatchFor("A", "B")), "A", red2);

    }


    @Test
    void test04CantPlayNotHisTurn() throws Exception {
        String resp = playerPlayFailing(UUID.fromString(createMatchFor("A", "B")), "B", blue1);
        assertEquals(Player.NotPlayersTurn + "B", resp);
    }

    @Test
    void test05CantPlayNotHisCard() throws Exception {
        String resp = playerPlayFailing(UUID.fromString(createMatchFor("A", "B")), "A", blue1);
        assertEquals(Match.NotACardInHand + "A", resp);
    }

    @Test
    void test05CantPlayPlayerDoesntExist() throws Exception {
        String resp = playerPlayFailing(UUID.fromString(createMatchFor("A", "B")), "C", blue1);
        assertEquals(Player.NotPlayersTurn + "C", resp);
    }

    @Test
    void test05CantPlayCardDoentExist() throws Exception {
        Card grey1 = new NumberCard( "Grey", 1 );
        String resp = playerPlayFailing(UUID.fromString(createMatchFor("A", "B")), "A", grey1);
        assertEquals(Match.NotACardInHand + "A", resp);
    }

    @Test
    void test05CantPlayCardDoesntMatch() throws Exception {
        String resp = playerPlayFailing(UUID.fromString(createMatchFor("A", "B")), "A", green5);
        assertEquals(Match.CardDoNotMatch, resp);
    }


    @Test
    void test06DrawCard() throws Exception {
        playerDraw(UUID.fromString(createMatchFor("A", "B")), "A");
    }

    @Test
    void test07CantDrawCard() throws Exception {
        playerDrawFailing(UUID.fromString(createMatchFor("A", "B")), "B");
    }

    @Test
    void test08getActiveCard() throws Exception {
        JsonCard card = getActiveCard(UUID.fromString(createMatchFor("A", "B")));
        assertEquals("Red", card.getColor());
        assertEquals(1, card.getNumber());
    }

    @Test
    void test09getActiveCardFails() throws Exception {
        getActiveCardFailing(UUID.randomUUID());
    }

    @Test
    void test10getPlayerHand() throws Exception {
        List<JsonCard> hand = getPlayerHand(UUID.fromString(createMatchFor("A", "B")));
        assertEquals(7, hand.size());
    }

    @Test
    void test11getPlayerHandFails() throws Exception {
        getPlayerHandFailing(UUID.randomUUID());
    }

    private String createMatchFor(String player1, String player2) throws Exception {
        String resp = mockMvc.perform(post("/uno/newmatch")
                        .param("players", player1)
                        .param("players", player2))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        return mapper.readTree(resp).asText();
    }

    private void playerPlay(UUID matchId, String player, Card card) throws Exception {
        mockMvc.perform(post("/uno/play/" + matchId + "/" + player)
                        .contentType( MediaType.APPLICATION_JSON )
                        .content(card.asJson().toString()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    private String playerPlayFailing(UUID matchId, String player, Card card) throws Exception {
        return mockMvc.perform(post("/uno/play/" + matchId + "/" + player)
                        .contentType( MediaType.APPLICATION_JSON )
                        .content(card.asJson().toString()))
                .andDo(print())
                .andExpect(status().is(500))
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    private void playerDraw(UUID matchId, String player) throws Exception {
        mockMvc.perform(post("/uno/draw/" + matchId + "/" + player))
                .andDo(print())
                .andExpect(status().isOk());
    }

    private void playerDrawFailing(UUID matchId, String player) throws Exception {
        mockMvc.perform(post("/uno/draw/" + matchId + "/" + player))
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
                .andExpect(status().is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
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
                .andExpect(status().is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

}
