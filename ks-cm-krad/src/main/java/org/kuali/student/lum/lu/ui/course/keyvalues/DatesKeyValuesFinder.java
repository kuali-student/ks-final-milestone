/**
 * Copyright 2005-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.lum.lu.ui.course.keyvalues;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.student.cm.course.form.wrapper.CourseInfoWrapper;
import org.kuali.student.cm.course.form.wrapper.RetireCourseWrapper;
import org.kuali.student.cm.course.util.CourseProposalUtil;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.lum.course.infc.Course;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * This is the helper class for CourseView
 * 
 * @author OpenCollab/rSmart KRAD CM Conversion Alliance!
 */

public class DatesKeyValuesFinder extends UifKeyValuesFinderBase {
    private transient AtpService atpService;

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {
        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        List<CourseProposalUtil.TermResult> termResults = new ArrayList<CourseProposalUtil.TermResult>();

        if (model instanceof MaintenanceDocumentForm) {
            MaintenanceDocumentForm courseForm = (MaintenanceDocumentForm) model;
            if(courseForm.getDocument().getNewMaintainableObject().getDataObject() instanceof CourseInfoWrapper){
                CourseInfoWrapper courseInfoWrapper = ((CourseInfoWrapper) courseForm.getDocument().getNewMaintainableObject().getDataObject());
                if (courseInfoWrapper.getCourseInfo().isPilotCourse() && StringUtils.isNotEmpty(courseInfoWrapper.getCourseInfo().getStartTerm())) {
                    termResults = CourseProposalUtil.getNextTerms(courseInfoWrapper.getCourseInfo().getStartTerm(),ContextUtils.createDefaultContextInfo()) ;
                }else if(!courseInfoWrapper.getCourseInfo().isPilotCourse()){
                    courseInfoWrapper.getCourseInfo().setEndTerm(null);
                }
            } else if(courseForm.getDocument().getNewMaintainableObject().getDataObject() instanceof RetireCourseWrapper){
                RetireCourseWrapper retireCourseWrapper = ((RetireCourseWrapper) courseForm.getDocument().getNewMaintainableObject().getDataObject());
                termResults = CourseProposalUtil.getNextTerms(retireCourseWrapper.getCourseInfo().getStartTerm(),ContextUtils.createDefaultContextInfo()) ;
            }
        }
        for (CourseProposalUtil.TermResult result : termResults) {
            keyValues.add(new ConcreteKeyValue(result.getAtpId() , result.getShortName()));
        }

        return keyValues;
    }


}
