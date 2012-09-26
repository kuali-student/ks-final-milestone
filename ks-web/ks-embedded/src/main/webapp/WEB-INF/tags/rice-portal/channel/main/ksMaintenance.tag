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

<channel:portalChannelTop channelTitle="Kuali Student Maintenance" />
<div class="body">
  <strong>Academic Time Period</strong>
  <ul class="chan">
  	 <li><portal:portalLink displayTitle="true" title="ATP" url="${ConfigProperties.kr.url}/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.student.core.atp.bo.Atp&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&docFormKey=88888888" /></li>
     <li><portal:portalLink displayTitle="true" title="Date Range" url="${ConfigProperties.kr.url}/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.student.core.atp.bo.DateRange&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&docFormKey=88888888" /></li>
     <li><portal:portalLink displayTitle="true" title="Milestone" url="${ConfigProperties.kr.url}/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.student.core.atp.bo.Milestone&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&docFormKey=88888888" /></li>
  </ul>
  <strong>Utility</strong>
  <ul class="chan">
  	<li><portal:portalLink displayTitle="true" title="Enumeration" url="${ConfigProperties.kr.url}/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.student.core.enumerationmanagement.bo.Enumeration&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&docFormKey=88888888" /></li>
    <li><portal:portalLink displayTitle="true" title="Enumerated Value" url="${ConfigProperties.kr.url}/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.student.core.enumerationmanagement.bo.EnumeratedValue&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&docFormKey=88888888" /></li>
    <li><portal:portalLink displayTitle="true" title="Message" url="${ConfigProperties.kr.url}/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.student.core.messages.bo.MessageEntity&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&docFormKey=88888888" /></li>
  </ul>
  <strong>Reference</strong>
  <ul class="chan">
  	 <li><portal:portalLink displayTitle="true" title="ATP Type" url="${ConfigProperties.kr.url}/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.student.core.atp.bo.AtpType&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&docFormKey=88888888" /></li>
     <li><portal:portalLink displayTitle="true" title="ATP Duration Type" url="${ConfigProperties.kr.url}/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.student.core.atp.bo.AtpDurationType&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&docFormKey=88888888" /></li>
  	 <li><portal:portalLink displayTitle="true" title="ATP Seasonal Type" url="${ConfigProperties.kr.url}/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.student.core.atp.bo.AtpSeasonalType&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&docFormKey=88888888" /></li>
     <li><portal:portalLink displayTitle="true" title="Date Range Type" url="${ConfigProperties.kr.url}/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.student.core.atp.bo.DateRangeType&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&docFormKey=88888888" /></li>
     <li><portal:portalLink displayTitle="true" title="Milestone Type" url="${ConfigProperties.kr.url}/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.student.core.atp.bo.MilestoneType&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true&docFormKey=88888888" /></li> 
  </ul>

    <strong>R2 Enrollment Administrative Views</strong>
    <strong>Type and State</strong>
    <ul class="chan">
         <li><portal:portalLink displayTitle="true" title="Type Verification" url="${ConfigProperties.application.url}/kr-krad/typeVerification?viewId=typeVerificationView&pageId=validateTypeEntryPage&methodToCall=start" /> </li>
        <li><portal:portalLink displayTitle="true" title="Type Lookup" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.student.r2.core.class1.type.dto.TypeInfo&viewId=KS-TypeInfo-AdminLookupView&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" /></li>
        <li><portal:portalLink displayTitle="true" title="Type-Type Relation Lookup" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.student.r2.core.class1.type.dto.TypeTypeRelationInfo&viewId=KS-TypeTypeRelationInfo-AdminLookupView&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" /></li>
        <li><portal:portalLink displayTitle="true" title="State Lookup" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.student.r2.core.class1.state.dto.StateInfo&viewId=KS-StateInfo-AdminLookupView&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" /></li>
        <li><portal:portalLink displayTitle="true" title="Lifecycle Lookup" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.student.r2.core.class1.state.dto.LifecycleInfo&viewId=KS-LifecycleInfo-AdminLookupView&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" /></li>
    </ul>
    <strong>Academic Time Period (ATP)</strong>
    <ul class="chan">
        <li><portal:portalLink displayTitle="true" title="ATP Lookup" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.student.r2.core.atp.dto.AtpInfo&viewId=KS-AtpInfo-AdminLookupView&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" /></li>
        <li><portal:portalLink displayTitle="true" title="ATP ATP Relationship Lookup" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.student.r2.core.atp.dto.AtpAtpRelationInfo&viewId=KS-AtpAtpRelationInfo-AdminLookupView&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" /></li>
        <li><portal:portalLink displayTitle="true" title="Milestone Lookup" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.student.r2.core.atp.dto.MilestoneInfo&viewId=KS-MilestoneInfo-AdminLookupView&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" /></li>
    </ul>

    <strong>Learning Result Catalog (LRC)</strong>
    <ul class="chan">
        <li><portal:portalLink displayTitle="true" title="Result Values Group Lookup" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo&viewId=KS-ResultValuesGroupInfo-AdminLookupView&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" /></li>
        <li><portal:portalLink displayTitle="true" title="Result Scale Lookup" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.student.r2.lum.lrc.dto.ResultScaleInfo&viewId=KS-ResultScaleInfo-AdminLookupView&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" /></li>
        <li><portal:portalLink displayTitle="true" title="Result Values Lookup" url="${ConfigProperties.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.student.r2.lum.lrc.dto.ResultValueInfo&viewId=KS-ResultValueInfo-AdminLookupView&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" /></li>
    </ul>

</div>
<channel:portalChannelBottom />