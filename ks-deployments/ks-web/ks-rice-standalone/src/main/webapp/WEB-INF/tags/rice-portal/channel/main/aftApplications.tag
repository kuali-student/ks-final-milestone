<%@ include file="/rice-portal/jsp/sys/riceTldHeader.jsp"%>

<channel:portalChannelTop channelTitle="Automated Functional Testing" />
<div class="body">

    <ul class="chan">
        <li><portal:portalLink displayTitle="true" title="Test State Propagation" url="${ConfigProperties.application.url}/kr-krad/testStatePropagation?viewId=testStatePropagationView&pageId=testStatePropagationPageId&methodToCall=start"/></li>
        <li><portal:portalLink displayTitle="true" title="Test SOC State Change" url="${ConfigProperties.application.url}/kr-krad/testStatePropagation?viewId=testStatePropagationView&pageId=aft-changeSocStatePage&methodToCall=start"/></li>
    </ul>

</div>

<channel:portalChannelBottom />
