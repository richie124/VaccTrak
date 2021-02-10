package com.coolkidz.vacctrak.data;

import com.coolkidz.vacctrak.models.VaccCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class VtDbDao implements VtDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public VtDbDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public VaccCenter createVaccCenter(VaccCenter vaccCenter) {
        return null;
    }
}
