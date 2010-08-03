package org.kuali.student.common.ui.client.widgets.table.summary;

import java.util.ArrayList;
import java.util.List;

public class SummaryTableModel {
    List<SummaryTableSection> sectionList = new ArrayList<SummaryTableSection>();
    private String contentColumnHeader1;
    private String contentColumnHeader2;
    
    public String getContentColumnHeader1() {
        return contentColumnHeader1;
    }

    public void setContentColumnHeader1(String contentColumnHeader1) {
        this.contentColumnHeader1 = contentColumnHeader1;
    }

    public String getContentColumnHeader2() {
        return contentColumnHeader2;
    }

    public void setContentColumnHeader2(String contentColumnHeader2) {
        this.contentColumnHeader2 = contentColumnHeader2;
    }

    public List<SummaryTableSection> getSectionList() {
        return sectionList;
    }
    
    public void addSection(SummaryTableSection section) {
        this.sectionList.add(section);
    }
}
