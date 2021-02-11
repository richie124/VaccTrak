package com.coolkidz.vacctrak.data;

import com.coolkidz.vacctrak.models.VaccCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class VtDbDao implements VtDao {

    private final JdbcTemplate jdbc;

    @Autowired
    public VtDbDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public VaccCenter createVaccCenter(VaccCenter vaccCenter) {

        final String sql = "INSERT INTO VaccineSites(vacCenter, Address, City, StateAbbreviation, ZipCode, PhoneNumber, NumFirstVaccine, NumSecondVaccine) VALUES(?,?,?,?,?,?,?,?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, vaccCenter.getName());
            statement.setString(2, vaccCenter.getAddress());
            statement.setString(3, vaccCenter.getCity());
            statement.setString(4, vaccCenter.getState());
            statement.setString(5, vaccCenter.getZipcode());
            statement.setString(6, vaccCenter.getPhoneNumber());
            statement.setInt(7, vaccCenter.getSingleDoses());
            statement.setInt(8, vaccCenter.getDoubleDoses());
            return statement;

        }, keyHolder);

        vaccCenter.setId(keyHolder.getKey().intValue());

        return vaccCenter;
    }

    @Override
    public List<VaccCenter> getAllVaccCenters() {
        final String sql = "SELECT * FROM VaccineSites;";
        return jdbc.query(sql, new VaccCenterMapper());
    }
    

    @Override
    public VaccCenter getVaccCenterById(int id) {
        final String sql = "SELECT * FROM VaccineSites WHERE ID=?;";
        return jdbc.queryForObject(sql, new VaccCenterMapper(), id);
    }

    @Override
    public List<VaccCenter> getVaccCenterByState(String stateAbbr) {
        final String sql = "SELECT * FROM VaccineSites WHERE StateAbbreviation=?;";
        return jdbc.query(sql, new VaccCenterMapper(), stateAbbr);
    }

    // NEED TO TEST OUT UPDATE
    @Override
    public boolean update(VaccCenter vaccCenter) {
        final String sql = "UPDATE VaccineSites SET "
                + "NumFirstVaccine = ?, "
                + "NumSecondVaccine = ?, "
                + "WHERE ID = ?;";

        return jdbc.update(sql,
                vaccCenter.getSingleDoses(),
                vaccCenter.getDoubleDoses(),
                vaccCenter.getId()) > 0;
    }

    // NEED TO TEST OUT DELETE
    @Override
    public boolean deleteById(int id) {
        final String sql = "DELETE FROM VaccineSites WHERE ID = ?;";
        return jdbc.update(sql, id) > 0;
    }

    private static final class VaccCenterMapper implements RowMapper<VaccCenter> {

        @Override
        public VaccCenter mapRow(ResultSet rs, int index) throws SQLException {
            VaccCenter vaccCenter = new VaccCenter();
            vaccCenter.setId(rs.getInt("ID"));
            vaccCenter.setName(rs.getString("VacCenter"));
            vaccCenter.setAddress(rs.getString("Address"));
            vaccCenter.setCity(rs.getString("City"));
            vaccCenter.setState(rs.getString("StateAbbreviation"));
            vaccCenter.setZipcode(rs.getString("ZipCode"));
            vaccCenter.setPhoneNumber(rs.getString("PhoneNumber"));
            vaccCenter.setSingleDoses(rs.getInt("NumFirstVaccine"));
            vaccCenter.setDoubleDoses(rs.getInt("NumSecondVaccine"));
            return vaccCenter;
        }
    }

}
