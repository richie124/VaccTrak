package com.coolkidz.vacctrak.data;

import com.coolkidz.vacctrak.models.CenterPermission;
import com.coolkidz.vacctrak.models.VtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class VtDbPermsDao implements VtPermsDao {

    private final JdbcTemplate jdbc;

    @Autowired
    public VtDbPermsDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<CenterPermission> getPermsByUserId(int userId) {
        final String sql = "select * from permissions where userId=?;";
        return jdbc.query(sql, new PermsMapper(), userId);
    }


    private static final class PermsMapper implements RowMapper<CenterPermission> {

        @Override
        public CenterPermission mapRow(ResultSet rs, int index) throws SQLException {
            CenterPermission perms = new CenterPermission();
            perms.setPermId(rs.getInt("PermId"));
            perms.setUserId(rs.getInt("UserId"));
            perms.setVacCenterId(rs.getInt("VacCenterId"));
            return perms;
        }
    }
}
