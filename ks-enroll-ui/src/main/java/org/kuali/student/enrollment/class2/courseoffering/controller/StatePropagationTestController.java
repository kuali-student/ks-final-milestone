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
 */
package org.kuali.student.enrollment.class2.courseoffering.controller;

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper;
import org.kuali.student.enrollment.class2.courseoffering.form.CourseOfferingManagementForm;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.courseoffering.dto.*;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.core.class1.state.dto.StateInfo;
import org.kuali.student.r2.core.class1.state.service.StateService;
import org.kuali.student.r2.core.scheduling.dto.ScheduleInfo;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping(value = "/statusview/**")
public class StatePropagationTestController extends UifControllerBase {
    private transient AcademicCalendarService academicCalendarService;
    private transient CourseOfferingService courseOfferingService;
    private transient StateService stateService;
    private transient SchedulingService schedulingService;
    private transient CourseOfferingSetService courseOfferingSetService;

    private static final Logger LOG = Logger.getLogger(StatePropagationTestController.class);

    public ContextInfo getContextInfo() {
         return ContextUtils.createDefaultContextInfo();
     }

    protected AcademicCalendarService getAcademicCalendarService(){
        if(academicCalendarService == null) {
            academicCalendarService = CourseOfferingResourceLoader.loadAcademicCalendarService();
        }
        return academicCalendarService;
    }

   protected CourseOfferingService getCourseOfferingService() {
       if(courseOfferingService == null){
            courseOfferingService = CourseOfferingResourceLoader.loadCourseOfferingService();
        }
       return courseOfferingService;
    }

   protected StateService getStateService() {
        if(stateService == null) {
            stateService = CourseOfferingResourceLoader.loadStateService();
        }
        return this.stateService;
    }

    protected SchedulingService getSchedulingService() {
         if(schedulingService == null){
             schedulingService =  CourseOfferingResourceLoader.loadSchedulingService();
         }
         return schedulingService;
     }

     protected CourseOfferingSetService getCourseOfferingSetService(){
        if (courseOfferingSetService == null){
            courseOfferingSetService = CourseOfferingResourceLoader.loadCourseOfferingSetService();
        }
        return courseOfferingSetService;
    }
    @Override
    protected UifFormBase createInitialForm(HttpServletRequest request) {
        return new UifFormBase();
    }

    @RequestMapping(value = "/statusview/{termCode}/{coCode}/{aoCode}", method = RequestMethod.GET)
    @ResponseBody
    public String showAOStates(@PathVariable("termCode") String termCode, @PathVariable("coCode") String coCode, @PathVariable("aoCode") String aoCode, @ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                      HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuilder layoutString = new StringBuilder();
        List<TermInfo> termList;

        try{
            termList = findTermByTermCode(termCode);
        } catch (Exception e) {
            LOG.error("Error calling findTermByTermCode - " + termCode);
            return "Error calling findTermByTermCode - " + termCode + " " + e.getMessage();
        }

        if (termList != null && !termList.isEmpty()){
            if( termList.size() == 1) {
                loadSocByTerm(layoutString, termList.get(0).getId());

                CourseOfferingInfo courseOffering;
                try{
                    //load courseoffing based on term and coCode
                    courseOffering = loadCourseOfferingByTermAndCoCode(termList.get(0).getId(), coCode);
                    layoutString.append("<b>CourseOffering</b> " + coCode + " " + getStateName(courseOffering.getStateKey()));
                    layoutString.append("</br>");

                    //load activityOffering based on aoCode
                    ActivityOfferingInfo activityOffering = loadActivityOfferingByCOAndAoCode(courseOffering.getId(), aoCode);
                    if(activityOffering != null){
                        layoutString.append("<b>ActivityOffering</b> " + aoCode + " " + getStateName(activityOffering.getStateKey()));
                        layoutString.append("</br>");
                    }else {
                        return "The ActivityOffering with code " + aoCode + " not found in " + termCode + "/" + coCode;
                    }

                    //load formatOffering
                    FormatOfferingInfo formatOffering = getCourseOfferingService().getFormatOffering(activityOffering.getFormatOfferingId(), getContextInfo());
                    if(formatOffering != null){
                        layoutString.append("<b>FormatOffering</b> " + getStateName(formatOffering.getStateKey()));
                        layoutString.append("</br>");
                    }else {
                        layoutString.append("The FormatOffering not found for " + coCode + "/" + aoCode);
                        layoutString.append("</br>");
                    }

                    //ao scheduling state
                    layoutString.append("<b>AO Scheduling</b> " + getStateName(activityOffering.getSchedulingStateKey()));
                    layoutString.append("</br>");

                    loadRegGroup(layoutString, activityOffering.getId());
                    loadSeatPool(layoutString, activityOffering.getId());

                 } catch (Exception e) {
                    LOG.error("Error calling loadCourseOfferingByTermAndCoCode - " + termCode + "/" + coCode);
                    return "Error calling loadCourseOfferingByTermAndCoCode - " + termCode + "/" + coCode + " " + e.getMessage();
                 }

            } else {
                LOG.error("Error: Found more than one Term for term code: " + termCode);
                return "Error: Found more than one Term for term code: " + termCode;
             }
        } else {
            LOG.error("Error: Can't find any Term for term code: " + termCode);
            return "Error: Can't find any Term for term code: " + termCode;
        }

        response.setHeader("content-type", "text/html");

        return layoutString.toString();
    }

