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
import org.kuali.student.enrollment.type.dto.TypeVerificationBasics;
import org.kuali.student.enrollment.type.dto.TypeVerificationInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.ContextUtils;
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
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import java.lang.reflect.Field;
import java.util.*;

/**
 * This class //TODO ...
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
            "kuali.attribute.course.evaluation.indicator",
            "kuali.attribute.final.exam.indicator",
            "kuali.attribute.finding.source",
            "kuali.attribute.grade.roster.level.type.key",
            "kuali.attribute.max.enrollment.is.estimate",
            "kuali.attribute.wait.list.level.type.key",
            "kuali.attribute.where.fees.attached.flag",
            "kuali.appointment.slot.type.closed",
            "kuali.appointment.type.registration",
            "kuali.appointment.window.type.one.slot",
            "kuali.appointment.window.type.slotted.max",
            "kuali.appointment.window.type.slotted.uniform",
            "kuali.atp.duration.Day",
            "kuali.atp.duration.FourYears",
            "kuali.atp.duration.HalfSemester",
            "kuali.atp.duration.Hours",
            "kuali.atp.duration.Mini-mester",
            "kuali.atp.duration.Minutes",
            "kuali.atp.duration.Month",
            "kuali.atp.duration.Period",
            "kuali.atp.duration.Quarter",
            "kuali.atp.duration.Semester",
            "kuali.atp.duration.Session",
            "kuali.atp.duration.TBD",
            "kuali.atp.duration.Term",
            "kuali.atp.duration.TwoYears",
            "kuali.atp.duration.Year",
            "kuali.atp.milestone.AddDropPeriod1",
            "kuali.atp.milestone.AddDropPeriod2",
            "kuali.atp.milestone.Christmas",
            "kuali.atp.milestone.ChristmasObserved",
            "kuali.atp.milestone.ColumbusDay",
            "kuali.atp.milestone.Commencement",
            "kuali.atp.milestone.FinalExamPeriod",
            "kuali.atp.milestone.GradingPeriod",
            "kuali.atp.milestone.Homecoming",
            "kuali.atp.milestone.IndependenceDay",
            "kuali.atp.milestone.IndependenceDayObserved",
            "kuali.atp.milestone.InstructionalPeriod",
            "kuali.atp.milestone.LaborDay",
            "kuali.atp.milestone.MLKDayObserved",
            "kuali.atp.milestone.MemorialDay",
            "kuali.atp.milestone.NewStudentConvocation",
            "kuali.atp.milestone.NewYearsDay",
            "kuali.atp.milestone.NewYearsDayObserved",
            "kuali.atp.milestone.PresidentsDay",
            "kuali.atp.milestone.RegistrationOpen",
            "kuali.atp.milestone.RegistrationPeriod1",
            "kuali.atp.milestone.RegistrationPeriod2",
            "kuali.atp.milestone.RegistrationPeriod3",
            "kuali.atp.milestone.RegistrationPeriod4",
            "kuali.atp.milestone.RegistrationPeriod5",
            "kuali.atp.milestone.RegistrationPeriod6",
            "kuali.atp.milestone.ThanksgivingBreak",
            "kuali.atp.milestone.VeteransDayObserved",
            "kuali.atp.milestone.endoffirstweekofclasses",
            "kuali.atp.milestone.firstdayofclasses",
            "kuali.atp.milestone.monthpriortostartofclasses",
            "kuali.atp.type.AcademicCalendar",
            "kuali.atp.type.Fall",
            "kuali.atp.type.HolidayCalendar",
            "kuali.atp.type.Summer",
            "kuali.atp.type.Winter",
            "kuali.milestone.type.group.appt.regperiods",
            "kuali.milestone.type.group.event",
            "kuali.milestone.type.group.holiday",
            "kuali.milestone.type.group.registration",
            "kuali.milestone.type.group.seatpool",
            "kuali.atp.duration.Week",
            "kuali.atp.milestone.AddDropPeriod3",
            "kuali.atp.milestone.VeteransDay",
            "kuali.atp.milestone.lastdayofregistration",
            "kuali.atp.type.Spring",
            "kuali.atp.type.group.term",
            "kuali.milestone.type.group.instructional",
            "kuali.soc.rollover.results.type.reverse",
            "kuali.soc.rollover.results.type.rollover",
            "kuali.soc.type.departmental",
            "kuali.soc.type.main",
            "kuali.soc.type.subject.area",
            "kuali.soc.type.units.content.owner",
            "kuali.soc.type.units.deployment.owner",
            "kuali.hold.issue.type.academic.progress",
            "kuali.hold.issue.type.advising",
            "kuali.hold.issue.type.discipline",
            "kuali.hold.issue.type.library",
            "kuali.hold.type.student",
            "kuali.hold.issue.type.financial",
            "kuali.lpr.advisor",
            "kuali.lpr.enrollee",
            "kuali.lpr.exam.proctor",
            "kuali.lpr.roster.entry.type.automatic",
            "kuali.lpr.roster.type.course.grade.final",
            "kuali.lpr.trans.item.type.add",
            "kuali.lpr.trans.registrant",
            "kuali.lpr.trans.type.register",
            "kuali.lpr.type.instructor.assistant",
            "kuali.lpr.type.instructor.main",
            "kuali.lpr.type.instructor.support",
            "kuali.lpr.type.roster.grade.final",
            "kuali.lpr.instructor.supervisor",
            "kuali.lpr.trans.item.type.drop",
            "kuali.lpr.type.registrant",
            "kuali.result.scale.type.certificate",
            "kuali.result.scale.type.certification",
            "kuali.result.scale.type.credit",
            "kuali.result.scale.type.gpa",
            "kuali.result.scale.type.grade",
            "kuali.result.scale.type.grade.admin",
            "kuali.result.scale.type.honor",
            "kuali.result.scale.type.minor",
            "kuali.result.scale.type.requirement.completion",
            "kuali.result.value.type.value",
            "kuali.result.values.group.type.fixed",
            "kuali.result.values.group.type.multiple",
            "kuali.result.values.group.type.range",
            "kuali.result.scale.type.degree",
            "kuali.result.scale.type.student.year",
            "kuali.lu.type.activity.conference",
            "kuali.lu.type.activity.independentstudy",
            "kuali.lu.type.activity.practicum",
            "kuali.lu.type.activity.quiz",
            "kuali.lu.type.activity.seminar",
            "kuali.lu.type.activity.studio",
            "kuali.lu.type.grouping.activity",
            "kuali.lu.type.CreditCourse",
            "kuali.lu.type.CreditCourseFormatShell",
            "kuali.lu.type.activity.Directed",
            "kuali.lu.type.activity.Homework",
            "kuali.lu.type.activity.Lab",
            "kuali.lu.type.activity.Lecture",
            "kuali.lu.type.activity.Tutorial",
            "kuali.lu.type.activity.WebDiscussion",
            "kuali.lu.type.activity.WebLecture",
            "kuali.lu.type.activity.clinic",
            "kuali.lu.type.activity.Discussion",
            "kuali.lu.type.activity.clerkship",
            "kuali.lui.lui.relation.type.deliveredvia.co2fo",
            "kuali.lui.lui.relation.type.deliveredvia.fo2ao",
            "kuali.lui.lui.relation.type.deliveredvia.fo2rg",
            "kuali.lui.lui.relation.type.registeredforvia.rg2ao",
            "kuali.lui.type.activity.offering.activity",
            "kuali.lui.type.activity.offering.clerkship",
            "kuali.lui.type.activity.offering.clinic",
            "kuali.lui.type.activity.offering.colloquium",
            "kuali.lui.type.activity.offering.compbased",
            "kuali.lui.type.activity.offering.conference",
            "kuali.lui.type.activity.offering.correspond",
            "kuali.lui.type.activity.offering.demonstration",
            "kuali.lui.type.activity.offering.directed",
            "kuali.lui.type.activity.offering.discussion",
            "kuali.lui.type.activity.offering.field",
            "kuali.lui.type.activity.offering.homework",
            "kuali.lui.type.activity.offering.independ",
            "kuali.lui.type.activity.offering.internship",
            "kuali.lui.type.activity.offering.lab",
            "kuali.lui.type.activity.offering.lecture",
            "kuali.lui.type.activity.offering.practicum",
            "kuali.lui.type.activity.offering.private",
            "kuali.lui.type.activity.offering.quiz",
            "kuali.lui.type.activity.offering.recitation",
            "kuali.lui.type.activity.offering.research",
            "kuali.lui.type.activity.offering.selfpaced",
            "kuali.lui.type.activity.offering.seminar",
            "kuali.lui.type.activity.offering.studio",
            "kuali.lui.type.activity.offering.tutorial",
            "kuali.lui.type.activity.offering.videoconf",
            "kuali.lui.type.activity.offering.webdiscussion",
            "kuali.lui.type.activity.offering.weblecture",
            "kuali.lui.type.course.bundle",
            "kuali.lui.type.course.format.offering",
            "kuali.lui.type.course.offering",
            "kuali.lui.type.grouping.activity",
            "kuali.population.rule.type.exclusion",
            "kuali.population.rule.type.intersection",
            "kuali.population.rule.type.person",
            "kuali.population.rule.type.rule",
            "kuali.population.rule.type.union",
            "kuali.population.type.student",
            "kuali.population.rule.type.search",
            "kuali.scheduling.schedule.request.type.schedule.request",
            "kuali.type.type.relation.type.allowed",
            "kuali.type.type.relation.type.group"
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
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
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
