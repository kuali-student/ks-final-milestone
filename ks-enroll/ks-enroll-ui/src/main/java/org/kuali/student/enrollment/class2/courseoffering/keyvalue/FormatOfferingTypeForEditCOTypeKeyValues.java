package org.kuali.student.enrollment.class2.courseoffering.keyvalue;


import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.MaintenanceForm;

import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.r2.lum.course.dto.FormatInfo;

import java.util.ArrayList;
import java.util.List;


public class FormatOfferingTypeForEditCOTypeKeyValues extends AbstractFormatOfferingTypeKeyValues {
    private static final long serialVersionUID = 1L;

//    @Override
//    public List<KeyValue> getKeyValues(ViewModel model) {
//        MaintenanceForm form = (MaintenanceForm)model;
//        CourseOfferingEditWrapper coEditWrapper = (CourseOfferingEditWrapper)form.getDocument().getNewMaintainableObject().getDataObject();
//
//        List<KeyValue> keyValues = new ArrayList<KeyValue>();
//        keyValues.add(new ConcreteKeyValue("", "Select Format Type"));
//
//        List<FormatInfo> formatOptions;
//        List<String> availableFormatTypes;
//
//        try {
//            formatOptions = getFormatOptions(coEditWrapper);
//            availableFormatTypes = getAvailableFormatTypes(coEditWrapper);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        for (FormatInfo format : formatOptions) {
//            ConcreteKeyValue keyValue = new ConcreteKeyValue();
//            if(!availableFormatTypes.contains(format.getId())){
//                keyValue.setKey(format.getId());
//                keyValue.setValue(format.getName());
//                keyValues.add(keyValue);
//            }
//        }
//
//        return keyValues;
//    }

//    private List<FormatInfo> getFormatOptions(CourseOfferingEditWrapper coEditWrapper) throws Exception{
//        if(coEditWrapper!=null && coEditWrapper.getCourse() != null){
//            return coEditWrapper.getCourse().getFormats();
//        }else if(coEditWrapper!=null && coEditWrapper.getCourse() == null) {
//            return (getCourseService().getCourse(coEditWrapper.getCoInfo().getCourseId())).getFormats();
//        }
//        else{
//            throw new Exception("Error: CourseOfferingEditWrapper in this page is null");
//        }
//    }

    @Override
    protected List<FormatInfo> getFormats(ViewModel model) {
        MaintenanceForm form = (MaintenanceForm)model;
        CourseOfferingEditWrapper coEditWrapper = (CourseOfferingEditWrapper)form.getDocument().getNewMaintainableObject().getDataObject();

        if(coEditWrapper.getCourse() != null){
            return coEditWrapper.getCourse().getFormats();
        }   else {
            return null;
        }

    }

    @Override
    protected List<String> getExistingFormatIdsFromFormatOfferings(ViewModel model) throws Exception {
        MaintenanceForm form = (MaintenanceForm)model;
        CourseOfferingEditWrapper coEditWrapper = (CourseOfferingEditWrapper)form.getDocument().getNewMaintainableObject().getDataObject();
        List<String> availableFormatTypes = new ArrayList<String>();
        List <FormatOfferingInfo> formatOfferingList= coEditWrapper.getFormatOfferingList();
        for (FormatOfferingInfo formatOfferingInfo : formatOfferingList){
            availableFormatTypes.add(formatOfferingInfo.getFormatId());
        }
        return availableFormatTypes;
    }


}

