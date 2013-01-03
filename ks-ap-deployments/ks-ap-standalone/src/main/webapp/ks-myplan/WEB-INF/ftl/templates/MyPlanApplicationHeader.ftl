<#macro myplan_app_header element>
<div id="applicationHeader">
    <div id="applicationHeading">
        <div id="applicationLogo">MyPlan</div>
        <div id="applicationUser">
            <#if UserSession.objectMap["kuali.uw.authz.adviser"]??>
                <#if UserSession.objectMap["kuali.uw.authz.adviser"]?string("true","false")=="true">
                    <div class="identity">
                        Welcome, <span class="name">${UserSession.person.firstName?cap_first} ${UserSession.person.lastName?substring(0,1)?capitalize} .</span>
                    </div>
                    <div class="logout">
                        <a href="javascript:openHelpWindow('http://depts.washington.edu/myplan/help-site/');">Help</a>
                        |
                        <a href="/${ConfigProperties['app.code']}/logout.do">Log out</a>
                    </div>
                </#if>
            <#else>
                <div class="identity">
                    Welcome, <a class="name" onclick="openPopUpForm('student_academic_planner_page','student_academic_planner_page','startAcademicPlannerForm','plan',
                        {viewId:'StudentAcademicPlanner-FormView',pageId:'student_academic_planner_page'},event,null,{width:'16px'},{tail:{align:'right'},align:'right',position:'bottom',alwaysVisible:'false'},true);">${UserSession.person.firstName?cap_first} ${UserSession.person.lastName?substring(0,1)?capitalize} .</a>
                </div>
            </#if>
        </div>

        <img id="myplanUwPatch" src="../ks-myplan/images/myplan_w_patch_purple.png"/>

        <div id="applicationNavigation">
            <ul>
                <#if RequestParameters.viewId??>
                <#--Plan Page Link-->
                    <#if RequestParameters.viewId=="PlannedCourses-FormView">
                        <li class="home"><a href="plan?methodToCall=start&viewId=PlannedCourses-FormView" class="active">Plan</a>
                        </li>
                    <#else>
                        <li class="home"><a href="plan?methodToCall=start&viewId=PlannedCourses-FormView">Plan</a>
                        </li>
                    </#if>

                <#--Find a Course Page Link-->
                    <#if RequestParameters.viewId=="CourseSearch-FormView">
                        <li><a href="course?methodToCall=start&viewId=CourseSearch-FormView" class="active">Find Courses</a></li>
                    <#else>
                        <li><a href="course?methodToCall=start&viewId=CourseSearch-FormView">Find Courses</a></li>
                    </#if>

                <#--DegreeAudit Page Link-->
                    <#if RequestParameters.viewId=="DegreeAudit-FormView">
                        <li><a href="audit?methodToCall=audit&viewId=DegreeAudit-FormView" class="active">Audit Degree</a></li>
                    <#else>
                        <li><a href="audit?methodToCall=audit&viewId=DegreeAudit-FormView">Audit Degree</a></li>
                    </#if>
                    <#else >
                        <li class="home"><a href="plan?methodToCall=start&viewId=PlannedCourses-FormView&currentPage=planPage">Plan</a></li>
                        <li><a href="course?methodToCall=start&viewId=CourseSearch-FormView&currentPage=coursePage">Find Courses</a></li>
                        <li><a href="audit?methodToCall=audit&viewId=DegreeAudit-FormView&currentPage=auditPage">Audit Degree</a></li>
                </#if>
            </ul>
        </div>
    </div>

    <#if UserSession.objectMap["kuali.uw.authz.adviser"]??>
        <#if UserSession.objectMap["kuali.uw.authz.adviser"]?string("true","false")=="true">
            <div class="adviser-banner myplan-status info" style="float: none;">
                <img src="/student/ks-myplan/images/pixel.gif" alt="" class="icon"/>
                <div class="message">
                    You&#39;re viewing <strong>${UserSession.objectMap["kuali.uw.authn.studentName"]}</strong>&#39;s MyPlan.
                    Some features are restricted in Adviser View. <a href="javascript:openHelpWindow('https://depts.washington.edu/myplan/?page_id=985#view');">Learn more.</a>
                </div>
            </div>
        </#if>
    </#if>
</div>
</#macro>