package org.kuali.student.lum.lrc.dao;

import java.util.List;

import org.kuali.student.core.dao.CrudDao;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.lum.lrc.entity.Credential;
import org.kuali.student.lum.lrc.entity.Credit;
import org.kuali.student.lum.lrc.entity.Grade;
import org.kuali.student.lum.lrc.entity.ResultComponentType;

public interface LrcDao extends CrudDao  {
    public List<Credential> getCredentialsByIdList(List<String> creditIdList);
    public List<String> getCredentialIdsByCredentialType(String creditTypeId);
    public List<Credit> getCreditsByIdList(List<String> creditIdList);
    public List<String> getCreditIdsByCreditType(String creditTypeId);
    public List<Grade> getGradesByIdList(List<String> creditIdList);
    public List<String> getGradeIdsByGradeType(String creditTypeId);
    public List<Grade> getGradesByScale(String scale);
    public List<String> getResultComponentIdsByResult(String resultValueId, String resultComponentTypeKey);
    public List<String> getResultComponentIdsByResultComponentType(String resultComponentTypeKey);
    public ResultComponentType getResultComponentType(String resultComponentTypeKey) throws DoesNotExistException;
}
