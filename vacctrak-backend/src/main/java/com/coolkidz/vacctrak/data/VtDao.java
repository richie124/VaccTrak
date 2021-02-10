package com.coolkidz.vacctrak.data;

import com.coolkidz.vacctrak.models.VaccCenter;

import java.util.List;

public interface VtDao {

    public VaccCenter createVaccCenter(VaccCenter vaccCenter);

    public List<VaccCenter> getAllVaccCenters();
}
