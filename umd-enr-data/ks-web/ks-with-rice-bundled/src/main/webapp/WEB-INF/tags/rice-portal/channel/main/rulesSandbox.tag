<%--
 Copyright 2007-2009 The Kuali Foundation
 
 Licensed under the Educational Community License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 
 http://www.opensource.org/licenses/ecl2.php
 
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
--%>
<%@ include file="/rice-portal/jsp/sys/riceTldHeader.jsp"%>

<channel:portalChannelTop channelTitle="KS KRMS Sandbox" />
<div class="body">
    <strong>Student Views</strong>
    <ul class="chan">
        <li><portal:portalLink displayTitle="true" title="Create New Agenda" url="${ConfigProperties.application.url}/kr-krad/krmsAgendaStudentEditor?methodToCall=start&dataObjectClassName=org.kuali.rice.krms.impl.ui.AgendaEditor&viewName=StudentLookupView&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true"/></li>
        <li><portal:portalLink displayTitle="true" title="Agenda Lookup" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.rice.krms.impl.repository.AgendaBo&showMaintenanceLinks=true&viewName=StudentLookupView&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" /></li>
        <li><portal:portalLink displayTitle="true" title="Create New Rule" url="${ConfigProperties.application.url}/kr-krad/krmsRuleStudentEditor?methodToCall=start&dataObjectClassName=org.kuali.student.enrollment.class1.krms.dto.RuleEditor&viewName=StudentView&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true"/></li>
        <li><portal:portalLink displayTitle="true" title="Rule Lookup" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.rice.krms.impl.repository.RuleBo&showMaintenanceLinks=true&viewName=StudentRuleLookupView&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true"/></li>
        <li><portal:portalLink displayTitle="true" title="Rule Edit" url="${ConfigProperties.application.url}/kr-krad/krmsRuleStudentEditor?viewTypeName=MAINTENANCE&methodToCall=maintenanceEdit&dataObjectClassName=org.kuali.student.enrollment.class1.krms.dto.RuleEditor&viewName=StudentView&id=10102"/></li>
        <li><portal:portalLink displayTitle="true" title="KRMS Components" url="${ConfigProperties.application.url}/kr-krad/krmsComponents?viewId=manageKrmsComponentsView&methodToCall=start"/></li>
        <li><portal:portalLink displayTitle="true" title="Manage Enrollment Eligibility" url="${ConfigProperties.application.url}/kr-krad/requisitesController?viewId=manageRequisitesView&methodToCall=start&cluId=REFERENCECOURSEMATH140"/></li>
        <li><portal:portalLink displayTitle="true" title="Department Lookup" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.student.enrollment.class1.krms.dto.DepartmentInfo&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true"/></li>
    </ul>
</div>
<channel:portalChannelBottom />
