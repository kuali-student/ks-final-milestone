<#-- list of rnames to skip (not show) -->
<#assign skipList = [
	"LINE1WHAT", "LINE1"
]>

<#assign sectionHeadingOpen = false>

<#assign toolTipsMap = {
	"+":"The sub-requirement has been satisfied.",
	"-":"The sub-requirement has not been satisfied.",
	"IP -":"A course is in-progress which partially satisfies the sub-requirement but does not complete it.",
	"IP +":"A course is in-progress which partially satisfies the sub-requirement.",
	"0000":"Zeros precede some transfer courses to indicate that term and year the course was taken are not available to DARS.",
	"R":"This sub-requirement is mandatory.",
	"*":"This sub-requirement is optional.",
	">S":"The course credit is split.",
	">R":"The course is repeatable.",
	">-":"The course has exceeded the repeatable limit and has had its credit reduced.",
	"DP":"This course has been retaken.",
	">D":"Credit has been removed from this retaken course. For the purpose of a given requirement, credit may be restored--as when a minimum grade is required. This course is used in your UW GPA."
}>

<#assign subreqStatusMap = {
	"Status_NONE":"",
	"Status_NO":"-",
	"Status_OK":"+",
	"Status_IP":"+"
}>

<#assign termMap = {
	"1":"WIN",
	"2":"SPR",
	"3":"SUM",
	"4":"AUT"
}>

<#assign satisfiedMap = {
	"Status_NONE" : "",
	"Status_NO" : "NO",
	"Status_IP" : "IP",
	"Status_OK" : "OK"
}>
<html>
<head>
<link href="/student/ks-ap/css/ksap.audit.css" rel="stylesheet" type="text/css"/>
</head>    
<div class="ksap-audit-report">
	<h1>${dpTitle1?xml}</h1>   
	<div class="audit-summary">
		<div class="date-prepared">
			<label>Date Prepared:</label> ${preparedDate}
		</div>
		<div class="prepared-for">
			<label>Prepared For:</label> <span class="prepared-for-name">prepared-for-name</span>
		</div>
		<div class="program-entry-qtr">
			<label>Program Entry Date:</label> ${termMap[catalogYearTerm?substring(4,5)]} ${catalogYearTerm?substring(0,4)}
		</div>
		<#if degreeDate?trim != "NotFound">
		<div class="graduation-date">
			<label>Gradutation Date:</label> ${degreeDate?replace("/", " ")}
		</div>
		</#if>
	</div>
	
	<!--
	<#if showTestMessage>
	<div id="testMessageHeader">
	    ${testMessage?xml}
	</div>
	</#if>
	-->
	<!-- should be this stored text instead of the hardcoded paragraph above  -->
	<div class="toptext">
	      <#list includeTopText as topTextLine>
	        ${topTextLine?xml?replace("*", "")?trim} </#list>
	</div>
	
	<!--
	<#if showRefArtHeader>
	<div id="refArtHeaderText">
	    ${refArtHeaderTextLine1?xml}
	    ${refArtHeaderTextLine2?xml}
	</div>
	</#if>
	-->

<#if auditStatus = 0>
<div class="audit-status-msg audit-status-ok"><label>NOTE:</label> ${auditStatusMessage?replace( "*", "")?xml}</div>
<#elseif auditStatus = -1>
<div class="audit-status-msg audit-status-no"><label>NOTE:</label> ${auditStatusMessage?replace( "NOTE:", "")?xml}</div>
<#else>
<div class="audit-status-msg audit-status-ip"><label>NOTE:</label> ${auditStatusMessage?replace( "NOTE:", "")?xml}</div>
</#if>

<![CDATA[
cncflg: ${cncflg?string}
cmess: ${cmess}
ncmess: ${ncmess}
]]>


<#assign inSection = false>
<#assign needsDropdown = true>

<#list auditReportReqs as req>

