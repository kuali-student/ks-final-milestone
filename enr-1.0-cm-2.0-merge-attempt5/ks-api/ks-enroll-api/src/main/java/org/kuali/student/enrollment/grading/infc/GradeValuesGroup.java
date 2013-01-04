package org.kuali.student.enrollment.grading.infc;

import java.util.List;

import org.kuali.student.r2.common.infc.KeyEntity;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValueRangeInfo;

public interface GradeValuesGroup extends KeyEntity {

    public List<ResultValueInfo> getResultValueInfos();

    public ResultValueRangeInfo getResultValueRange();

}
