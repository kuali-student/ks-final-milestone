package org.kuali.student.cm.uif.propertyeditors;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;

import javax.xml.namespace.QName;
import java.beans.PropertyEditorSupport;
import java.io.Serializable;
import java.util.List;

/**
 * Displays a Term name given an ATP id.
 */
public class TermPropertyEditor extends PropertyEditorSupport implements Serializable {

    private transient AtpService atpService;

    /**
     * Formats the value.
     */
    @Override
    public String getAsText() {
        Object obj = this.getValue();

        if (obj == null) {
            return null;
        }

        return getTermDesc((String) obj);
    }

    /**
     * Stores the value.
     */
    @Override
    public void setAsText(String text) {
        this.setValue(text);
    }

    /**
     * Finds the Term name.
     */
    public String getTermDesc(String termId) {

        String result = "";

        if (StringUtils.isNotEmpty(termId)) {
            QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
            qbcBuilder.setPredicates(PredicateFactory.in("id", termId));
            QueryByCriteria qbc = qbcBuilder.build();
            try {
                List<AtpInfo> searchResult = this.getAtpService().searchForAtps(qbc, ContextUtils.createDefaultContextInfo());
                AtpInfo atpInfo = KSCollectionUtils.getOptionalZeroElement(searchResult);

                if (atpInfo != null) {
                    result = atpInfo.getName();
                }
            } catch (Exception ex) {
                throw new RuntimeException("Could not retrieve description of Term [" + termId + "].", ex);
            }
        }
        return result;
    }

    protected AtpService getAtpService() {
        if (atpService == null) {
            QName qname = new QName(AtpServiceConstants.NAMESPACE, AtpServiceConstants.SERVICE_NAME_LOCAL_PART);
            atpService = (AtpService) GlobalResourceLoader.getService(qname);
        }
        return atpService;
    }
}