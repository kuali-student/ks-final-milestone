package org.kuali.student.ap.academicplan.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.ap.academicplan.infc.DegreeMap;
import org.kuali.student.ap.academicplan.infc.DegreeMapRequirement;

/**
 * Degree Map message structure
 *
 * @Author mguilla
 * Date: 1/21/14
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DegreeMapInfo", propOrder = {"requirements"})
public class DegreeMapInfo extends LearningPlanInfo implements DegreeMap {

	private static final long serialVersionUID = 8024618591278071960L;
	
	@XmlElement
	private List<DegreeMapRequirementInfo> requirements;

	public List<DegreeMapRequirement> getRequirements() {
		if (requirements == null) {
			return Collections.emptyList();
		} else {
			return Collections.<DegreeMapRequirement> unmodifiableList(requirements);
		}
	}

	public void setRequirements(List<DegreeMapRequirement> requirements) {
		if (requirements != null) {
			List<DegreeMapRequirementInfo> requirementInfos = new ArrayList<DegreeMapRequirementInfo>(
					requirements.size());
			for (DegreeMapRequirement requirement : requirements) {
				DegreeMapRequirementInfo requirementInfo = new DegreeMapRequirementInfo(
						requirement);
				requirementInfos.add(requirementInfo);
			}
			this.requirements = requirementInfos;
		} else {
			this.requirements = null;
		}
	}

}
