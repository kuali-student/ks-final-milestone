<%@ include file="/rice-portal/jsp/sys/riceTldHeader.jsp"%>

<channel:portalChannelTop channelTitle="Holds (NWU)" />
<div class="body">
    <strong>Holds</strong>
    <ul class="chan">
        <li><portal:portalLink displayTitle="true" title="Create Hold" url="${ConfigProperties.application.url}/kr-krad/createHold?viewId=holdCreateView&methodToCall=start" /></li>
        <li><portal:portalLink displayTitle="true" title="Manage hold catalog" url="${ConfigProperties.application.url}/kr-krad/holdIssueInfoSearch?viewId=holdSearchView&methodToCall=start"/></li>
        <li><portal:portalLink displayTitle="true" title="Org lookup (temp)" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.student.r2.core.organization.dto.OrgInfo&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true"/></li>
    </ul>
    <strong>Processes</strong>
    <ul class="chan">
        <li><portal:portalLink displayTitle="true" title="Create Process" url= "${ConfigProperties.application.url}/kr-krad/createProcess?viewId=processCreateView&methodToCall=start" /></li>
        <li><portal:portalLink displayTitle="true" title="Create Check" url="${ConfigProperties.application.url}/kr-krad/createCheck?viewId=checkCreateView&methodToCall=start"/></li>
        <li><portal:portalLink displayTitle="true" title="Manage Process" url="${ConfigProperties.application.url}/kr-krad/processInfoSearch?viewId=processInfoSearchView&methodToCall=start"/></li>
        <li><portal:portalLink displayTitle="true" title="Manage Check" url="" /></li>
        <li><portal:portalLink displayTitle="true" title="Manage Instruction" url=" " /></li>
    </ul>
</div>
<channel:portalChannelBottom />
