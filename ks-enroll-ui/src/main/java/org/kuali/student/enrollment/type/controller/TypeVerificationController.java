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
 * Created by cmuller on 8/13/12
 */
package org.kuali.student.enrollment.type.controller;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.type.dto.TypeVerificationBasics;
import org.kuali.student.enrollment.type.dto.TypeVerificationInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.common.util.constants.LuServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.appointment.constants.AppointmentServiceConstants;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.constants.HoldServiceConstants;
import org.kuali.student.r2.core.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.constants.TypeServiceConstants;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class provides controller logic for the Type Verification report ui
 *
 * @author Kuali Student Team
 */
@Controller
@RequestMapping(value = "/typeVerification")
public class TypeVerificationController extends UifControllerBase  {

    private transient TypeService typeService;
    private List<TypeVerificationBasics> equivalences;
    private List<TypeVerificationBasics> inDBNotInJava;
    private List<TypeVerificationBasics> inJavaNotInDB;
    TypeVerificationInfo typeInfo;
    Map<String, String> keyToUri = new HashMap<String, String>();
    ArrayList<String> typeKeysDB = new ArrayList<String>(Arrays.asList(
            CourseOfferingServiceConstants.COURSE_EVALUATION_INDICATOR_ATTR,
            CourseOfferingServiceConstants.FINAL_EXAM_INDICATOR_ATTR,
            "kuali.attribute.finding.source",
            CourseOfferingServiceConstants.GRADE_ROSTER_LEVEL_TYPE_KEY_ATTR,
            CourseOfferingServiceConstants.MAX_ENROLLMENT_IS_ESTIMATED_ATTR,
            CourseOfferingServiceConstants.WAIT_LIST_LEVEL_TYPE_KEY_ATTR,
            CourseOfferingServiceConstants.WHERE_FEES_ATTACHED_FLAG_ATTR,
            AppointmentServiceConstants.APPOINTMENT_SLOT_TYPE_CLOSED_KEY,
            AppointmentServiceConstants.APPOINTMENT_TYPE_REGISTRATION,
            AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_ONE_SLOT_KEY,
            AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_SLOTTED_MAX_KEY,
            AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_SLOTTED_UNIFORM_KEY,
            AtpServiceConstants.DURATION_DAY_TYPE_KEY,
            AtpServiceConstants.DURATION_FOUR_YEARS_TYPE_KEY,
            AtpServiceConstants.DURATION_HALF_SEMESTER_TYPE_KEY,
            AtpServiceConstants.DURATION_HOURS_TYPE_KEY,
            AtpServiceConstants.DURATION_MINI_MESTER_TYPE_KEY,
            AtpServiceConstants.DURATION_MINUTES_TYPE_KEY,
            AtpServiceConstants.DURATION_MONTH_TYPE_KEY,
            AtpServiceConstants.DURATION_PERIOD_TYPE_KEY,
            AtpServiceConstants.DURATION_QUARTER_TYPE_KEY,
            AtpServiceConstants.DURATION_SEMESTER_TYPE_KEY,
            AtpServiceConstants.DURATION_SESSION_TYPE_KEY,
            AtpServiceConstants.DURATION_TBD_TYPE_KEY,
            AtpServiceConstants.DURATION_TERM_TYPE_KEY,
            AtpServiceConstants.DURATION_TWO_YEARS_TYPE_KEY,
            AtpServiceConstants.DURATION_YEAR_TYPE_KEY,
            "kuali.atp.milestone.AddDropPeriod1",
            "kuali.atp.milestone.AddDropPeriod2",
            AtpServiceConstants.MILESTONE_CHRISTMAS_TYPE_KEY,
            AtpServiceConstants.MILESTONE_CHRISTMAS_OBSERVED_TYPE_KEY,
            AtpServiceConstants.MILESTONE_COLUMBUS_DAY_TYPE_KEY,
            AtpServiceConstants.MILESTONE_COMMENCEMENT_TYPE_KEY,
            AtpServiceConstants.MILESTONE_FINAL_EXAM_PERIOD_TYPE_KEY,
            "kuali.atp.milestone.GradingPeriod",
            AtpServiceConstants.MILESTONE_HOMECOMING_TYPE_KEY,
            AtpServiceConstants.MILESTONE_INDEPENDENCE_DAY_TYPE_KEY,
            AtpServiceConstants.MILESTONE_INDEPENDENCE_DAY_OBSERVED_TYPE_KEY,
            AtpServiceConstants.MILESTONE_INSTRUCTIONAL_PERIOD_TYPE_KEY,
            AtpServiceConstants.MILESTONE_LABOR_DAY_TYPE_KEY,
            AtpServiceConstants.MILESTONE_MLK_DAY_OBSERVED_TYPE_KEY,
            AtpServiceConstants.MILESTONE_MEMORIAL_DAY_TYPE_KEY,
            AtpServiceConstants.MILESTONE_NEW_STUDENT_CONVOCATION_TYPE_KEY,
            AtpServiceConstants.MILESTONE_NEW_YEAR_DAY_TYPE_KEY,
            AtpServiceConstants.MILESTONE_NEW_YEAR_DAY_OBSERVED_TYPE_KEY,
            AtpServiceConstants.MILESTONE_PRESIDENTS_DAY_TYPE_KEY,
            "kuali.atp.milestone.RegistrationOpen",
            AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD1_TYPE_KEY,
            AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD2_TYPE_KEY,
            AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD3_TYPE_KEY,
            AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD4_TYPE_KEY,
            AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD5_TYPE_KEY,
            AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD6_TYPE_KEY,
            AtpServiceConstants.MILESTONE_THANKSGIVING_BREAK_TYPE_KEY,
            AtpServiceConstants.MILESTONE_VETERANS_DAY_OBSERVED_TYPE_KEY,
            AtpServiceConstants.MILESTONE_SEATPOOL_END_OF_FIRST_WEEK_OF_CLASSES_TYPE_KEY,
            AtpServiceConstants.MILESTONE_SEATPOOL_FIRST_DAY_OF_CLASSES_TYPE_KEY,
            AtpServiceConstants.MILESTONE_SEATPOOL_MONTH_PRIOR_TO_START_OF_CLASSES_TYPE_KEY,
            AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY,
            AtpServiceConstants.ATP_FALL_TYPE_KEY,
            AtpServiceConstants.ATP_HOLIDAY_CALENDAR_TYPE_KEY,
            AtpServiceConstants.ATP_SUMMER_TYPE_KEY,
            AtpServiceConstants.ATP_WINTER_TYPE_KEY,
            AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD_GROUP_TYPE_KEY,
            AtpServiceConstants.MILESTONE_EVENT_GROUPING_TYPE_KEY,
            AtpServiceConstants.MILESTONE_HOLIDAY_GROUPING_TYPE_KEY,
            "kuali.milestone.type.group.registration",
            AtpServiceConstants.MILESTONE_SEATPOOL_GROUPING_TYPE_KEY,
            AtpServiceConstants.DURATION_WEEK_TYPE_KEY,
            "kuali.atp.milestone.AddDropPeriod3",
            AtpServiceConstants.MILESTONE_VETERANS_DAY_TYPE_KEY,
            AtpServiceConstants.MILESTONE_SEATPOOL_LAST_DAY_OF_REGISTRATION_TYPE_KEY,
            AtpServiceConstants.ATP_SPRING_TYPE_KEY,
            AtpServiceConstants.ATP_TERM_GROUPING_TYPE_KEY,
            "kuali.milestone.type.group.instructional",
            CourseOfferingSetServiceConstants.REVERSE_ROLLOVER_RESULT_TYPE_KEY,
            CourseOfferingSetServiceConstants.ROLLOVER_RESULT_TYPE_KEY,
            "kuali.soc.type.departmental",
            CourseOfferingSetServiceConstants.MAIN_SOC_TYPE_KEY,
            CourseOfferingSetServiceConstants.SUBJECT_AREA_SOC_TYPE_KEY,
            CourseOfferingSetServiceConstants.UNITS_CONTENT_OWNER_SOC_TYPE_KEY,
            CourseOfferingSetServiceConstants.UNITS_DEPLOYMENT_OWNER_SOC_TYPE_KEY,
            HoldServiceConstants.ACADEMIC_PROGRESS_ISSUE_TYPE_KEY,
            "kuali.hold.issue.type.advising",
            HoldServiceConstants.DISCIPLINE_ISSUE_TYPE_KEY,
            "kuali.hold.issue.type.library",
            HoldServiceConstants.STUDENT_HOLD_TYPE_KEY,
            HoldServiceConstants.FINANCIAL_ISSUE_TYPE_KEY,
            "kuali.lpr.advisor",
            "kuali.lpr.enrollee",
            "kuali.lpr.exam.proctor",
            "kuali.lpr.roster.entry.type.automatic",
            LprServiceConstants.LPRROSTER_COURSE_FINAL_GRADE_TYPE_KEY,
            "kuali.lpr.trans.item.type.add",
            "kuali.lpr.trans.registrant",
            LprServiceConstants.LPRTRANS_REGISTRATION_TYPE_KEY,
            LprServiceConstants.INSTRUCTOR_ASSISTANT_TYPE_KEY,
            LprServiceConstants.INSTRUCTOR_MAIN_TYPE_KEY,
            LprServiceConstants.INSTRUCTOR_SUPPORT_TYPE_KEY,
            "kuali.lpr.type.roster.grade.final",
            "kuali.lpr.instructor.supervisor",
            "kuali.lpr.trans.item.type.drop",
            "kuali.lpr.type.registrant",
            LrcServiceConstants.RESULT_SCALE_TYPE_KEY_CERTIFICATE,
            LrcServiceConstants.RESULT_SCALE_TYPE_KEY_CERTIFICATION,
            LrcServiceConstants.RESULT_SCALE_TYPE_KEY_CREDIT,
            LrcServiceConstants.RESULT_SCALE_TYPE_KEY_GPA,
            LrcServiceConstants.RESULT_SCALE_TYPE_KEY_GRADE,
            LrcServiceConstants.RESULT_SCALE_TYPE_KEY_ADMIN_GRADE,
            LrcServiceConstants.RESULT_SCALE_TYPE_KEY_HONOR,
            LrcServiceConstants.RESULT_SCALE_TYPE_KEY_MINOR,
            LrcServiceConstants.RESULT_SCALE_TYPE_KEY_COMPLETION,
            LrcServiceConstants.RESULT_VALUE_TYPE_KEY_VALUE,
            LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED,
            LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_MULTIPLE,
            LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_RANGE,
            LrcServiceConstants.RESULT_SCALE_TYPE_KEY_DEGREE,
            LrcServiceConstants.RESULT_SCALE_TYPE_KEY_STUDENT_YEAR,
            "kuali.lu.type.activity.conference",
            "kuali.lu.type.activity.independentstudy",
            "kuali.lu.type.activity.practicum",
            "kuali.lu.type.activity.quiz",
            CluServiceConstants.COURSE_ACTIVITY_SEMINAR_TYPE_KEY,
            "kuali.lu.type.activity.studio",
            "kuali.lu.type.grouping.activity",
            LuServiceConstants.CREDIT_COURSE_LU_TYPE_KEY,
            LuServiceConstants.COURSE_FORMAT_TYPE_KEY,
            LuServiceConstants.COURSE_ACTIVITY_DIRECTED_TYPE_KEY,
            CluServiceConstants.COURSE_ACTIVITY_HOMEWORK_TYPE_KEY,
            LuServiceConstants.COURSE_ACTIVITY_LAB_TYPE_KEY,
            LuServiceConstants.COURSE_ACTIVITY_LECTURE_TYPE_KEY,
            LuServiceConstants.COURSE_ACTIVITY_TUTORIAL_TYPE_KEY,
            LuServiceConstants.COURSE_ACTIVITY_WEBDISCUSS_TYPE_KEY,
            LuServiceConstants.COURSE_ACTIVITY_WEBLECTURE_TYPE_KEY,
            "kuali.lu.type.activity.clinic",
            LuServiceConstants.COURSE_ACTIVITY_DISCUSSION_TYPE_KEY,
            "kuali.lu.type.activity.clerkship",
            LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_CO_TO_FO_TYPE_KEY,
            LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_AO_TYPE_KEY,
            LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_RG_TYPE_KEY,
            LuiServiceConstants.LUI_LUI_RELATION_REGISTERED_FOR_VIA_RG_TO_AO_TYPE_KEY,
            LuiServiceConstants.ACTIVITY_ACTIVITY_OFFERING_TYPE_KEY,
            LuiServiceConstants.CLERKSHIP_ACTIVITY_OFFERING_TYPE_KEY,
            LuiServiceConstants.CLINIC_ACTIVITY_OFFERING_TYPE_KEY,
            LuiServiceConstants.COLLOQUIUM_ACTIVITY_OFFERING_TYPE_KEY,
            LuiServiceConstants.COMP_BASED_ACTIVITY_OFFERING_TYPE_KEY,
            LuiServiceConstants.CONFERENCE_ACTIVITY_OFFERING_TYPE_KEY,
            LuiServiceConstants.CORRESPOND_ACTIVITY_OFFERING_TYPE_KEY,
            LuiServiceConstants.DEMONSTRATION_ACTIVITY_OFFERING_TYPE_KEY,
            LuiServiceConstants.DIRECTED_ACTIVITY_OFFERING_TYPE_KEY,
            LuiServiceConstants.DISCUSSION_ACTIVITY_OFFERING_TYPE_KEY,
            LuiServiceConstants.FIELD_ACTIVITY_OFFERING_TYPE_KEY,
            LuiServiceConstants.HOMEWORK_ACTIVITY_OFFERING_TYPE_KEY,
            LuiServiceConstants.INDEPEND_ACTIVITY_OFFERING_TYPE_KEY,
            LuiServiceConstants.INTERNSHIP_ACTIVITY_OFFERING_TYPE_KEY,
            LuiServiceConstants.LAB_ACTIVITY_OFFERING_TYPE_KEY,
            LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY,
            LuiServiceConstants.PRACTICUM_ACTIVITY_OFFERING_TYPE_KEY,
            LuiServiceConstants.PRIVATE_ACTIVITY_OFFERING_TYPE_KEY,
            LuiServiceConstants.QUIZ_ACTIVITY_OFFERING_TYPE_KEY,
            LuiServiceConstants.RECITATION_ACTIVITY_OFFERING_TYPE_KEY,
            LuiServiceConstants.RESEARCH_ACTIVITY_OFFERING_TYPE_KEY,
            LuiServiceConstants.SELF_PACED_ACTIVITY_OFFERING_TYPE_KEY,
            LuiServiceConstants.SEMINAR_ACTIVITY_OFFERING_TYPE_KEY,
            LuiServiceConstants.STUDIO_ACTIVITY_OFFERING_TYPE_KEY,
            LuiServiceConstants.TUTORIAL_ACTIVITY_OFFERING_TYPE_KEY,
            LuiServiceConstants.VIDEO_CONF_ACTIVITY_OFFERING_TYPE_KEY,
            LuiServiceConstants.WEB_DISCUSS_ACTIVITY_OFFERING_TYPE_KEY,
            LuiServiceConstants.WEB_LECTURE_ACTIVITY_OFFERING_TYPE_KEY,
            "kuali.lui.type.course.bundle",
            LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY,
            LuiServiceConstants.COURSE_OFFERING_TYPE_KEY,
            LuiServiceConstants.ACTIVITY_OFFERING_GROUP_TYPE_KEY,
            PopulationServiceConstants.POPULATION_RULE_TYPE_EXCLUSION_KEY,
            PopulationServiceConstants.POPULATION_RULE_TYPE_INTERSECTION_KEY,
            PopulationServiceConstants.POPULATION_RULE_TYPE_PERSON_KEY,
            PopulationServiceConstants.POPULATION_RULE_TYPE_RULE_KEY,
            PopulationServiceConstants.POPULATION_RULE_TYPE_UNION_KEY,
            PopulationServiceConstants.POPULATION_STUDENT_TYPE_KEY,
            PopulationServiceConstants.POPULATION_RULE_TYPE_SEARCH_KEY,
            SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST,
            TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY,
            TypeServiceConstants.TYPE_TYPE_RELATION_GROUP_TYPE_KEY
    ));


