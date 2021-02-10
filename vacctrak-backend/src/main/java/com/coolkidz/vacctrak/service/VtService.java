package com.coolkidz.vacctrak.service;

import com.coolkidz.vacctrak.data.VtDao;
import com.coolkidz.vacctrak.models.VaccCenter;
import org.springframework.stereotype.Service;

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
}
