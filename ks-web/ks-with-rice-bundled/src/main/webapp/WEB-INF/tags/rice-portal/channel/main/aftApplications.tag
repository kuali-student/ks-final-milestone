<%@ include file="/rice-portal/jsp/sys/riceTldHeader.jsp"%>

<channel:portalChannelTop channelTitle="Developer Utilities" />
<div class="body">
    <ul class="chan">
        <li><a class="portal_link" title="Delete Target Term" href="${ConfigProperties.lum.application.url}/kr-krad/deleteTargetTerm?viewId=deleteTargetTermView&pageId=selectTargetTermToDelete&methodToCall=start">Delete Target Term</a></li>
        <li><a class="portal_link" title="Create Soc for Term" href="${ConfigProperties.lum.application.url}/kr-krad/createSoc?viewId=createSocView&pageId=selectTermForSocCreation&methodToCall=start">Create Soc for Term</a></li>
        <li><a class="portal_link" title="Diagnose Rollover" href="${ConfigProperties.lum.application.url}/kr-krad/diagnoseRollover?viewId=diagnoseRolloverView&pageId=selectCOToDiagnose&methodToCall=start">Diagnose Rollover</a></li>
        <li><a class="portal_link" title="Test Service Call" href="${ConfigProperties.lum.application.url}/kr-krad/testServiceCall?viewId=testServiceCallView&pageId=firstServiceCall&methodToCall=start">Test Service Call</a></li>
        <li><a class="portal_link" title="Test State Propagation" href="${ConfigProperties.lum.application.url}/kr-krad/testStatePropagation?viewId=testStatePropagationView&pageId=testStatePropagationPageId&methodToCall=start">Test State Propagation</a></li>
        <li><a class="portal_link" title="Test SOC State Change" href="${ConfigProperties.lum.application.url}/kr-krad/testStatePropagation?viewId=testStatePropagationView&pageId=aft-changeSocStatePage&methodToCall=start">Test SOC State Change</a></li>
    </ul>
</div>
<channel:portalChannelBottom />
