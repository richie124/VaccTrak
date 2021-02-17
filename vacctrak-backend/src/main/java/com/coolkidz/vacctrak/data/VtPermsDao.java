package com.coolkidz.vacctrak.data;

import com.coolkidz.vacctrak.models.CenterPermission;
import com.coolkidz.vacctrak.models.VtUser;

import java.util.List;

public interface VtPermsDao {

    public List<Integer> setPerms(VtUser user);

    public List<Integer> getPermsByUserId(int userId);
}
