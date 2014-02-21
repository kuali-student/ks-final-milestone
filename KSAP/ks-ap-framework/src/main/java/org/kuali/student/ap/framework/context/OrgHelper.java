package org.kuali.student.ap.framework.context;


import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.infc.SearchResult;
import org.kuali.student.r2.core.search.infc.SearchResultCell;
import org.kuali.student.r2.core.search.infc.SearchResultRow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface OrgHelper {

    public HashMap<String, List<OrgInfo>> getOrgTypeCache();

    /**
     * Sets the cache to a set value
     */
    public void setOrgTypeCache(
            HashMap<String, List<OrgInfo>> orgTypeCache);

    /**
     * Gets information for entries matching the passed in params.
     *
     * @return All entries found.
     */
    public List<OrgInfo> getOrgInfo(String param,
                                           String searchRequestKey, String paramKey, ContextInfo context);

    /**
     * Gets map of subject areas for the organizations
     *
     * @return Map of subjects
     */
    public Map<String, String> getSubjectAreas();

    /* Used for the subjects area's with trimmed key value */
    public Map<String, String> getTrimmedSubjectAreas();

    /**
     * Gets an individual value of a search result entry.
     *
     * @return Entry's value
     */
    public String getCellValue(SearchResultRow row, String key);

}
