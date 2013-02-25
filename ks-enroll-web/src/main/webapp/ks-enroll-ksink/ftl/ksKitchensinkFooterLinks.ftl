<#--

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

-->
<#macro uif_ksKitchensinkFooterLinks widget>

    <div class="uif-ks-kitchensink-footerLinkSection">
        <#list widget.footerLinkGroups as footerLinkGroup>
            <div class="uif-ks-kitchensink-footerLinkGroup" <#if footerLinkGroup.style??>style="${footerLinkGroup.style}"</#if> >
                <#if footerLinkGroup.label??>
                    <span class="uif-ks-kitchensink-footerLinkGroup-label">${footerLinkGroup.label}:</span>
                </#if>
                <#if (footerLinkGroup.footerLinks?size > 1)>
                    <span class="uif-ks-kitchensink-footerLinkGroup-linkDelimiterStart">${widget.linkDelimiterStart}</span>
                </#if>
                <#list footerLinkGroup.footerLinks?keys as key>
                    <span class="uif-ks-kitchensink-footerLinkGroup-link">
                        <a href="${footerLinkGroup.footerLinks[key]}" target="_blank">${key}</a>
                    </span>
                    <#if key_has_next>
                        <span class="uif-ks-kitchensink-footerLinkGroup-linkDelimiterMiddle">${widget.linkDelimiterMiddle}</span>
                    </#if>
                </#list>
                <#if (footerLinkGroup.footerLinks?size > 1)>
                    <span class="uif-ks-kitchensink-footerLinkGroup-linkDelimiterEnd">${widget.linkDelimiterEnd}</span>
                </#if>
            </div>
        </#list>
    </div>

</#macro>
