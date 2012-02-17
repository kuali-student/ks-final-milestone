package org.kuali.student.r1.common.ui.client.widgets.table.scroll;

import java.util.Comparator;

@Deprecated
public abstract class RowComparator implements Comparator<Row>{
	
	public abstract int compare(Row row0, Row row1);
}
