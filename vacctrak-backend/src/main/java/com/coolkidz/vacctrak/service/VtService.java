package com.coolkidz.vacctrak.service;

import com.coolkidz.vacctrak.data.VtDao;
import com.coolkidz.vacctrak.models.VaccCenter;
import org.springframework.stereotype.Service;

@Service
public class VtService implements VtServiceInterface {

    private final VtDao vtDao;

    public VtService(VtDao vtDao) {
        this.vtDao = vtDao;
    }

    @Override
    public VaccCenter createVaccCenter(VaccCenter newVaccCenter) {
        return null;
    }
}
