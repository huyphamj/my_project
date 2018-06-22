package com.database.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.database.bean.JackpotBean;

import common.jdbc.JdbcConnectionPool;
import common.jdbc.core.RowMapper;
import common.jdbc.core.simple.SimpleJdbcDaoSupport;

public class JackpotDao extends SimpleJdbcDaoSupport {
	public JackpotDao(JdbcConnectionPool pool) {
		super(pool);
	}

	public class JackpotMapper implements RowMapper {

		@Override
		public JackpotBean mapRow(ResultSet rs, int arg1) throws SQLException {
			JackpotBean b = new JackpotBean();
			b.setGen_date(rs.getDate("gen_date"));
			b.setGiai_ba(rs.getInt("giai_ba"));
			b.setGiai_nhat(rs.getInt("giai_nhat"));
			b.setGiai_nhi(rs.getInt("giai_nhi"));
			b.setValue(rs.getLong("value"));
			b.setGiai_jackpot(rs.getInt("giai_jackpot"));
			return b;
		}
	}

	public List<JackpotBean> getByGenDate(String gen_date) {
		String sql = "SELECT * FROM jackpot WHERE date(gen_date) = '"
				+ gen_date.substring(0, gen_date.indexOf(" ")).trim() + "'";
		return getSimpleJdbcTemplate().query(sql, new JackpotMapper());
	}

	public void insert(String gen_date, long value, int giai_jackpot, int giai_nhat, int giai_nhi, int giai_ba) {
		if (getByGenDate(gen_date).size() > 0)
			return;
		String sql = "INSERT INTO jackpot (gen_date, value, giai_jackpot, giai_nhat, giai_nhi, giai_ba) VALUES ('"
				+ gen_date + "', " + value + ", " + giai_jackpot + ", " + giai_nhat + ", " + giai_nhi + ", " + giai_ba
				+ ")";
		getSimpleJdbcTemplate().update(sql);
	}
}
