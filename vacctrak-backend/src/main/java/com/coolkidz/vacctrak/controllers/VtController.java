package com.coolkidz.vacctrak.controllers;

import com.coolkidz.vacctrak.models.VaccCenter;
import com.coolkidz.vacctrak.service.VtServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public VaccCenter createVaccCenter(@RequestBody VaccCenter vaccCenter) {
        return VtSi.createVaccCenter(vaccCenter);
    }

    @PutMapping("/UpdateDoses")
    public ResponseEntity updateVaccDoses(@RequestBody VaccCenter vaccCenter) {
        ResponseEntity response = new ResponseEntity(HttpStatus.NOT_FOUND);
        if (VtSi.updateVaccDoses(vaccCenter)) {
            response = new ResponseEntity(HttpStatus.OK);
        }
        return response;
    }

    @DeleteMapping("/DeleteVaccCenter/{id}")
    public ResponseEntity deleteVaccCenter(@PathVariable int id){
        if (VtSi.deleteById(id)) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

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
    public ResponseEntity<VaccCenter> findRoundById(@PathVariable int id) {
        VaccCenter result = VtSi.getVaccCenterById(id);
        if (result == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }

    // Returns a list of rounds for the specified game, sorted by time
    @GetMapping("/AllVaccCenters/{StateAbbr}")
    public List<VaccCenter> findRoundById(@PathVariable String StateAbbr) {
        return VtSi.getVaccCenterByState(StateAbbr);
    }

    // Returns a specific game based on ID
    @GetMapping()
    public String Welcome() {
        return "Welcome to VaccTrak, powered by CoolKidz™";
    }
}

// Get all --- done :)
// get by vacc center --- Done :)
// get vaccination centers by state --- done :)
// Get single and double doses by state --- returns a jsonified map

// Create vacc center --- done :)
// update single/double doses
// delete a vacc center

// Remove code to take json of addresses and add coordinates

// What to submit to David:
// just the react files we created
// just the java files we created + pom


// Where we're at:
    // Gathered a bunch of data for real sites
    // built out most of the backend for the public centric portal
    // Mostly have the map api up and running
// fun level: 6
// stress level: 3
// project completion 35%
// Blockers: Gathering up the data is time consuming
//           We're having difficulties mapping the centers on google map api becuase it only takes lat/long
//
//