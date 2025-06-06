package org.udesa.unoback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.udesa.unoback.model.JsonCard;
import org.udesa.unoback.service.UnoService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/uno")
public class UnoController {

    @Autowired UnoService unoService;

    @PostMapping("newmatch") public ResponseEntity newMatch(@RequestParam List<String> players ) {
        UUID id = unoService.newMatch(players);
        return ResponseEntity.ok(id);
    }
    @PostMapping("play/{matchId}/{player}") public ResponseEntity play( @PathVariable UUID matchId, @PathVariable String player, @RequestBody JsonCard card ) {
        unoService.play(matchId, player, card);
        return ResponseEntity.ok().build();

    }
    @PostMapping("draw/{matchId}/{player}")public ResponseEntity drawCard(@PathVariable UUID matchId, @RequestParam String player ) {
        unoService.draw(matchId, player);
        return ResponseEntity.ok().build();
    }

    @GetMapping("activecard/{matchId}") public ResponseEntity activeCard( @PathVariable UUID matchId ) {
        return ResponseEntity.ok(unoService.activeCard(matchId));

    }
    @GetMapping("playerhand/{matchId}")  public ResponseEntity playerHand( @PathVariable UUID matchId ) {
        return ResponseEntity.ok(unoService.playerHand(matchId));
    }

}
