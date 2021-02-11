package com.coolkidz.vacctrak.service;

import com.coolkidz.vacctrak.models.VaccCenter;
import com.coolkidz.vacctrak.models.VtUser;

import java.util.List;
import java.util.Map;

public interface VtServiceInterface {

    public VaccCenter createVaccCenter(VaccCenter newVaccCenter);

    public List<VaccCenter> getAllVaccCenters();

    public Map<String, List<Integer>> getByStates();

    public VaccCenter getVaccCenterById(int id);

    public List<VaccCenter> getVaccCenterByState(String stateAbbr);

    public boolean updateVaccDoses(VaccCenter vaccCenter);

    public boolean deleteById(int id);


    public VtUser createUser(VtUser user);

}
