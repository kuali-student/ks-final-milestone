<%@ include file="/rice-portal/jsp/sys/riceTldHeader.jsp"%>

<channel:portalChannelTop channelTitle="Developer Utilities" />
<div class="body">
    <ul class="chan">
        <li><portal:portalLink displayTitle="true" title="Delete Target Term" url="${ConfigProperties.application.url}/kr-krad/deleteTargetTerm?viewId=deleteTargetTermView&pageId=selectTargetTermToDelete&methodToCall=start"/></li>
        <li><portal:portalLink displayTitle="true" title="Create Soc for Term" url="${ConfigProperties.application.url}/kr-krad/createSoc?viewId=createSocView&pageId=selectTermForSocCreation&methodToCall=start"/></li>
        <li><portal:portalLink displayTitle="true" title="Diagnose Rollover" url="${ConfigProperties.application.url}/kr-krad/diagnoseRollover?viewId=diagnoseRolloverView&pageId=selectCOToDiagnose&methodToCall=start"/></li>
        <li><portal:portalLink displayTitle="true" title="Test Service Call" url="${ConfigProperties.application.url}/kr-krad/testServiceCall?viewId=testServiceCallView&pageId=firstServiceCall&methodToCall=start"/></li>
        <li><portal:portalLink displayTitle="true" title="Test State Propagation" url="${ConfigProperties.application.url}/kr-krad/testStatePropagation?viewId=testStatePropagationView&pageId=testStatePropagationPageId&methodToCall=start"/></li>
        <li><portal:portalLink displayTitle="true" title="Test SOC State Change" url="${ConfigProperties.application.url}/kr-krad/testStatePropagation?viewId=testStatePropagationView&pageId=aft-changeSocStatePage&methodToCall=start"/></li>
    </ul>
</div>
<channel:portalChannelBottom />
