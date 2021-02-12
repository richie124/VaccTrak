package com.coolkidz.vacctrak.data;

import com.coolkidz.vacctrak.models.VaccCenter;
import com.coolkidz.vacctrak.models.VtUser;

import java.util.List;

public interface VtDao {

    public VaccCenter createVaccCenter(VaccCenter vaccCenter);

    public List<VaccCenter> getAllVaccCenters();

    public VaccCenter getVaccCenterById(int id);

    public List<VaccCenter> getVaccCenterByState(String stateAbbr);

    boolean update(VaccCenter vaccCenter);

    boolean deleteById(int id);


}