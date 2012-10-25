<%@ include file="/rice-portal/jsp/sys/riceTldHeader.jsp"%>

<channel:portalChannelTop channelTitle="Holds (NWU)" />
<div class="body">
    <strong>Holds</strong>
    <ul class="chan">
        <li><portal:portalLink displayTitle="true" title="Create Hold" url="${ConfigProperties.application.url}/kr-krad/holdIssueInfoController?viewId=holdCreateView&methodToCall=start" /></li>
        <li><portal:portalLink displayTitle="true" title="Manage Hold Catalog" url="${ConfigProperties.application.url}/kr-krad/holdIssueInfoController?viewId=holdSearchView&methodToCall=start"/></li>
        <li><portal:portalLink displayTitle="true" title="Org lookup (temp)" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.student.r2.core.organization.dto.OrgInfo&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true"/></li>
    </ul>
    <strong>Processes</strong>
    <ul class="chan">
        <li><portal:portalLink displayTitle="true" title="Create Process" url= "${ConfigProperties.application.url}/kr-krad/processInfoController?viewId=processCreateView&methodToCall=start" /></li>
        <li><portal:portalLink displayTitle="true" title="Create Check" url="${ConfigProperties.application.url}/kr-krad/createCheck?viewId=checkCreateView&methodToCall=start"/></li>
        <li><portal:portalLink displayTitle="true" title="Manage Process" url="${ConfigProperties.application.url}/kr-krad/processInfoController?viewId=processInfoSearchView&methodToCall=start"/></li>
        <li><portal:portalLink displayTitle="true" title="Manage Check" url="${ConfigProperties.application.url}/kr-krad/createCheck?viewId=checkInfoSearchView&methodToCall=start" /></li>
        <li><portal:portalLink displayTitle="true" title="Manage Instruction" url=" " /></li>
    </ul>
    <strong>Dev Links</strong>
        <ul class="chan">
            <li><portal:portalLink displayTitle="true" title="Process Info Lookup View" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.student.r2.core.process.dto.ProcessInfo&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" /></li>
            <li><portal:portalLink displayTitle="true" title="Hold Issue Info Lookup View" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.student.r2.core.hold.dto.HoldIssueInfo&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" /></li>
        </ul>
</div>
<channel:portalChannelBottom />
