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
<#--
    Tabbed Collection Layout Manager:

 -->

<#macro uif_tabbedcollection items manager container>

    <#if manager.styleClassesAsString?has_content>
        <#local styleClass="class=\"${manager.styleClassesAsString}\""/>
    </#if>

    <#if manager.style?has_content>
        <#local style="style=\"${manager.style}\""/>
    </#if>

    <div id="${manager.id}_tabs" ${style!} ${styleClass!}>
        <ul id="${manager.id}_tabList">
            <#list manager.stackedGroups as item>
                <li data-tabfor="${item.id}">
                    <a href="#${item.id}_tab">${item.headerText}</a>
                </li>
            </#list>
        </ul>

        <#list manager.stackedGroups as item>
            <div data-tabwrapperfor="${item.id}" data-type="TabWrapper" id="${item.id}_tab">
                <@krad.template component=item/>
            </div>
        </#list>
    </div>

    <@krad.script component=parent value="createTabs('${manager.id}', ${container.tabsWidget.templateOptionsJSString});"/>

</#macro>