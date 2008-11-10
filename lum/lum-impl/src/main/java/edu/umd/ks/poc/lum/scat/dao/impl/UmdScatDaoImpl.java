package edu.umd.ks.poc.lum.scat.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import edu.umd.ks.poc.lum.scat.dao.ScatDao;
import edu.umd.ks.poc.lum.scat.dto.ScatInfo;
import edu.umd.ks.poc.lum.scat.dto.ScatTableInfo;
import edu.umd.ks.poc.lum.scat.entity.Scat;
import edu.umd.ks.poc.lum.scat.entity.ScatTable;

public class UmdScatDaoImpl implements ScatDao {
	private SimpleJdbcTemplate simpleJdbcTemplate;

	public void setDataSource(DataSource dataSource) {
	    this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
	}

	public List<ScatInfo> findScatInfos(String scatTableNum ) {
	    String sql = "SELECT SCAT_TBL_NUM, SCAT_CD, SCAT_ABREV, SCAT_DESC" +
	    		     "  FROM PUB.SCAT_CD " +
	    		     " WHERE SCAT_TBL_NUM = ?";

	    ParameterizedRowMapper<ScatInfo> mapper = new ParameterizedRowMapper<ScatInfo>() {
	    
	        public ScatInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	ScatInfo scatInfo = new ScatInfo();
	        	scatInfo.setTableId(String.valueOf(rs.getInt("SCAT_TBL_NUM")));
	        	scatInfo.setCode(rs.getString("SCAT_CD"));
	        	scatInfo.setShortDesc(rs.getString("SCAT_ABREV"));
	        	scatInfo.setLongDesc(rs.getString("SCAT_DESC"));
	        	return scatInfo;
	        }
	    };

	    return this.simpleJdbcTemplate.query(sql, mapper, Integer.parseInt(scatTableNum)); 
	}

	public List<ScatTableInfo> searchScatTableInfo(String searchString) {
	    String sql = "SELECT SCAT_TBL_NUM, SCAT_TBL_NAME " +
	    		     "  FROM PUB.SCAT_TTL " +
	    		     " WHERE UPPER(SCAT_TBL_NAME) LIKE ?";
		
		ParameterizedRowMapper<ScatTableInfo> mapper = new ParameterizedRowMapper<ScatTableInfo>() {
		
		public ScatTableInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			ScatTableInfo scatTableInfo = new ScatTableInfo();
			scatTableInfo.setTableId(String.valueOf(rs.getInt("SCAT_TBL_NUM")));
			scatTableInfo.setTableDescription(rs.getString("SCAT_TBL_NAME"));
			return scatTableInfo;
		}
		};
		
		return this.simpleJdbcTemplate.query(sql, mapper, "%"+searchString.toUpperCase()+"%"); 
	}

	@Override
	public ScatTable createScatTable(ScatTable scatTable) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Method Not Implemented");
	}

	@Override
	public void deleteScatTable(String scatTableId) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Method Not Implemented");
	}

	@Override
	public ScatTable fetchScatTable(String scatTableId) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Method Not Implemented");
	}

	@Override
	public ScatTable updateScatTable(ScatTable scatTable) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Method Not Implemented");
	}

	@Override
	public List<ScatTable> searchScats(String searchString) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Method Not Implemented");
	}

	@Override
	public void deleteScat(Scat scat) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Method Not Implemented");
	}

	@Override
	public Scat createScat(Scat scat) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Method Not Implemented");
	}

	@Override
	public Scat fetchScat(String scatId) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Method Not Implemented");
	}


}
