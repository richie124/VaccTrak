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
    public VtUser createUser(@RequestBody VtUser user) throws InvalidKeySpecException, NoSuchAlgorithmException {
        return VtSi.createUser(user);
    }

    @PostMapping("/Login")
    public Boolean login(@RequestBody VtUser user) throws InvalidKeySpecException, NoSuchAlgorithmException {
        Boolean good = VtSi.validateUser(user);
        return good;
    }


    @GetMapping()
    public String Welcome() {
        return "Welcome, User!";
    }

}
