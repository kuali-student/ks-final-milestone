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

<channel:portalChannelTop channelTitle="R2 Applications" />
<div class="body">  
  <strong>KS Enroll</strong>
  <ul class="chan">
    <li><portal:portalLink displayTitle="true" title="Academic Calendar Maintenance (New)" url="${ConfigProperties.application.url}/spring/maintenance?methodToCall=start&dataObjectClassName=org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true"/></li> 
    <li><portal:portalLink displayTitle="true" title="Term Maintenance (New)" url="${ConfigProperties.application.url}/spring/maintenance?methodToCall=start&dataObjectClassName=org.kuali.student.enrollment.acal.dto.TermInfo&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true"/></li> 
    <br>
    <li><portal:portalLink displayTitle="true" title="Manage Hold Maintenance (New)" url="${ConfigProperties.application.url}/spring/maintenance?methodToCall=start&dataObjectClassName=org.kuali.student.enrollment.hold.dto.HoldInfo&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true"/></li> 
    
	<li><portal:portalLink displayTitle="true" title="Academic Calendar" url="${ConfigProperties.application.url}/spring/acal?viewId=AcademicCalendarView&methodToCall=start" /></li>
    <li><portal:portalLink displayTitle="true" title="Academic Calendar Lookup" url="${ConfigProperties.application.url}/spring/lookup?methodToCall=start&dataObjectClassName=org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" /></li>
<!-- 
    <li><portal:portalLink displayTitle="true" title="Course Info Dto Lookup" url="${ConfigProperties.application.url}/spring/lookup?methodToCall=start&dataObjectClassName=org.kuali.student.enrollment.class2.acal.dto.CourseInfoDto&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" /></li>
-->    
  </ul>      
</div>
<channel:portalChannelBottom />
