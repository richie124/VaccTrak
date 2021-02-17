package com.coolkidz.vacctrak.data;

import com.coolkidz.vacctrak.models.VtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.List;

@Repository
public class VtDbUserDao implements VtUserDao{

    private final JdbcTemplate jdbc;

    @Autowired
    public VtDbUserDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    @Transactional
    public VtUser createUser(VtUser vtUser) {

        final String querySql = "select * from users where UserName=?;";
        List<VtUser> users = jdbc.query(querySql, new UsersMapper(), vtUser.getUserName());

        if (users.size() < 1) {
            // Insert new user into Users table
            final String insertSql = "INSERT INTO users(UserName, UserPassword) VALUES(?,?);";
            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

            jdbc.update((Connection conn) -> {

                PreparedStatement statement = conn.prepareStatement(
                        insertSql,
                        Statement.RETURN_GENERATED_KEYS);

                statement.setString(1, vtUser.getUserName());
                statement.setString(2, vtUser.getPassword());
                return statement;

            }, keyHolder);

            vtUser.setId(keyHolder.getKey().intValue());


            insertPerms(vtUser);

            return vtUser;
        }
        // vtUser.setUserName("AlreadyTaken");
        return null;
    }

    private void insertPerms(VtUser vtUser) {
        List<Integer> vaccCenterPerms = vtUser.getVaccCenterAccesses();
        for (int i = 0; i < vaccCenterPerms.size(); i++) {

            int vaccCenterId = vaccCenterPerms.get(i);

            final String insertSql = "INSERT INTO Permissions(UserId, VacCenterId) VALUES(?,?);";
            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

            jdbc.update((Connection conn) -> {

                PreparedStatement statement = conn.prepareStatement(
                        insertSql,
                        Statement.RETURN_GENERATED_KEYS);

                statement.setInt(1, vtUser.getId());
                statement.setInt(2, vaccCenterId);
                return statement;

            }, keyHolder);

        }
    }

    private void userExists(VtUser vtUser) {

    }

    @Override
    public List<VtUser> validateUser(VtUser vtUser) {
        final String sql = "select * from Users where UserName=? and UserPassword=?;";
        return jdbc.query(sql, new UsersMapper(), vtUser.getUserName(), vtUser.getPassword());

    }


    private static final class UsersMapper implements RowMapper<VtUser> {

        @Override
        public VtUser mapRow(ResultSet rs, int index) throws SQLException {
            VtUser vtUser = new VtUser();
            vtUser.setId(rs.getInt("UserId"));
            vtUser.setUserName(rs.getString("UserName"));
            vtUser.setPassword(rs.getString("UserPassword"));
            return vtUser;
        }
    }
}
