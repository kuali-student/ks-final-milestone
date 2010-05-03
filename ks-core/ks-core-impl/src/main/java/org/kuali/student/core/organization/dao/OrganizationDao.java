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

package org.kuali.student.core.organization.dao;

import java.util.List;

import org.kuali.student.core.dao.CrudDao;
import org.kuali.student.core.dao.SearchableDao;
import org.kuali.student.core.organization.dto.OrgTreeInfo;
import org.kuali.student.core.organization.entity.Org;
import org.kuali.student.core.organization.entity.OrgOrgRelation;
import org.kuali.student.core.organization.entity.OrgOrgRelationType;
import org.kuali.student.core.organization.entity.OrgPersonRelation;
import org.kuali.student.core.organization.entity.OrgPersonRelationType;
import org.kuali.student.core.organization.entity.OrgPositionRestriction;

public interface OrganizationDao extends CrudDao, SearchableDao{

    public List<OrgPersonRelation> getAllOrgPersonRelationsByPerson(String personId);
    public List<OrgPersonRelation> getAllOrgPersonRelationsByOrg(String orgId);
    public List<String> getPersonIdsForOrgByRelationType(String orgId, String orgPersonRelationTypeKey);
	public List<Org> getOrganizationsByIdList(List<String> orgIdList);
	public List<OrgOrgRelation> getOrgOrgRelationsByOrg(String orgId);
	public List<OrgPositionRestriction> getPositionRestrictionsByOrg(String orgId);
	public List<String> getAllAncestors(String orgId, String orgHierarchy);
	public List<String> getAllDescendants(String orgId, String orgHierarchy);
	public List<OrgOrgRelationType> getOrgOrgRelationTypesForOrgHierarchy(String orgHierarchyKey);
	public boolean validatePositionRestriction(String orgId, String orgPersonRelationTypeKey);
	public List<OrgPersonRelationType> getOrgPersonRelationTypesForOrgType(String orgTypeKey);
	public List<OrgOrgRelation> getOrgOrgRelationsByIdList(List<String> orgOrgRelationIdList);
	public List<OrgPersonRelation> getOrgPersonRelationsByIdList(List<String> orgPersonRelationIdList);
	public List<OrgPersonRelation> getOrgPersonRelationsByPerson(String personId, String orgId);
	public List<OrgOrgRelationType> getOrgOrgRelationTypesForOrgType(String orgTypeKey);
	public List<OrgOrgRelation> getOrgOrgRelationsByRelatedOrg(String relatedOrgId);
	public List<OrgTreeInfo> getOrgTreeInfo(String rootOrgId, String orgHierarchyId);
	public boolean hasOrgOrgRelation(String orgId, String comparisonOrgId, String orgOrgRelationTypeKey);
	public boolean hasOrgPersonRelation(String orgId, String personId, String orgPersonRelationTypeKey);
	public OrgPositionRestriction getPositionRestrictionByOrgAndPersonRelationTypeKey(String orgId, String orgPersonRelationTypeKey);
	public Long getOrgMemebershipCount(String orgId);
}
