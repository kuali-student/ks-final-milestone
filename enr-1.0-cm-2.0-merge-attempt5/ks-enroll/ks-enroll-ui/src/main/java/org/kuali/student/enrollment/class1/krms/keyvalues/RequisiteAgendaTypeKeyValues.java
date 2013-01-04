package org.kuali.student.enrollment.class1.krms.keyvalues;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 21310823
 * Date: 7/19/12
 * Time: 5:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class RequisiteAgendaTypeKeyValues extends KeyValuesBase {

    private static final long serialVersionUID = 1L;

    @Override
    public List<KeyValue> getKeyValues() {
        List<KeyValue> labels = new ArrayList<KeyValue>();
        labels.add(new ConcreteKeyValue("0", "Select One"));
        labels.add(new ConcreteKeyValue("1", "Enrollment Eligibility"));
        labels.add(new ConcreteKeyValue("2", "Credit Constraints"));
        return labels;
    }

//    @Override
//    public List<KeyValue> getKeyValues(ViewModel model) {
//        List<KeyValue> keyValues = new ArrayList<KeyValue>();
//
//         //Use Type Type Relation to get Agenda Types
//
//        //KrmsRepositoryServiceLocator.getKrmsTypeRepositoryService().getTypeByName("kuali.statement.type.course.");
//
//
//
//        try {
//
//
//
//
//            //kuali.statement.type.course.enrollmentEligibility
//            //kuali.statement.type.course.creditConstraints
//            List<TypeInfo> types = getTypeService().getTypesByRefObjectUri(RequisiteServiceConstants.REF_OBJECT_TYPE_URI_ISSUE, TestHelper.getContext1());
//            for (TypeInfo type : types) {
//                    ConcreteKeyValue keyValue = new ConcreteKeyValue();
//                    keyValue.setKey(type.getKey());
//                    keyValue.setValue(type.getName());
//                    keyValues.add(keyValue);
//                }
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        return keyValues;
//    }
//
//    public KeyValue getTypeKeyValue(String typeKey) {
//        ConcreteKeyValue keyValue = new ConcreteKeyValue();
//
//        ContextInfo context = TestHelper.getContext1();
//        //No data exists for Agenda Type
//        try {
//            List<TypeInfo> types = getTypeService().getTypesByRefObjectUri(RequisiteServiceConstants.REF_OBJECT_TYPE_URI_ISSUE, context);
//            for (TypeInfo type : types) {
//                if(type.getKey().equals(typeKey)) { //TODO remove check after data is fixed
//                    keyValue.setKey(type.getKey());
//                    keyValue.setValue(type.getName());
//                }
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        return keyValue;
//    }
//



}
