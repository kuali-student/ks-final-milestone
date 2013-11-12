<%@ page import="org.kuali.student.ap.utils.UserSessionHelper" %>
<%--

    Copyright 2005-2012 The Kuali Foundation

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
<div id="applicationHeader">
    <div id="applicationHeading">
        <div id="applicationLogo">MyPlan</div>
        <div id="applicationUser">
            <% if (!UserSessionHelper.isAdviser()) { %>
            <div class="identity">
                Welcome, <a class="name" onclick="openPopUpForm('student_academic_planner_page','student_academic_planner_page','startAcademicPlannerForm','plan',{viewId:'StudentAcademicPlanner-FormView',pageId:'student_academic_planner_page'},event,null,{width:'16px'},{tail:{align:'right'},align:'right',position:'bottom',alwaysVisible:'false'},true);">${UserSession.person.firstName}</a>
            </div>
            <% } %>
            <% if (UserSessionHelper.isAdviser()) { %>
            <div class="identity">
                Welcome, <span class="name">${UserSession.person.firstName}</span>
            </div>
            <% } %>
        </div>
        <div id="applicationNavigation">
            <ul>
                <li class="active home"><a href="planner?methodToCall=start&amp;viewId=Planner-FormView"">Planner</a></li>
                <li><a href="course?methodToCall=start&viewId=CourseSearch-FormView">Find Courses</a></li>
                <li><a href="audit?methodToCall=audit&viewId=DegreeAudit-FormView">Audit Degree</a></li>
            </ul>
        </div>
    </div>



    <% if (UserSessionHelper.isAdviser()) { %>
    <div class="adviser-banner">
        You are viewing <strong><%= UserSessionHelper.getStudentName() %>.</strong>'s MyPlan: functionalities are limited except
        <a href="comment?methodToCall=startCommentForm&amp;viewId=Message-FormView&amp;pageId=message_dialog_response_page">leaving
        a message</a> to <%= UserSessionHelper.getStudentName() %>. <a href="#">Learn more about Adviser View</a>
    </div>
    <% } %>
</div>