package com.coolkidz.vacctrak.controllers;

import com.coolkidz.vacctrak.models.VaccCenter;
import com.coolkidz.vacctrak.models.VtUser;
import com.coolkidz.vacctrak.service.VtServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/VaccTrak/AdminPortal")
@CrossOrigin
public class UserController {

    private final VtServiceInterface VtSi;

    public UserController(VtServiceInterface VtSi) {
        this.VtSi = VtSi;
    }

    @PostMapping("/CreateUser")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<VtUser> createUser(@RequestBody VtUser user) throws InvalidKeySpecException, NoSuchAlgorithmException {
        VtUser response = VtSi.createUser(user);
        if (response == null) {
            return new ResponseEntity(null, HttpStatus.CONFLICT);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/Login")
    public ResponseEntity<VtUser> login(@RequestBody VtUser user) throws InvalidKeySpecException, NoSuchAlgorithmException {
        VtUser response = VtSi.validateUser(user);
        if (response == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/AddPermissions")
    public ResponseEntity<List<Integer>> updatePerms(@RequestBody VtUser user) throws InvalidKeySpecException, NoSuchAlgorithmException {
        List<Integer> response = VtSi.insertPerms(user);
        if (response == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public String Welcome() {
        return "Welcome, User!";
    }

}