    private List<TermInfo> findTermByTermCode(String termCode) throws Exception {
       QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
       qbcBuilder.setPredicates(PredicateFactory.equal("atpCode", termCode));
       QueryByCriteria criteria = qbcBuilder.build();

       // Do search.  In ideal case, terms returns one element, which is the desired term.
       AcademicCalendarService acalService = getAcademicCalendarService();
       return acalService.searchForTerms(criteria, getContextInfo());
    }

    private CourseOfferingInfo loadCourseOfferingByTermAndCoCode(String termId, String coCode) throws Exception {
        CourseOfferingInfo courseOffering = null;

        // Building a query
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.and(
                PredicateFactory.equal("courseOfferingCode", coCode),
                PredicateFactory.equalIgnoreCase("atpId", termId)));
        QueryByCriteria criteria = qbcBuilder.build();
        List<String> courseOfferingIds = getCourseOfferingService().searchForCourseOfferingIds(criteria, getContextInfo());

        if(courseOfferingIds.size() > 0){
            if(courseOfferingIds.size() == 1){
                 courseOffering = getCourseOfferingService().getCourseOffering(courseOfferingIds.get(0), getContextInfo());

            } else {
                LOG.error("Error: Found more than one CourseOffering for CourseOffering code: " + coCode);
                throw new Exception ("Error: Found more than one CourseOffering for CourseOffering code: " + coCode);
             }
        } else {
            LOG.error("Error: Can't find any Course Offering for a CourseOffering Code: " + coCode + " in term: " + termId);
            throw new Exception("Error: Can't find any Course Offering for a CourseOffering Code: " + coCode + " in term: " + termId);
        }

