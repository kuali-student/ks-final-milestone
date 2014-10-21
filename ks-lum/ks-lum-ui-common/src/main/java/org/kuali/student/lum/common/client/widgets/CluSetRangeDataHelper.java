package org.kuali.student.lum.common.client.widgets;

import java.util.List;

import org.kuali.student.common.ui.client.widgets.DataHelper;
import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r1.common.assembly.data.LookupMetadata;
import org.kuali.student.r1.common.assembly.data.LookupParamMetadata;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.lum.clu.dto.MembershipQueryInfo;

public class CluSetRangeDataHelper implements DataHelper {

    private LookupMetadata lookupMetadata;

    @Override
    public String parse(Data data) {
        MembershipQueryInfo membershipQueryInfo;
        StringBuilder labelText = new StringBuilder();
        int paramCounter = 0;
        membershipQueryInfo = CluSetRangeModelUtil.INSTANCE.toMembershipQueryInfo(data);
        if (membershipQueryInfo != null) {
            if (membershipQueryInfo.getQueryParamValues() != null && 
                    !membershipQueryInfo.getQueryParamValues().isEmpty()) {
                for (SearchParamInfo searchParam : membershipQueryInfo.getQueryParamValues()) {
                    if (paramCounter > 0) {
                        labelText.append(" ");
                    }
                    labelText.append(getParameterDisplayName(searchParam.getKey())).append(": ");
                    labelText.append("<b>").append(searchParam.getValues().get(0)).append("</b>");
                    paramCounter++;
                }
            } else {
                labelText.append("All Courses");
            }
        }
        return labelText.toString();
    }
    
    @Override
    public String getKey(Data data) {
        return parse(data);
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

    public LookupMetadata getLookupMetadata() {
        return lookupMetadata;
    }

    public void setLookupMetadata(LookupMetadata lookupMetadata) {
        this.lookupMetadata = lookupMetadata;
    }

}
