package org.kuali.rice.student.lookup.keyvalues;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.core.search.dto.SortDirection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Daniel
 * Date: 2/25/13
 * Time: 11:27 AM
 * To change this template use File | Settings | File Templates.
 */
public class SubjectAreaValuesFinder extends OrgsOfTypeValuesFinder{

    /**
     * Find the Orgs with specified orgType.
     *
     * @param orgType
     * @return
     */
    public static List<KeyValue> findOrgs(String orgType) {
        List<KeyValue> orgEntities = new ArrayList<KeyValue>();
        SearchRequestInfo searchRequest = new SearchRequestInfo("org.search.generic");
        searchRequest.addParam("org.queryParam.orgOptionalType",orgType);
        searchRequest.setSortColumn("org.resultColumn.orgOptionalLongName");
        searchRequest.setSortDirection(SortDirection.ASC);
        try {
            SearchResultInfo results = getOrganizationService().search(searchRequest, ContextUtils.getContextInfo());

            for (SearchResultRowInfo result : results.getRows()) {
                String orgShortName = "";
                for (SearchResultCellInfo resultCell : result.getCells()) {
                    if ("org.resultColumn.orgShortName".equals(resultCell.getKey())) {
                        orgShortName = resultCell.getValue();
                    }
                }
                orgEntities.add(new ConcreteKeyValue(orgShortName, orgShortName));
            }
            return orgEntities;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<KeyValue> getKeyValues() {
        return findOrgs("kuali.org.SubjectCode");
    }
}

