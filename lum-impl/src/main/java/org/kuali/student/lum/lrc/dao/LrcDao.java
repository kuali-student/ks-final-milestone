package org.kuali.student.lum.lrc.dao;

import java.util.List;

import org.kuali.student.core.dao.CrudDao;
import org.kuali.student.lum.lrc.entity.Credit;

public interface LrcDao extends CrudDao  {
    List<Credit> getCreditsByKeyList(List<String> creditKeyList);
}
