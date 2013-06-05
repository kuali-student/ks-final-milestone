<#macro ks_ToolbarGroup group>
    <#local isNoLeftBorder=group.noLeftBorder/>

    <@krad.groupWrap group=group>
    <#include "${group.layoutManager.template}" parse=true/>
    <#local macroInvokeSrc="<" + "@${group.layoutManager.templateName} items=group.items"/>
    <#local macroInvokeSrc="${macroInvokeSrc} manager=group.layoutManager container=group/>"/>
    <#local macroInvoke = macroInvokeSrc?interpret>

    <@macroInvoke />

    <#if isNoLeftBorder>
        <@krad.script value="decorateToolbar('${group.id}');"/>
    </#if>
    </@krad.groupWrap>

</#macro>