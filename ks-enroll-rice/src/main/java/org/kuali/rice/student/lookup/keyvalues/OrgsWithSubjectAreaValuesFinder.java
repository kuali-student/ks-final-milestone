package org.kuali.rice.student.lookup.keyvalues;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Daniel
 * Date: 5/2/13
 * Time: 3:22 PM
 * Key Value finder that searches for all orgs that are children of subject codes. This links to admin orgs of
 * course offerings for now.
 */
public class OrgsWithSubjectAreaValuesFinder extends KeyValuesBase {

    private static OrganizationService organizationService = (OrganizationService) GlobalResourceLoader
            .getService(new QName("http://student.kuali.org/wsdl/organization", "OrganizationService"));

    @Override
    public List<KeyValue> getKeyValues() {
        //Setup a search on the org service for all orgs that are children of subject codes
        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        SearchRequestInfo searchRequestInfo = new SearchRequestInfo("org.search.relatedOrgQuickViewByRelationType");
        searchRequestInfo.addParam("org.queryParam.optionalOrgRelationType", "kuali.org.org.relation.type.subjectcode2org");

        try {
            //Perform the search
            SearchResultInfo searchResultInfo = organizationService.search(searchRequestInfo, ContextUtils.createDefaultContextInfo());

            //Parse the search results
            for (SearchResultRowInfo row : searchResultInfo.getRows()) {
                String orgId = null;
                String orgName = null;
                for (SearchResultCellInfo cell : row.getCells()) {
                    if ("org.resultColumn.orgId".equals(cell.getKey())) {
                        orgId = cell.getValue();
                    } else if ("org.resultColumn.orgShortName".equals(cell.getKey())) {
                        orgName = cell.getValue();
                    }
                }
                //Add a new key value based on OrgId and ShortName
                keyValues.add(new ConcreteKeyValue(orgId, orgName));
            }

        } catch (Exception e) {
            throw new RuntimeException("Error searching for Org Ids", e);
        }

        return keyValues;

    }
}
