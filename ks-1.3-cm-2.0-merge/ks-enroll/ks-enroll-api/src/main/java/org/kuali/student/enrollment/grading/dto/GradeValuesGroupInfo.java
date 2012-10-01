package org.kuali.student.enrollment.grading.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.grading.infc.GradeValuesGroup;
import org.kuali.student.r2.common.dto.KeyEntityInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValueRangeInfo;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GradeValuesGroupInfo", propOrder = {"key", "typeKey", "stateKey", "name", "descr", "resultValueInfos",
        "resultValueRange", "meta", "attributes", "_futureElements"})
public class GradeValuesGroupInfo extends KeyEntityInfo implements GradeValuesGroup {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private List<ResultValueInfo> resultValueInfos;

    @XmlElement
    private ResultValueRangeInfo resultValueRange;

    @XmlAnyElement
    private List<Element> _futureElements;

    public GradeValuesGroupInfo() {

        super();
        resultValueInfos = new ArrayList<ResultValueInfo>();
        resultValueRange = null;
    }

    public GradeValuesGroupInfo(GradeValuesGroup gradeValuesGroup) {
        super(gradeValuesGroup);
        if (gradeValuesGroup != null) {
            resultValueInfos = new ArrayList<ResultValueInfo>();
            if (gradeValuesGroup.getResultValueInfos() != null) {
                for (ResultValueInfo resultValue : gradeValuesGroup.getResultValueInfos()) {
                    resultValueInfos.add(new ResultValueInfo(resultValue));
                }
            }

            resultValueRange = new ResultValueRangeInfo(resultValueRange);
        }

    }

    @Override
    public List<ResultValueInfo> getResultValueInfos() {
        return resultValueInfos;
    }

    public void setResultValueInfos(List<ResultValueInfo> resultValueInfos) {
        this.resultValueInfos = resultValueInfos;
    }

    @Override
    public ResultValueRangeInfo getResultValueRange() {
        return resultValueRange;
    }

    public void setResultValueRange(ResultValueRangeInfo resultValueRange) {
        this.resultValueRange = resultValueRange;
    }

}
