/**
 * Copyright 2012 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * Created by David Yin on 5/15/12
 */
package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.maintenance.MaintainableImpl;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.common.state.service.StateService;
import org.kuali.student.r2.common.type.service.TypeService;

import javax.xml.namespace.QName;
import java.util.Locale;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class FormatOfferingInfoMaintainableImpl extends MaintainableImpl {
    private static final long serialVersionUID = 1L;

    private static final String DEFAULT_DOCUMENT_DESC_FOR_CREATING_FORMAT_OFFERING =
            "Create a new Format offering";
    private static final String DEFAULT_DOCUMENT_DESC_FOR_EDITING_FORMAT_OFFERING =
            "Edit an existing Format offering";
    private static final String DEFAULT_DOCUMENT_DESC_FOR_COPYING_FORMAT_OFFERING =
            "Copy from an existing Format offering to create a new one";

    private transient CourseOfferingService courseOfferingService;
    private ContextInfo contextInfo;
    final Logger logger = Logger.getLogger(FormatOfferingInfoMaintainableImpl.class);

    @Override
    public void saveDataObject() {
        System.out.println (">>> in save ");
        FormatOfferingInfo formatOfferingInfoMaintenance = (FormatOfferingInfo) getDataObject();
        if(getMaintenanceAction().equals(KRADConstants.MAINTENANCE_NEW_ACTION) ||
                getMaintenanceAction().equals(KRADConstants.MAINTENANCE_COPY_ACTION)) {
            try {

                FormatOfferingInfo formatOfferingInfoCreated = getCourseOfferingService().createFormatOffering(formatOfferingInfoMaintenance.getCourseOfferingId(), formatOfferingInfoMaintenance.getFormatId(),
                                                                                            LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY, formatOfferingInfoMaintenance, getContextInfo());
                //setDataObject(new FormatOfferingInfo(formatOfferingInfoCreated));
            } catch (Exception e) {
                //logger.error("FormatOfferingInfoMaintenance - create new failed. ", e);
                throw new RuntimeException("FormatOfferingInfoMaintenance - create new failed. ", e);
            }
        }
        else {   //should be edit action
            try {
                FormatOfferingInfo formatOfferingInfoUpdated = getCourseOfferingService().updateFormatOffering(formatOfferingInfoMaintenance.getId(), formatOfferingInfoMaintenance, getContextInfo());
            } catch (Exception e) {
                //logger.error("FormatOfferingInfoMaintenance - edit failed. ", e);
                throw new RuntimeException("FormatOfferingInfoMaintenance - edit failed. ", e);
            }
        }
    }

    /**
     * @see org.kuali.rice.kns.maintenance.KualiMaintainableImpl#prepareForSave()
     */
    @Override
    public void prepareForSave() {
        System.out.println (">>> in prepareForSave ");
        if (getMaintenanceAction().equalsIgnoreCase(KRADConstants.MAINTENANCE_NEW_ACTION)) {
            FormatOfferingInfo formatOfferingInfoMaintenance = (FormatOfferingInfo) getDataObject();
            formatOfferingInfoMaintenance.setTypeKey(LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY);
            formatOfferingInfoMaintenance.setStateKey(LuiServiceConstants.LUI_DRAFT_STATE_KEY);
        }
        super.prepareForSave();
    }

    @Override
    public Object retrieveObjectForEditOrCopy(MaintenanceDocument document, Map<String, String> dataObjectKeys) {
        try {
            FormatOfferingInfo info = getCourseOfferingService().getFormatOffering(dataObjectKeys.get("id"), getContextInfo());
            return info;
        } catch (Exception e) {
            logger.error("FormatOfferingInfoMaintenance - edit/copy failed. ", e);
            throw new RuntimeException("FormatOfferingInfoMaintenance - edit/copy failed. ", e);
        }
    }

    /**
     * @see org.kuali.rice.krad.maintenance.Maintainable#processAfterCopy
     */
    @Override
    public void processAfterCopy(MaintenanceDocument document, Map<String, String[]> requestParameters) {
        //set documentDescription to document.documentHeader.documentDescription
        document.getDocumentHeader().setDocumentDescription(DEFAULT_DOCUMENT_DESC_FOR_COPYING_FORMAT_OFFERING);
    }

    /**
     * @see org.kuali.rice.krad.maintenance.Maintainable#processAfterEdit
     */
    @Override
    public void processAfterEdit(MaintenanceDocument document, Map<String, String[]> requestParameters) {
        //set documentDescription to document.documentHeader.documentDescription
        document.getDocumentHeader().setDocumentDescription(DEFAULT_DOCUMENT_DESC_FOR_EDITING_FORMAT_OFFERING);

    }

    /**
     * @see org.kuali.rice.krad.maintenance.Maintainable#processAfterNew
     */
    @Override
    public void processAfterNew(MaintenanceDocument document, Map<String, String[]> requestParameters) {
        //set documentDescription to document.documentHeader.documentDescription
        document.getDocumentHeader().setDocumentDescription(DEFAULT_DOCUMENT_DESC_FOR_CREATING_FORMAT_OFFERING);

    }

    public ContextInfo getContextInfo() {
        if (null == contextInfo) {
            contextInfo = new ContextInfo();
            contextInfo.setAuthenticatedPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
            contextInfo.setPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
            LocaleInfo localeInfo = new LocaleInfo();
            localeInfo.setLocaleLanguage(Locale.getDefault().getLanguage());
            localeInfo.setLocaleRegion(Locale.getDefault().getCountry());
            contextInfo.setLocale(localeInfo);
        }
        return contextInfo;
    }

    protected CourseOfferingService getCourseOfferingService() {
        if (courseOfferingService == null) {
            courseOfferingService = (CourseOfferingService) GlobalResourceLoader.getService(new QName(CourseOfferingServiceConstants.NAMESPACE, "CourseOfferingService"));
        }
        return courseOfferingService;
    }
}