    public TypeVerificationController(){
        //typeService = this.getTypeService();
        keyToUri.put("appointment", "AppointmentServiceConstants");
        keyToUri.put("atp", "AtpServiceConstants");
        keyToUri.put("milestone", "AtpServiceConstants");
        keyToUri.put("soc", "CourseOfferingSetServiceConstants");
        keyToUri.put("hold", "AppointmentServiceConstants");
        keyToUri.put("lpr", "LprServiceConstants");
        keyToUri.put("result", "LrcServiceConstants");
        keyToUri.put("lu", "LuServiceConstants");
        keyToUri.put("lui", "LuiServiceConstants");
        keyToUri.put("population", "PopulationServiceConstants");
        keyToUri.put("scheduling", "SchedulingServiceConstants");
        keyToUri.put("type", "TypeServiceConstants");
        inDBNotInJava = new ArrayList<TypeVerificationBasics>();
        inJavaNotInDB = new ArrayList<TypeVerificationBasics>();
        equivalences = new ArrayList<TypeVerificationBasics>();
        typeInfo = new TypeVerificationInfo();
    }

    @Override
    @RequestMapping(params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form,
                              HttpServletRequest request, HttpServletResponse response) {

        // check view authorization
        // TODO: this needs to be invoked for each request
        if (form.getView() != null) {
            String methodToCall = request.getParameter(KRADConstants.DISPATCH_REQUEST_PARAMETER);
            checkViewAuthorization(form, methodToCall);
        }

        getEquivalence();
        getInDBNotInJava();
        getInJavaNotInDB();

        ((TypeVerificationInfo) form).setEquivalences(typeInfo.getEquivalences());
        ((TypeVerificationInfo) form).setNotInJava(typeInfo.getNotInJava());
        ((TypeVerificationInfo) form).setNotInDB(typeInfo.getNotInDB());


        return getUIFModelAndView(form);
    }



