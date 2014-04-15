package org.kuali.student.ap.planner.util;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.util.UifKeyValue;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.ap.coursesearch.CreditsFormatter;
import org.kuali.student.ap.planner.form.PlannerFormImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CourseCreditListBuilder extends UifKeyValuesFinderBase {

    @Override
    public List<KeyValue> getKeyValues(ViewModel model){
        PlannerFormImpl form = (PlannerFormImpl)model;

        CreditsFormatter.Range range = CreditsFormatter.getRange(form.getCourse());
        List<String> variableCreditValues = new ArrayList<String>();
        if(range.getMultiple()!=null && !range.getMultiple().isEmpty()){
            for(BigDecimal value : range.getMultiple()){
                variableCreditValues.add(value.toString());
            }
        } else{
            if(range.getMin()==range.getMax()){
                variableCreditValues.add(range.getMax().toString());
            }else{
                for(int i = range.getMin().intValue();i<=range.getMax().intValue();i++){
                    variableCreditValues.add(i+"");
                }
            }
        }

        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        keyValues.add(new ConcreteKeyValue("-1","Select"));
        for(String value : variableCreditValues){
            KeyValue keyValue = new ConcreteKeyValue(value,value);
            keyValues.add(keyValue);
        }
        return keyValues;
    }
}
