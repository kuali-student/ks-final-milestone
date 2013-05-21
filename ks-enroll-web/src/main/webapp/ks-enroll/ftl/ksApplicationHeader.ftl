<#macro ks_applicationHeader element>

<script type="text/javascript">
    function logout() {
        var url = "${ConfigProperties['rice.server.url']}/backdoorlogin.do?methodToCall=logout";
        redirect(url);
    }
</script>

<div class="ks-uif-viewHeader-container">
    <img class="ks-logo-image" title="Kuali Student" src="../ks-enroll/images/header/logo_kuali.png">
    <span class="ks-header-student">Student</span>

    <div class="header-right-group">
        <ul class="ks-header-list">
            <li class="ks-header-action-list"><a href="${ConfigProperties['kew.url']}/ActionList.do">Action List</a>
            </li>

            <li class="ks-header-admin">
                <img src="../ks-enroll/images/header/user.png" class="ks-header-user">
                <span>${UserSession.loggedInUserPrincipalName!"You are not logged in."}</span>
            </li>
            <li class="ks-header-logout">
                <#--<span><a href="#" onclick="logout();">Logout</a></span>-->
                <span><button type="button" onclick="logout();" class="btn">Logout</button></span>
            </li>
        </ul>
    </div>
</div>

</#macro>