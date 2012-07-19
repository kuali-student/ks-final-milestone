<%@ include file="/rice-portal/jsp/sys/riceTldHeader.jsp"%>

<channel:portalChannelTop channelTitle="Holds (NWU)" />
<div class="body">
    <ul class="chan">
        <li><portal:portalLink displayTitle="true" title="Create Hold" url="${ConfigProperties.application.url}/kr-krad/holdLookup?viewId=holdLookupView&methodToCall=start"/></li>
        <li><portal:portalLink displayTitle="true" title="Manage hold catalog" url=""/></li>
        <li><portal:portalLink displayTitle="true" title="Org lookup (temp)" url="${ConfigProperties.application.url}/kr-krad/maintenance?methodToCall=maintenanceEdit&dataObjectClassName=org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper&coInfo.id=34a0303d-af3a-467a-83b1-8a1cdd391cc1&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true"/></li>
    </ul>
</div>
<channel:portalChannelBottom />
