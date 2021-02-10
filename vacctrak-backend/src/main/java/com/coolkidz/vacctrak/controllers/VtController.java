package com.coolkidz.vacctrak.controllers;

import com.coolkidz.vacctrak.data.VtDao;
import com.coolkidz.vacctrak.models.VaccCenter;
import com.coolkidz.vacctrak.service.VtServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/VaccTrak")
@CrossOrigin
public class VtController {

    private final VtServiceInterface VtSi;

    public VtController(VtServiceInterface VtSi) {
        this.VtSi = VtSi;
    }

//    @PostMapping("/begin")
//    @ResponseStatus(HttpStatus.CREATED)
//    public VaccCenter createVaccCenter(@RequestBody VtServiceInterface VtSi) {
//        return VtSi.createVaccCenter();
//    }
//
//    // User enters gameId and guess
//    // Sends user's guess, returns round
//    @PostMapping("/guess")
//    @ResponseStatus(HttpStatus.CREATED)
//    public Round createRound(@RequestBody Round round) {
//        return VtSi.createRound(round);
//    }
//
//
//    // Returns a specific game based on ID
//    @GetMapping("/game/{id}")
//    public Game findById(@PathVariable int id) {
//        return VtSi.findById(id);
//    }
//
////    // Returns a list of all games, with answer nulled if game status is not true (completed)
////    @GetMapping("/game")
////    public List<Game> all() {
////        return serviceInterface.allGames();
////    }
//
//    // Returns a list of rounds for the specified game, sorted by time
//    @GetMapping("/rounds/{id}")
//    public List<Round> findRoundById(@PathVariable int id) {
//        return VtSi.findRoundById(id);
//    }

    // Returns a specific game based on ID
    @GetMapping()
    public String Welcome() {
        return "Welcome to VaccTrak, powered by CoolKidz™";
    }
}
// Get all
// get by vacc center
// get by state

// Create vacc center
// update single/double doses
// delete a vacc center