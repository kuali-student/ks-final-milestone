package org.kuali.student.enrollment.class2.scheduleofclasses.util;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krms.api.repository.reference.ReferenceObjectBinding;
import org.kuali.student.enrollment.class1.krms.service.impl.AORuleEditorMaintainableImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class contains all the required requisites for display on the ui
 *
 * @author Kuali Student Team
 */
public class ScheduleOfClassesRequisites extends AORuleEditorMaintainableImpl {

    private String catalogUsageId;
    private List<ReferenceObjectBinding> coRefObjectsBindingList;
    private Map<String, List<ReferenceObjectBinding>> aoRefObjectsBindingMap;

    private Map<String, String> aoRequisiteMap;

    private StringBuilder coRequisite;
    private StringBuilder aoRequisite;

    public ScheduleOfClassesRequisites() {
        catalogUsageId = StringUtils.EMPTY;
        coRefObjectsBindingList = new ArrayList<ReferenceObjectBinding>();
        aoRefObjectsBindingMap = new HashMap<String, List<ReferenceObjectBinding>>();
        aoRequisiteMap = new HashMap<String, String>();
        coRequisite = new StringBuilder();
        aoRequisite = new StringBuilder();
    }

    public String getCatalogUsageId() {
        return catalogUsageId;
    }

    public void setCatalogUsageId(String catalogUsageId) {
        this.catalogUsageId = catalogUsageId;
    }

    public List<ReferenceObjectBinding> getCoRefObjectsBindingList() {
        return coRefObjectsBindingList;
    }

    public void setCoRefObjectsBindingList(List<ReferenceObjectBinding> coRefObjectsBindingList) {
        this.coRefObjectsBindingList = coRefObjectsBindingList;
    }

    public Map<String, List<ReferenceObjectBinding>> getAoRefObjectsBindingMap() {
        return aoRefObjectsBindingMap;
    }

    public void setAoRefObjectsBindingMap(Map<String, List<ReferenceObjectBinding>> aoRefObjectsBindingMap) {
        this.aoRefObjectsBindingMap = aoRefObjectsBindingMap;
    }

    public Map<String, String> getAoRequisiteMap() {
        return aoRequisiteMap;
    }

    public void setAoRequisiteMap(Map<String, String> aoRequisiteMap) {
        this.aoRequisiteMap = aoRequisiteMap;
    }

    public StringBuilder getCoRequisite() {
        return coRequisite;
    }

    public void setCoRequisite(StringBuilder coRequisite) {
        this.coRequisite = coRequisite;
    }

    public StringBuilder getAoRequisite() {
        return aoRequisite;
    }

    public void setAoRequisite(StringBuilder aoRequisite) {
        this.aoRequisite = aoRequisite;
    }
}
