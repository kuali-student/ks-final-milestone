package org.kuali.student.r1.common.ui.client.widgets.table.summary;

import org.kuali.student.r1.common.ui.client.mvc.DataModel;

public interface ShowRowConditionCallback {
	public void processShowConditions(SummaryTableFieldRow row, DataModel column1, DataModel column2);
}
