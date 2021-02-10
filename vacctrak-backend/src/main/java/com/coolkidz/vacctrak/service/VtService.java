package com.coolkidz.vacctrak.service;

import com.coolkidz.vacctrak.data.VtDao;
import com.coolkidz.vacctrak.models.VaccCenter;
import org.springframework.stereotype.Service;

import java.util.*;

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
    public Map<String, List<Integer>> getByStates() {

        Map<String, List<Integer>> stateVaccs = new HashMap<String, List<Integer>>();

        List<VaccCenter> allVaccSites = vtDao.getAllVaccCenters();
        for ( int i = 0; i < allVaccSites.size(); i++ ) {
            // Get a vaccCenter from the list pulled from DB
            VaccCenter tempCenter = allVaccSites.get(i);
            String tempState = tempCenter.getState();

            if(!stateVaccs.containsKey(tempState)) {
                stateVaccs.put(tempState, new ArrayList<Integer>(Arrays.asList(tempCenter.getSingleDoses(), tempCenter.getDoubleDoses())));
            } else {
                List<Integer> tempDoses = stateVaccs.get(tempState);
                int newSingleDoses = tempDoses.get(0) + tempCenter.getSingleDoses();
                int newDoubleDoses = tempDoses.get(1) + tempCenter.getDoubleDoses();
                tempDoses.set(0, newSingleDoses);
                tempDoses.set(1, newDoubleDoses);

                stateVaccs.put(tempState, tempDoses);
            }
        }
        return stateVaccs;
    }

    @Override
    public VaccCenter getVaccCenterById(int id) {
        return vtDao.getVaccCenterById(id);
    }

    @Override
    public List<VaccCenter> getVaccCenterByState(String stateAbbr) {
        return vtDao.getVaccCenterByState(stateAbbr);
    }
}
