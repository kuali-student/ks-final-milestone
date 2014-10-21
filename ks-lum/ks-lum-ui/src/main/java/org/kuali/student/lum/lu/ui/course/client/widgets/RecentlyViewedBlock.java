package org.kuali.student.lum.lu.ui.course.client.widgets;

import java.util.List;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.layout.LinkContentBlock;
import org.kuali.student.lum.common.client.helpers.HasRecentlyViewedData;
import org.kuali.student.lum.common.client.helpers.RecentDocInfo;
import org.kuali.student.lum.common.client.helpers.RecentlyViewedHelper;

public class RecentlyViewedBlock extends LinkContentBlock implements HasRecentlyViewedData{

	public RecentlyViewedBlock(String blockTitle, String blockDescriptionHtml) {
		super(blockTitle, blockDescriptionHtml);
		RecentlyViewedHelper.addDependant(this);
		KSLabel label = new KSLabel(Application.getApplicationContext().getMessage("noRecent"));
		label.addStyleName("contentBlock-desc");
		this.add(label);
	}
	
	public void update(){
		List<RecentDocInfo> infos = RecentlyViewedHelper.getRecentlyViewed();
		listLayout.clear();
		for(int i = 0; i < infos.size(); i++){
			this.addNavLinkWidget(infos.get(i).getName(), infos.get(i).getLocation());
		}
	}

}
