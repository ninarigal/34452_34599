package org.udesa.unoback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.udesa.unoback.model.JsonCard;
import org.udesa.unoback.service.UnoService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/uno")
public class UnoController {

    @Autowired UnoService unoService;


    // Maneja únicamente IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(ex.getMessage());
    }

    // Maneja cualquier otro RuntimeException
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntime(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());
    }


    @PostMapping("newmatch") public ResponseEntity newMatch(@RequestParam List<String> players ) {
        return ResponseEntity.ok(unoService.newMatch(players));
    }
    @PostMapping("play/{matchId}/{player}") public ResponseEntity play( @PathVariable UUID matchId, @PathVariable String player, @RequestBody JsonCard card ) {
        unoService.play(matchId, player, card.asCard());
        return ResponseEntity.ok().build();

    }
    @PostMapping("draw/{matchId}/{player}")public ResponseEntity drawCard(@PathVariable UUID matchId, @PathVariable String player ) {
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
