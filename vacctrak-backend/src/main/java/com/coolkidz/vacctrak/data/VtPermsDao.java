package com.coolkidz.vacctrak.data;

import com.coolkidz.vacctrak.models.CenterPermission;

import java.util.List;

public interface VtPermsDao {

    public List<Integer> getPermsByUserId(int userId);
}