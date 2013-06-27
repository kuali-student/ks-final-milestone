package org.kuali.student.r2.common.assembler;

import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.infc.HasAttributes;
import org.kuali.student.r2.common.util.date.DateFormatters;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Utility class containing utility methods to aide in DTO & Entity transformations
 */
public class TransformUtility {


    /**
     *
     * This merges DTO attributes into Entity attributes and returns the orphaned attributes to delete
     *
     * @param attributeClass The attribute entity class for the attributes on the entity owner
     * @param dto The dto to copy attributes from
     * @param owner The owner entity of the attributes.
     *
     * @return The orphaned attributes to delete.
     */
    public static <A extends BaseAttributeEntity<O>, O extends AttributeOwner<A>> List<Object>
    mergeToEntityAttributes(Class<A> attributeClass, HasAttributes dto, O owner) {


        // Existing Attributes
        Map<String, A> existingAttributes = new HashMap<String, A>();
        Map<String, Attribute> dtoAttributes = new HashMap<String, Attribute>();

        // Find all the old attributes and add to existing attributes map
        if(owner.getAttributes()!=null){
            for (A attribute : owner.getAttributes()) {
                existingAttributes.put(attribute.getKey(), attribute);
            }
        }

        for (Attribute attribute : dto.getAttributes()) {
            dtoAttributes.put(attribute.getKey(), attribute);
        }

        //Clear out the attributes
        Set<A> attributes = new HashSet<A>();

        //Update anything that exists, or create a new attribute if it doesn't
        for (Map.Entry<String, Attribute> entry : dtoAttributes.entrySet()) {
            Attribute attributeInfo = entry.getValue();
            A attribute;
            if (existingAttributes.containsKey(attributeInfo.getKey())) {
                attribute = existingAttributes.remove(attributeInfo.getKey());
            }else{
                try{
                    attribute = attributeClass.newInstance();
                }catch(Exception e){
                    throw new RuntimeException("Error copying attributes.",e);
                }

                attribute.setOwner(owner);
                attribute.setKey(attributeInfo.getKey());
            }
            attribute.setValue(attributeInfo.getValue());
            attributes.add(attribute);
        }
        owner.setAttributes(attributes);

        //Remove the orphaned attributes
        List<Object> orphansToDelete = new ArrayList<Object>();
        orphansToDelete.addAll(existingAttributes.values());

        return orphansToDelete;
    }


    /**
     * Converts attributes from an entity to list of AttributeInfo objects for a DTO
     *
     * @param owner The entity containing the attributes
     * @return list of attributeInfo object
     */
    public static <A extends BaseAttributeEntity<O>, O extends AttributeOwner<A>> List<AttributeInfo> toAttributeInfoList(AttributeOwner<A> owner){
        List<AttributeInfo> attributes = new ArrayList<AttributeInfo>();
        if (null != owner.getAttributes()){
            for (A attr : owner.getAttributes()) {
                AttributeInfo attrInfo = attr.toDto();
                attributes.add(attrInfo);
            }
        }

        return attributes;
    }
    // ======================================================================================================
    // Pick a format that is human readable.  Year-month-day is kinda neutral way to represent a date
    // so that it isn't the US month-day-year, nor other places day-month-year.  The advantage of using this over
    // a more agnostic UTC representation (i.e., milliseconds since Jan 1, 1970) is that
    // Used in the two methods below for KSDateFormatter
    public static final String DYNAMIC_ATTRIBUTE_DATE_FORMAT = "yyyy MMM d HH:mm:ss zzz";

    /**
     * dateTime refers to a date where hours/minutes/seconds are important in addition to the month day year
     * This is used to convert to and from a dynamic attribute which requires a string format.
     * @param date A date object to convert
     * @return The string version to save it as a dynamic attribute
     */
    public static String dateTimeToDynamicAttributeString(Date date) {
        if (date == null) {
            return null;
        }

        String formattedDate = DateFormatters.DYNAMIC_ATTRIBUTE_DATE_FORMATTER.format(date);
        return formattedDate;
    }

    /**
     * Takes a dynamic attribute representing
     * @param formattedDateStr
     * @return
     * @throws ParseException
     */
    public static Date dynamicAttributeStringToDateTime(String formattedDateStr) throws ParseException {
        if (formattedDateStr == null) {
            return null;
        }

        Date date = DateFormatters.DYNAMIC_ATTRIBUTE_DATE_FORMATTER.parse(formattedDateStr);
        return date;
    }
    // ------------------------ For Dynamic Attributes ---------------------------------
    private static Map<Class, DynAttrConverter<? extends Object>> dynAttrConverterMap =
            new HashMap<Class, DynAttrConverter<? extends Object>>();

    private static DynAttrConverter<Date> dateDynAttrConverter = null;
    private static DynAttrConverter<Date> _getDateDynamicAttributeConverter() {
        if (dateDynAttrConverter == null) {
            dateDynAttrConverter = new DynAttrConverter<Date>() {
                @Override
                public String convertNativeValueToString(Date date) {
                    return dateTimeToDynamicAttributeString(date);
                }

                @Override
                public Date convertStringValueToNative(String formattedDateStr) {
                    try {
                        return dynamicAttributeStringToDateTime(formattedDateStr);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            };
        }
        return dateDynAttrConverter;
    }

    private static DynAttrConverter<Integer> intDynAttrConverter = null;
    private static DynAttrConverter<Integer> _getIntegerDynamicAttributeConverter() {
        if (intDynAttrConverter == null) {
            intDynAttrConverter = new DynAttrConverter<Integer>() {
                @Override
                public String convertNativeValueToString(Integer val) {
                    if (val == null) {
                        return null;
                    }

                    return val.toString();
                }

                @Override
                public Integer convertStringValueToNative(String intStr) {
                    if (intStr == null) {
                        return null;
                    }
                    try {
                        Integer intVal = Integer.parseInt(intStr);
                        return intVal;
                    } catch (Exception e) {
                        return null;
                    }
                }
            };
        }
        return intDynAttrConverter;
    }
    static {
        dynAttrConverterMap.put(Date.class, _getDateDynamicAttributeConverter());
        dynAttrConverterMap.put(Integer.class, _getIntegerDynamicAttributeConverter());
    }

    public static DynAttrConverter<? extends Object> getConverterByClass(Class clazz) {
        return dynAttrConverterMap.get(clazz);
    }
}

