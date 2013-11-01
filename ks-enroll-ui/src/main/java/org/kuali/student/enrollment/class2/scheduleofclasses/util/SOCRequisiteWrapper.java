package org.kuali.student.enrollment.class2.scheduleofclasses.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class contains all the required requisites for display on the ui
 *
 * @author Kuali Student Team
 */
public class SOCRequisiteWrapper {

    private StringBuilder coRequisite;
    private Map<String, Map<String, String>> aoRequisiteMap;

    public SOCRequisiteWrapper() {
        coRequisite = new StringBuilder();
        aoRequisiteMap = new HashMap<String, Map<String, String>>();
    }

    public StringBuilder getCoRequisite() {
        return coRequisite;
    }

    public void setCoRequisite(StringBuilder coRequisite) {
        this.coRequisite = coRequisite;
    }

    public Map<String, Map<String,String>> getAoRequisiteMap() {
        return aoRequisiteMap;
    }

    public void setAoRequisiteMap(Map<String, Map<String,String>> aoRequisiteMap) {
        this.aoRequisiteMap = aoRequisiteMap;
    }
}
