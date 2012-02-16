package org.kuali.student.r1.common.ui.client.configurable.mvc.sections;

import org.kuali.student.r1.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.r1.common.ui.client.widgets.field.layout.layouts.TableFieldLayout;

/**
 * Section that uses a TableFieldLayout.
 * 
 * @author Kuali Student Team
 * @see TableFieldLayout
 */
public class TableSection extends BaseSection{
	public TableSection(){
		init();
	}

	public TableSection(SectionTitle title){
		init();
		layout.setLayoutTitle(title);
	}

	private void init() {
		layout = new TableFieldLayout();
		this.add(layout);
	}
}
