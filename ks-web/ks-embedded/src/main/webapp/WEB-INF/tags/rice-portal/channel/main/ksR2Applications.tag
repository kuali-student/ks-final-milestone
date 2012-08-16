<%--
 Copyright 2007-2009 The Kuali Foundation
 
 Licensed under the Educational Community License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 
 http://www.opensource.org/licenses/ecl2.php
 
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
--%>
<%@ include file="/rice-portal/jsp/sys/riceTldHeader.jsp"%>

<channel:portalChannelTop channelTitle="R2 Applications" />
<div class="body">
  <strong>KS Enroll -- Academic Calendar</strong>
  <ul class="chan">
      <li><portal:portalLink displayTitle="true" title="Academic Calendar Info Lookup 1(by Key)" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" /></li>
      <li><portal:portalLink displayTitle="true" title="Academic Calendar Info Lookup 2(General)" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&id=2&dataObjectClassName=org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo&viewId=AcademicCalendarInfo-LookupView2&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true"/></li>
      <li><portal:portalLink displayTitle="true" title="Academic Calendar Info Lookup 3" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&id=2&dataObjectClassName=org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo&viewId=AcademicCalendarInfo-LookupView3&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true"/></li>
      <li><portal:portalLink displayTitle="true" title="Academic Calendar Info Maintenance (New)" url="${ConfigProperties.application.url}/kr-krad/maintenance?methodToCall=start&dataObjectClassName=org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true"/></li>
      <br>
      <li><portal:portalLink displayTitle="true" title="Term Info Maintenance (New)" url="${ConfigProperties.application.url}/kr-krad/maintenance?methodToCall=start&dataObjectClassName=org.kuali.student.enrollment.acal.dto.TermInfo&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true"/></li>
      <li><portal:portalLink displayTitle="true" title="Term Info Lookup (by Key)" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.student.enrollment.acal.dto.TermInfo&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" /></li>
      <br>
      <li><portal:portalLink displayTitle="true" title="Term with Key Dates (New)" url="${ConfigProperties.application.url}/kr-krad/maintenance?methodToCall=start&dataObjectClassName=org.kuali.student.enrollment.class2.acal.dto.TermWrapper&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true"/></li>
      <li><portal:portalLink displayTitle="true" title="TermInfo --> TermWrapper Lookup (by Key)" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.student.enrollment.acal.dto.TermInfo&viewId=TermWrapper-LookupView2&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" /></li>
      <li><portal:portalLink displayTitle="true" title="Term Lookup (by Key)" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.student.enrollment.class2.acal.dto.TermWrapper&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" /></li>

      <br>
      <li><strong><portal:portalLink displayTitle="true" title="Academic Calendar with Terms (New)" url="${ConfigProperties.application.url}/kr-krad/maintenance?methodToCall=start&dataObjectClassName=org.kuali.student.enrollment.class2.acal.dto.AcademicCalendarWrapper&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true"/></strong></li>
      <li><strong><portal:portalLink displayTitle="true" title="Academic Calendar Lookup" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&id=2&dataObjectClassName=org.kuali.student.enrollment.class2.acal.dto.AcademicCalendarWrapper&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" /></strong></li>
<!--      <li><portal:portalLink displayTitle="true" title="Academic Calendar Inquiry" url="${ConfigProperties.application.url}/kr-krad/inquiry?methodToCall=start&key=kuali.academic.calendar.Baccalaureate.2011-2012&dataObjectClassName=org.kuali.student.enrollment.class2.acal.dto.AcademicCalendarWrapper&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true"/></li>
      -->
      
  </ul>
  <strong>KS Enroll -- Schedule Classes</strong>
  <ul class="chan">
      <li><portal:portalLink displayTitle="true" title="Schedule Classes" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&id=2&dataObjectClassName=org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo&viewId=ScheduleClasses-LookupView&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" /></li>
  </ul>
  <strong>KS Enroll -- Course Offering</strong>
  <ul class="chan">
  	  <li><portal:portalLink displayTitle="true" title="Create a Course Offering" url="${ConfigProperties.application.url}/kr-krad/maintenance?methodToCall=start&dataObjectClassName=org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true"/></li>
      <li><portal:portalLink displayTitle="true" title="Course Lookup" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.student.r2.lum.course.dto.CourseInfo&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" /></li>
 
  </ul>
  <strong>KS Enroll -- Registration</strong>
  <ul class="chan">
      <li><portal:portalLink displayTitle="true" title="Register for Fall 2011 Classes" url="${ConfigProperties.application.url}/kr-krad/registration?methodToCall=start&viewId=studentRegistrationView&termKey=testTermId1&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true"/></li>
  </ul>
  <strong>KS Enroll -- Grading</strong>
  <ul class="chan">
     <li><portal:portalLink displayTitle="true" title="Grading" url="${ConfigProperties.application.url}/kr-krad/grading?viewId=GradingView&methodToCall=start" /></li>
     <li><portal:portalLink displayTitle="true" title="View Term Grades" url="${ConfigProperties.application.url}/kr-krad/grading?viewId=StudentGradeView&methodToCall=start" /></li>
  </ul>
 <!-- 

    <li><portal:portalLink displayTitle="true" title="Manage Hold Maintenance (New)" url="${ConfigProperties.application.url}/kr-krad/maintenance?methodToCall=start&dataObjectClassName=org.kuali.student.enrollment.hold.dto.HoldInfo&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true"/></li>
-->    
<!-- 	<li><portal:portalLink displayTitle="true" title="Academic Calendar" url="${ConfigProperties.application.url}/kr-krad/acal?viewId=AcademicCalendarView&methodToCall=start" /></li> -->
 <!-- 
    <li><portal:portalLink displayTitle="true" title="Course Info Dto Lookup" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.student.enrollment.class2.acal.dto.CourseInfoDto&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" /></li>
-->    
      
</div>
<channel:portalChannelBottom />
