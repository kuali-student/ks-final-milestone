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

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.courseoffering.dto.*;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.class1.state.dto.StateInfo;
import org.kuali.student.r2.core.class1.state.service.StateService;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @RequestMapping(value = "/statusview/{termCode}/{coCode}", method = RequestMethod.GET)
        @ResponseBody
        public String showAOStates(@PathVariable("termCode") String termCode, @PathVariable("coCode") String coCode, @ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                          HttpServletRequest request, HttpServletResponse response) throws IOException {
        return showAOStates(termCode, coCode, StringUtils.EMPTY, form, result, request, response);
    }

    @RequestMapping(value = "/statusview/{termCode}/{coCode}/{aoCode}", method = RequestMethod.GET)
    @ResponseBody
    public String showAOStates(@PathVariable("termCode") String termCode, @PathVariable("coCode") String coCode, @PathVariable("aoCode") String aoCode, @ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                      HttpServletRequest request, HttpServletResponse response) throws IOException {

        StringBuilder stringBuilder = new StringBuilder();
        List<TermInfo> termList;

        boolean displayAllAOs = StringUtils.isBlank(aoCode);

        try{
            termList = findTermByTermCode(termCode);
        } catch (Exception e) {
            LOG.error("Error calling findTermByTermCode - " + termCode);
            return "Error calling findTermByTermCode - " + termCode + " " + e.getMessage();
        }

        if (termList != null && !termList.isEmpty()){
            if( termList.size() == 1) {
                loadSocByTerm(stringBuilder, termList.get(0).getId());

                CourseOfferingInfo courseOffering;
                try{
                    //load courseoffing based on term and coCode
                    courseOffering = loadCourseOfferingByTermAndCoCode(termList.get(0).getId(), coCode);
                    appendHtmlSpanForState(stringBuilder, "course_offering", coCode + " " + getStateName(courseOffering.getStateKey()));

                    stringBuilder.append("<table id=\"ao_list\" border=\"1\">");
                    stringBuilder.append("<tr><th>AO Code</th><th>AO State</th><th>FO State</th><th>AO Scheduling State</th><th>Registration Group</th><th>Seat pool</th></tr>");
                    if (displayAllAOs){
                        List<ActivityOfferingInfo> aoInfos = getCourseOfferingService().getActivityOfferingsByCourseOffering(courseOffering.getId(), getContextInfo());
                        for (ActivityOfferingInfo aoInfo : aoInfos) {
                            displayAO(stringBuilder,coCode,termCode,aoInfo);
                        }
                    } else {
                        ActivityOfferingInfo activityOffering = loadActivityOfferingByCOAndAoCode(courseOffering.getId(), aoCode);
                        displayAO(stringBuilder,coCode,termCode,activityOffering);
                    }
                    stringBuilder.append("</table>");
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

        return stringBuilder.toString();
    }

    private void displayAO(StringBuilder stringBuilder, String coCode, String termCode, ActivityOfferingInfo activityOffering) throws Exception{
        //load activityOffering based on aoCode

        FormatOfferingInfo formatOffering = getCourseOfferingService().getFormatOffering(activityOffering.getFormatOfferingId(), getContextInfo());

        stringBuilder.append("<tr>");
        stringBuilder.append(getHTMLTableCell(activityOffering.getActivityCode()));
        stringBuilder.append(getHTMLTableCell(getStateName(activityOffering.getStateKey())));

        if(formatOffering != null){
            stringBuilder.append(getHTMLTableCell(getStateName(formatOffering.getStateKey())));
        }else {
            stringBuilder.append(getHTMLTableCell("Not found"));
        }

        stringBuilder.append(getHTMLTableCell(getStateName(activityOffering.getSchedulingStateKey())));

        loadRegGroup(stringBuilder, activityOffering.getId());
        loadSeatPool(stringBuilder, activityOffering.getId());

        stringBuilder.append("</tr>");
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
        CourseOfferingInfo courseOffering;

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
        if (StringUtils.isNotBlank(aoCode)){
            for (ActivityOfferingInfo info : aoInfos) {
                    if (info.getActivityCode().equals(aoCode)) {
                        aoInfo = info;
                    }
            }
        }

        return aoInfo;
    }

    private String getStateName(String stateKey) throws Exception {
        StateInfo state = getStateService().getState(stateKey, getContextInfo());
        return state.getName();
    }

    private void loadSeatPool(StringBuilder stringBuilder, String aoId) throws Exception{
        List<SeatPoolDefinitionInfo> seatPoolDefinitionInfoList;
        try {
            seatPoolDefinitionInfoList = getCourseOfferingService().getSeatPoolDefinitionsForActivityOffering(aoId, getContextInfo());
        } catch (Exception e) {
            LOG.error("Error calling getSeatPoolDefinitionsForActivityOffering - " + aoId);
            stringBuilder.append("Error calling Seat Pool");
            stringBuilder.append("</br>");
            return;
        }

        //Sort the seatpools by priority order
        Collections.sort(seatPoolDefinitionInfoList, new Comparator<SeatPoolDefinitionInfo>() {
            @Override
            public int compare(SeatPoolDefinitionInfo sp1, SeatPoolDefinitionInfo sp2) {
                return sp1.getProcessingPriority().compareTo(sp2.getProcessingPriority());
            }
        });

        if(seatPoolDefinitionInfoList.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for(SeatPoolDefinitionInfo spd : seatPoolDefinitionInfoList){
                sb.append(spd.getProcessingPriority() + " - " + spd.getStateKey() + "</br>");
            }
            stringBuilder.append(getHTMLTableCell(sb.toString()));
        } else {
            stringBuilder.append(getHTMLTableCell("No Data"));
        }

    }

    private SocInfo loadSocByTerm(StringBuilder stringBuilder, String termId){
        List<String> socIds;
        SocInfo soc = null;
        try {
            socIds = getCourseOfferingSetService().getSocIdsByTerm(termId, getContextInfo());
        } catch (Exception e) {
            LOG.error("Error calling getSocIdsByTerm - " + termId);
            stringBuilder.append("\n" )
                            .append( "Error calling SOC: " )
                            .append( e.getMessage() );
            return null;
        }

        if (socIds != null && !socIds.isEmpty()){
            //For M5, it should have only one SOC
            if (socIds.size() > 1){
                LOG.error("More than one SOC found for a term");
                stringBuilder.append("\n Error calling SOC: More than one SOC found for a term");
                return null;
            }


            try {
                soc = getCourseOfferingSetService().getSoc(socIds.get(0), getContextInfo());
                appendHtmlSpanForState(stringBuilder, "soc", getStateName(soc.getStateKey()));
                appendHtmlSpanForState(stringBuilder, "soc_scheduling", getStateName(soc.getSchedulingStateKey()));
            } catch (Exception e) {
                LOG.error("Error calling getSoc - " + socIds.get(0));
                stringBuilder.append("\n")
                                .append( "Error calling SOC: " )
                                .append( e.getMessage() );
                return null;
            }
        }

        return soc;

    }

    private static StringBuilder appendHtmlSpanForState(StringBuilder stringBuilder, String domId, String value) {

        if( stringBuilder == null ) stringBuilder = new StringBuilder();

        if( domId == null || value == null ) return stringBuilder;
        domId = domId.trim();
        value = value.trim();

        stringBuilder.append( "<span" )
                        .append( " id=" )
                        .append( "\"")
                        .append( domId.trim() )
                        .append( "\"")
                        .append( ">");
        stringBuilder.append( value.trim() );
        stringBuilder.append( "</span>" );
        stringBuilder.append( "<br/>" );

        return stringBuilder;
    }

    private void loadRegGroup(StringBuilder stringBuilder, String aoId){

        try {
            List<RegistrationGroupInfo>  registrationGroupInfos = getCourseOfferingService().getRegistrationGroupsByActivityOffering(aoId, getContextInfo());
            if(registrationGroupInfos.size()>0){
                StringBuilder sb = new StringBuilder();
                for(RegistrationGroupInfo rg : registrationGroupInfos){
                    sb.append(rg.getId() + " - " + getStateName(rg.getStateKey()) + "</br>");
                }
                stringBuilder.append(getHTMLTableCell(sb.toString()));
            }else{
                stringBuilder.append(getHTMLTableCell("No Data"));
            }
        } catch (Exception e) {
            LOG.error("Error calling getRegistrationGroupsByActivityOffering - " + aoId);
            stringBuilder.append("Error calling SOC: " )
                            .append(e.getMessage())
                            .append("</br>");
        }
    }

    private String getHTMLTableCell(String data){
        return "<td>" + data + "</td>";
    }

}
