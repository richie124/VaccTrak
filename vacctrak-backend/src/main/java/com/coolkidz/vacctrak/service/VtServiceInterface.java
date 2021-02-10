package com.coolkidz.vacctrak.service;

import com.coolkidz.vacctrak.models.StateVaccs;
import com.coolkidz.vacctrak.models.VaccCenter;

import java.util.List;

public interface VtServiceInterface {

    public VaccCenter createVaccCenter(VaccCenter newVaccCenter);

    public List<VaccCenter> getAllVaccCenters();

    public List<StateVaccs> getByStates();

}
