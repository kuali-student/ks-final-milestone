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
        <h3>KSAP Main</h3>
        <li><a href="${ConfigProperties.application.url}/myplan/course?methodToCall=start&amp;viewId=CourseSearch-FormView">KSAP Course Search</a></li>
        <li><a href="${ConfigProperties.application.url}/ap/planner?methodToCall=start&amp;viewId=Planner-FormView">KSAP Planner (v0.75)</a></li>
        <h3>KRAD Planner POC</h3>
        <li><a href="${ConfigProperties.application.url}/myplan/plan?methodToCall=start&amp;viewId=PlannedCourses-FormView-POC-Same-View">KSAP Planner - No Page Embedded</a></li>
        <li><a href="${ConfigProperties.application.url}/myplan/plan?methodToCall=start&amp;viewId=PlannedCourses-FormView-POC-Single-Year">KSAP Planner - Single Year</a></li>
        <li><a href="${ConfigProperties.application.url}/myplan/plan?methodToCall=start&amp;viewId=PlannedCourses-FormView-POC-Dialog">KSAP Planner - Krad Dialog</a></li>
        <li><a href="${ConfigProperties.application.url}/myplan/plan?methodToCall=start&amp;viewId=PlannedCourses-FormView-POC-delay-load&amp;loadCalendar=true">KSAP Planner - Delayed Calendar Load</a></li>
        <h3>0.75 Merge Other Ongoing Screens</h3>
        <li><a href="${ConfigProperties.application.url}/myplan/sb?termId=kuali.atp.2013Fall&requestedLearningPlanId=testPlan1">KSAP Schedule Builder</a></li>
        <li><a href="${ConfigProperties.application.url}/myplan/reviewList">KSAP Planner Review</a></li>
        <h4>Shopping Cart Dialogs - Not working</h4>
        <li><a href="${ConfigProperties.application.url}/myplan/sb/cart?viewId=ShoppingCart-FormView&pageId=sb_cart_add_from_plan_page">KSAP Add Shopping Cart From Plan</a></li>
        <li><a href="${ConfigProperties.application.url}/myplan/sb/cart?viewId=ShoppingCart-FormView&pageId=sb_cart_add_from_sb_page">KSAP Add Shopping Cart From Schedule Builder</a></li>
        <li><a href="${ConfigProperties.application.url}/myplan/sb/cart?viewId=ShoppingCart-FormView&pageId=sb_cart_remove_page">KSAP Remove Shopping Cart</a></li>
        <h3>Course Search POC</h3>
        <li><a href="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&amp;dataObjectClassName=org.kuali.student.r2.lum.course.dto.CourseInfo&amp;viewId=CourseInfo-LookupView-ForKSAP">Simple Course Lookup for KSAP</a></li>
        <h3>Legacy</h3>
        <li><a href="${ConfigProperties.application.url}/myplan/plan?methodToCall=start&amp;viewId=PlannedCourses-FormView">KSAP Planner (v0.7)</a></li>

    </ul>

</div>
<channel:portalChannelBottom />
