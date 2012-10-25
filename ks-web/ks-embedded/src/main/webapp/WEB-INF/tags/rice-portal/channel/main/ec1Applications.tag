<%@ include file="/rice-portal/jsp/sys/riceTldHeader.jsp"%>

<channel:portalChannelTop channelTitle="East Coast Sandbox" />
<div class="body">
    <strong>Population</strong>
    <ul class="chan">
        <li><portal:portalLink displayTitle="true" title="Manage Population (Lookup)" url="${ConfigProperties.application.url}/kr-krad/lookup?viewId=KS-Population-LookupView&methodToCall=start&dataObjectClassName=org.kuali.student.enrollment.class2.population.dto.PopulationWrapper&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&showMaintenanceLinks=true&viewName=Manage Populations" /></li>
        <li><portal:portalLink displayTitle="true" title="Population Maintenance eDoc (New)" url="${ConfigProperties.application.url}/kr-krad/population?methodToCall=start&dataObjectClassName=org.kuali.student.enrollment.class2.population.dto.PopulationWrapper&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true"/></li>
    </ul>
    
    <strong>Registration</strong>
    <ul class="chan">
        <%--<li><portal:portalLink displayTitle="true" title="Manage General Environment" url=""/></li>--%>
        <li><portal:portalLink displayTitle="true" title="Manage Registration Windows And Appointments" url="${ConfigProperties.application.url}/kr-krad/registrationWindows?viewId=registrationWindowsManagementView&pageId=selectTermForRegWindows&methodToCall=start"/></li>
        <%--<li><portal:portalLink displayTitle="true" title="Manage Individual Student Appointments" url=""/></li>--%>
        <%--<li><portal:portalLink displayTitle="true" title="Registration Windows Edit Page" url="${ConfigProperties.application.url}/kr-krad/registrationWindows?viewId=registrationWindowsManagementView&pageId=registrationWindowsEditPage&methodToCall=start"/></li>--%>
        <li><portal:portalLink displayTitle="true" title="Registration Windows Lookup and Inquiry " url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.student.enrollment.class2.appointment.dto.AppointmentWindowWrapper&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" /></li>
    </ul>

    <strong>Course Offering</strong>
    <ul class="chan">
        <li><portal:portalLink displayTitle="true" title="Schedule of Classes" url="${ConfigProperties.application.url}/kr-krad/scheduleOfClassesSearch?viewId=scheduleOfClassesSearchView&pageId=scheduleOfClassesSearchInputPage&methodToCall=start"/></li>
        <li><portal:portalLink displayTitle="true" title="Diagnose Rollover" url="${ConfigProperties.application.url}/kr-krad/diagnoseRollover?viewId=diagnoseRolloverView&pageId=selectCOToDiagnose&methodToCall=start"/></li>
    </ul>
    <strong>General Testing</strong>
    <ul class="chan">
        <li><portal:portalLink displayTitle="true" title="Test Service Call" url="${ConfigProperties.application.url}/kr-krad/testServiceCall?viewId=testServiceCallView&pageId=firstServiceCall&methodToCall=start"/></li>
    </ul>

</div>

<channel:portalChannelBottom />
