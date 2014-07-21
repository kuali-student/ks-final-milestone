<#macro ksap_app_header element>
<script type="text/javascript">
    function logout() {
        var url = "${ConfigProperties['rice.server.url']}/backdoorlogin.do?methodToCall=logout";
        redirect(url);
    }
</script>

<div class="ks-uif-viewHeader-container navbar-inverse navbar">
    <a href="../kr-krad/launch?methodToCall=start&viewId=ksFunctionalHomeView">
        <img class="ks-logo-image" title="Kuali Student" alt="Kuali Student logo" height="23" width="95" src="../themes/ksboot/images/header/logo_kuali.png">
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
                        <li id="courseSearchNav"><a href="course?methodToCall=start&viewId=CourseSearch-FormView&resetForm=true" class="active">Find Courses</a></li>
                    <#else>
                        <li id="courseSearchNav"><a href="course?methodToCall=start&viewId=CourseSearch-FormView&resetForm=true">Find Courses</a></li>
                    </#if>

                </#if>
            </ul>
        </div>
        <div class="ksapBookmarkCount">
            <a id="Ksap-Header-Bookmark-Count" class="uif-link" target="" href="lookup?methodToCall=search&viewId=BookmarkDetail-LookupView">
                <span id="Ksap-Header-Bookmark-Count-Label">Bookmarks</span>
                (<span id="Ksap-Header-Bookmark-Count-Value">0</span>)
            </a>
        </div>
    </div>
</div>
</#macro>