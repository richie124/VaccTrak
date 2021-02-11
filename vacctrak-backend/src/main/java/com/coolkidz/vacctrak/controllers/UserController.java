package com.coolkidz.vacctrak.controllers;

import com.coolkidz.vacctrak.models.VaccCenter;
import com.coolkidz.vacctrak.models.VtUser;
import com.coolkidz.vacctrak.service.VtServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public VtUser createVaccCenter(@RequestBody VtUser user) {
        return VtSi.createUser(user);
    }



    @GetMapping()
    public String Welcome() {
        return "Welcome, User!";
    }

}
