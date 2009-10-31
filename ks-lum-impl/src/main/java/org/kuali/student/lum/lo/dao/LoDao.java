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
package org.kuali.student.lum.lo.dao;

import java.util.List;

import org.kuali.student.core.dao.CrudDao;
import org.kuali.student.core.dao.SearchableDao;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DependentObjectsExistException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.lum.lo.entity.Lo;
import org.kuali.student.lum.lo.entity.LoCategory;

public interface LoDao extends CrudDao, SearchableDao  {
	public List<Lo> getLoByIdList(List<String> loIds);
	public List<LoCategory> getLoCategoriesForLo(String loId);
	public List<Lo> getLosByLoCategory(String loCategoryId);
	public List<LoCategory> getLoCategories(String loHierarchyKey);
	public List<Lo> getLoChildren(String loId);
	public List<String> getAllDescendantLoIds(String loId);
	public List<Lo> getLoParents(String loId);
	public boolean isDescendant(String loId, String descendantLoId);
	public List<String> getAncestors(String loId);
	public List<Lo> getEquivalentLos(String loId);
	public List<Lo> getLoEquivalents(String loId);
    public boolean isEquivalent(String loId, String equivalentLoId);
    public boolean addLoCategoryToLo(String loCategoryId, String loId);
    public boolean removeLoCategoryFromLo(String loCategoryId, String loId);
    public boolean addChildLoToLo(String loId, String parentLoId) throws DoesNotExistException, AlreadyExistsException;
    public boolean removeChildLoFromLo(String loId, String parentLoId) throws DoesNotExistException, DependentObjectsExistException;
    public boolean addEquivalentLoToLo(String loId, String equivalentLoId) throws DoesNotExistException, AlreadyExistsException;
    public boolean removeEquivalentLoFromLo(String loId, String equivalentLoId);
    public boolean deleteLoCategory(String loCategoryId) throws DoesNotExistException, DependentObjectsExistException;
    public boolean deleteLo(String loId) throws DoesNotExistException, DependentObjectsExistException;
}
