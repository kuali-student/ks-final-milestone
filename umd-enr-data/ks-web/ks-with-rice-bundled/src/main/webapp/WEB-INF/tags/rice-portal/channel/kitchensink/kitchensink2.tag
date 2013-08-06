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

<channel:portalChannelTop channelTitle="KRAD Components (continued)" />
<div class="body">
    <strong>Progressive Disclosure</strong>
    <ul class="chan">
        <li></li>
    </ul>

    <strong>Lookups, etc</strong>
    <ul class="chan">
        <%--
        <li><portal:portalLink displayTitle="true" title="Combo Boxes"
                               url="${ConfigProperties.application.url}/kr-krad/kitchensink?viewId=KS-KitchenSink-ComboBoxes-View&methodToCall=start" /></li>
        --%>
        <li><portal:portalLink displayTitle="true" title="Lookup with default action"
                               url="${ConfigProperties.application.url}/kr-krad/lookup?viewId=KS-KitchenSink-Population-LookupView-Uim&methodToCall=start&dataObjectClassName=org.kuali.student.r2.core.population.dto.PopulationInfo&returnLocation=${ConfigProperties.application.url}/portal.do&hideReturnLink=true" /></li>
        <li><portal:portalLink displayTitle="true" title="Lookup Overview"
                               url="${ConfigProperties.application.url}/kr-krad/kitchensink?viewId=KS-KitchenSink-LookupOverview-View&methodToCall=start" /></li>
        <li><portal:portalLink displayTitle="true" title="Suggest (Auto-Complete)"
                               url="${ConfigProperties.application.url}/kr-krad/kitchensink?viewId=KS-KitchenSink-LookupSuggest-View&methodToCall=start" /></li>
    </ul>

    <strong>Collections</strong>
    <ul class="chan">
        <li><portal:portalLink displayTitle="true" title="Collection action image buttons"
                               url="${ConfigProperties.application.url}/kr-krad/kitchensink?viewId=KS-KitchenSink-CollectionActionImages-View&methodToCall=collection" /></li>
        <li><portal:portalLink displayTitle="true" title="Collection action links on hover"
                               url="${ConfigProperties.application.url}/kr-krad/kitchensink?viewId=KS-KitchenSink-CollectionActionLinksOnHover-View&methodToCall=collection" /></li>
        <li><portal:portalLink displayTitle="true" title="Collection as form"
                               url="${ConfigProperties.application.url}/kr-krad/kitchensink?viewId=KS-KitchenSink-CollectionAsForm-View&methodToCall=collection" /></li>
        <li><portal:portalLink displayTitle="true" title="Collection column sorting"
                               url="${ConfigProperties.application.url}/kr-krad/kitchensink?viewId=KS-KitchenSink-CollectionColumnSorting-View&methodToCall=collection" /></li>
        <li><portal:portalLink displayTitle="true" title="Collection in form"
                               url="${ConfigProperties.application.url}/kr-krad/kitchensink?viewId=KS-KitchenSink-CollectionInForm-View&methodToCall=collection" /></li>
        <li><portal:portalLink displayTitle="true" title="Collection row selection and toggle"
                               url="${ConfigProperties.application.url}/kr-krad/kitchensink?viewId=KS-KitchenSink-CollectionRowSelection-View&methodToCall=collection" /></li>
        <li><portal:portalLink displayTitle="true" title="Collection row selection highlighting"
                               url="${ConfigProperties.application.url}/kr-krad/kitchensink?viewId=KS-KitchenSink-CollectionRowSelectionHighlight-View&methodToCall=collection" /></li>
        <li><portal:portalLink displayTitle="true" title="Collection table options"
                               url="${ConfigProperties.application.url}/kr-krad/kitchensink?viewId=KS-KitchenSink-CollectionOptions-View&methodToCall=collection" /></li>
        <li><portal:portalLink displayTitle="true" title="Collection Group Table Layout"
                               url="${ConfigProperties.application.url}/kr-krad/kitchensink?viewId=KS-KitchenSink-CollectionsTL-View&methodToCall=start" /></li>
        <%-- replaced by CollectionStacked.xml
        <li><portal:portalLink displayTitle="true" title="Collection Group Stacked Layout"
                               url="${ConfigProperties.application.url}/kr-krad/kitchensink?viewId=KS-KitchenSink-CollectionSL-View&methodToCall=start" /></li>
        --%>
        <li><portal:portalLink displayTitle="true" title="Collection with add blank line"
                               url="${ConfigProperties.application.url}/kr-krad/kitchensink?viewId=KS-KitchenSink-CollectionAddBlankLine-View&methodToCall=collectionTerm" /></li>
        <li><portal:portalLink displayTitle="true" title="Nested Table"
                               url="${ConfigProperties.application.url}/kr-krad/kitchensink?viewId=KS-KitchenSink-NestedTable-View&methodToCall=start" /></li>
        <li><portal:portalLink displayTitle="true" title="Stacked collection"
                               url="${ConfigProperties.application.url}/kr-krad/kitchensink?viewId=KS-KitchenSink-CollectionStacked-View&methodToCall=collectionOne" /></li>
    </ul>

    <strong>Button / Header Levels</strong>
    <ul class="chan">
        <li><portal:portalLink displayTitle="true" title="Buttons"
                               url="${ConfigProperties.application.url}/kr-krad/kitchensink?viewId=KS-KitchenSink-Buttons-View&methodToCall=start" /></li>
    </ul>
</div>
<channel:portalChannelBottom />
