package org.kuali.student.r2.common.assembler;

/**
 * Created with IntelliJ IDEA.
 * User: Charles
 * Date: 7/9/12
 * Time: 1:30 PM
 * To change this template use File | Settings | File Templates.
 */
public interface DynAttrConverter<NativeType> {
    // Convert to a string
    public String convertNativeValueToString(NativeType nativeType);

    // Convert from a string
    public NativeType convertStringValueToNative(String s);
}
