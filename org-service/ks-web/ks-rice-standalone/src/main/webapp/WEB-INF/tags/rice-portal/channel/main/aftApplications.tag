<%@ include file="/rice-portal/jsp/sys/riceTldHeader.jsp"%>

<channel:portalChannelTop channelTitle="Automated Functional Testing" />
<div class="body">

    <strong>State Propagation Testing</strong>
    <ul class="chan">
        <li><portal:portalLink displayTitle="true" title="Test State Propagation" url="${ConfigProperties.application.url}/kr-krad/testStatePropagation?viewId=testStatePropagationView&pageId=testStatePropagationPageId&methodToCall=start"/></li>
    </ul>

</div>

<channel:portalChannelBottom />
