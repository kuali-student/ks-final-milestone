<%@ include file="/rice-portal/jsp/sys/riceTldHeader.jsp"%>

<channel:portalChannelTop channelTitle="Course Offering Applications" />
<div class="body">
    <strong>Course Offering</strong>
    <ul class="chan">
        <li><portal:portalLink displayTitle="true" title="Manage Course Offering Rollover" url="${ConfigProperties.application.url}/kr-krad/courseOfferingRollover?viewId=courseOfferingRolloverManagementView&pageId=selectTermsForRollover&methodToCall=start"/></li>

    </ul>

</div>
<channel:portalChannelBottom />
