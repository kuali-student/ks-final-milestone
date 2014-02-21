<#macro ksap_app_header element>
<script type="text/javascript">
    function logout() {
        var url = "${ConfigProperties['rice.server.url']}/backdoorlogin.do?methodToCall=logout";
        redirect(url);
    }
</script>

<div class="ks-uif-viewHeader-container navbar-inverse navbar">
    <a href="../kr-krad/launch?methodToCall=start&viewId=ksFunctionalHomeView">
        <img class="ks-logo-image" title="Kuali Student" src="../themes/ksboot/images/header/logo_kuali.png">
        <span class="ks-header-student">Student</span>
    </a>

    <div class="header-right-group">
        <ul class="ks-header-list nav pull-right">
            <li class="ks-header-action-list"><a href="${ConfigProperties['ks.rice.actionList.serviceAddress']}">Action List</a>
            </li>

            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                    <i class="icon-user icon-white"></i>
                ${UserSession.loggedInUserPrincipalName!"You are not logged in."}
                    <b class="caret"></b>
                </a>
                <ul class="dropdown-menu">
                    <li>
                        <a href="#" onclick="logout();">Logout</a>
                    </li>
                </ul>
            </li>
        </ul>
    </div>
</div>
<div id="applicationHeader">
    <div id="applicationHeading">
        <div id="applicationLogo">Kuali Student Academic Planning</div>
        <div id="applicationNavigation">
            <ul>
                <#if RequestParameters.viewId??>
                <#--Plan Page Link-->
                    <#if RequestParameters.viewId=="Planner-FormView">
                        <li class="home"><a href="planner?methodToCall=start&amp;viewId=Planner-FormView" class="active">Plan</a>
                        </li>
                    <#else>
                        <li class="home"><a href="planner?methodToCall=start&amp;viewId=Planner-FormView">Plan</a>
                        </li>
                    </#if>

                <#--Find a Course Page Link-->
                    <#if RequestParameters.viewId=="CourseSearch-FormView">
                        <li><a href="course?methodToCall=start&viewId=CourseSearch-FormView" class="active">Find Courses</a></li>
                    <#else>
                        <li><a href="course?methodToCall=start&viewId=CourseSearch-FormView">Find Courses</a></li>
                    </#if>

                <#--DegreeAudit Page Link-->
                   <#-- <#if RequestParameters.viewId=="DegreeAudit-FormView">
                        <li><a href="audit?methodToCall=audit&viewId=DegreeAudit-FormView" class="active">Audit Degree</a></li>
                    <#else>
                        <li><a href="audit?methodToCall=audit&viewId=DegreeAudit-FormView">Audit Degree</a></li>
                    </#if>
                    <#else >
                        <li class="home"><a href="plan?methodToCall=start&viewId=PlannedCourses-FormView&currentPage=planPage">Plan</a></li>
                        <li><a href="course?methodToCall=start&viewId=CourseSearch-FormView&currentPage=coursePage">Find Courses</a></li>
                        <li><a href="audit?methodToCall=audit&viewId=DegreeAudit-FormView&currentPage=auditPage">Audit Degree</a></li>-->
                </#if>
            </ul>
        </div>

    </div>
</div>
</#macro>