package org.kuali.student.lum.lu.ui.course.client.configuration.sectionmanager;

import org.kuali.student.lum.lu.ui.course.client.configuration.DefaultCreateUpdateLayout;
import org.kuali.student.lum.lu.ui.course.client.configuration.IFrameSection;
import org.kuali.student.lum.lu.ui.course.client.service.CluProposal;

public class KewLinksLayoutManager {
    private DefaultCreateUpdateLayout<CluProposal> layout;

    public KewLinksLayoutManager() {
        super();
    }

    public KewLinksLayoutManager(DefaultCreateUpdateLayout<CluProposal> layout) {
        super();
        this.layout = layout;
    }

    public DefaultCreateUpdateLayout<CluProposal> addSection(String type, String state) {

		layout.addSection(new String[] { "KEW", "Action List" },
				new IFrameSection<CluProposal>().setUrl("http://localhost:8081/ks-rice-dev/kew/ActionList.do"));
		layout.addSection(new String[] { "KEW", "Person Lookup" },
				new IFrameSection<CluProposal>().setUrl("http://localhost:8081/ks-rice-dev/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.rice.kim.bo.impl.PersonImpl"));

        return layout;
    }
}
