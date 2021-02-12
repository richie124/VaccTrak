package com.coolkidz.vacctrak.data;

import com.coolkidz.vacctrak.models.VtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.*;

@Repository
public class VtDbUserDao implements VtUserDao{

    private final JdbcTemplate jdbc;

    @Autowired
    public VtDbUserDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public VtUser createUser(VtUser vtUser) throws InvalidKeySpecException, NoSuchAlgorithmException {

        final String sql = "INSERT INTO Users(UserName, UserPassword) VALUES(?,?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, vtUser.getUserName());
            statement.setString(2, vtUser.getPassword());
            return statement;

        }, keyHolder);

        vtUser.setId(keyHolder.getKey().intValue());

        return vtUser;
    }

    private static final class UsersMapper implements RowMapper<VtUser> {

        @Override
        public VtUser mapRow(ResultSet rs, int index) throws SQLException {
            VtUser vtUser = new VtUser();
            vtUser.setId(rs.getInt("UserId"));
//            vtUser.setfName(rs.getString("FirstName"));
//            vtUser.setlName(rs.getString("LastName"));
            vtUser.setUserName(rs.getString("UserName"));
            vtUser.setPassword(rs.getString("UserPassword"));
            return vtUser;
        }
    }
}
