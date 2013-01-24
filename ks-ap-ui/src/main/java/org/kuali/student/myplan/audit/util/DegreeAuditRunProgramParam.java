package org.kuali.student.myplan.audit.util;

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;
import org.kuali.student.myplan.audit.service.DegreeAuditConstants;
import org.kuali.student.myplan.plan.util.OrgHelper;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.organization.dto.OrgInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 *   Date                     @Author                            @Work Description
 *  January/23/2013           Soar, Donald                       Initial  Issuance  -  Coded a Program Key/Value finder for a DegreeAuditDetailsUI.xml Drop-Down Control
 */

//public class DegreeAuditRunProgramParam extends UifKeyValuesFinderBase implements Serializable {
   public class DegreeAuditRunProgramParam extends KeyValuesBase {

    private final Logger logger = Logger.getLogger(DegreeAuditRunProgramParam.class);

    private boolean blankOption;

    private HashMap<String, List<OrgInfo>> hashMap;

    public DegreeAuditRunProgramParam() {
        super();
    }

    @Override
    //public List<KeyValue> getKeyValues(ViewModel viewModel) {
    public List<KeyValue> getKeyValues() {
        // TODO: factory for context /mwfyffe
        ContextInfo context = new ContextInfo();
        List <OrgInfo> orgInfoList = OrgHelper.getOrgInfo(DegreeAuditConstants.PROGRAM_TYPE_KEY, DegreeAuditConstants.ORG_QUERY_SEARCH_BY_TYPE_REQUEST, DegreeAuditConstants.ORG_QUERY_PARAM, context);
        List<KeyValue> keyValues = null;

        if (orgInfoList != null && orgInfoList.size() > 0) {
            keyValues = new ArrayList<KeyValue>();
            for (OrgInfo entry : orgInfoList) {
                keyValues.add(new ConcreteKeyValue(entry.getId(), entry.getLongName()));
            }
            /*Removing Duplicate entries from Key values*/
            HashSet hs = new HashSet();
            hs.addAll(keyValues);
            keyValues.clear();
            keyValues.addAll(hs);
            Collections.sort(keyValues, new Comparator<KeyValue>() {
                @Override
                public int compare(KeyValue keyValue1, KeyValue keyValue2) {
                    return keyValue1.getValue().compareTo(keyValue2.getValue());
                }
            });
            Collections.reverse(keyValues);
            keyValues.add(new ConcreteKeyValue("", DegreeAuditConstants.DEFAULT_VALUE));
            Collections.reverse(keyValues);
        }else{
            logger.error("DegreeAuditRunProgramParam.getKeyValues(): No Values for programs found");
        }
        return keyValues;
    }
}
