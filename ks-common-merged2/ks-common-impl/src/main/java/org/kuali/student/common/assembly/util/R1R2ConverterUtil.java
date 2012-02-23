package org.kuali.student.common.assembly.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

public class R1R2ConverterUtil {

    private final static Logger LOG = Logger.getLogger(R1R2ConverterUtil.class);

    public static <T, X> X convert(T source, X target, String[] ignoreProperties) {
        try {
            BeanUtils.copyProperties(source, target, ignoreProperties);
        } catch (Exception e) {
            LOG.error("Failed to convert " + source.getClass() + " to " + target.getClass());
        }
        return target;
    }

    @SuppressWarnings("unchecked")
    public static <T, X> List<X> convertLists(List<T> sourceList, Class<X> targetListType, String[] ignoreProperties) {
        List<X> targetList = new ArrayList<X>();
        for (T sourceObject : sourceList) {
            X targetObject = null;
            try {
                targetObject = (X) ConstructorUtils.invokeConstructor(targetListType.getClass(), null);
            } catch (Exception e) {
                LOG.error("Failed to invoke a default constructor for " + targetListType);
                LOG.error("Conversion will not continue further.");
                break;
            }
            targetObject = convert(sourceObject, targetObject, ignoreProperties);
            targetList.add(targetObject);

        }
        return targetList;
    }
}
