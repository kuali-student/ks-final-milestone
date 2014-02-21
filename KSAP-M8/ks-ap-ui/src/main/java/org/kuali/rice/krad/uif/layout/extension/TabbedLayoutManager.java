package org.kuali.rice.krad.uif.layout.extension;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.uif.container.Group;
import org.kuali.rice.krad.uif.layout.StackedLayoutManager;
import org.kuali.rice.krad.uif.util.ObjectPropertyUtils;
import org.kuali.rice.krad.uif.view.View;

/**
 * kmuthu Don't forget to add comment
 * 
 * @Author kmuthu Date: 2/14/13
 */
public class TabbedLayoutManager extends StackedLayoutManager {

    /**
     * Builds the header text for the collection line
     *
     * <p>
     * Header text is built up by first the collection label, either specified
     * on the collection definition or retrieved from the dictionary. Then for
     * each summary field defined, the value from the model is retrieved and
     * added to the header.
     * </p>
     *
     * <p>
     * Note the {@link #getSummaryTitle()} field may have expressions defined, in which cause it will be copied to the
     * property expressions map to set the title for the line group (which will have the item context variable set)
     * </p>
     *
     * @param view view instance the collection belongs to, used to get the expression evaluator
     * @param line Collection line containing data
     * @param lineGroup Group instance for rendering the line and whose title should be built
     * @return header text for line
     */
    @Override
    protected String buildLineHeaderText(View view, Object line, Group lineGroup) {
        return buildLineHeaderText(line,lineGroup);
    }
	/*
	 * Builds the header text for the collection line
	 * 
	 * <p> Header text is built up by first the collection label, either
	 * specified on the collection definition or retrieved from the dictionary.
	 * Then for each summary field defined, the value from the model is
	 * retrieved and added to the header. If no summaryTitle exist, only summary
	 * field value is used as a header. </p>
	 * 
	 * <p> Note the {@link #getSummaryTitle()} field may have expressions
	 * defined, in which cause it will be copied to the property expressions map
	 * to set the title for the line group (which will have the item context
	 * variable set) </p>
	 * 
	 * @param line - Collection line containing data
	 * 
	 * @param lineGroup - Group instance for rendering the line and whose title
	 * should be built
	 * 
	 * @return String header text for line
	 */
	protected String buildLineHeaderText(Object line, Group lineGroup) {
		// check for expression on summary title
		// TODO: KSAP-34 Rice 2.3 no longer includes expressionEvaluatorService
		// if
		// (KRADServiceLocatorWeb.getExpressionEvaluatorService().containsElPlaceholder(getSummaryTitle()))
		// {
		// lineGroup.getPropertyExpressions().put("title", getSummaryTitle());
		// return null;
		// }

		// build up line summary from declared field values and fixed title
		String summaryFieldString = "";
		for (String summaryField : getSummaryFields()) {
			Object summaryFieldValue = ObjectPropertyUtils.getPropertyValue(
					line, summaryField);
			if (StringUtils.isNotBlank(summaryFieldString)) {
				summaryFieldString += " - ";
			}

			if (summaryFieldValue != null) {
				summaryFieldString += summaryFieldValue;
			} else {
				summaryFieldString += "Null";
			}
		}

		String headerText = getSummaryTitle();
		if (StringUtils.isNotBlank(headerText)
				&& StringUtils.isNotBlank(summaryFieldString)) {
			headerText += " ( " + summaryFieldString + " )";
		} else if (StringUtils.isNotBlank(summaryFieldString)) {
			headerText = summaryFieldString;
		}

		return headerText;
	}
}
