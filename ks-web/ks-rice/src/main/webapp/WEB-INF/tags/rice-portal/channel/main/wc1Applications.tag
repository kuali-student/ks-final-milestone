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

<channel:portalChannelTop channelTitle="Time Setup" />
<div class="body">
    <strong>Calendar Search</strong>
    <ul class="chan">
        <li><portal:portalLink displayTitle="true" title="Calendar Search" url="${ConfigProperties.lum.application.url}/kr-krad/calendarSearch?viewId=calendarSearchView&methodToCall=start" /></li>
    </ul>
    <strong>Holiday Calendar</strong>
    <ul class="chan">
        <li><portal:portalLink displayTitle="true" title="Create Holiday Calendar" url="${ConfigProperties.lum.application.url}/kr-krad/holidayCalendar?viewId=holidayCalendarFlowView&pageId=holidayCalendarEditPage&methodToCall=start" /></li>
        <li><portal:portalLink displayTitle="true" title="Holiday Calendar Lookup and Inquiry " url="${ConfigProperties.lum.application.url}/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.student.enrollment.class2.acal.dto.HolidayCalendarWrapper&returnLocation=${ConfigProperties.lum.application.url}/portal.do&hideReturnLink=true" /></li>
        <li><portal:portalLink displayTitle="true" title="Holiday Calendar Search (dev link)" url="${ConfigProperties.lum.application.url}/kr-krad/calendarSearch?viewId=calendarSearchView&methodToCall=start&calendarSearchType=HolidayCalendar" /></li>
    </ul>
    <strong>Academic Calendar</strong>
    <ul class="chan">
        <li><portal:portalLink displayTitle="true" title="Create Academic Calendar" url="${ConfigProperties.lum.application.url}/kr-krad/academicCalendar?viewId=academicCalendarFlowView&pageId=academicCalendarEditPage&methodToCall=start" /></li>
        <li><portal:portalLink displayTitle="true" title="Copy Academic Calendar" url="${ConfigProperties.lum.application.url}/kr-krad/academicCalendar?viewId=academicCalendarFlowView&pageId=academicCalendarCopyPage&methodToCall=startNew" /></li>
    </ul>
</div>
<channel:portalChannelBottom />
