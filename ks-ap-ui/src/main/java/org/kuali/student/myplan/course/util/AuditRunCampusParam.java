package org.kuali.student.myplan.course.util;

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;
import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.myplan.plan.util.OrgHelper;

import java.util.*;

/**
 * Logic for building list of FacetItems and coding CourseSearchItems.
 */
public class AuditRunCampusParam extends KeyValuesBase {

    private final Logger logger = Logger.getLogger(AuditRunCampusParam.class);

    private boolean blankOption;


    private HashMap<String, List<OrgInfo>> hashMap;

    public HashMap<String, List<OrgInfo>> getHashMap() {
        if (this.hashMap == null) {
            this.hashMap = new HashMap<String, List<OrgInfo>>();
        }
        return this.hashMap;
    }

    public void setHashMap(HashMap<String, List<OrgInfo>> hashMap) {
        this.hashMap = hashMap;
    }

    @Override
    public List<KeyValue> getKeyValues() {
        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        if (blankOption) {
            keyValues.add(new ConcreteKeyValue("", ""));
        }
        List<OrgInfo> orgInfoList = new ArrayList<OrgInfo>();
        try {
            if (!this.getHashMap().containsKey(CourseSearchConstants.CAMPUS_LOCATION)) {
                orgInfoList = OrgHelper.getOrgInfo(CourseSearchConstants.CAMPUS_LOCATION, CourseSearchConstants.ORG_QUERY_SEARCH_BY_TYPE_REQUEST, CourseSearchConstants.ORG_TYPE_PARAM);
                getHashMap().put(CourseSearchConstants.CAMPUS_LOCATION, orgInfoList);
            } else {
                orgInfoList = getHashMap().get(CourseSearchConstants.CAMPUS_LOCATION);
            }
        } catch (Exception e) {
            logger.error("No Values for campuses found", e);
        }
        if (orgInfoList != null && orgInfoList.size() > 0) {
            for (OrgInfo entry : orgInfoList) {
                keyValues.add(new ConcreteKeyValue(entry.getId(), entry.getLongName() + " campus"));
            }
        }
        Collections.sort(keyValues,
                new Comparator<KeyValue>() {
                    @Override
                    public int compare(KeyValue keyValue1, KeyValue keyValue2) {
                        return keyValue1.getKey().compareTo(keyValue2.getKey());
                    }
                });
        return keyValues;
    }

    public AuditRunCampusParam() {
        super();
    }

    public boolean isBlankOption() {
        return blankOption;
    }

    public void setBlankOption(boolean blankOption) {
        this.blankOption = blankOption;
    }
}
