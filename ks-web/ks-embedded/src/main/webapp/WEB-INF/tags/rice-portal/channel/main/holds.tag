<%@ include file="/rice-portal/jsp/sys/riceTldHeader.jsp"%>

<channel:portalChannelTop channelTitle="Holds (NWU)" />
<div class="body">
    <ul class="chan">
        <li><portal:portalLink displayTitle="true" title="Create Hold" url="${ConfigProperties.application.url}/kr-krad/holdLookup?viewId=holdLookupView&methodToCall=start"/></li>
        <li><portal:portalLink displayTitle="true" title="Manage hold catalog" url="${ConfigProperties.application.url}/kr-krad/holdSearch?viewId=holdSearchView&methodToCall=start"/></li>
        <li><portal:portalLink displayTitle="true" title="Org lookup (temp)" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.student.r2.core.organization.dto.OrgInfo&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true"/></li>
    </ul>
</div>
<channel:portalChannelBottom />
