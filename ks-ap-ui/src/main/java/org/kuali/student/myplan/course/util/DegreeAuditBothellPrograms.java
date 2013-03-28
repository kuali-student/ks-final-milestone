package org.kuali.student.myplan.course.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.myplan.audit.dto.AuditProgramInfo;
import org.kuali.student.myplan.audit.service.DegreeAuditConstants;
import org.kuali.student.myplan.audit.service.DegreeAuditService;
import org.kuali.student.myplan.audit.service.DegreeAuditServiceConstants;
import org.kuali.student.r2.core.enumerationmanagement.dto.EnumeratedValueInfo;

/**
 * Created by IntelliJ IDEA.
 * User: hemanthg
 * Date: 5/17/12
 * Time: 11:51 AM
 * To change this template use File | Settings | File Templates.
 * @deprecated TODO: remove this, use generic impl instead /mwfyffe
 */
public class DegreeAuditBothellPrograms extends KeyValuesBase {


    private final Logger logger = Logger.getLogger(DegreeAuditBothellPrograms.class);

    private boolean blankOption;

    private transient DegreeAuditService degreeAuditService;

    private HashMap<String, List<EnumeratedValueInfo>> hashMap = new HashMap<String, List<EnumeratedValueInfo>>();

    public HashMap<String, List<EnumeratedValueInfo>> getHashMap() {
        return hashMap;
    }

    public void setHashMap(HashMap<String, List<EnumeratedValueInfo>> hashMap) {
        this.hashMap = hashMap;
    }

    public DegreeAuditService getDegreeAuditService() {
        if (degreeAuditService == null) {
            degreeAuditService = (DegreeAuditService)
                    GlobalResourceLoader.getService(new QName(DegreeAuditServiceConstants.NAMESPACE,
                            DegreeAuditServiceConstants.SERVICE_NAME));
        }
        return degreeAuditService;
    }


    @Override
    public List<KeyValue> getKeyValues() {
        List<AuditProgramInfo> auditProgramInfoList = new ArrayList<AuditProgramInfo>();
        try {
            auditProgramInfoList = getDegreeAuditService().getAuditPrograms(KsapFrameworkServiceLocator.getContext().getContextInfo());
        } catch (Exception e) {
            logger.error("could not retrieve AuditPrograms", e);
        }

        List<KeyValue> keyValues = new ArrayList<KeyValue>();

        for (AuditProgramInfo programInfo : auditProgramInfoList) {
            /*Bothell campus programs starts with 1*/
            if (programInfo.getProgramId().startsWith("1")) {
                keyValues.add(new ConcreteKeyValue(programInfo.getProgramId(), programInfo.getProgramTitle()));
            }
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
        keyValues.add(new ConcreteKeyValue(DegreeAuditConstants.DEFAULT_KEY, DegreeAuditConstants.DEFAULT_VALUE));
        Collections.reverse(keyValues);
        return keyValues;
    }


    public DegreeAuditBothellPrograms() {
        super();
    }

    public boolean isBlankOption() {
        return blankOption;
    }

    public void setBlankOption(boolean blankOption) {
        this.blankOption = blankOption;
    }
}
