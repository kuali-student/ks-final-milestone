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
    <%@ page import="java.io.*,java.util.*" %>
    <%@ page import="javax.servlet.*,java.text.*" %>

    <div id="applicationFooter">
        <div id="applicationFooterSection">
            <ul>
                <li><a href="#">Feedback</a></li>
                <li><a href="#">Help</a></li>
                <li><a href="http://myuw.washington.edu/" target="_blank">MyUW</a></li>
                <li><a href="http://www.washington.edu/" target="_blank">UW Home</a></li>
            </ul>
            <ul class="smaller">
                <li>&copy; <%
                   Date dNow = new Date( );
                   SimpleDateFormat year = new SimpleDateFormat ("yyyy");
                   out.print( year.format(dNow) );
                %> University of Washington</li>
                <li><a href="http://www.washington.edu/online/terms" target="_blank">Terms &amp; Conditions</a></li>
                <li><a href="http://www.washington.edu/online/privacy" target="_blank">Privacy</a></li>
            </ul>
        </div>
    </div>