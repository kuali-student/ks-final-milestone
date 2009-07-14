package org.kuali.student.lum.lu.ui.course.client.configuration.sectionmanager;

import org.kuali.student.lum.lu.ui.course.client.configuration.DefaultCreateUpdateLayout;
import org.kuali.student.lum.lu.ui.course.client.configuration.IFrameSection;
import org.kuali.student.lum.lu.ui.course.client.service.CluProposal;
import org.kuali.student.lum.lu.ui.course.client.service.ServerProperties;
import org.kuali.student.lum.lu.ui.course.client.service.ServerPropertiesAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class KewLinksLayoutManager {
    private static final String personLookupUrlParams = "?methodToCall=start&businessObjectClassName=org.kuali.rice.kim.bo.impl.PersonImpl";
    private String personLookupUrl = "http://localhost:8081/ks-rice-dev/kr/lookup.do" + personLookupUrlParams;
    private String actionListUrl = "http://localhost:8081/ks-rice-dev/kew/ActionList.do";
    private DefaultCreateUpdateLayout<CluProposal> layout;
    ServerPropertiesAsync serverProperties = GWT.create(ServerProperties.class);


    public KewLinksLayoutManager() {
        super();
        serverProperties.get("ks.rice.personLookup.serviceAddress", new AsyncCallback<String>() {
            
            @Override
            public void onSuccess(String result) {
                if(result != null)
                    personLookupUrl = result + personLookupUrlParams;
            }
            
            @Override
            public void onFailure(Throwable caught) {
            }
        });
        serverProperties.get("ks.rice.actionList.serviceAddress", new AsyncCallback<String>() {

            @Override
            public void onFailure(Throwable caught) {
            }

            @Override
            public void onSuccess(String result) {
                if(result != null)
                    actionListUrl = result;
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