    @Override
    protected UifFormBase createInitialForm(HttpServletRequest httpServletRequest) {
        return typeInfo;
    }

    @RequestMapping(params = "methodToCall=getEquivalence")
    public List<TypeVerificationBasics> getEquivalence(){
        for(String line: typeKeysDB){
            findEqual(line);
        }
        typeInfo.setEquivalences(equivalences);
            return typeInfo.getEquivalences();
    }

    @RequestMapping(params = "methodToCall=getInDBNotInJava")
    public List<TypeVerificationBasics> getInDBNotInJava(){
        for(String line: typeKeysDB){
            findInDbNotInJava(line);
        }
        typeInfo.setNotInJava(inDBNotInJava);
        return typeInfo.getNotInJava();
    }

    @RequestMapping(params = "methodToCall=getInJavaNotInDB")
    public List<TypeVerificationBasics> getInJavaNotInDB(){
        findInJavaNotInDB();
        typeInfo.setNotInDB(inJavaNotInDB);
        return typeInfo.getNotInDB();
    }



    private TypeService getTypeService() {
        if(typeService  == null) {
            typeService = (TypeService) GlobalResourceLoader.getService(new QName(TypeServiceConstants.NAMESPACE, "TypeService"));
        }
        return typeService;
    }

    public TypeInfo getType(String typeKey){
        ContextInfo context = ContextUtils.createDefaultContextInfo();
        TypeInfo info;
        try{
            info=typeService.getType(typeKey, context);
        } catch (Exception ex){
            throw new RuntimeException(ex);
        }
        return info;
    }

