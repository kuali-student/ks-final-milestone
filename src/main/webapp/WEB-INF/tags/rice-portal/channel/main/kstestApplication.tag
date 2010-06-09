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

<channel:portalChannelTop channelTitle="Messages" />
<div class="body">
  
  <ul class="chan">
     <li><portal:portalLink displayTitle="true" title="Enumeration Table Lookup" url="${ConfigProperties.kr.url}/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.student.core.enumerationmanagement.bo.EnumerationTable&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&docFormKey=88888888" /></li>
     <li><portal:portalLink displayTitle="true" title="Enumeration Table Context View Lookup" url="${ConfigProperties.kr.url}/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.student.core.enumerationmanagement.bo.EnumerationTableContextView&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&docFormKey=88888888" /></li>
  	 <li><portal:portalLink displayTitle="true" title="Enumeration Context Lookup" url="${ConfigProperties.kr.url}/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.student.core.enumerationmanagement.bo.EnumerationContext&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&docFormKey=88888888" /></li>
  	 <li><portal:portalLink displayTitle="true" title="Enumeration Value Lookup" url="${ConfigProperties.kr.url}/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.student.core.enumerationmanagement.bo.EnumerationValue&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&docFormKey=88888888" /></li>
     <li><portal:portalLink displayTitle="true" title="Message Lookup" url="${ConfigProperties.kr.url}/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.student.core.messages.bo.MessageEntity&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&docFormKey=88888888" /></li>
  </ul>

  
</div>
<channel:portalChannelBottom />