package com.coolkidz.vacctrak.service;

import com.coolkidz.vacctrak.models.VaccCenter;

import java.util.List;
import java.util.Map;

public interface VtServiceInterface {

    public VaccCenter createVaccCenter(VaccCenter newVaccCenter);

    public List<VaccCenter> getAllVaccCenters();

    public Map<String, List<Integer>> getByStates();

    public VaccCenter getVaccCenterById(int id);



}