<#assign rname = req.rname?xml?substring(7)?trim>
<#assign satisfied = "${req.satisfied?xml}" >	
<#if !req.showStatus >
<#assign satisfied="Status_NONE">
</#if>
<![CDATA[
rname: ${rname}
ok: ${req.ok?string}
category: ${req.category?xml}
status: ${req.status}

titleline: 
<#list req.titleLines as titleLine> 
	${titleLine?xml} 
</#list> 

headerline: 
<#list req.headerLines as headerLine> 
	${headerLine?xml} 
</#list> 
]]>
<#--
-->
<#if skipList?seq_contains(rname) >
<#-- do nothing -->
<#elseif req.category?contains("advising_note") > <#-- ADVISING NOTES -->

	<#if sectionHeadingOpen = true>
		</div>
	    <div class="ksap-status info uif-boxLayoutVerticalItem all-reqs-filtered" style="margin-bottom:20px; float:none; display:none;">
	        <img src="/student/ks-ap/images/pixel.gif" alt="" class="icon"/>
	        <div class="message">All requirements in this section have been hidden. See &quot;All Requirements&quot; for the full audit report.</div>
	    </div>
	    <#assign sectionHeadingOpen = false>
	    <#assign inSection = true>			
 	</#if>

 	<#if inSection>
	</div> <!-- close section -->
	<#assign inSection = false>
	</#if> 

    <div class="advisory ${rname} ${satisfied} linkify"> 
	<#list req.headerLines as headerLine> 
		${headerLine?replace("*", "")?replace("NOTE:", "<br/>NOTE:")?trim} 
	</#list> 
	</div>
<#elseif req.category?contains("other_courses" )  ><#-- OTHER COURSES aka hungry -->

	<#if sectionHeadingOpen = true>
		</div>
	    <div class="ksap-status info uif-boxLayoutVerticalItem all-reqs-filtered" style="margin-bottom:20px; float:none; display:none;">
	        <img src="/student/ks-ap/images/pixel.gif" alt="" class="icon"/>
	        <div class="message">All requirements in this section have been hidden. See &quot;All Requirements&quot; for the full audit report.</div>
	    </div>
	    <#assign sectionHeadingOpen = false>
	    <#assign inSection = true>			
 	</#if>

 	<#if inSection>
	</div> <!-- close section -->
	<#assign inSection = false>
	</#if> 
	
	<div class="section ${rname}"> 
		<div class="heading"><#list req.titleLines as titleLine>${titleLine?replace("*", "")?replace("_","")?trim} </#list></div>
        <div class="ksap-status info uif-boxLayoutVerticalItem all-reqs-filtered" style="margin-bottom:20px; float:none; display:none;">
            <img src="/student/ks-ap/images/pixel.gif" alt="" class="icon"/>
            <div class="message">All requirements in this section have been hidden. See &quot;All Requirements&quot; for the full audit report.</div>
        </div>
 
		<#list req.auditReportSubreqs as subreq>
		<div class="requirement">
			<div class="header">
				<div class="toggle"> </div>
				<div class="status Status_NONE"></div>
				<#if subreq.showTitle >
				<div class="title linkify">
					<#list subreq.titleLines as titleLine>${titleLine?replace("*NOTE:", "<br/>NOTE:")?replace("*", "")?replace("_", "")?replace(". NOTE:", ".<br/>NOTE:")?trim} </#list>
				</div>
				</#if>
			</div>
			<div class="body">
				<#if subreq.showTakenCourses>
				<table class="taken">
					<thead>
						<tr>
							<th>Qtr</th>
							<th colspan="2">Course Name</th>
							<th>Credits</th>
							<th>Grade</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
					<#list subreq.takenCourses as takenCourse>
						<tr class="${takenCourse.courseType}">
							<td class="term">${takenCourse.yt?xml}</td>
							<td class="course linkify">${takenCourse.displayCourse?substring(1,7)?trim?xml} ${takenCourse.displayCourse?substring(7,10)?trim?xml}</td>
							<td class="description">
							<#list takenCourse.descriptiveLines as descriptiveLine> 
							${descriptiveLine?xml} <br />
							</#list>
							</td>
							<td class="credit">${takenCourse.credit?string?replace(".0","")?xml}</td>
							<td class="grade">${takenCourse.grade?xml}</td>
							<#if toolTipsMap[takenCourse.condCode]?exists >
							<td class="ccode" title="${toolTipsMap[takenCourse.condCode]}">${takenCourse.condCode?xml}</td>
							<#else>
							<td class="ccode"></td>
							</#if>
						</tr>
					</#list>
					</tbody>
				</table>
				</#if>
			</div>			
		</div>
		</#list>        
    </div>
