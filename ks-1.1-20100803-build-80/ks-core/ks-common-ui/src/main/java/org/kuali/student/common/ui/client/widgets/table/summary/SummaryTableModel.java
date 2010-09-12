package org.kuali.student.common.ui.client.widgets.table.summary;

import java.util.ArrayList;
import java.util.List;

public class SummaryTableModel {
    List<SummaryTableSection> sectionList = new ArrayList<SummaryTableSection>();

    public List<SummaryTableSection> getSectionList() {
        return sectionList;
    }
    
    public void addSection(SummaryTableSection section) {
        this.sectionList.add(section);
    }
}
