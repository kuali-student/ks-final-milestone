package org.kuali.student.common.kitchensink.performance;

import org.kuali.rice.krad.web.form.UifFormBase;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: swedev
 * Date: 11/21/13
 * Time: 1:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class KitchenSinkPerformanceForm extends UifFormBase {
    private String inputOne;
    private List<KitchenSinkPerformanceCollection>  perfCollection;

    public String getInputOne() {
        return inputOne;
    }

    public void setInputOne(String inputOne) {
        this.inputOne = inputOne;
    }

    public List<KitchenSinkPerformanceCollection> getPerfCollection() {
        return perfCollection;
    }

    public void setPerfCollection(List<KitchenSinkPerformanceCollection> perfCollection) {
        this.perfCollection = perfCollection;
    }
}