<#elseif ( req.headerLines?size > 2 )  > <#-- temporary fix for overly large headers, treat it as a requirement -->
	<#if sectionHeadingOpen = true>
		</div>
	    <div class="ksap-status info uif-boxLayoutVerticalItem all-reqs-filtered" style="margin-bottom:20px; float:none; display:none;">
	        <img src="/student/ks-ap/images/pixel.gif" alt="" class="icon"/>
	        <div class="message">All requirements in this section have been hidden. See &quot;All Requirements&quot; for the full audit report.</div>
	    </div>
	    <#assign sectionHeadingOpen = false>
    	<#assign inSection = true>			
 	</#if>
 
 	<#if inSection>
	</div> <!-- close section -->
	<#assign inSection = false>
	</#if> 

	<div class="bigsection bignote ${rname} ${satisfied}"> 
		<div class="heading linkify">
		<#list req.headerLines as headerLine> 
			<#if headerLine?trim?starts_with( "*" )> 
				<#if ( headerLine_index > 0 ) ><br /></#if>
				${headerLine?replace("*", "")?xml}
				<#if ( headerLine_has_next ) ><br /></#if>
			<#else>
				${headerLine?xml}
			</#if>
		</#list>
		</div>
	    <div class="ksap-status info uif-boxLayoutVerticalItem all-reqs-filtered" style="margin-bottom:20px; float:none; display:none;">
	        <img src="/student/ks-ap/images/pixel.gif" alt="" class="icon"/>
	        <div class="message">All requirements in this section have been hidden. See &quot;All Requirements&quot; for the full audit report.</div>
	    </div>
	<#assign inSection = true>
	
<#elseif ( req.headerLines?size > 0 )  > <#-- SECTION -->	

 	<#if inSection>
	</div> <!-- close section -->
	<#assign inSection = false>
	</#if> 
	
	<#if sectionHeadingOpen = false>
	<div class="section ${rname} ${satisfied}"> 
		<#if needsDropdown >
		<#assign needsDropdown = false>
		<div class="control-toolbar">
			<label for="requirement-status">Show</label>
			<select id="requirement-status">
				<option value="all">All Requirements</option>
				<option value="unmet">Unmet Requirements Only</option>
			</select>
		</div>
        <div class="ksap-status alert uif-boxLayoutVerticalItem audit-filtered" style="margin-bottom:20px; float:none; display:none;">
            <img src="/student/ks-ap/images/pixel.gif" alt="" class="icon"/>
            <div class="message">You are viewing a partial degree audit report. See &quot;All Requirements&quot; for the full audit report.</div>
        </div>
		</#if>

		<div class="heading">
	</#if>	

			<#if sectionHeadingOpen = true><br /></#if>		
			<#list req.headerLines as headerLine> 
			${headerLine?replace("*", "")?replace("_", "")?replace(". NOTE:", ".<br/>NOTE:")?trim}
			<#if headerLine_has_next><br /> </#if>
			</#list>
	<#assign sectionHeadingOpen = true>

