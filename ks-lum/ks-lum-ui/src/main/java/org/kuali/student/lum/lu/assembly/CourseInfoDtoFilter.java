package org.kuali.student.lum.lu.assembly;

import org.kuali.student.r1.common.assembly.transform.*;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValueRangeInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;

import java.util.List;
import java.util.Map;

/**
 * This filter is used to add and process proposal info to data object.
 * 
 * @author SW Genis
 *
 */
public class CourseInfoDtoFilter extends AbstractDTOFilter {

    /**
     * This can be used to apply filters in the inbound dto object.
     *
     * @param dto The inbound dto object
     *  @param properties The metadata for the inbound dto object.
     */
    public void applyInboundDtoFilter(Object dto, Map<String, Object> properties) throws Exception{
        CourseInfo courseInfo = (CourseInfo) dto;
        List<ResultValuesGroupInfo> creditOptions = courseInfo.getCreditOptions();
        for (ResultValuesGroupInfo creditOption : creditOptions){
            if (("kuali.result.values.group.type.fixed".equals(creditOption.getTypeKey()))||
                    ("kuali.result.values.group.type.range".equals(creditOption.getTypeKey()))){
                if (creditOption.getResultValueRange() == null){
                    creditOption.setResultValueRange(new ResultValueRangeInfo());
                }
            }
        }
    }

    @Override
    public Class<?> getType() {
        return CourseInfo.class;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
