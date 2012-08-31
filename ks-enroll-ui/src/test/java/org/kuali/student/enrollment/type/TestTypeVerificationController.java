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
 * Created by cmuller on 8/21/12
 */
package org.kuali.student.enrollment.type;

import org.kuali.student.enrollment.type.controller.TypeVerificationController;
import org.kuali.student.enrollment.type.dto.TypeVerificationBasics;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.junit.Test;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.junit.runner.RunWith;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.Assert.*;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */


@Transactional
public class TestTypeVerificationController {


        TypeVerificationController tc;
        ArrayList<String> typeKeys = new ArrayList<String>(Arrays.asList(
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

    public TestTypeVerificationController() {
        tc = new TypeVerificationController();
    }

    @Test
        public void testEquivalence(){
                for(String line: typeKeys){
                    tc.findEqual(line);
                }
            for(TypeVerificationBasics basics: tc.getTypeVerificationInfo().getEquivalences()){
                System.out.println("typekey: " + basics.getTypeKey() + " service: " + basics.getService() + " constant: " + basics.getConstant());
            }

            }

         @Test
        public void testInDBNotInJava(){
            for(String line: typeKeys){
                tc.findInDbNotInJava(line);
            }
             for(TypeVerificationBasics basics: tc.getTypeVerificationInfo().getNotInJava()){
                 System.out.println("typekey: " + basics.getTypeKey() + " service: " + basics.getService() + " constant: " + basics.getConstant());
             }

         }
        @Test
         public void testInJavaNotInDB(){

             tc.findInJavaNotInDB();

            for(TypeVerificationBasics basics: tc.getTypeVerificationInfo().getNotInDB()){
                System.out.println("typekey: " + basics.getTypeKey() + " service: " + basics.getService() + " constant: " + basics.getConstant());
            }
        }

}
