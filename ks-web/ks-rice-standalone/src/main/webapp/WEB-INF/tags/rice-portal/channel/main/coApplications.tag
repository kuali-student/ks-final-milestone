<%@ include file="/rice-portal/jsp/sys/riceTldHeader.jsp"%>

<channel:portalChannelTop channelTitle="Sandbox" />
<div class="body">
    <strong>Schedule of Classes (Alternate Views)</strong>
    <ul class="chan">
        <li><a href="${ConfigProperties.lum.application.url}/kr-krad/scheduleOfClassesSearch?viewId=scheduleOfClassesSearchView&pageId=scheduleOfClassesSearchInputPage&methodToCall=start&scheduleOfClassesDisplayFormat=By Cluster" />Schedule of Classes - Cluster View</a></li>
        <li><a href="${ConfigProperties.lum.application.url}/kr-krad/scheduleOfClassesSearch?viewId=scheduleOfClassesSearchView&pageId=scheduleOfClassesSearchInputPage&methodToCall=start&scheduleOfClassesDisplayFormat=By Registration Group" />Schedule of Classes - Reg Group View</a></li>
    </ul>
    <strong>Registration (In Progress)</strong>
    <ul class="chan">
        <li><a href="${ConfigProperties.lum.application.url}/kr-krad/registrationWindows?viewId=registrationWindowsManagementView&pageId=selectTermForRegWindows&methodToCall=start"/>Manage Registration Windows And Appointments</a></li>
        <li><a href="${ConfigProperties.lum.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.student.enrollment.class2.appointment.dto.AppointmentWindowWrapper&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" />Registration Windows Lookup and Inquiry</a></li>
    </ul>
    <strong>Holds (In Progress)</strong>
    <ul class="chan">
        <li><a href="${ConfigProperties.lum.application.url}/kr-krad/holdIssueInfoController?viewId=holdCreateView&methodToCall=start" />Create Hold</a></li>
        <li><a href="${ConfigProperties.lum.application.url}/kr-krad/holdIssueInfoController?viewId=holdSearchView&methodToCall=start"/>Manage Hold Catalog</a></li>
        <li><a href="${ConfigProperties.lum.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.student.r2.core.organization.dto.OrgInfo&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true"/>Organization lookup (temp)</a></li>
        <li><a href="${ConfigProperties.lum.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.student.r2.core.hold.dto.HoldIssueInfo&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" />Hold Issue Info Lookup View</a></li>
    </ul>
    <strong>Process (In Progress)</strong>
    <ul class="chan">
        <li><a href="${ConfigProperties.lum.application.url}/kr-krad/processInfoController?viewId=processCreateView&methodToCall=start" />Create Process</a></li>
        <li><a href="${ConfigProperties.lum.application.url}/kr-krad/createCheck?viewId=checkCreateView&methodToCall=start"/>Create Check</a></li>
        <li><a href="${ConfigProperties.lum.application.url}/kr-krad/processInfoController?viewId=processInfoSearchView&methodToCall=start"/>Manage Process</a></li>
        <li><a href="${ConfigProperties.lum.application.url}/kr-krad/createCheck?viewId=checkInfoSearchView&methodToCall=start" />Manage Check</a></li>
        <li><a href=" " />Manage Instruction</a></li>
        <li><a href="${ConfigProperties.lum.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.student.r2.core.process.dto.ProcessInfo&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" />Process Info Lookup View</a></li>
    </ul>
</div>
<channel:portalChannelBottom />