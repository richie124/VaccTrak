package com.coolkidz.vacctrak.service;

import com.coolkidz.vacctrak.data.VtDao;
import com.coolkidz.vacctrak.models.StateVaccs;
import com.coolkidz.vacctrak.models.VaccCenter;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;

@Service
public class VtService implements VtServiceInterface {

    private final VtDao vtDao;

    public VtService(VtDao vtDao) {
        this.vtDao = vtDao;
    }

    @Override
    public VaccCenter createVaccCenter(VaccCenter newVaccCenter) {
        return vtDao.createVaccCenter(newVaccCenter);
    }

    @Override
    public List<VaccCenter> getAllVaccCenters() {
        return vtDao.getAllVaccCenters();
    }

    @Override
    public List<StateVaccs> getByStates() {

        List<StateVaccs> stateVaccs = new ArrayList<StateVaccs>();

        List<VaccCenter> allVaccSites = vtDao.getAllVaccCenters();
        for ( int i = 0; i < allVaccSites.size(); i++ ) {
            // Get a vaccCenter from the list pulled from DB
            VaccCenter tempCenter = allVaccSites.get(i);

            // CHECK IF STATE IS ALREADY IN LIST stateVaccs<>


            // If State is not in list stateVaccs<>
            // create a stateObejct to insert into stateVaccs list
            StateVaccs tempState = new StateVaccs();


            tempState.setStateName(tempCenter.getState());




            stateVaccs.add(tempState);
        }


        return null;
    }
}
