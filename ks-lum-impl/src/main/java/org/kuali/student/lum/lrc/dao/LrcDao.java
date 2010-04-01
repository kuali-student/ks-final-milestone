/*
 * Copyright 2009 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * 
 * http://www.osedu.org/licenses/ECL-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.lum.lrc.dao;

import java.util.List;

import org.kuali.student.core.dao.CrudDao;
import org.kuali.student.core.dao.SearchableDao;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.lum.lrc.entity.Credential;
import org.kuali.student.lum.lrc.entity.Credit;
import org.kuali.student.lum.lrc.entity.Grade;
import org.kuali.student.lum.lrc.entity.ResultComponentType;

public interface LrcDao extends CrudDao, SearchableDao  {
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
