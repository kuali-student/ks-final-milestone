package org.kuali.student.lum.lu.ui.tools.client.widgets;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.HasDataValue;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.LookupMetadata;
import org.kuali.student.core.assembly.data.LookupParamMetadata;
import org.kuali.student.core.assembly.data.Data.DataValue;
import org.kuali.student.core.assembly.data.Data.Value;
import org.kuali.student.core.search.dto.SearchParam;
import org.kuali.student.lum.lu.dto.MembershipQueryInfo;
import org.kuali.student.lum.lu.ui.tools.client.widgets.itemlist.CluSetRangeModelUtil;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;

public class CluSetRangeLabel extends Composite implements HasDataValue {

    private MembershipQueryInfo membershipQueryInfo;
    protected SimplePanel mainPanel = new SimplePanel();
    private KSLabel label = new KSLabel();
    private LookupMetadata lookupMetadata;
    private List<Callback<Value>> valueChangeCallbacks =
        new ArrayList<Callback<Value>>();

    public CluSetRangeLabel() {
        super.initWidget(mainPanel);
    }
    
    @Override
    public Value getValue() {
        DataValue value = new DataValue(
                CluSetRangeModelUtil.INSTANCE.toData(membershipQueryInfo));
        return value;
    }

    @Override
    public void setValue(Value value) {
        membershipQueryInfo = null;
        if (value != null) {
            Data d = value.get();
            membershipQueryInfo = CluSetRangeModelUtil.INSTANCE.toMembershipQueryInfo(d);
        }
        callHandlers();
        redraw();
    }
    
    @Override
    public void addValueChangeCallback(Callback<Value> callback) {
        valueChangeCallbacks.add(callback);
    }
    
    private void callHandlers() {
        if (valueChangeCallbacks != null) {
//            MyGwtEvent myEvent = new MyGwtEvent(getValue());
            for (Callback<Data.Value> handler : valueChangeCallbacks) {
                handler.exec(getValue());
            }
        }
    }

    private void redraw() {
        StringBuilder labelText = new StringBuilder();
        int paramCounter = 0;
        mainPanel.clear();
        mainPanel.setWidget(label);
        label.setText("");
        if (membershipQueryInfo != null && membershipQueryInfo.getQueryParamValueList() != null && 
                !membershipQueryInfo.getQueryParamValueList().isEmpty()) {
            labelText.append(getLookupDisplayName()).append(": ");
            for (SearchParam searchParam : membershipQueryInfo.getQueryParamValueList()) {
                if (paramCounter > 0) {
                    labelText.append(" ");
                }
                labelText.append(getParameterDisplayName(searchParam.getKey())).append(" ");
                labelText.append(searchParam.getValue());
                paramCounter++;
            }
            label.setText(labelText.toString());
        }
    }
    
    private String getLookupDisplayName() {
        String lookupDisplayName = null;
        if (lookupMetadata == null) {
            lookupDisplayName = membershipQueryInfo.getSearchTypeKey();
        } else {
            lookupDisplayName = lookupMetadata.getName();
        }
        return lookupDisplayName;
    }
    
    private String getParameterDisplayName(String parameterKey) {
        String parameterDisplayName = null;
        if (lookupMetadata == null) {
            parameterDisplayName = parameterKey;
        } else {
            List<LookupParamMetadata> searchParams = lookupMetadata.getParams();
            if (searchParams != null) {
                for (LookupParamMetadata searchParam : searchParams) {
                    if (nullSafeEquals(searchParam.getKey(), parameterKey)) {
                        parameterDisplayName = searchParam.getName();
                    }
                }
            }
            if (parameterDisplayName == null) {
                parameterDisplayName = parameterKey;
            }
        }
        return parameterDisplayName;
    }
    
    private boolean nullSafeEquals(Object str1, Object str2) {
        return (str1 == null && str2 == null ||
                str1 != null && str2 != null && str1.equals(str2));
    }
    
//    public class MyGwtEvent extends ValueChangeEvent<Data.Value> {
//        public MyGwtEvent(Data.Value value) {
//            super(value);
//        }
//    }

    public LookupMetadata getLookupMetadata() {
        return lookupMetadata;
    }

    public void setLookupMetadata(LookupMetadata lookupMetadata) {
        this.lookupMetadata = lookupMetadata;
    }

}
