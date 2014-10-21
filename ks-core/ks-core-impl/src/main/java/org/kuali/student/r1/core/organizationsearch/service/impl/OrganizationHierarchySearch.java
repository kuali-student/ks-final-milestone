package org.kuali.student.r1.core.organizationsearch.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r1.core.organization.dao.OrganizationDao;
import org.kuali.student.r1.core.organization.entity.Org;
import org.kuali.student.r2.core.search.dto.*;

/**
 * This is a description of what this class does - pctsw don't forget to fill this in.
 * 
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 */
public class OrganizationHierarchySearch implements OrganizationSearch {

    private OrganizationDao organizationDao;

    public OrganizationHierarchySearch() {
        super();
    }

    public OrganizationDao getOrganizationDao() {
        return organizationDao;
    }

    public void setOrganizationDao(OrganizationDao organizationDao) {
        this.organizationDao = organizationDao;
    }

    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.r1.core.organizationsearch.service.impl.OrganizationSearch#search(org.kuali.student.r1.common.search.dto.SearchRequestInfo)
     */
    @Override
    public SearchResultInfo search(SearchRequestInfo searchRequest) {

        List<String> relatedOrgIds = null;
        List<String> orgTypes = null;
        String relationTypeKey = null;
        String orgOptionalId = null;
        String sortColumn = searchRequest.getSortColumn();
        SortDirection sortDirection = searchRequest.getSortDirection();
        
        for (SearchParamInfo param : searchRequest.getParams()) {
            if ("org.queryParam.relatedOrgIds".equals(param.getKey())) {
                relatedOrgIds = (List<String>) param.getValues();
                continue;
            } else if ("org.queryParam.optionalOrgTypeList".equals(param.getKey())) {
                orgTypes = (List<String>) param.getValues();
                continue;
            } else if ("org.queryParam.optionalRelationType".equals(param.getKey())) {
                relationTypeKey = (String) param.getValues().get(0);
                continue;
            } else if ("org.queryParam.relOrgOptionalId".equals(param.getKey())) {
                orgOptionalId = (String) param.getValues().get(0);
                continue;
            }
        }
        try {
            List<Org> orgs = null;
            if (orgOptionalId != null) {
                orgs = new ArrayList<Org>();
                orgs.add(organizationDao.fetch(Org.class, orgOptionalId));
            } else {
                orgs = doOrgHierarchySearch(relatedOrgIds, orgTypes, relationTypeKey);
            }

            // Create a search result for the return value
            SearchResultInfo searchResult = new SearchResultInfo();
            searchResult.setSortColumn(sortColumn);
            searchResult.setSortDirection(sortDirection);
            for (Org org : orgs) {
                SearchResultRowInfo resultRow = new SearchResultRowInfo();

                // Map the result cells
                resultRow.addCell("org.resultColumn.orgId", org.getId());
                resultRow.addCell("org.resultColumn.orgShortName", org.getShortName());
                resultRow.addCell("org.resultColumn.orgOptionalLongName", org.getLongName());
                resultRow.addCell("org.resultColumn.orgType", org.getType().getId());

                searchResult.getRows().add(resultRow);
            }
            
            searchResult.sortRows();
            
            return searchResult;
        } catch (DoesNotExistException e) {
            throw new RuntimeException("Error performing search");
        }

    }

    /**
     * This method ...
     * 
     * @param relatedOrgIds
     * @param orgTypes
     * @param relationTypeKey
     * @return
     * @throws MissingParameterException
     * @throws DoesNotExistException
     */
    private List<Org> doOrgHierarchySearch(List<String> relatedOrgIds, List<String> orgTypes, String relationTypeKey) throws DoesNotExistException {

        // Loop thru org types and related org ids to add the ancestors one by one.
        Set<Org> orgs = new HashSet<Org>();
        for (String orgTypeKey : orgTypes) {
            for (String relatedOrgId : relatedOrgIds) {
                Org childOrg = null;
                try {
                    childOrg = organizationDao.fetch(Org.class, relatedOrgId);
                } catch (DoesNotExistException e) {
                    continue;
                }
                Org parentOrg = findParentOrgForType(childOrg, orgTypeKey, relationTypeKey);

                // Add parent Org to list
                if (parentOrg != null) {
                    orgs.add(parentOrg);
                }
            }
        }

        return new ArrayList<Org>(orgs);
    }

    /**
     * This method recursively looks for a parent organisation of a given type.
     * 
     * @param org
     * @param orgTypeKey
     * @param relationTypeKey
     * @return Org
     */
    private Org findParentOrgForType(Org org, String orgTypeKey, String relationTypeKey) {
        // If the org type is not found, return null
        if (org == null) {
            return null;
        }
        // Return org if type is of given type
        if (org.getType().getId().equals(orgTypeKey)) {
            return org;
        }
        // Recursive call
        return findParentOrgForType(organizationDao.getOrgByRelatedOrgAndType(org.getId(), relationTypeKey), orgTypeKey, relationTypeKey);
    }

}
