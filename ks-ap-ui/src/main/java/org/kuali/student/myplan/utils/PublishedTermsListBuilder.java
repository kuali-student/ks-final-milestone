package org.kuali.student.myplan.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.enrollment.acal.infc.Term;

/**
 * Assembles a list of published terms.
 */
public class PublishedTermsListBuilder extends KeyValuesBase {

	private static final long serialVersionUID = 5731515860737088740L;

	/**
	 * An optional suffix to add to each value in the list.
	 */
	private String suffix = "";

	/**
	 * An optional map of items to include in the list at the top or bottom.
	 * Note: The suffix doesn't get added to these items.
	 */
	private Map<String, String> additionalListItemsTop;
	private Map<String, String> additionalListItemsBottom;

	/**
	 * Build and returns the list of available terms.
	 * 
	 * @return A List of available terms as KeyValue items.
	 */
	@Override
	public List<KeyValue> getKeyValues() {

		List<KeyValue> keyValues = new ArrayList<KeyValue>();

		// Fetch the available terms from the Academic Calendar Service.
		List<Term> terms = KsapFrameworkServiceLocator.getTermHelper().getPublishedTerms();

		// Prepend and additional items to the list.
		if (additionalListItemsTop != null && additionalListItemsTop.size() > 0) {
			for (Map.Entry<String, String> entry : additionalListItemsTop
					.entrySet()) {
				keyValues.add(new ConcreteKeyValue(entry.getKey(), entry
						.getValue()));
			}
		}

		// Add term info to the list if the above service call was successful.
		if (terms != null) {
			// Add the individual term items.
			for (Term ti : terms)
				keyValues.add(new ConcreteKeyValue(ti.getId(), ti.getName()
						+ suffix));
		}

		// Append and additional items to the list.
		if (additionalListItemsBottom != null
				&& additionalListItemsBottom.size() > 0) {
			for (Map.Entry<String, String> entry : additionalListItemsBottom
					.entrySet()) {
				keyValues.add(new ConcreteKeyValue(entry.getKey(), entry
						.getValue()));
			}
		}
		return keyValues;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public Map<String, String> getAdditionalListItemsTop() {
		return additionalListItemsTop;
	}

	public void setAdditionalListItemsTop(
			Map<String, String> additionalListItems) {
		this.additionalListItemsTop = additionalListItems;
	}

	public Map<String, String> getAdditionalListItemsBottom() {
		return additionalListItemsBottom;
	}

	public void setAdditionalListItemsBottom(
			Map<String, String> additionalListItems) {
		this.additionalListItemsBottom = additionalListItems;
	}

}
