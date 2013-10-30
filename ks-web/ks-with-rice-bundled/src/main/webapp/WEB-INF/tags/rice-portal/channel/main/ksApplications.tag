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

<channel:portalChannelTop channelTitle="Kuali Student Applications" />
<div class="body">
  
  <ul class="chan">
    <li><a href="${ConfigProperties.ks.home.MainEntryPoint}">Kuali Student Home</a></li>
    <li><a href="${ConfigProperties.ks.lum.MainEntryPoint}">Curriculum Management</a></li>
    <!--<li><a href="${ConfigProperties.ks.org.MainEntryPoint}">Organization Management</a></li>      -->
  </ul>
  <ul class="chan">
    <li><a href="${ConfigProperties.application.url}/kr-krad/launch?viewId=enrollmentHomeView&methodToCall=start">Enrollment Home</a></li>
  </ul>
    <ul class="chan">
        <li><a href="${ConfigProperties.application.url}/myplan/course?methodToCall=start&amp;viewId=CourseSearch-FormView">KSAP Course Search</a></li>
        <li><a href="${ConfigProperties.application.url}/myplan/plan?methodToCall=start&amp;viewId=PlannedCourses-FormView">KSAP Planner</a></li>
        <li><a href="${ConfigProperties.application.url}/myplan/plan?methodToCall=start&amp;viewId=PlannedCourses-FormView-POC-Same-View">KSAP Planner - No Page Embedded</a></li>
        <li><a href="${ConfigProperties.application.url}/myplan/plan?methodToCall=start&amp;viewId=PlannedCourses-FormView-POC-Single-Year">KSAP Planner - Single Year</a></li>
        <li><a href="${ConfigProperties.application.url}/myplan/plan?methodToCall=start&amp;viewId=PlannedCourses-FormView-POC-Dialog">KSAP Planner - Krad Dialog</a></li>
        <li><a href="${ConfigProperties.application.url}/myplan/plan?methodToCall=start&amp;viewId=PlannedCourses-FormView-POC-delay-load&amp;loadCalendar=true">KSAP Planner - Delayed Calendar Load</a></li>
        <br>
        <li><a href="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&amp;dataObjectClassName=org.kuali.student.r2.lum.course.dto.CourseInfo&amp;viewId=CourseInfo-LookupView-ForKSAP">Simple Course Lookup for KSAP</a></li>
    </ul>

</div>
<channel:portalChannelBottom />
