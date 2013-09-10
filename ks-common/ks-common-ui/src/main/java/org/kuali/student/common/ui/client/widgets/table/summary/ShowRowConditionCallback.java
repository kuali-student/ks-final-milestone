package org.kuali.student.common.ui.client.widgets.table.summary;

import org.kuali.student.common.ui.client.mvc.DataModel;

public interface ShowRowConditionCallback {
	public void processShowConditions(SummaryTableFieldRow row, DataModel column1, DataModel column2);
}
