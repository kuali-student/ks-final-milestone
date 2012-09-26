<%@ include file="/rice-portal/jsp/sys/riceTldHeader.jsp"%>

<channel:portalChannelTop channelTitle="East Coast Sandbox" />
<div class="body">
    <strong>Course Offering</strong>
    <ul class="chan">
        <li><portal:portalLink displayTitle="true" title="Diagnose Rollover" url="${ConfigProperties.application.url}/kr-krad/diagnoseRollover?viewId=diagnoseRolloverView&pageId=selectCOToDiagnose&methodToCall=start"/></li>
    </ul>
    <strong>General Testing</strong>
    <ul class="chan">
        <li><portal:portalLink displayTitle="true" title="Test Service Call" url="${ConfigProperties.application.url}/kr-krad/testServiceCall?viewId=testServiceCallView&pageId=firstServiceCall&methodToCall=start"/></li>
    </ul>
</div>
<channel:portalChannelBottom />