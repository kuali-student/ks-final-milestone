<%@ include file="/rice-portal/jsp/sys/riceTldHeader.jsp"%>

<channel:portalChannelTop channelTitle="Sandbox" />
<div class="body">
    <strong>Schedule of Classes (Alternate Views)</strong>
    <ul class="chan">
        <li><portal:portalLink displayTitle="true" title="Schedule of Classes - Cluster View"
                               url="${ConfigProperties.application.url}/kr-krad/scheduleOfClassesSearch?viewId=scheduleOfClassesSearchView&pageId=scheduleOfClassesSearchInputPage&methodToCall=start&scheduleOfClassesDisplayFormat=By Cluster" /></li>
        <li><portal:portalLink displayTitle="true" title="Schedule of Classes - Reg Group View"
                               url="${ConfigProperties.application.url}/kr-krad/scheduleOfClassesSearch?viewId=scheduleOfClassesSearchView&pageId=scheduleOfClassesSearchInputPage&methodToCall=start&scheduleOfClassesDisplayFormat=By Registration Group" /></li>
    </ul>
    <strong>Registration (In Progress)</strong>
    <ul class="chan">
        <li><portal:portalLink displayTitle="true" title="Manage Registration Windows And Appointments" url="${ConfigProperties.application.url}/kr-krad/registrationWindows?viewId=registrationWindowsManagementView&pageId=selectTermForRegWindows&methodToCall=start"/></li>
        <li><portal:portalLink displayTitle="true" title="Registration Windows Lookup and Inquiry " url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.student.enrollment.class2.appointment.dto.AppointmentWindowWrapper&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" /></li>
    </ul>
    <strong>Holds (In Progress)</strong>
    <ul class="chan">
        <li><portal:portalLink displayTitle="true" title="Create Hold" url="${ConfigProperties.application.url}/kr-krad/holdIssueInfoController?viewId=holdCreateView&methodToCall=start" /></li>
        <li><portal:portalLink displayTitle="true" title="Manage Hold Catalog" url="${ConfigProperties.application.url}/kr-krad/holdIssueInfoController?viewId=holdSearchView&methodToCall=start"/></li>
        <li><portal:portalLink displayTitle="true" title="Organization lookup (temp)" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.student.r2.core.organization.dto.OrgInfo&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true"/></li>
        <li><portal:portalLink displayTitle="true" title="Hold Issue Info Lookup View" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.student.r2.core.hold.dto.HoldIssueInfo&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" /></li>
    </ul>
    <strong>Process (In Progress)</strong>
    <ul class="chan">
        <li><portal:portalLink displayTitle="true" title="Create Process" url= "${ConfigProperties.application.url}/kr-krad/processInfoController?viewId=processCreateView&methodToCall=start" /></li>
        <li><portal:portalLink displayTitle="true" title="Create Check" url="${ConfigProperties.application.url}/kr-krad/createCheck?viewId=checkCreateView&methodToCall=start"/></li>
        <li><portal:portalLink displayTitle="true" title="Manage Process" url="${ConfigProperties.application.url}/kr-krad/processInfoController?viewId=processInfoSearchView&methodToCall=start"/></li>
        <li><portal:portalLink displayTitle="true" title="Manage Check" url="${ConfigProperties.application.url}/kr-krad/createCheck?viewId=checkInfoSearchView&methodToCall=start" /></li>
        <li><portal:portalLink displayTitle="true" title="Manage Instruction" url=" " /></li>
        <li><portal:portalLink displayTitle="true" title="Process Info Lookup View" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.student.r2.core.process.dto.ProcessInfo&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" /></li>
    </ul>
</div>
<channel:portalChannelBottom />