<%--
 Copyright 2006-2007 The Kuali Foundation

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
<%@ include file="/krad/WEB-INF/jsp/tldHeader.jsp" %>

<tiles:useAttribute name="control"
                    classname="org.kuali.student.enrollment.uif.control.ScheduleCartControl"/>
<tiles:useAttribute name="field" classname="org.kuali.rice.krad.uif.field.InputField"/>

<div id="${control.id}" class="${control.styleClassesAsString}">
     <form:hidden id="${control.id}-value" path="${field.bindingInfo.bindingPath}"/>

<%--     <krad:template component="${control.headerField}"/>--%>

     <table class="scheduleKey" id="${control.id}-cart">
        <tr>
            <th>Key</th>
            <th style="text-align: left">Name</th>
        </tr>
    </table>
    <krad:script value="
           jq('#' +'${control.scheduleId}').ready(function() {
                jq('#' +'${control.scheduleId}').addBulkTimesAndKeys(eval('(' + jq('#' + '${control.id}-value').val() + ')'),'${control.id}');
                jq('#' +'${control.id}-cart').addButton('${control.removeText}',${control.removeJsFunction});
           });
    "/>
</div>