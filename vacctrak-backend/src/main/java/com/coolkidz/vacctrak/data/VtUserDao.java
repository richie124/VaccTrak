package com.coolkidz.vacctrak.data;

import com.coolkidz.vacctrak.models.VtUser;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface VtUserDao {

    public VtUser createUser(VtUser vtUser) throws InvalidKeySpecException, NoSuchAlgorithmException;

    public boolean validateUser(VtUser vtUser);
}
