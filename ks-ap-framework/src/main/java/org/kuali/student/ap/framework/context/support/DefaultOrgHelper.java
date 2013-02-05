package org.kuali.student.ap.framework.context.support;

import org.apache.log4j.Logger;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.CourseSearchConstants;
import org.kuali.student.ap.framework.context.OrgHelper;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.infc.SearchResult;
import org.kuali.student.r2.core.search.infc.SearchResultCell;
import org.kuali.student.r2.core.search.infc.SearchResultRow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultOrgHelper implements OrgHelper{

    private final Logger logger = Logger.getLogger(DefaultOrgHelper.class);
    public OrganizationService organizationService;

    public HashMap<String, List<OrgInfo>> orgTypeCache;

    @Override
    public HashMap<String, List<OrgInfo>> getOrgTypeCache() {
        if (this.orgTypeCache == null) {
            this.orgTypeCache = new HashMap<String, List<OrgInfo>>();
        }
        return this.orgTypeCache;
    }

    @Override
    public void setOrgTypeCache(
            HashMap<String, List<OrgInfo>> orgTypeCache) {
        this.orgTypeCache = orgTypeCache;
    }

    @Override
    public List<OrgInfo> getOrgInfo(String param,
                                           String searchRequestKey, String paramKey, ContextInfo context) {
        if (this.getOrgTypeCache() != null
                && this.getOrgTypeCache().containsKey(param)) {
            return getOrgTypeCache().get(param);
        } else {
            List<OrgInfo> orgInfoList = new ArrayList<OrgInfo>();
            SearchRequestInfo searchRequest = new SearchRequestInfo(
                    searchRequestKey);
            searchRequest.addParam(paramKey, param);
            SearchResult searchResult = new SearchResultInfo();
            try {
                searchResult = KsapFrameworkServiceLocator
                        .getOrganizationService()
                        .search(searchRequest, context);
            } catch (MissingParameterException e) {
                logger.error("Search Failed to get the Organization Data ", e);
            } catch (InvalidParameterException e) {
                logger.error("Search Failed to get the Organization Data ", e);
            } catch (OperationFailedException e) {
                logger.error("Search Failed to get the Organization Data ", e);
            } catch (PermissionDeniedException e) {
                logger.error("Search Failed to get the Organization Data ", e);
            }
            for (SearchResultRow row : searchResult.getRows()) {
                OrgInfo orgInfo = new OrgInfo();
                orgInfo.setId(getCellValue(row, "org.resultColumn.orgId"));
                orgInfo.setShortName(getCellValue(row,
                        "org.resultColumn.orgShortName"));
                orgInfo.setLongName(getCellValue(row,
                        "org.resultColumn.orgLongName"));
                orgInfoList.add(orgInfo);

            }
            if (orgInfoList.size() > 0) {
                this.getOrgTypeCache().put(param, orgInfoList);
            }
            return orgInfoList;
        }
    }

    @Override
    public Map<String, String> getSubjectAreas() {
        Map<String, String> subjects = new HashMap<String, String>();
        SearchRequestInfo searchRequest = new SearchRequestInfo(
                CourseSearchConstants.ORG_QUERY_SEARCH_SUBJECT_AREAS);
        SearchResult searchResult = new SearchResultInfo();
        try {
            searchResult = KsapFrameworkServiceLocator.getOrganizationService()
                    .search(searchRequest,
                            KsapFrameworkServiceLocator.getContext()
                                    .getContextInfo());
        } catch (MissingParameterException e) {
            logger.error("Search Failed to get the Organization Data ", e);
        } catch (InvalidParameterException e) {
            logger.error("Search Failed to get the Organization Data ", e);
        } catch (OperationFailedException e) {
            logger.error("Search Failed to get the Organization Data ", e);
        } catch (PermissionDeniedException e) {
            logger.error("Search Failed to get the Organization Data ", e);
        }
        for (SearchResultRow row : searchResult.getRows()) {
            subjects.put(getCellValue(row, "org.resultColumn.attrValue"),
                    getCellValue(row, "org.resultColumn.name"));
        }
        return subjects;
    }

    /* Used for the subjects area's with trimmed key value */
    @Override
    public Map<String, String> getTrimmedSubjectAreas() {
        Map<String, String> subjects = new HashMap<String, String>();
        SearchRequestInfo searchRequest = new SearchRequestInfo(
                CourseSearchConstants.ORG_QUERY_SEARCH_SUBJECT_AREAS);
        SearchResult searchResult = new SearchResultInfo();
        try {
            searchResult = KsapFrameworkServiceLocator.getOrganizationService()
                    .search(searchRequest,
                            KsapFrameworkServiceLocator.getContext()
                                    .getContextInfo());
        } catch (MissingParameterException e) {
            logger.error("Search Failed to get the Organization Data ", e);
        } catch (InvalidParameterException e) {
            logger.error("Search Failed to get the Organization Data ", e);
        } catch (OperationFailedException e) {
            logger.error("Search Failed to get the Organization Data ", e);
        } catch (PermissionDeniedException e) {
            logger.error("Search Failed to get the Organization Data ", e);
        }
        for (SearchResultRow row : searchResult.getRows()) {
            subjects.put(
                    getCellValue(row, "org.resultColumn.attrValue").trim(),
                    getCellValue(row, "org.resultColumn.orgLongName"));

        }
        return subjects;
    }

    @Override
    public String getCellValue(SearchResultRow row, String key) {
        for (SearchResultCell cell : row.getCells()) {
            if (key.equals(cell.getKey())) {
                return cell.getValue();
            }
        }
        throw new RuntimeException("cell result '" + key + "' not found");
    }

}