    public void findEqual(String typeKey){
        validate(typeKey, 0);
    }

    public void findInDbNotInJava(String typeKey){
        validate(typeKey, 1);
    }

    public void findInJavaNotInDB(){
        ArrayList<String> serviceConstants = new ArrayList<String>(keyToUri.keySet());
        for(String constant: serviceConstants){
            validate(constant, 2);
        }

    }

    public void validate(String typeKey, int function){

        String key = typeKey;
        String constantFile =  typeKey;
        if(function!=2){
            constantFile=constantFile.substring(6);
            constantFile=constantFile.substring(0,constantFile.indexOf("."));
        }
        if(constantFile.equals("appointment")){
            Class<?> serviceClass = AppointmentServiceConstants.class;
            Field[] fields = serviceClass.getFields();
            ArrayList<Field> fieldsAL = new ArrayList<Field>();
            ArrayList<String> fieldValues= new ArrayList<String>();
            for(int i=0; i<fields.length; i++){
                fieldsAL.add(fields[i]);
                try{
                if(!(fieldsAL.get(fieldsAL.size()-1).get(null) instanceof String) ){
                      fieldsAL.remove(fieldsAL.size()-1);
                }  else {

                    fieldValues.add((String) fieldsAL.get(fieldsAL.size()-1).get(null));


                }
                }  catch (IllegalAccessException ex){

                }
            }
            if(function==2){
                   for(String fieldValue: fieldValues){
                       if(fieldValue.contains("type")){
                            if(!(typeKeysDB.contains(fieldValue))){
                                String constName = fieldsAL.get(fieldValues.indexOf(fieldValue)).getName();
                                TypeVerificationBasics basics = new TypeVerificationBasics("NONE", serviceClass.getName(), constName );
                                inJavaNotInDB.add(basics);
                                //inJavaNotInDB.add(fieldsAL.get(fieldValues.indexOf(fieldValue)).getName() + " = " + fieldValue + " is not in the database");
                             }
                       }
                   }
            } else {
            if(fieldValues.contains(typeKey)){
                if(function==0){
                    String constName = fieldsAL.get(fieldValues.indexOf(typeKey)).getName();
                    try {
                        String constValue = (String) fieldsAL.get(fieldValues.indexOf(typeKey)).get(null);
                        TypeVerificationBasics basics = new TypeVerificationBasics(constValue, serviceClass.getName(), constName);
                        equivalences.add(basics);
                        //equivalences.add(constName + " =  " + constValue);
                    } catch (IllegalAccessException e) {
                       //illegal access exception
                    }

                }
                //return fieldsAL.get(fieldValues.indexOf(typeKey)).getName();

            } else {
                if(function==1){
                    TypeVerificationBasics basics = new TypeVerificationBasics(typeKey, serviceClass.getName(), "NONE");
                    inDBNotInJava.add(basics);
                    //inDBNotInJava.add(typeKey + " does not have a corresponding Java constant");
                }
            }

        }
        }
        if(constantFile.equals("atp")){
            Class<?> serviceClass = AtpServiceConstants.class;
            Field[] fields = serviceClass.getFields();
            ArrayList<Field> fieldsAL = new ArrayList<Field>();
            ArrayList<String> fieldValues= new ArrayList<String>();
            for(int i=0; i<fields.length; i++){
                fieldsAL.add(fields[i]);
                try{
                    if(!(fieldsAL.get(fieldsAL.size()-1).get(null) instanceof String) ){
                        fieldsAL.remove(fieldsAL.size()-1);
                    }  else {

                        fieldValues.add((String) fieldsAL.get(fieldsAL.size()-1).get(null));


                    }
                }  catch (IllegalAccessException ex){

                }
            }
            if(function==2){
                for(String fieldValue: fieldValues){
                    if(fieldValue.contains("type")){
                        if(!(typeKeysDB.contains(fieldValue))){
                            String constName = fieldsAL.get(fieldValues.indexOf(fieldValue)).getName();
                            TypeVerificationBasics basics = new TypeVerificationBasics("NONE", serviceClass.getName(), constName );
                            inJavaNotInDB.add(basics);
                            //inJavaNotInDB.add(fieldsAL.get(fieldValues.indexOf(fieldValue)).getName() + " = " + fieldValue + " is not in the database");
                        }
                    }
                }
            } else {
            if(fieldValues.contains(typeKey)){
                if(function==0){
                    String constName = fieldsAL.get(fieldValues.indexOf(typeKey)).getName();
                    try {
                        String constValue = (String) fieldsAL.get(fieldValues.indexOf(typeKey)).get(null);
                        TypeVerificationBasics basics = new TypeVerificationBasics(constValue, serviceClass.getName(), constName);
                        equivalences.add(basics);
                        //equivalences.add(constName + " =  " + constValue);
                    } catch (IllegalAccessException e) {
                        //illegal access exception
                    }

                }
                //return fieldsAL.get(fieldValues.indexOf(typeKey)).getName();

            } else {
                if(function==1){
                    TypeVerificationBasics basics = new TypeVerificationBasics(typeKey, serviceClass.getName(), "NONE");
                    inDBNotInJava.add(basics);
                    //inDBNotInJava.add(typeKey + " does not have a corresponding Java constant");
                }
            }
            }
        }
        if(constantFile.equals("soc")){
            Class<?> serviceClass = CourseOfferingSetServiceConstants.class;
            Field[] fields = serviceClass.getFields();
            ArrayList<Field> fieldsAL = new ArrayList<Field>();
            ArrayList<String> fieldValues= new ArrayList<String>();
            for(int i=0; i<fields.length; i++){
                fieldsAL.add(fields[i]);
                try{
                    if(!(fieldsAL.get(fieldsAL.size()-1).get(null) instanceof String) ){
                        fieldsAL.remove(fieldsAL.size()-1);
                    }  else {

                        fieldValues.add((String) fieldsAL.get(fieldsAL.size()-1).get(null));


                    }
                }  catch (IllegalAccessException ex){

                }
            }
            if(function==2){
                for(String fieldValue: fieldValues){
                    if(fieldValue.contains("type")){
                        if(!(typeKeysDB.contains(fieldValue))){
                            String constName = fieldsAL.get(fieldValues.indexOf(fieldValue)).getName();
                            TypeVerificationBasics basics = new TypeVerificationBasics("NONE", serviceClass.getName(), constName );
                            inJavaNotInDB.add(basics);
                            //inJavaNotInDB.add(fieldsAL.get(fieldValues.indexOf(fieldValue)).getName() + " = " + fieldValue + " is not in the database");
                        }
                    }
                }
            } else {
            if(fieldValues.contains(typeKey)){
                if(function==0){
                    String constName = fieldsAL.get(fieldValues.indexOf(typeKey)).getName();
                    try {
                        String constValue = (String) fieldsAL.get(fieldValues.indexOf(typeKey)).get(null);
                        TypeVerificationBasics basics = new TypeVerificationBasics(constValue, serviceClass.getName(), constName);
                        equivalences.add(basics);
                        //equivalences.add(constName + " =  " + constValue);
                    } catch (IllegalAccessException e) {
                        //illegal access exception
                    }

                }
                //return fieldsAL.get(fieldValues.indexOf(typeKey)).getName();

            } else {
                if(function==1){
                    TypeVerificationBasics basics = new TypeVerificationBasics(typeKey, serviceClass.getName(), "NONE");
                    inDBNotInJava.add(basics);
                    //inDBNotInJava.add(typeKey + " does not have a corresponding Java constant");
                }
            }
            }
        }
        if(constantFile.equals("lpr")){
            Class<?> serviceClass = LprServiceConstants.class;
            Field[] fields = serviceClass.getFields();
            ArrayList<Field> fieldsAL = new ArrayList<Field>();
            ArrayList<String> fieldValues= new ArrayList<String>();
            for(int i=0; i<fields.length; i++){
                fieldsAL.add(fields[i]);
                try{
                    if(!(fieldsAL.get(fieldsAL.size()-1).get(null) instanceof String) ){
                        fieldsAL.remove(fieldsAL.size()-1);
                    }  else {

                        fieldValues.add((String) fieldsAL.get(fieldsAL.size()-1).get(null));


                    }
                }  catch (IllegalAccessException ex){

                }
            }
            if(function==2){
                for(String fieldValue: fieldValues){
                    if(fieldValue.contains("type")){
                        if(!(typeKeysDB.contains(fieldValue))){
                            String constName = fieldsAL.get(fieldValues.indexOf(fieldValue)).getName();
                            TypeVerificationBasics basics = new TypeVerificationBasics("NONE", serviceClass.getName(), constName );
                            inJavaNotInDB.add(basics);
                            //inJavaNotInDB.add(fieldsAL.get(fieldValues.indexOf(fieldValue)).getName() + " = " + fieldValue + " is not in the database");
                        }
                    }
                }
            } else {
            if(fieldValues.contains(typeKey)){
                if(function==0){
                    String constName = fieldsAL.get(fieldValues.indexOf(typeKey)).getName();
                    try {
                        String constValue = (String) fieldsAL.get(fieldValues.indexOf(typeKey)).get(null);
                        TypeVerificationBasics basics = new TypeVerificationBasics(constValue, serviceClass.getName(), constName);
                        equivalences.add(basics);
                        //equivalences.add(constName + " =  " + constValue);
                    } catch (IllegalAccessException e) {
                        //illegal access exception
                    }

                }
               // return fieldsAL.get(fieldValues.indexOf(typeKey)).getName();

            } else {
                if(function==1){
                    TypeVerificationBasics basics = new TypeVerificationBasics(typeKey, serviceClass.getName(), "NONE");
                    inDBNotInJava.add(basics);
                    //inDBNotInJava.add(typeKey + " does not have a corresponding Java constant");
                }
            }
            }
        }
        if(constantFile.equals("lu")){
            Class<?> serviceClass = LuServiceConstants.class;
            Field[] fields = serviceClass.getFields();
            ArrayList<Field> fieldsAL = new ArrayList<Field>();
            ArrayList<String> fieldValues= new ArrayList<String>();
            for(int i=0; i<fields.length; i++){
                fieldsAL.add(fields[i]);
                try{
                    if(!(fieldsAL.get(fieldsAL.size()-1).get(null) instanceof String) ){
                        fieldsAL.remove(fieldsAL.size()-1);
                    }  else {

                        fieldValues.add((String) fieldsAL.get(fieldsAL.size()-1).get(null));


                    }
                }  catch (IllegalAccessException ex){

                }
            }
            if(function==2){
                for(String fieldValue: fieldValues){
                    if(fieldValue.contains("type")){
                        if(!(typeKeysDB.contains(fieldValue))){
                            String constName = fieldsAL.get(fieldValues.indexOf(fieldValue)).getName();
                            TypeVerificationBasics basics = new TypeVerificationBasics("NONE", serviceClass.getName(), constName );
                            inJavaNotInDB.add(basics);
                            //inJavaNotInDB.add(fieldsAL.get(fieldValues.indexOf(fieldValue)).getName() + " = " + fieldValue + " is not in the database");
                        }
                    }
                }
            } else {
            if(fieldValues.contains(typeKey)){
                if(function==0){
                    String constName = fieldsAL.get(fieldValues.indexOf(typeKey)).getName();
                    try {
                        String constValue = (String) fieldsAL.get(fieldValues.indexOf(typeKey)).get(null);
                        TypeVerificationBasics basics = new TypeVerificationBasics(constValue, serviceClass.getName(), constName);
                        equivalences.add(basics);
                        //equivalences.add(constName + " =  " + constValue);
                    } catch (IllegalAccessException e) {
                        //illegal access exception
                    }

                }
                // fieldsAL.get(fieldValues.indexOf(typeKey)).getName();

            } else {
                if(function==1){
                    TypeVerificationBasics basics = new TypeVerificationBasics(typeKey, serviceClass.getName(), "NONE");
                    inDBNotInJava.add(basics);
                    //inDBNotInJava.add(typeKey + " does not have a corresponding Java constant");
                }
            }
            }
        }
        if(constantFile.equals("population")){
            Class<?> serviceClass = PopulationServiceConstants.class;
            Field[] fields = serviceClass.getFields();
            ArrayList<Field> fieldsAL = new ArrayList<Field>();
            ArrayList<String> fieldValues= new ArrayList<String>();
            for(int i=0; i<fields.length; i++){
                fieldsAL.add(fields[i]);
                try{
                    if(!(fieldsAL.get(fieldsAL.size()-1).get(null) instanceof String) ){
                        fieldsAL.remove(fieldsAL.size()-1);
                    }  else {

                        fieldValues.add((String) fieldsAL.get(fieldsAL.size()-1).get(null));


                    }
                }  catch (IllegalAccessException ex){

                }
            }
            if(function==2){
                for(String fieldValue: fieldValues){
                    if(fieldValue.contains("type")){
                        if(!(typeKeysDB.contains(fieldValue))){
                            String constName = fieldsAL.get(fieldValues.indexOf(fieldValue)).getName();
                            TypeVerificationBasics basics = new TypeVerificationBasics("NONE", serviceClass.getName(), constName );
                            inJavaNotInDB.add(basics);
                            //inJavaNotInDB.add(fieldsAL.get(fieldValues.indexOf(fieldValue)).getName() + " = " + fieldValue + " is not in the database");
                        }
                    }
                }
            } else {
            if(fieldValues.contains(typeKey)){
                if(function==0){
                    String constName = fieldsAL.get(fieldValues.indexOf(typeKey)).getName();
                    try {
                        String constValue = (String) fieldsAL.get(fieldValues.indexOf(typeKey)).get(null);
                        TypeVerificationBasics basics = new TypeVerificationBasics(constValue, serviceClass.getName(), constName);
                        equivalences.add(basics);
                        //equivalences.add(constName + " =  " + constValue);
                    } catch (IllegalAccessException e) {
                        //illegal access exception
                    }

                }
                //return fieldsAL.get(fieldValues.indexOf(typeKey)).getName();

            } else {
                if(function==1){
                    TypeVerificationBasics basics = new TypeVerificationBasics(typeKey, serviceClass.getName(), "NONE");
                    inDBNotInJava.add(basics);
                    //inDBNotInJava.add(typeKey + " does not have a corresponding Java constant");
                }
            }
            }
        }
        if(constantFile.equals("scheduling")){
            Class<?> serviceClass = SchedulingServiceConstants.class;
            Field[] fields = serviceClass.getFields();
            ArrayList<Field> fieldsAL = new ArrayList<Field>();
            ArrayList<String> fieldValues= new ArrayList<String>();
            for(int i=0; i<fields.length; i++){
                fieldsAL.add(fields[i]);
                try{
                    if(!(fieldsAL.get(fieldsAL.size()-1).get(null) instanceof String) ){
                        fieldsAL.remove(fieldsAL.size()-1);
                    }  else {

                        fieldValues.add((String) fieldsAL.get(fieldsAL.size()-1).get(null));


                    }
                }  catch (IllegalAccessException ex){

                }
            }
            if(function==2){
                for(String fieldValue: fieldValues){
                    if(fieldValue.contains("type")){
                        if(!(typeKeysDB.contains(fieldValue))){
                            String constName = fieldsAL.get(fieldValues.indexOf(fieldValue)).getName();
                            TypeVerificationBasics basics = new TypeVerificationBasics("NONE", serviceClass.getName(), constName );
                            inJavaNotInDB.add(basics);
                            //inJavaNotInDB.add(fieldsAL.get(fieldValues.indexOf(fieldValue)).getName() + " = " + fieldValue + " is not in the database");
                        }
                    }
                }
            } else {
            if(fieldValues.contains(typeKey)){
                if(function==0){
                    String constName = fieldsAL.get(fieldValues.indexOf(typeKey)).getName();
                    try {
                        String constValue = (String) fieldsAL.get(fieldValues.indexOf(typeKey)).get(null);
                        TypeVerificationBasics basics = new TypeVerificationBasics(constValue, serviceClass.getName(), constName);
                        equivalences.add(basics);
                        //equivalences.add(constName + " =  " + constValue);
                    } catch (IllegalAccessException e) {
                        //illegal access exception
                    }

                }
                //return fieldsAL.get(fieldValues.indexOf(typeKey)).getName();

            } else {
                if(function==1){
                    TypeVerificationBasics basics = new TypeVerificationBasics(typeKey, serviceClass.getName(), "NONE");
                    inDBNotInJava.add(basics);
                    //inDBNotInJava.add(typeKey + " does not have a corresponding Java constant");
                }
            }
            }
        }

        if(constantFile.equals("result")){
            Class<?> serviceClass = LrcServiceConstants.class;
            Field[] fields = serviceClass.getFields();
            ArrayList<Field> fieldsAL = new ArrayList<Field>();
            ArrayList<String> fieldValues= new ArrayList<String>();
            for(int i=0; i<fields.length; i++){
                fieldsAL.add(fields[i]);
                try{
                    if(!(fieldsAL.get(fieldsAL.size()-1).get(null) instanceof String) ){
                        fieldsAL.remove(fieldsAL.size()-1);
                    }  else {

                        fieldValues.add((String) fieldsAL.get(fieldsAL.size()-1).get(null));


                    }
                }  catch (IllegalAccessException ex){

                }
            }
            if(function==2){
                for(String fieldValue: fieldValues){
                    if(fieldValue.contains("type")){
                        if(!(typeKeysDB.contains(fieldValue))){
                            String constName = fieldsAL.get(fieldValues.indexOf(fieldValue)).getName();
                            TypeVerificationBasics basics = new TypeVerificationBasics("NONE", serviceClass.getName(), constName );
                            inJavaNotInDB.add(basics);
                            //inJavaNotInDB.add(fieldsAL.get(fieldValues.indexOf(fieldValue)).getName() + " = " + fieldValue + " is not in the database");
                        }
                    }
                }
            } else {
            if(fieldValues.contains(typeKey)){
                if(function==0){
                    String constName = fieldsAL.get(fieldValues.indexOf(typeKey)).getName();
                    try {
                        String constValue = (String) fieldsAL.get(fieldValues.indexOf(typeKey)).get(null);
                        TypeVerificationBasics basics = new TypeVerificationBasics(constValue, serviceClass.getName(), constName);
                        equivalences.add(basics);
                        //equivalences.add(constName + " =  " + constValue);
                    } catch (IllegalAccessException e) {
                        //illegal access exception
                    }

                }
                //return fieldsAL.get(fieldValues.indexOf(typeKey)).getName();

            } else {
                if(function==1){
                    TypeVerificationBasics basics = new TypeVerificationBasics(typeKey, serviceClass.getName(), "NONE");
                    inDBNotInJava.add(basics);
                    //inDBNotInJava.add(typeKey + " does not have a corresponding Java constant");
                }
            }
            }
        }

        if(constantFile.equals("lui")){
            Class<?> serviceClass = LuiServiceConstants.class;
            Field[] fields = serviceClass.getFields();
            ArrayList<Field> fieldsAL = new ArrayList<Field>();
            ArrayList<String> fieldValues= new ArrayList<String>();
            for(int i=0; i<fields.length; i++){
                fieldsAL.add(fields[i]);
                try{
                    if(!(fieldsAL.get(fieldsAL.size()-1).get(null) instanceof String) ){
                        fieldsAL.remove(fieldsAL.size()-1);
                    }  else {

                        fieldValues.add((String) fieldsAL.get(fieldsAL.size()-1).get(null));


                    }
                }  catch (IllegalAccessException ex){

                }
            }
            if(function==2){
                for(String fieldValue: fieldValues){
                    if(fieldValue.contains("type")){
                        if(!(typeKeysDB.contains(fieldValue))){
                            String constName = fieldsAL.get(fieldValues.indexOf(fieldValue)).getName();
                            TypeVerificationBasics basics = new TypeVerificationBasics("NONE", serviceClass.getName(), constName );
                            inJavaNotInDB.add(basics);
                            //inJavaNotInDB.add(fieldsAL.get(fieldValues.indexOf(fieldValue)).getName() + " = " + fieldValue + " is not in the database");
                        }
                    }
                }
            } else {
            if(fieldValues.contains(typeKey)){
                if(function==0){
                    String constName = fieldsAL.get(fieldValues.indexOf(typeKey)).getName();
                    try {
                        String constValue = (String) fieldsAL.get(fieldValues.indexOf(typeKey)).get(null);
                        TypeVerificationBasics basics = new TypeVerificationBasics(constValue, serviceClass.getName(), constName);
                        equivalences.add(basics);
                        //equivalences.add(constName + " =  " + constValue);
                    } catch (IllegalAccessException e) {
                        //illegal access exception
                    }

                }
                //return fieldsAL.get(fieldValues.indexOf(typeKey)).getName();

            } else {
                if(function==1){
                    TypeVerificationBasics basics = new TypeVerificationBasics(typeKey, serviceClass.getName(), "NONE");
                    inDBNotInJava.add(basics);
                    //inDBNotInJava.add(typeKey + " does not have a corresponding Java constant");
                }
            }
            }
        }

        if(constantFile.equals("hold")){
            Class<?> serviceClass = HoldServiceConstants.class;
            Field[] fields = serviceClass.getFields();
            ArrayList<Field> fieldsAL = new ArrayList<Field>();
            ArrayList<String> fieldValues= new ArrayList<String>();
            for(int i=0; i<fields.length; i++){
                fieldsAL.add(fields[i]);
                try{
                    if(!(fieldsAL.get(fieldsAL.size()-1).get(null) instanceof String) ){
                        fieldsAL.remove(fieldsAL.size()-1);
                    }  else {

                        fieldValues.add((String) fieldsAL.get(fieldsAL.size()-1).get(null));


                    }
                }  catch (IllegalAccessException ex){

                }
            }
            if(function==2){
                for(String fieldValue: fieldValues){
                    if(fieldValue.contains("type")){
                        if(!(typeKeysDB.contains(fieldValue))){
                            String constName = fieldsAL.get(fieldValues.indexOf(fieldValue)).getName();
                            TypeVerificationBasics basics = new TypeVerificationBasics("NONE", serviceClass.getName(), constName );
                            inJavaNotInDB.add(basics);
                            //inJavaNotInDB.add(fieldsAL.get(fieldValues.indexOf(fieldValue)).getName() + " = " + fieldValue + " is not in the database");
                        }
                    }
                }
            } else {
            if(fieldValues.contains(typeKey)){
                if(function==0){
                    String constName = fieldsAL.get(fieldValues.indexOf(typeKey)).getName();
                    try {
                        String constValue = (String) fieldsAL.get(fieldValues.indexOf(typeKey)).get(null);
                        TypeVerificationBasics basics = new TypeVerificationBasics(constValue, serviceClass.getName(), constName);
                        equivalences.add(basics);
                        //equivalences.add(constName + " =  " + constValue);
                    } catch (IllegalAccessException e) {
                        //illegal access exception
                    }

                }
                //return fieldsAL.get(fieldValues.indexOf(typeKey)).getName();

            } else {
                if(function==1){
                    TypeVerificationBasics basics = new TypeVerificationBasics(typeKey, serviceClass.getName(), "NONE");
                    inDBNotInJava.add(basics);
                    //inDBNotInJava.add(typeKey + " does not have a corresponding Java constant");
                }
            }
            }
        }
        if(constantFile.equals("type")){
            Class<?> serviceClass = TypeServiceConstants.class;
            Field[] fields = serviceClass.getFields();
            ArrayList<Field> fieldsAL = new ArrayList<Field>();
            ArrayList<String> fieldValues= new ArrayList<String>();
            for(int i=0; i<fields.length; i++){
                fieldsAL.add(fields[i]);
                try{
                    if(!(fieldsAL.get(fieldsAL.size()-1).get(null) instanceof String) ){
                        fieldsAL.remove(fieldsAL.size()-1);
                    }  else {

                        fieldValues.add((String) fieldsAL.get(fieldsAL.size()-1).get(null));


                    }
                }  catch (IllegalAccessException ex){

                }
            }
            if(function==2){
                for(String fieldValue: fieldValues){
                    if(fieldValue.contains("type")){
                        if(!(typeKeysDB.contains(fieldValue))){
                            String constName = fieldsAL.get(fieldValues.indexOf(fieldValue)).getName();
                            TypeVerificationBasics basics = new TypeVerificationBasics("NONE", serviceClass.getName(), constName );
                            inJavaNotInDB.add(basics);
                            //inJavaNotInDB.add(fieldsAL.get(fieldValues.indexOf(fieldValue)).getName() + " = " + fieldValue + " is not in the database");
                        }
                    }
                }
            } else {
            if(fieldValues.contains(typeKey)){
                if(function==0){
                    String constName = fieldsAL.get(fieldValues.indexOf(typeKey)).getName();
                    try {
                        String constValue = (String) fieldsAL.get(fieldValues.indexOf(typeKey)).get(null);
                        TypeVerificationBasics basics = new TypeVerificationBasics(constValue, serviceClass.getName(), constName);
                        equivalences.add(basics);
                        //equivalences.add(constName + " =  " + constValue);
                    } catch (IllegalAccessException e) {
                        //illegal access exception
                    }

                }
                //return fieldsAL.get(fieldValues.indexOf(typeKey)).getName();

            } else {
                if(function==1){
                    TypeVerificationBasics basics = new TypeVerificationBasics(typeKey, serviceClass.getName(), "NONE");
                    inDBNotInJava.add(basics);
                    //inDBNotInJava.add(typeKey + " does not have a corresponding Java constant");
                }
            }
        }
        }

        typeInfo.setEquivalences(equivalences);
        typeInfo.setNotInDB(inJavaNotInDB);
        typeInfo.setNotInJava(inDBNotInJava);

    }
    public TypeVerificationInfo getTypeVerificationInfo(){
        return typeInfo;
    }

}
