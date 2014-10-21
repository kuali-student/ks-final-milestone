<%--
 Copyright 2005-2012 The Kuali Foundation

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

<channel:portalChannelTop channelTitle="KRAD Components" />
<div class="body">
    <strong>Unified Page Header</strong>
    <ul class="chan">
        <li><portal:portalLink displayTitle="true" title="Context Bar"
                               url="${ConfigProperties.lum.application.url}/kr-krad/kitchensink?viewId=KS-KitchenSink-ContextBar-View-Base&methodToCall=start" /></li>
        <li><a href="${ConfigProperties.lum.application.url}/kr-krad/kitchensink?viewId=KS-KitchenSink-UnifiedPageHeader-View-Base-M7&methodToCall=start">Unified Page Header M7</a></li>
        <li><a href="${ConfigProperties.lum.application.url}/kr-krad/kitchensink?viewId=KS-KitchenSink-UnifiedPageHeader-View-Base&methodToCall=start">Unified Page Header Future</a></li>
    </ul>

    <strong>Data Fields</strong>
    <ul class="chan">
        <li></li>
    </ul>
    <strong>Input Fields</strong>
    <ul class="chan">
        <li><portal:portalLink displayTitle="true" title="Checkboxes"
                               url="${ConfigProperties.lum.application.url}/kr-krad/kitchensink?viewId=KS-KitchenSink-Checkbox-View&methodToCall=start" /></li>
        <li><portal:portalLink displayTitle="true" title="Date Text Field"
                               url="${ConfigProperties.lum.application.url}/kr-krad/kitchensink?viewId=KS-KitchenSink-DateField-View&methodToCall=start" /></li>
        <li><portal:portalLink displayTitle="true" title="Link Fields"
                               url="${ConfigProperties.lum.application.url}/kr-krad/kitchensink?viewId=KS-KitchenSink-Links-View&methodToCall=start" /></li>
        <li><portal:portalLink displayTitle="true" title="Multi-Select Control"
                               url="${ConfigProperties.lum.application.url}/kr-krad/kitchensink?viewId=KS-KitchenSink-MultiSelect-View&methodToCall=start" /></li>
        <li><portal:portalLink displayTitle="true" title="Radio Buttons"
                               url="${ConfigProperties.lum.application.url}/kr-krad/kitchensink?viewId=KS-KitchenSink-RadioButtons-View&methodToCall=start" /></li>
        <li><portal:portalLink displayTitle="true" title="Select Control (Dropdown)"
                               url="${ConfigProperties.lum.application.url}/kr-krad/kitchensink?viewId=KS-KitchenSink-Select-View&methodToCall=start" /></li>
        <li><portal:portalLink displayTitle="true" title="Spinner Control"
                               url="${ConfigProperties.lum.application.url}/kr-krad/kitchensink?viewId=KS-KitchenSink-Spinner-View&methodToCall=start" /></li>
        <li><portal:portalLink displayTitle="true" title="Text Area Field"
                               url="${ConfigProperties.lum.application.url}/kr-krad/kitchensink?viewId=KS-KitchenSink-TextAreaField-View&methodToCall=start" /></li>
        <li><portal:portalLink displayTitle="true" title="Text Field"
                               url="${ConfigProperties.lum.application.url}/kr-krad/kitchensink?viewId=KS-KitchenSink-TextField-View&methodToCall=start" /></li>
    </ul>

    <strong>Performance</strong>
    <ul class="chan">
        <li><portal:portalLink displayTitle="true" title="Performance"
                               url="${ConfigProperties.lum.application.url}/kr-krad/kitchensinkperformance?viewId=KS-KitchenSink-Performance-FormView&methodToCall=start" /></li>
    </ul>

    <strong>Validation</strong>
    <ul class="chan">
        <li></li>
    </ul>

    <strong>Validation - Regex</strong>
    <ul class="chan">
        <li></li>
    </ul>
</div>
<channel:portalChannelBottom />