<#else> <#-- REQUIREMENT -->

	<#if sectionHeadingOpen = true>
		</div>
	    <div class="ksap-status info uif-boxLayoutVerticalItem all-reqs-filtered" style="margin-bottom:20px; float:none; display:none;">
	        <img src="/student/ks-ap/images/pixel.gif" alt="" class="icon"/>
	        <div class="message">All requirements in this section have been hidden. See &quot;All Requirements&quot; for the full audit report.</div>
	    </div>
	    <#assign sectionHeadingOpen = false>
    	<#assign inSection = true>			
	    
 	</#if>
 	
 	<#if inSection = false>
	<div class="section"> <!-- no section -->
	</#if> 

	<#assign inSection = true>
	
		<#if needsDropdown ><#-- cut and paste from above, sorry! -->
		<#assign needsDropdown = false>
		<div class="control-toolbar">
			<label for="requirement-status">Show</label>
			<select id="requirement-status">
				<option value="all">All Requirements</option>
				<option value="unmet">Unmet Requirements Only</option>
			</select>
		</div>
		</#if>
		<div class="requirement ${rname} ${satisfied} ${req.summary?xml}">
			<div class="header">
				<div class="toggle"></div>
				<div class="status ${satisfied}">${satisfiedMap[satisfied]}</div>
				<#if req.showNumber><div class="reqNumber">${req.number?xml}</div></#if>
				<#if req.showGroups><div class="reqGroups">${req.groups?xml}</div></#if>
				<div class="title linkify"><#list req.titleLines as titleLine>${titleLine?replace("*NOTE:", "<br/>NOTE:")?replace("*", "")?replace("_","")?replace(". NOTE:", ".<br/>NOTE:")?trim} </#list></div>
			</div>
	
			<div class="body">
				<#if req.showGotSummary || req.showInProgressHours || req.showNeedsSummary >
		        <div class="totals">
					<#if req.showGotSummary>
		            <span class="earned">
		                <#if req.showWarnInd>${req.warnInd?xml}</#if>
		                Earned:
		            	<#if req.showGotHours>
		            		<span class="value">${req.gotHours?replace(".0","")?xml}</span> ${req.gotHoursText?xml}
						</#if>
		            	<#if req.showGotCount>
		            		<span class="value">${req.gotCount?xml}</span> ${req.gotCountText?xml}
		            	</#if>
		                <#if req.showGotSubreqs>
		                	<span class="value">${req.gotSubreqs?xml}</span> ${req.gotSubreqsText?xml}
		                </#if>
		                <#if req.showGotGpa>
		                	<span class="value">${req.gotGpa?xml}</span> GPA
		                </#if>
		            </span>
		            </#if> 
					<#--
		            <#if req.showDetailGpaLine>
		            <span class="reqGpaDetail">
		            	${req.gotGpaHours} 
		            	${req.gotGpaHoursText?xml} 
		            	${req.gotGpaPoints}
						${req.gotGpaPointsText?xml}
						${req.gotGpa} GPA
		            </div>
		            </#if>
					-->
					<#if req.showInProgressHours>
		            <span class="inprogress"> 
		            	In-progress: 
		            	<span class="value"> ${req.ipHours?replace(".0","")?xml} </span> ${req.ipHoursText?xml} 
		            </span>
		            </#if>
					<#--                
		            <#if req.showPlannedHours>
		            <div class="whatif">
		                ${req.wifStub?xml}
		                <span class="value">${req.wifHours?replace(".0","")?xml} </span> ${req.wifHoursText?xml}
		            </div>
					</#if>
					-->                
		            <#if req.showNeedsSummary>
		            <span class="needs">
		                Needs:
						<#if req.showNeedsHours>
							<span class="value"> ${req.needsHours?replace(".0","")?xml} </span> ${req.needsHoursText?xml}
						</#if>
		            	<#if req.showNeedsCount>
		            		<span class="value"> ${req.needsCount?xml} </span> ${req.needsCountText?xml}
		            	</#if>
		                <#if req.showNeedsSubreqs>
		                	<span class="value"> ${req.needsSubreqs?xml} </span> ${req.needsSubreqsText?xml}
		                </#if>
		                <#if req.showNeedsGpa>
		                	<span class="value"> ${req.needsGpa?xml} </span> GPA
		                </#if>
		            </span>
		            </#if>
		        </div> <#-- end of totals -->
		        </#if>
		            
				<#if req.showExcLines>
				<div class="exceptions">
					<#list req.appliedExceptionText as ex> <div class="reqCline">${ex?xml}</div></#list>
				</div>
				</#if>
				
				<#list req.auditReportSubreqs as subreq>
					<#assign justTitle = "">
					<#if ( !subreq.showGotSummary && !subreq.showInProgressHours && !subreq.showNeedsSummary && !subreq.showTakenCourses && !subreq.showSubreqNumber && subreq.status == "Status_NONE" ) >
						<#assign justTitle = "justtitle" >
					</#if>
				<![CDATA[
					showSubreqStatus: ${subreq.showSubreqStatus?string}
					showExcLines: ${subreq.showExcLines?string}
					status: ${subreq.status?xml}
					seqErr: ${subreq.seqErr?xml}
					showTitle: ${subreq.showTitle?string} 
					required: ${subreq.required?string}
					subreqRequired: ${subreq.subreqRequired?xml}
					subreq titleLines:
						<#list subreq.titleLines as titleLine>
						<#if titleLine?trim == "." > just period </#if>
						<#if titleLine?trim != "." > not period </#if>
						${titleLine}
						</#list>
					justTitle: ${justTitle}
				]]>
				<#--
				-->
				<div class="subrequirement ${justTitle}">
					<#if subreq.showSubreqStatus >
					<div class="header">
						<#-- if subreq.showSubreqStatus && ( subreq.status != "Status_NONE" ) -->
						<#if subreq.showSubreqStatus >
						<div class="status ${subreq.status?xml}">${subreq.seqErr?xml}${subreqStatusMap[subreq.status]}</div>
						</#if>
						
						<div class="subreqNumber required"> 
							<#if subreq.required>${subreq.subreqRequired?xml}</#if> 
							<#if subreq.showSubreqNumber>${subreq.subreqNumber?xml}
								<#if subreq.showParen>)</#if>
							</#if>
						</div>
						<#if subreq.showTitle >
						<div class="title linkify">
							<#list subreq.titleLines as titleLine>
							<#if titleLine?trim != "." > 
							${titleLine?replace("*", "")?replace("_", "")?replace("-&gt;","--")?replace("-[-]+", " ", "r")?replace(". NOTE:", ".<br/>NOTE:")?replace("*NOTE:", "<br/>NOTE:")?trim}
							</#if>
							</#list>
						</div>
						</#if>
						
						<#if subreq.showExcLines>
						<#list subreq.appliedExceptionText as ex>
						<div class="subreqCline">${ex?xml}</div>
						</#list>
						</#if>
					</div>
					</#if>
					
					<#if subreq.showGotSummary || subreq.showInProgressHours || subreq.showNeedsSummary >
					<div class="totals">
						<#if subreq.showGotSummary>
			            <span class="earned">
			                Earned:
			            	<#if subreq.showGotHours>
			        		 ${subreq.gotHoursOpenDecoration} <span class="value"> ${subreq.gotHours?replace(".0","")?xml} </span> ${subreq.gotHoursText?xml}${subreq.gotHoursCloseDecoration}
							</#if>
			            	<#if subreq.showGotCount>
			            	<span class="value"> ${subreq.gotCount?xml} </span> ${subreq.gotCountText?xml}
			            	</#if>
			                <#if subreq.showGotGpa>
			                <span class="value"> ${subreq.gotGpa?xml} </span> GPA
			                </#if>
			            </span>
			            </#if> 
						<#--
						<#if subreq.showDetailGpaLine>
		                <span class="gpadetail">
		                	${subreq.gotGpaHours?xml}
		                	${subreq.gotGpaHoursText?xml}
		                    ${subreq.gotGpaPoints?xml}
		                    ${subreq.gotGpaPointsText?xml}
		                    ${subreq.gotGpa?xml} GPA
		                </span>
		                </#if>
						-->
						<#if subreq.showInProgressHours >
		                <span class="inprogress">
		                	In-progress:
		                	${subreq.ipSrStub?xml}
		                	<#if subreq.showInProgressHours >
		                	<span class="value"> ${subreq.ipHours?replace(".0","")?xml} </span> ${subreq.ipHoursText?xml}
		                	</#if>
		                	<#if subreq.showInProgressCount >
		                    <span class="value"> ${subreq.ipCount?xml} </span> ${subreq.ipCountText}
		                	</#if>
		                </span>
						</#if> 
						<#--
						<#if subreq.showPlannedHours > 
		                <span class="subreqWhatIfDetail">
		                    ${subreq.wifSrStub?xml} 
		                    ${subreq.plannedHours?xml}
		                    ${subreq.plannedHoursText?xml}
		                    ${subreq.plannedCount?xml} 
							${subreq.plannedCountText?xml}
		                </span>
		            	</#if>
						-->
						<#if subreq.showNeedsSummary >
						<span class="needs">
							Needs:
							<#if subreq.showNeedsHours >
							<span class="value"> ${subreq.needsHours?replace(".0","")?xml} </span> ${subreq.needsHoursText?xml} 
							</#if>
							<#if subreq.showNeedsCount >
							<span class="value"> ${subreq.needsCount?xml} </span> ${subreq.needsCountText?xml}
							</#if>
							<#if subreq.showNeedsGpa >
							<span class="value"> ${subreq.needsGpa?xml} </span> GPA
							</#if>
						</span>
						</#if>
		        	</div> <#-- end of totals -->
					</#if>
					
					<#if subreq.showTakenCourses>
					<table class="taken">
						<caption>Courses Used To Satisfy This Requirement</caption>
						<thead>
							<tr>
								<th>Qtr</th>
								<th colspan="2">Course Name</th>
								<th>Credits</th>
								<th>Grade</th>
								<th></th>
							</tr>
						</thead>
						<tbody>
						<#list subreq.takenCourses as takenCourse>
							<tr class="${takenCourse.courseType}">
								<td class="term">${takenCourse.yt?xml}</td>
								<td class="course linkify">${takenCourse.displayCourse?substring(1,7)?trim?xml} ${takenCourse.displayCourse?substring(7,10)?trim?xml}</td>
								<td class="description"><#list takenCourse.descriptiveLines as descriptiveLine> ${descriptiveLine?xml} </#list></td>
								<td class="credit">${takenCourse.credit?string?replace(".0","")?xml}</td>
								<td class="grade">${takenCourse.grade?xml}</td>
								<#if toolTipsMap[takenCourse.condCode]?exists >
								<td class="ccode" title="${toolTipsMap[takenCourse.condCode]}">${takenCourse.condCode?xml}</td>
								<#else>
								<td class="ccode"></td>
								</#if>
							</tr>
						</#list>
						</tbody>
					</table>
					</#if>
					<#--
					
					subreq.notText: ${subreq.notText?xml}
					<#list subreq.notFromHtmlCourses as course>
					${course?xml}
					</#list>

					subreq.selectText: ${subreq.selectText?xml}
					<#list subreq.selectFromHtmlCourses as course>
					${course?xml}
					</#list>
					
					-->
					<#if subreq.showSelectNotFrom>
					<table class="fromcourses">
						<#if subreq.showReject>
						<tr class="notfromcourses">
							<td class="fromlabel">${subreq.notText?xml}</td>
							<td>
								<table>
			   						<#list subreq.notFromHtmlCourses as course>
									<tr><td class="fromcourselist">${course}</td></tr>
			    					</#list>
			    				</table>
			    			</td>
			    		</tr>
						</#if>
						
						<#if subreq.showAccept>
						<tr class="selectfromcourses">
							<td class="fromlabel">${subreq.selectText?xml}</td>
							<td>
								<table>
			   						<#list subreq.selectFromHtmlCourses as course>
									<tr><td class="fromcourselist">${course?replace( "&", "&amp;")}</td></tr>
			    					</#list>
			    				</table>
			    			</td>
			    		</tr>
			    		
						</#if>
					</table>
					<#if subreq.showAccept>
					<#--
						show accept:
   						<#list subreq.acceptListElements as accept>
							${accept.useCourse?string} 
							${accept.course}
							${accept.useFullCourse?string} 
							${accept.shortCourse}
							${accept.courseLink}
							followedByANumber: ${accept.followedByANumber?string} 
							followingANumber: ${accept.followingANumber?string} 
							padDept: ${accept.padDept?string} 
							dept: ${accept.dept}

							hasRefCourse: ${accept.hasRefCourse?string} 
							refCourse: ${accept.refCourse}

							noteElement: ${accept.noteElement?string} 
							note: ${accept.note}

							symbolElement: ${accept.symbolElement?string} 
							connector: ${accept.connector}

							spanCourse: ${accept.spanCourse}

							forceNewLine: ${accept.forceNewLine?string} 
    					</#list>
					-->
					</#if>
					
					<#if subreq.showNoRefCoursesFoundMessage>
						${noRefCourseMessage?xml}
					</#if>
					</#if>
				</div> <#-- end of subrequirement -->
			</#list>
			</div>
		</div> <#-- end of requirement -->
		
</#if>

</#list> <#-- AuditRunReq list -->

<#if inSection>
	</div>
</#if> 

<#if showIncludeBottomText>
    <div>
    <#list includeBottomText as bottomTextLine>
	${bottomTextLine?replace("*", "")?replace("_", "")?replace("END OF ANALYSIS", "<br/>END OF ANALYSIS")?trim }      
    </#list>
     <hr class="headerRule" />
    </div>
</#if>    
<input name="script" type="hidden" value="jQuery.publish('NEW_AUDIT');"/>    
<div> htm.ftl updated: 2012/11/30 12:22pm</div>       
</div>
</html>