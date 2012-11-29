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
        <li><portal:portalLink displayTitle="true" title="Create New Proposition" url="${ConfigProperties.application.url}/kr-krad/krmsProposition?methodToCall=start&dataObjectClassName=org.kuali.rice.krms.impl.repository.PropositionBo&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true"/></li>
        <li><portal:portalLink displayTitle="true" title="Proposition Lookup" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.rice.krms.impl.repository.PropositionBo&showMaintenanceLinks=true&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true"/></li>
        <li><portal:portalLink displayTitle="true" title="Proposition Edit" url="${ConfigProperties.application.url}/kr-krad/krmsProposition?viewTypeName=MAINTENANCE&methodToCall=maintenanceEdit&dataObjectClassName=org.kuali.rice.krms.impl.repository.PropositionBo&id=10186"/></li>
        <li><portal:portalLink displayTitle="true" title="Rule Lookup" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.rice.krms.impl.repository.RuleBo&viewName=StudentRuleLookupView&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true"/></li>
    </ul>
</div>
<channel:portalChannelBottom />
