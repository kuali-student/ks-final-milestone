/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

package org.kuali.student.lum.lo.dao;

import java.util.List;

import org.kuali.student.core.dao.CrudDao;
import org.kuali.student.core.dao.SearchableDao;
import org.kuali.student.core.exceptions.DependentObjectsExistException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.UnsupportedActionException;
import org.kuali.student.lum.lo.entity.Lo;
import org.kuali.student.lum.lo.entity.LoCategory;
import org.kuali.student.lum.lo.entity.LoLoRelation;

public interface LoDao extends CrudDao, SearchableDao  {
	public List<Lo> getLoByIdList(List<String> loIds);
	public List<Lo> getLosByLoCategory(String loCategoryId);
	public List<LoCategory> getLoCategories(String loHierarchyKey);
    public boolean addLoCategoryToLo(String loCategoryId, String loId) throws UnsupportedActionException, DoesNotExistException;
    public boolean removeLoCategoryFromLo(String loCategoryId, String loId) throws DoesNotExistException;
    public boolean deleteLoCategory(String loCategoryId) throws DoesNotExistException, DependentObjectsExistException;
    public boolean deleteLo(String loId) throws DoesNotExistException, DependentObjectsExistException;
    public List<Lo> getRelatedLosByLoId(String loId, String loLoRelationTypeId) throws DoesNotExistException;
    public List<Lo> getLosByRelatedLoId(String relatedLoId, String loLoRelationTypeId) throws DoesNotExistException;
	public void deleteLoLoRelation(String loLoRelationId) throws DoesNotExistException, DependentObjectsExistException;
	public List<LoCategory> getLoCategoriesForLo(String loId);
	public List<String> getAllowedLoLoRelationTypesForLoType(String loTypeKey, String relatedLoTypeKey);
	public List<Lo> getLosByRepository(String loRepositoryId);
	public List<LoLoRelation> getLoLoRelationsByLoId(String loId);
}
