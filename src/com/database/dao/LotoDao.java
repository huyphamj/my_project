package com.database.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.database.bean.LotoBean;

import common.jdbc.JdbcConnectionPool;
import common.jdbc.core.RowMapper;
import common.jdbc.core.simple.SimpleJdbcDaoSupport;

public class LotoDao extends SimpleJdbcDaoSupport {
	public LotoDao(JdbcConnectionPool pool) {
		super(pool);
	}

	public class LottoMapper implements RowMapper {

		@Override
		public Object mapRow(ResultSet rs, int arg1) throws SQLException {
			LotoBean loto = new LotoBean();
			loto.setCat_id(rs.getInt("cat_id"));
			loto.setGen_date(rs.getDate("gen_date"));
			loto.setId(rs.getInt("id"));
			loto.setValue(rs.getString("value"));
			return loto;
		}
	}
}
