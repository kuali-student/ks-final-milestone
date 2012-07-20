package org.kuali.student.enrollment.class1.hold.service.keyvalues;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;
import org.kuali.rice.krad.keyvalues.KeyValuesFinder;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.student.enrollment.acal.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.constants.HoldServiceConstants;
import org.kuali.student.r2.core.hold.service.HoldService;
import org.kuali.student.r2.core.type.dto.TypeInfo;

import javax.xml.namespace.QName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 21310823
 * Date: 7/19/12
 * Time: 5:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class HoldIssueInfoTypeKeyValues extends KeyValuesBase implements Serializable {

    private static final long serialVersionUID = 1L;

    private transient HoldService holdService;

    private static List<TypeInfo> holdIssueTypes;

    public List<KeyValue> getKeyValues() {
        List<KeyValue> keyValues = new ArrayList<KeyValue>();

        ConcreteKeyValue topKeyValue = new ConcreteKeyValue();
        topKeyValue.setKey("");
        topKeyValue.setValue("");
        keyValues.add(topKeyValue);

        ConcreteKeyValue keyValue = new ConcreteKeyValue();
        keyValue.setKey("progress");
        keyValue.setValue("Academic Progress Issue");
        keyValues.add(keyValue);

        ConcreteKeyValue keyValue1 = new ConcreteKeyValue();
        keyValue1.setKey("advising");
        keyValue1.setValue("Academic Advising Issue");
        keyValues.add(keyValue1);

        ConcreteKeyValue keyValue2 = new ConcreteKeyValue();
        keyValue2.setKey("discipline");
        keyValue2.setValue("Disciplinary Issue");
        keyValues.add(keyValue2);

        ConcreteKeyValue keyValue3 = new ConcreteKeyValue();
        keyValue3.setKey("financial");
        keyValue3.setValue("Financial Issue");
        keyValues.add(keyValue3);

        ConcreteKeyValue keyValue4 = new ConcreteKeyValue();
        keyValue4.setKey("library");
        keyValue4.setValue("Library Issue");
        keyValues.add(keyValue4);

        /*try {
            List<TypeInfo> types = getHoldIssueTypes();
            for (TypeInfo type : types) {
                ConcreteKeyValue keyValue = new ConcreteKeyValue();
                keyValue.setKey(type.getKey());
                keyValue.setValue(type.getName());
                keyValues.add(keyValue);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }*/

        return keyValues;
    }

    private List<TypeInfo> getHoldIssueTypes() throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {

        if(holdIssueTypes == null) {

            //TODO:Build real context.
            ContextInfo context = new ContextInfo();

            //holdIssueTypes = Collections.unmodifiableList(getHoldService().get(HoldServiceConstants.ACADEMIC_CALENDAR_TYPE_KEY, context));
        }

        return holdIssueTypes;  //To change body of created methods use File | Settings | File Templates.
    }

    protected HoldService getHoldService(){
        if(holdService == null) {
            holdService = (HoldService) GlobalResourceLoader.getService(new QName(HoldServiceConstants.NAMESPACE, HoldServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return holdService;
    }
}
