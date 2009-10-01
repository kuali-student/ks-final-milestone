package org.kuali.rice.student.lookup.keyvalues;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.kuali.rice.core.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kns.lookup.keyvalues.KeyValuesBase;
import org.kuali.rice.kns.web.ui.KeyLabelPair;
import org.kuali.student.core.organization.service.OrganizationService;
import org.kuali.student.core.search.dto.QueryParamValue;
import org.kuali.student.core.search.dto.Result;
import org.kuali.student.core.search.dto.ResultCell;

public class OrgCocValuesFinder extends KeyValuesBase{
    public static List<KeyLabelPair> findCocOrgs() {
        List<KeyLabelPair> departments = new ArrayList<KeyLabelPair>();

        OrganizationService orgService = (OrganizationService) GlobalResourceLoader
                .getService(new QName(
                        "http://student.kuali.org/wsdl/organization",
                        "OrganizationService"));

        List<QueryParamValue> queryParamValues = new ArrayList<QueryParamValue>(2);
        QueryParamValue qpOrgType = new QueryParamValue();
        qpOrgType.setKey("org.queryParam.relationType");
        qpOrgType.setValue("kuali.org.CurriculumChild");
        queryParamValues.add(qpOrgType);
        
        qpOrgType = new QueryParamValue();
        qpOrgType.setKey("org.queryParam.orgType1");
        qpOrgType.setValue("kuali.org.College");
        queryParamValues.add(qpOrgType);
        
        qpOrgType = new QueryParamValue();
        qpOrgType.setKey("org.queryParam.orgType2");
        qpOrgType.setValue("kuali.org.Department");
        queryParamValues.add(qpOrgType);
        
        qpOrgType = new QueryParamValue();
        qpOrgType.setKey("org.queryParam.orgType3");
        qpOrgType.setValue("kuali.org.Division");
        queryParamValues.add(qpOrgType);
        
        qpOrgType = new QueryParamValue();
        qpOrgType.setKey("org.queryParam.relatedOrgType");
        qpOrgType.setValue("kuali.org.COC");
        queryParamValues.add(qpOrgType);

        try {
            List<Result> results = orgService.searchForResults("org.search.orgQuickViewByRelationTypeOrgTypeRelatedOrgTypeMulti",
                    queryParamValues);

            for (Result result : results) {
                String orgId = "";
                String orgShortName = "";
                for (ResultCell resultCell : result.getResultCells()) {
                    if ("org.resultColumn.orgId".equals(resultCell
                            .getKey())) {
                        orgId = resultCell.getValue();
                    } else if ("org.resultColumn.orgShortName"
                            .equals(resultCell.getKey())) {
                        orgShortName = resultCell.getValue();
                    }
                }
                // departments.add(new KeyLabelPair(orgId, orgShortName));
                departments.add(new KeyLabelPair(orgShortName, orgShortName));
            }

            return departments;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List getKeyValues() {

        return findCocOrgs();
    }

}
