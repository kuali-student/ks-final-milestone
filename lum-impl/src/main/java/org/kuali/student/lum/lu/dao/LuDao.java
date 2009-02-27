package org.kuali.student.lum.lu.dao;

import java.util.List;

import org.kuali.student.core.dao.CrudDao;
import org.kuali.student.lum.lu.entity.Clu;

public interface LuDao extends CrudDao{
	public List<Clu> getClusByIdList(List<String> cluIdList);
	public List<Clu> getClusByLuType(String luTypeKey, String luState);
}
