package org.kuali.student.ap.plannerreview.util;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.ap.plannerreview.infc.ConversationAdvisor;
import org.kuali.student.ap.plannerreview.dto.ConversationAdvisorInfo;
import org.kuali.student.ap.plannerreview.form.ConversationCreateForm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Builds the option list for the control to display available advisors for a student
 * @see ConversationCreateForm#getAdvisorMap()
 * @author Chris Maurer <chmaurer@iupui.edu>
 *
 */
public class AvailableAdvisorBuilder extends UifKeyValuesFinderBase {

	private static final long serialVersionUID = 6304950866647571324L;

	@Override
	public List<KeyValue> getKeyValues(ViewModel model) {
		List<ConcreteKeyValue> advisorList = new ArrayList<ConcreteKeyValue>();
		if (model instanceof ConversationCreateForm) {
			Map<String, ConversationAdvisorInfo> advisorMap = ((ConversationCreateForm) model).getAdvisorMap();
			for (Entry<String, ConversationAdvisorInfo> entry : advisorMap.entrySet()) {
				ConversationAdvisor advisor = entry.getValue();
				advisorList.add(new ConcreteKeyValue(entry.getKey(), format(advisor.getName(), advisor.getAdvisorRoleName())));
			}
		}

		Collections.sort(advisorList);
		
		return new ArrayList<KeyValue>(advisorList);
	}
	
	/**
	 * Apply some additional styling to the value of the select option
	 * @param input1
	 * @param input2
	 * @return
	 */
	public String format(String input1, String input2) {
		String formattedOutput = input1 + "[css='uif-constraintMessage advisorListDetail']" + input2 + "[/css]";
		
		return formattedOutput;
	}

}
