package org.kuali.student.lum.lu.ui.course.client.configuration.sectionmanager;

import org.kuali.student.common.ui.client.service.ServerPropertiesRpcService;
import org.kuali.student.common.ui.client.service.ServerPropertiesRpcServiceAsync;
import org.kuali.student.lum.lu.ui.course.client.configuration.DefaultCreateUpdateLayout;
import org.kuali.student.lum.lu.ui.course.client.configuration.IFrameSection;
import org.kuali.student.lum.lu.ui.course.client.service.CluProposal;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.Arrays;
import java.util.Map;

public class KewLinksLayoutManager {
    private static final String personLookupUrlParams = "?methodToCall=start&businessObjectClassName=org.kuali.rice.kim.bo.impl.PersonImpl";
    private String personLookupUrl = "http://localhost:8081/ks-rice-dev/kr/lookup.do" + personLookupUrlParams;
    private String actionListUrl = "http://localhost:8081/ks-rice-dev/kew/ActionList.do";
    private DefaultCreateUpdateLayout<CluProposal> layout;
    ServerPropertiesRpcServiceAsync serverProperties = GWT.create(ServerPropertiesRpcService.class);


    public KewLinksLayoutManager() {
        super();
        serverProperties.get(Arrays.asList("ks.rice.personLookup.serviceAddress","ks.rice.actionList.serviceAddress"), new AsyncCallback<Map<String,String>>() {
            
            @Override
            public void onSuccess(Map<String,String> result) {
                if(result != null) {
                    if(result.get("ks.rice.personLookup.serviceAddress") != null)
                        personLookupUrl = result.get("ks.rice.personLookup.serviceAddress") + personLookupUrlParams;
                    if(result.get("ks.rice.actionList.serviceAddress") != null)
                        actionListUrl = result.get("ks.rice.actionList.serviceAddress");
                }
            }
            
            @Override
            public void onFailure(Throwable caught) { //ignored, we'll just use defaults
            }
        });
    }

    public KewLinksLayoutManager(DefaultCreateUpdateLayout<CluProposal> layout) {
        this();
        this.layout = layout;
    }

    public DefaultCreateUpdateLayout<CluProposal> addSection(String type, String state) {

		layout.addSection(new String[] { "KEW", "Action List" },
				new IFrameSection<CluProposal>().setUrl(actionListUrl));
		layout.addSection(new String[] { "KEW", "Person Lookup" },
				new IFrameSection<CluProposal>().setUrl(personLookupUrl));

        return layout;
    }
}
