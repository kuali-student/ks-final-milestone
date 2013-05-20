<#macro ks_applicationHeader element>

<script type="text/javascript">
    function logout() {
        console.log("Called");
        var formData = {
            "methodToCall":"logout"
        };

        jQuery.ajax({
            url:"${ConfigProperties['rice.server.url']}/backdoorlogin.do",
            type:"POST",
            data:formData
        });
    }
</script>

<div class="ks-uif-viewHeader-container">

    <img class="ks-logo-image" title="Kuali Student" src="../ks-enroll/images/logo_kuali.png">

    <div class="header-right-group">
        <ul class="ks-header-list">
            <li><a href="${ConfigProperties['kew.url']}/ActionList.do">Action List</a></li>

            <li>
                <span>${UserSession.loggedInUserPrincipalName!"You are not logged in."}</span>
            </li>
        <#--<li>-->
        <#--<span><a href="logout();">Logout</a></span>-->
        <#--</li>-->
            <li>
                <form action="${ConfigProperties['rice.server.url']}/backdoorlogin.do" method="post"
                      style="margin:0; display:inline">
                    <input name="imageField" type="submit" value="Logout" class="go" title="Click to logout.">
                    <input name="methodToCall" type="hidden" value="logout"/>
                </form>
            </li>
        </ul>
    </div>
</div>

</#macro>