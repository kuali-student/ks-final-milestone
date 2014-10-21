<#--

    Copyright 2005-2013 The Kuali Foundation

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
<#macro ks_SimpleDataField field>

    <#local readOnly=field.readOnly || !field.inputAllowed/>

    <@krad.div component=field>

            <#local renderLabel=field.label?has_content && !field.labelRendered/>

            <#-- render field label -->
            <#if renderLabel>
                <@krad.template component=field.fieldLabel/>
             </#if>

            <#-- TODO: verify removal -->
            <#--<#if field.renderFieldset>-->
            <#--<fieldset data-type="InputSet" aria-labelledby="${field.id}_label" id="${field.id}_fieldset">-->
            <#--<legend style="display: none">${field.label!}</legend>-->
            <#--</#if>-->

            <#local quickfinderInputOnly=(field.widgetInputOnly!false) && ((field.quickfinder.dataObjectClassName)!"")?has_content />

            <#-- render field value (if read-only/quickfinder-input-only) or control (if edit) -->
            <#if readOnly>

                <#local readOnlyDisplay>
                    <#-- if it is a textarea add a pre tag to preserve formatting-->
                    <#if field.multiLineReadOnlyDisplay>
                        <pre>
                            <#-- display replacement display value if set -->
                            <#if field.readOnlyDisplayReplacement?has_content>
                               ${field.readOnlyDisplayReplacement?replace(" ","&nbsp;")}
                            <#else>
                                <#-- display actual field value -->
                                <@spring.bindEscaped path="KualiForm.${field.bindingInfo.bindingPath}"
                                htmlEscape=field.escapeHtmlInPropertyValue/>
                                ${(spring.status.value?default(""))?replace(" ","&nbsp;")}

                                <#-- add display suffix value if set -->
                                <#if field.readOnlyDisplaySuffix?has_content>
                                   *-* ${field.readOnlyDisplaySuffix?replace(" ","&nbsp;")}

                                </#if>
                            </#if>
                        </pre>
                      <#else>
                        <#-- display replacement display value if set -->
                        <#if field.readOnlyDisplayReplacement?has_content>
                            ${field.readOnlyDisplayReplacement}
                        <#else>
                            <#-- display actual field value -->
                            <@spring.bindEscaped path="KualiForm.${field.bindingInfo.bindingPath}"
                            htmlEscape=field.escapeHtmlInPropertyValue/>
                            ${spring.status.value?default("")}

                            <#-- add display suffix value if set -->
                            <#if field.readOnlyDisplaySuffix?has_content>
                                *-* ${field.readOnlyDisplaySuffix}
                            </#if>
                        </#if>
                    </#if>
                </#local>

                <span id="${field.id}_control" class="uif-readOnlyContent">
                    <#-- render inquiry if enabled -->
                    <#if field.inquiry?? && field.inquiry?has_content && field.inquiry.render>
                        <@krad.template component=field.inquiry componentId="${field.id}" body="${readOnlyDisplay}"
                          readOnly=field.readOnly/>
                    <#else>
                        ${readOnlyDisplay}
                    </#if>
                </span>

            <#else>

                <#-- render field instructional text -->
                <#if field.instructionalMessage??>
                    <@krad.template component=field.instructionalMessage/>
                </#if>

                <#-- render control for input -->
                <@krad.template component=field.control field=field/>

            </#if>

            <#-- render field quickfinder -->
            <#if field.quickfinder?? && field.inputAllowed>
                <@krad.template component=field.quickfinder componentId="${field.id}"/>
            </#if>

            <#-- render field direct inquiry if field is editable and inquiry is enabled-->
            <#if !readOnly && field.inquiry?? && field.inquiry?has_content && field.inquiry.render>
                <@krad.template component=field.inquiry componentId="${field.id}" readOnly=field.readOnly/>
            </#if>

            <#-- render field help -->
            <#if field.help?? >
                <@krad.template component=field.help/>
            </#if>

            <#if field.renderFieldset>
                </fieldset>
            </#if>

    <!-- placeholder for dynamic field markers -->
    <span id="${field.id}_markers"></span>

        <#if !readOnly>
            <#-- render error container for field -->
            <#if field.validationMessages??>
                <@krad.template component=field.validationMessages/>
            </#if>

            <#-- render field constraint -->
            <#if field.constraintMessage??>
                <@krad.template component=field.constraintMessage/>
            </#if>
        </#if>

        <#-- render span and values for informational properties -->
        <span id="${field.id}_info_message"></span>

        <#list field.propertyNamesForAdditionalDisplay as infoPropertyPath>
            <span id="${field.id}_info_${krad.cleanPath(infoPropertyPath)}" class="uif-informationalMessage">
                <@spring.bind path="KualiForm.${infoPropertyPath}"/>
                 ${spring.status.value?default("")}
            </span>
        </#list>

        <#-- render field suggest if field is editable -->
        <#if !readOnly>
            <@krad.template component=field.suggest parent=field/>
        </#if>

        <#-- render hidden fields -->
        <#-- TODO: always render hiddens if configured? -->
        <#list field.additionalHiddenPropertyNames as hiddenPropertyName>
            <@spring.formHiddenInput id="${field.id}_h${hiddenPropertyName_index}"
            path="KualiForm.${hiddenPropertyName}"/>
        </#list>

        <#-- transform all text on attribute field to uppercase -->
        <#if !readOnly && field.control?? && field.uppercaseValue>
            <@krad.script value="uppercaseValue('${field.control.id}');"/>
        </#if>

    </@krad.div>

</#macro>


