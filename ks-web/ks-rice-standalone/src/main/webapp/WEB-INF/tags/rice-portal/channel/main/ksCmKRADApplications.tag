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

<channel:portalChannelTop channelTitle="KS Curriculum Management - KRAD" />
<div class="body">

    <ul class="chan">
        <li><a class="portal_link" title="Home" href="${ConfigProperties.lum.application.url}/kr-krad/launch?methodToCall=start&viewId=ksFunctionalHomeView">Home</a></li>
        <li><a class="portal_link" title="Curriculum Management" href="${ConfigProperties.ks.lum.MainEntryPoint}">Curriculum Management</a></li>
        <li><a class="portal_link" title="Curriculum Management KRAD" href="${ConfigProperties.lum.application.url}/kr-krad/cmHome?viewId=curriculumHomeView&methodToCall=start">Curriculum Management KRAD</a></li>
        <li><a class="portal_link" title="Enrollment" href="${ConfigProperties.lum.application.url}/kr-krad/launch?viewId=enrollmentHomeView&methodToCall=start">Enrollment</a></li>
    </ul>
  
</div>
<channel:portalChannelBottom />
