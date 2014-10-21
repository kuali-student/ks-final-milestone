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
    <strong>Authorization</strong>
    <ul class="chan">
        <li></li>
    </ul>

    <strong>Trees</strong>
    <ul class="chan">
        <li><portal:portalLink displayTitle="true" title="Trees"
                               url="${ConfigProperties.lum.application.url}/kr-krad/kitchensink?viewId=KS-KitchenSink-Tree-View&methodToCall=trees" /></li>
    </ul>

    <strong>Other Examples</strong>
    <ul class="chan">
        <li><portal:portalLink displayTitle="true" title="Growls"
                               url="${ConfigProperties.lum.application.url}/kr-krad/kitchensink?viewId=KS-KitchenSink-Growls-View&methodToCall=start" /></li>
        <li><portal:portalLink displayTitle="true" title="Lightboxes (in progress)"
                               url="${ConfigProperties.lum.application.url}/kr-krad/kitchensink?viewId=KS-KitchenSink-Lightbox-View&methodToCall=start" /></li>
        <li><portal:portalLink displayTitle="true" title="Popovers"
                               url="${ConfigProperties.lum.application.url}/kr-krad/kitchensink?viewId=KS-KitchenSink-Popovers-View&methodToCall=start" /></li>
        <li><portal:portalLink displayTitle="true" title="Message Box"
                               url="${ConfigProperties.lum.application.url}/kr-krad/kitchensink?viewId=KS-KitchenSink-MessageBox-View&methodToCall=start" /></li>
    </ul>

    <strong>Event Examples</strong>
    <ul class="chan">
        <li></li>
    </ul>
</div>
<channel:portalChannelBottom />
