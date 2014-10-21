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
<#macro uif_ruleTreeGroup group>

    <@krad.groupWrap group=group>

        <div id="${group.id}_tree">
            <ul>

                <#-- only create node if root not null -->
                <#if group.treeGroups.rootElement??>
                    <#include "ruleTreeNode.ftl" parse=true/>
                    <#list group.treeGroups.rootElement.children as node>
                        <@ruleTreeNode node=node />
                    </#list>
                </#if>
            </ul>
        </div>

        <#-- invoke tree widget only when tree not null. -->
        <#if group.tree??>
            <#include "rulePreviewTree.ftl" parse=true/>
            <@krad.template component=group.tree componentId="${group.id}_tree"/>
        </#if>

    </@krad.groupWrap>

</#macro>