        return courseOffering;
    }


    private ActivityOfferingInfo loadActivityOfferingByCOAndAoCode(String coId, String aoCode)throws Exception {
        ActivityOfferingInfo aoInfo = null;
        List<ActivityOfferingInfo> aoInfos = getCourseOfferingService().getActivityOfferingsByCourseOffering(coId, getContextInfo());
        for (ActivityOfferingInfo info : aoInfos) {
                if (info.getActivityCode().equals(aoCode)) {
                    aoInfo = info;
                }
        }

        return aoInfo;
    }

    private String getStateName(String stateKey) throws Exception {
        StateInfo state = getStateService().getState(stateKey, getContextInfo());
        return state.getName();
    }

    private void loadSeatPool(StringBuilder layoutBuilder, String aoId) throws Exception{
        List<SeatPoolDefinitionInfo> seatPoolDefinitionInfoList = null;
        try {
            seatPoolDefinitionInfoList = getCourseOfferingService().getSeatPoolDefinitionsForActivityOffering(aoId, getContextInfo());
        } catch (Exception e) {
            LOG.error("Error calling getSeatPoolDefinitionsForActivityOffering - " + aoId);
            layoutBuilder.append("Error calling Seat Pool ");
            layoutBuilder.append("</br>");
            return;
        }

        //Sort the seatpools by priority order
        Collections.sort(seatPoolDefinitionInfoList, new Comparator<SeatPoolDefinitionInfo>() {
            @Override
            public int compare(SeatPoolDefinitionInfo sp1, SeatPoolDefinitionInfo sp2) {
                return sp1.getProcessingPriority().compareTo(sp2.getProcessingPriority());
            }
        });

        layoutBuilder.append("<b>Seat Pool</b> ");
        if(seatPoolDefinitionInfoList.size() > 0) {
            for(SeatPoolDefinitionInfo spd : seatPoolDefinitionInfoList){
                layoutBuilder.append(spd.getProcessingPriority() + " " + getStateName(spd.getStateKey()));
                layoutBuilder.append("</br>");
            }
        } else {
            layoutBuilder.append("no data");
        }

        layoutBuilder.append("</br>");
    }

    private SocInfo loadSocByTerm(StringBuilder layoutBuilder, String termId){
        List<String> socIds = null;
        SocInfo soc = null;
        try {
            socIds = getCourseOfferingSetService().getSocIdsByTerm(termId, getContextInfo());
        } catch (Exception e) {
            LOG.error("Error calling getSocIdsByTerm - " + termId);
            layoutBuilder.append("\n Error calling SOC: " + e.getMessage());
            return null;
        }

        if (socIds != null && !socIds.isEmpty()){
            //For M5, it should have only one SOC
            if (socIds.size() > 1){
                LOG.error("More than one SOC found for a term");
                layoutBuilder.append("\n Error calling SOC: More than one SOC found for a term");
                return null;
            }


            try {
                soc = getCourseOfferingSetService().getSoc(socIds.get(0), getContextInfo());
                layoutBuilder.append("<b>SOC</b> " + getStateName(soc.getStateKey()));
                layoutBuilder.append("</br>");
                layoutBuilder.append("<b>SOC Scheduling</b> " + getStateName(soc.getSchedulingStateKey()));
                layoutBuilder.append("</br>");
            } catch (Exception e) {
                LOG.error("Error calling getSoc - " + socIds.get(0));
                layoutBuilder.append("\n Error calling SOC: " + e.getMessage());
                return null;
            }
        }

        return soc;

    }

    private void loadRegGroup(StringBuilder layoutBuilder, String aoId){
        List<String> activityOfferingIds = new ArrayList<String>();
        activityOfferingIds.add(aoId);

        try {
            List<RegistrationGroupInfo>  registrationGroupInfos = getCourseOfferingService().getRegistrationGroupsWithActivityOfferings(activityOfferingIds, getContextInfo());
            layoutBuilder.append("<b>RegistrationGroups</b> ");
            if(registrationGroupInfos.size()>0){
                for(RegistrationGroupInfo rg : registrationGroupInfos){
                    layoutBuilder.append(rg.getId() + " " + getStateName(rg.getStateKey()));
                    layoutBuilder.append("</br>");
                }
            }else{
                layoutBuilder.append("no data");
            }
            layoutBuilder.append("</br>");
        } catch (Exception e) {
            LOG.error("Error calling getRegistrationGroupsWithActivityOfferings - " + aoId);
            layoutBuilder.append("Error calling SOC: " + e.getMessage());
            layoutBuilder.append("</br>");
        }
    }
}
