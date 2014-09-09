<#--

    Copyright 2005-2014 The Kuali Foundation

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
<!-- This macro replaces the scripts of the KRAD datepicker with static HTML. A global event handler is used
 to check if the click event occurs on something that should have a datepicker (has "ks-deferred-datepicker" class)
 If so the original KRAD script is run. This reduces JS execution time for pages with many datepickers (ACal)
 see ks.krad.override.js-->
<#macro uif_deferred_datePicker widget componentId><button type="button" data-dp-control-id="${componentId}" data-dp-options="${widget.templateOptionsJSString?html}" class="ui-datepicker-trigger btn-link ks-fontello-icon-calendar ks-deferred-datepicker" alt="Date picker" value="Date picker"/></#macro>




