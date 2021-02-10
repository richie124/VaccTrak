package com.coolkidz.vacctrak.controllers;

import com.coolkidz.vacctrak.models.VaccCenter;
import com.coolkidz.vacctrak.service.VtServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/VaccTrak")
@CrossOrigin
public class VtController {

    private final VtServiceInterface VtSi;

    public VtController(VtServiceInterface VtSi) {
        this.VtSi = VtSi;
    }

    @PostMapping("/createVaccCenter")
    @ResponseStatus(HttpStatus.CREATED)
    public VaccCenter createVaccCenter(@RequestBody VaccCenter newVaccCenter) {
        return VtSi.createVaccCenter(newVaccCenter);
    }
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

    // Returns vaccine center table
    @GetMapping("/getAllVaccCenters")
    public List<VaccCenter> getAll() {
        return VtSi.getAllVaccCenters();
    }

    // Returns vaccine center table
    @GetMapping("/getVaccNumbersByState")
    public Map<String, List<Integer>> getVaccNumbersByState() {
        return VtSi.getByStates();
    }

    // Returns a list of rounds for the specified game, sorted by time
    @GetMapping("/VaccCenter/{id}")
    public VaccCenter findRoundById(@PathVariable int id) {
        return VtSi.getVaccCenterById(id);
    }

    // Returns a specific game based on ID
    @GetMapping()
    public String Welcome() {
        return "Welcome to VaccTrak, powered by CoolKidz™";
    }
}
// Get all --- done :)
// get by vacc center
// get vaccination centers by state
// Get single and double doses by state --- returns a jsonified map

// Create vacc center --- done :)
// update single/double doses
// delete a vacc center