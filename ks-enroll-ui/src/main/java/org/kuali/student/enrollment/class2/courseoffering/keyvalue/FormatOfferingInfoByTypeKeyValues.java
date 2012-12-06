package org.kuali.student.enrollment.class2.courseoffering.keyvalue;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.enrollment.common.util.ContextBuilder;
import org.kuali.student.r2.common.dto.ContextInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dosoar
 * Date: 12/5/12
 * Time: 2:22 PM
 * To change this template use File | Settings | File Templates.
 */
//public class FormatOfferingInfoByTypeKeyValues extends  AbstractFormatOfferingTypeKeyValues implements Serializable {
public class FormatOfferingInfoByTypeKeyValues extends UifKeyValuesFinderBase implements Serializable {

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {

       // List<KeyValue> keyValues = new ArrayList<KeyValue>();
       String typeKey = "kuali.lui.type.course.format.offering";

        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        //Will get the correct FormatOfferingInfo later
       // TypeService typeService = CourseOfferingResourceLoader.loadTypeService();
       // QueryByCriteria.Builder qBuilder = QueryByCriteria.Builder.create();
        try{
           // List<String> types = typeService.searchForTypeKeys(qBuilder.build(), new ContextInfo());
            keyValues.add(new ConcreteKeyValue("", "Select Term Type"));
            keyValues.add(new ConcreteKeyValue("1234567890", typeKey));
          //  for(int i=0;i<1;i++){
               // keyValues.add(new ConcreteKeyValue(types.get(i),typeKey));
           // }
        }catch(Exception e){
            e.printStackTrace();
        }

        return keyValues;
    }


    public ContextInfo getContextInfo() {
        ContextInfo contextInfo = new ContextInfo();
        if (contextInfo == null){
            contextInfo =  ContextBuilder.loadContextInfo();
        }
        return contextInfo;
    }


    public static int getLineNumber() {
        return Thread.currentThread().getStackTrace()[2].getLineNumber();
    }
}
