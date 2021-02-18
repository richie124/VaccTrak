package com.coolkidz.vacctrak.controllers;

import com.coolkidz.vacctrak.models.StateVaccs;
import com.coolkidz.vacctrak.models.VaccCenter;
import com.coolkidz.vacctrak.service.VtServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
//    public VaccCenter createVaccCenter(@RequestBody VaccCenter vaccCenter) {
//        return VtSi.createVaccCenter(vaccCenter);
//    }
    public VaccCenter createVaccCenter(@RequestBody HashMap<String, String> vaccCenter) {
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
    public List<StateVaccs> getVaccNumbersByState() {
        return VtSi.getByStates();
    }

    // returns the response entity with the VaccCenter of the searched for Id
    @GetMapping("/VaccCenter/{id}")
    public ResponseEntity<VaccCenter> findVaccCenterById(@PathVariable int id) {
        VaccCenter result = VtSi.getVaccCenterById(id);
        if (result == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }

    // Returns a list of all VaccCenters of the searched-for state
    @GetMapping("/AllVaccCenters/{StateAbbr}")
    public ResponseEntity<List<VaccCenter>> findRoundById(@PathVariable String StateAbbr) {
        List<VaccCenter> result =  VtSi.getVaccCenterByState(StateAbbr);
        if (result.isEmpty()) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping()
    public String Welcome() {
        return "Welcome to VaccTrak, powered by CoolKidz™";
    }
}

// Get all --- done :)
// get by vacc center --- Done :)
// get vaccination centers by state --- done :)
// Get single and double doses by state --- returns state objects :)

// Create vacc center --- done :)
// update single/double doses --- :)
// delete a vacc center --- :)

// Remove code to take json of addresses and add coordinates

// What to submit to David:
// just the react files we created
// just the java files we created + pom

// LOGIN
// pass in user id, vacCenter id

// how to add user auth to React and Java Spring Boot
// if frontend sends userId


// Error handling
// User should only be created if username is unique
// Figure out how to catch errors etc


// change endpoint to UpdatePermissions
// will take in array of all new user permissions
// delete all permissions for user
// insert all new permissions for user

// a smooth presentation
// pretend David is a client
// intro group
// why we built app
// what technologies we used
// demo
// lessons learned
// questions?