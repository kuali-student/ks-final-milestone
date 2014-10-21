<%@ include file="/rice-portal/jsp/sys/riceTldHeader.jsp"%>

<channel:portalChannelTop channelTitle="Sandbox" />
<div class="body">
    <strong>Schedule of Classes (Alternate Views)</strong>
    <ul class="chan">
        <li><a class="portal_link" title="Schedule of Classes - Cluster View"
                               href="${ConfigProperties.lum.application.url}/kr-krad/scheduleOfClassesSearch?viewId=scheduleOfClassesSearchView&pageId=scheduleOfClassesSearchInputPage&methodToCall=start&scheduleOfClassesDisplayFormat=By Cluster">Schedule of Classes - Cluster View</a></li>
        <li><a class="portal_link" title="Schedule of Classes - Reg Group View"
                               href="${ConfigProperties.lum.application.url}/kr-krad/scheduleOfClassesSearch?viewId=scheduleOfClassesSearchView&pageId=scheduleOfClassesSearchInputPage&methodToCall=start&scheduleOfClassesDisplayFormat=By Registration Group">Schedule of Classes - Reg Group View</a></li>
    </ul>
    <strong>Registration (In Progress)</strong>
    <ul class="chan">
        <li><a class="portal_link" title="Manage Registration Windows And Appointments" href="${ConfigProperties.lum.application.url}/kr-krad/registrationWindows?viewId=registrationWindowsManagementView&pageId=selectTermForRegWindows&methodToCall=start">Manage Registration Windows And Appointments</a></li>
        <li><a class="portal_link" title="Registration Windows Lookup and Inquiry" href="${ConfigProperties.lum.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.student.enrollment.class2.appointment.dto.AppointmentWindowWrapper&returnLocation=${ConfigProperties.lum.application.url}/portal.do&hideReturnLink=true">Registration Windows Lookup and Inquiry</a></li>
    </ul>
    <strong>Holds (In Progress)</strong>
    <ul class="chan">
        <li><a class="portal_link" title="Create Hold Issue" href="${ConfigProperties.lum.application.url}/kr-krad/holdIssueMaintenance?viewName=HoldIssueMaintenanceView&dataObjectClassName=org.kuali.student.enrollment.class1.hold.dto.HoldIssueMaintenanceWrapper&methodToCall=start">Create Hold Issue</a></li>
        <li><a class="portal_link" title="Manage Hold Catalog" href="${ConfigProperties.lum.application.url}/kr-krad/holdIssueManagement?viewId=KS-HoldIssueManagementView&methodToCall=start">Manage Hold Catalog</a></li>
        <li><a class="portal_link" title="Manage Applied Holds" href="${ConfigProperties.lum.application.url}/kr-krad/appliedHoldManagement?viewId=KS-AppliedHoldManagementView&methodToCall=start">Manage Applied Holds</a></li>
    </ul>
    <strong>Process (In Progress)</strong>
    <ul class="chan">
        <li><a class="portal_link" title="Create Process" href="${ConfigProperties.lum.application.url}/kr-krad/processInfoController?viewId=processCreateView&methodToCall=start">Create Process</a></li>
        <li><a class="portal_link" title="Create Check" href="${ConfigProperties.lum.application.url}/kr-krad/createCheck?viewId=checkCreateView&methodToCall=start">Create Check</a></li>
        <li><a class="portal_link" title="Manage Process" href="${ConfigProperties.lum.application.url}/kr-krad/processInfoController?viewId=processInfoSearchView&methodToCall=start">Manage Process</a></li>
        <li><a class="portal_link" title="Manage Check" href="${ConfigProperties.lum.application.url}/kr-krad/createCheck?viewId=checkInfoSearchView&methodToCall=start">Manage Check</a></li>
        <li>Manage Instruction</li>
        <li><a class="portal_link" title="Process Info Lookup View" href="${ConfigProperties.lum.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.student.r2.core.process.dto.ProcessInfo&returnLocation=${ConfigProperties.lum.application.url}/portal.do&hideReturnLink=true">Process Info Lookup View</a></li>
    </ul>
    <strong>Populations (In Progress)</strong>
    <ul class="chan">
        <li><a class="portal_link" title="Manage Populations" href="${ConfigProperties.lum.application.url}/kr-krad/lookup?viewId=KS-Population-LookupView&methodToCall=start&dataObjectClassName=org.kuali.student.enrollment.class2.population.dto.PopulationWrapper&hideReturnLink=true&showMaintenanceLinks=true&viewName=Manage Populations">Manage Populations</a></li>
    </ul>
    <strong>Course Registration</strong>
    <ul class="chan">
        <li><a class="portal_link" title="POC Search" href="${ConfigProperties.lum.application.url}/kscr-poc/index.jsp">POC Search</a></li>
        <li><a class="portal_link" title="Reg Cart" href="${ConfigProperties.lum.application.url}/registration/">Reg Cart</a></li>
        <li><a class="portal_link" title="Performance Test" href="${ConfigProperties.lum.application.url}/kr-krad/courseRegistrationKrad?viewId=CourseRegistrationKrad-FormView&methodToCall=start&hideReturnLink=true">Performance Test</a></li>
        <li><a class="portal_link" title="Admin Registration" href="${ConfigProperties.lum.application.url}/kr-krad/adminreg?viewId=KS-AdminRegistration&methodToCall=start"">Admin Registration</a></li>
    </ul>
</div>
<channel:portalChannelBottom />