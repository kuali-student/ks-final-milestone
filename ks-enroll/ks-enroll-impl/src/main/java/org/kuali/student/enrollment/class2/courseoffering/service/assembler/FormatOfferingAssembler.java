package org.kuali.student.enrollment.class2.courseoffering.service.assembler;

import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.r2.common.dto.ContextInfo;


import org.kuali.student.enrollment.lui.dto.LuiInfo;



import java.util.ArrayList;
import java.util.List;

public class FormatOfferingAssembler{

    public static FormatOfferingInfo assemble(LuiInfo baseDTO)
        {
            FormatOfferingInfo  formatOfferingInfo = new FormatOfferingInfo();
            formatOfferingInfo.setActivityOfferingTypeKeys(baseDTO.getRelatedLuiTypes());
            formatOfferingInfo.setId(baseDTO.getId());
            return formatOfferingInfo;
        }


    public static LuiInfo disassemble(FormatOfferingInfo businessDTO)
        {
            return new LuiInfo();  //To change body of implemented methods use File | Settings | File Templates.
        }
}