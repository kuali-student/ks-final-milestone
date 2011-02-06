package ca.ubc.student.kuali.lum.workflow.qualifierresolver;

import java.util.ArrayList;
import java.util.List;

import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.student.bo.KualiStudentKimAttributes;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.core.search.dto.SearchResultRow;
import org.kuali.student.lum.workflow.qualifierresolver.AbstractCocOrgQualifierResolver;

public abstract class AbstractOrgQualifierResolver extends
		AbstractCocOrgQualifierResolver {
	public String getQualificationId(RouteContext routeContext){
		List<AttributeSet> attributeSets = super.resolve(routeContext);
		String orgId = null;
		if (attributeSets.size() > 0 && attributeSets.get(0).size() > 0) {
			orgId = getAttribute(attributeSets, KualiStudentKimAttributes.QUALIFICATION_ORG_ID);
		}
		return orgId;
	}
	protected List<AttributeSet> attributeOrganizationSetFromSearchResult(String orgShortNameKey, String orgIdKey, List<OrgInfo> results){
		List<AttributeSet> returnAttrSetList = new ArrayList<AttributeSet>();
		if(results!=null){
			for(OrgInfo result:results){
				AttributeSet attributeSet = new AttributeSet();
				String orgId =  result.getId();
				String orgShortName = result.getShortName();
				
				if(orgShortNameKey!=null){
					attributeSet.put(orgShortNameKey, orgShortName);
				}
				if(orgIdKey!=null){
					attributeSet.put(orgIdKey, orgId);
				}
				attributeSet.put(KualiStudentKimAttributes.QUALIFICATION_ORG, orgShortName);
				attributeSet.put(KualiStudentKimAttributes.QUALIFICATION_ORG_ID, orgId);
				returnAttrSetList.add(attributeSet);
			}
		}
		return returnAttrSetList;
	}
    protected List<OrgInfo> getOrganizationsFromList(List<String> orgIdList) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
        return getOrganizationService().getOrganizationsByIdList(orgIdList);
    }
	protected List<AttributeSet> orgAttributeSetsFromAncestors(String orgId, String orgType, String orgShortNameKey,String orgIdKey){
		List<AttributeSet> returnAttributeSets = new ArrayList<AttributeSet>();
		List<OrgInfo> ancestorOrgs = null;
		
		if(orgId!=null){
			try {
				List<String> ancestorIds = getOrganizationService().getAllAncestors(orgId, KUALI_ORG_HIERARCHY_CURRICULUM);
				if(ancestorIds != null && ancestorIds.size() > 0) {
					ancestorOrgs = getOrganizationService().getOrganizationsByIdList(ancestorIds);
				}
			} catch (Exception e) {
				LOG.error("Error calling org service");
				throw new RuntimeException(e);
			}
			List<OrgInfo> filteredOrgs = new ArrayList<OrgInfo>();
			
			if(ancestorOrgs!=null){
				for(OrgInfo ancestorOrg:ancestorOrgs){
					if(orgType!=null && orgType.equals(ancestorOrg.getType())){
						filteredOrgs.add(ancestorOrg);
					}
				}
					
			}
			
			returnAttributeSets = attributeOrganizationSetFromSearchResult(orgShortNameKey, orgIdKey, filteredOrgs);
		}
		return returnAttributeSets;
	}
	
	protected AttributeSet attributeSetFromOrgId(String orgId, String orgShortNameKey, String orgIdKey){
		AttributeSet attributeSet = new AttributeSet();
		if ( (orgId != null) && (!"".equals(orgId.trim())) ) {
			try {
				OrgInfo orgInfo = getOrganizationService().getOrganization(orgId);
				if (orgInfo == null) {
					LOG.error("Cannot find valid Org with id: " + orgId);
				}
				else {
					String resolvedOrgShortName = orgInfo.getShortName();
					String resolvedOrgId = orgInfo.getId();
					
					
					if(orgShortNameKey!=null){
						attributeSet.put(orgShortNameKey, resolvedOrgShortName);
					}
					if(orgIdKey!=null){
						attributeSet.put(orgIdKey, resolvedOrgId);
					}
					attributeSet.put(KualiStudentKimAttributes.QUALIFICATION_ORG, resolvedOrgShortName);
					attributeSet.put(KualiStudentKimAttributes.QUALIFICATION_ORG_ID, resolvedOrgId);					
				}
			} catch (Exception e) {
				LOG.error("Error calling org service to retrieve org id: " + orgId);
				throw new RuntimeException(e);
			}
		}
		return attributeSet;		
		
	}
}
