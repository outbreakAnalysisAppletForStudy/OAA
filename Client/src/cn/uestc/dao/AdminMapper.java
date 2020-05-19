package cn.uestc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class AdminMapper implements RowMapper {
	
	public Admin mapRow(ResultSet rs,int rowNUM) throws SQLException {
		Admin admin = new Admin();
		admin.setArea(rs.getString("area"));
		admin.setHashcode(rs.getString("hashcode"));
		admin.setId(rs.getString("id"));
		admin.setLevel(rs.getByte("level"));
		admin.setName(rs.getString("name"));
		admin.setTel(rs.getString("tel"));
		return admin;
	}
	
	
}

