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
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
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
//    public VaccCenter createVaccCenter(VaccCenter newVaccCenter) {
//        return vtDao.createVaccCenter(newVaccCenter);
//    }
    public VaccCenter createVaccCenter(HashMap newVaccCenterData) {

        VaccCenter newVaccCenter = new VaccCenter();

        newVaccCenter.setName((String) newVaccCenterData.get("name"));
        newVaccCenter.setAddress((String) newVaccCenterData.get("address"));
        newVaccCenter.setCity((String) newVaccCenterData.get("city"));
        newVaccCenter.setState((String) newVaccCenterData.get("state"));
        newVaccCenter.setZipcode((String) newVaccCenterData.get("zipcode"));
        newVaccCenter.setLatitude((String) newVaccCenterData.get("latitude"));
        newVaccCenter.setLongitude((String) newVaccCenterData.get("longitude"));
        newVaccCenter.setPhoneNumber((String) newVaccCenterData.get("phoneNumber"));
        newVaccCenter.setSingleDoses(Integer.parseInt((String) newVaccCenterData.get("singleDoses")));
        newVaccCenter.setDoubleDoses(Integer.parseInt((String) newVaccCenterData.get("doubleDoses")));

        newVaccCenter = vtDao.createVaccCenter(newVaccCenter);

        List<Integer> vaccCenterList = new ArrayList<>();

        vaccCenterList.add(newVaccCenter.getId());

        VtUser newVtUser = new VtUser();

        newVtUser.setId(Integer.parseInt((String) newVaccCenterData.get("userId")));
        newVtUser.setVaccCenterAccesses(vaccCenterList);

        vtPermsDao.setPerms(newVtUser);

        return newVaccCenter;
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

        user.setPassword(encryptPsswd(user.getPassword()));

        VtUser createdUser = vtUsrDao.createUser(user);

        if(createdUser != null){
            vtPermsDao.setPerms(user);

            int userId = createdUser.getId();
            createdUser.setVaccCenterAccesses(vtPermsDao.getPermsByUserId(userId));
        }


        return createdUser;
    }

    @Override
    public VtUser validateUser(VtUser user) {

        // Generate hashed password

        user.setPassword(encryptPsswd(user.getPassword()));

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

    @Override
    public List<Integer> insertPerms(VtUser user) {
        return vtPermsDao.setPerms(user);
    }

    private String encryptPsswd(String input) {
        try {
            // getInstance() method is called with algorithm SHA-512
            MessageDigest md = MessageDigest.getInstance("SHA-512");

            // digest() method is called
            // to calculate message digest of the input string
            // returned as array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);

            // Add preceding 0s to make it 32 bit
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }

            // return the HashText
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}

