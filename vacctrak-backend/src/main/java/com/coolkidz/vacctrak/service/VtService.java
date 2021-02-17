package com.coolkidz.vacctrak.service;

import com.coolkidz.vacctrak.data.VtDao;
import com.coolkidz.vacctrak.data.VtPermsDao;
import com.coolkidz.vacctrak.data.VtUserDao;
import com.coolkidz.vacctrak.models.CenterPermission;
import com.coolkidz.vacctrak.models.StateVaccs;
import com.coolkidz.vacctrak.models.VaccCenter;
import com.coolkidz.vacctrak.models.VtUser;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.*;

@Service
public class VtService implements VtServiceInterface {

    private final VtDao vtDao;
    private final VtUserDao vtUsrDao;
    private final VtPermsDao vtPermsDao;

    public VtService(VtDao vtDao, VtUserDao VtUsrDao, VtPermsDao vtPermsDao) {
        this.vtDao = vtDao;
        this.vtUsrDao = VtUsrDao;
        this.vtPermsDao = vtPermsDao;
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

        List<StateVaccs> stateVaccsList = new ArrayList<StateVaccs>();

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
        stateVaccs.forEach((key, value) -> stateVaccsList.add(new StateVaccs(key, value.get(0), value.get(1))));

        return stateVaccsList;
    }

    @Override
    public VaccCenter getVaccCenterById(int id) {
        return vtDao.getVaccCenterById(id);
    }

    @Override
    public List<VaccCenter> getVaccCenterByState(String stateAbbr) {
        return vtDao.getVaccCenterByState(stateAbbr);
    }

    @Override
    public boolean updateVaccDoses(VaccCenter vaccCenter) {
        return vtDao.update(vaccCenter);
    }

    @Override
    public boolean deleteById(int id) {
        return vtDao.deleteById(id);
    }

    @Override
    public VtUser createUser(VtUser user) throws InvalidKeySpecException, NoSuchAlgorithmException {

        // Generate hashed password


        return vtUsrDao.createUser(user);
    }

    @Override
    public VtUser validateUser(VtUser user) {

        // Generate hashed password

        List<VtUser> users = vtUsrDao.validateUser(user);

        if(users.size() == 1) {
            VtUser validUser = users.get(0);
            validUser.setPassword(null);
            int userId = validUser.getId();
            validUser.setVaccCenterAccesses(vtPermsDao.getPermsByUserId(userId));

            return validUser;
        }
        return null;
    }
}

