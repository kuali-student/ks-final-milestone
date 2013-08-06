<%@ include file="/rice-portal/jsp/sys/riceTldHeader.jsp"%>

<channel:portalChannelTop channelTitle="East Coast Sandbox" />
<div class="body">
    <strong>Course Offering</strong>
    <ul class="chan">
        <!-- WARNING: the following 3 links are duplicated in "EnrollmentHomeView.xml" -->
        <li><portal:portalLink displayTitle="true" title="Diagnose Rollover" url="${ConfigProperties.application.url}/kr-krad/diagnoseRollover?viewId=diagnoseRolloverView&pageId=selectCOToDiagnose&methodToCall=start"/></li>
    </ul>
</div>
<channel:portalChannelBottom />