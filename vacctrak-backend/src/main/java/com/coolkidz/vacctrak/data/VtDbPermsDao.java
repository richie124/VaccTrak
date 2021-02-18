package com.coolkidz.vacctrak.data;

import com.coolkidz.vacctrak.models.CenterPermission;
import com.coolkidz.vacctrak.models.VtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class VtDbPermsDao implements VtPermsDao {

    private final JdbcTemplate jdbc;

    @Autowired
    public VtDbPermsDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<Integer> setPerms(VtUser user) {

        //deletePerms(user);

        List<Integer> vaccCenterPerms = user.getVaccCenterAccesses();
        for (int i = 0; i < vaccCenterPerms.size(); i++) {

            int vaccCenterId = vaccCenterPerms.get(i);

            final String insertSql = "INSERT INTO Permissions(UserId, VacCenterId) VALUES(?,?);";
            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

            jdbc.update((Connection conn) -> {

                PreparedStatement statement = conn.prepareStatement(
                        insertSql,
                        Statement.RETURN_GENERATED_KEYS);

                statement.setInt(1, user.getId());
                statement.setInt(2, vaccCenterId);
                return statement;

            }, keyHolder);

        }

        return getPermsByUserId(user.getId());
    }

    @Override
    public boolean deletePerms(VtUser user) {
        final String sql = "DELETE FROM permissions WHERE UserId = ?;";
        return jdbc.update(sql, user.getId()) > 0;
    }

    @Override
    public List<Integer> getPermsByUserId(int userId) {
        final String sql = "select VacCenterId from permissions where userId=?;";
        return jdbc.query(sql, new PermsMapper(), userId);
    }

    private static final class PermsMapper implements RowMapper<Integer> {

        @Override
        public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
            return resultSet.getInt("VacCenterId");
        }
    }


//    private static final class PermsMapper implements RowMapper<CenterPermission> {
//
//        @Override
//        public CenterPermission mapRow(ResultSet rs, int index) throws SQLException {
//            CenterPermission perms = new CenterPermission();
////            perms.setPermId(rs.getInt("PermId"));
////            perms.setUserId(rs.getInt("UserId"));
//            perms.setVacCenterId(rs.getInt("VacCenterId"));
//            return perms;
//        }
//    }
}
