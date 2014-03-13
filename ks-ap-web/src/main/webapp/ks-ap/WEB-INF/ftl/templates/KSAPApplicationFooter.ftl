<#macro ksap_app_footer group>
<div id="applicationFooter">
    <div id="applicationFooterSection">
        <div class="version smaller" title="${Request.request.serverName}">Version: ${ConfigProperties['ksap.version']}</div>
    </div>
</div>
<#--Conditional include on less live processing for rice dev mode-->
<#if ConfigProperties['rice.krad.dev.mode'] == "true">
    <#include "KSAPLessDevScripts.ftl"/>
</#if>
</#macro>