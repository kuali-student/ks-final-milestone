package org.kuali.student.ap.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.PlanConstants;
import org.kuali.student.ap.framework.util.KsapHelperUtil;
import org.kuali.student.ap.framework.util.KsapConstants;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.infc.Term;
import org.kuali.student.r2.core.constants.AtpServiceConstants;

import javax.xml.namespace.QName;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;
import static org.kuali.rice.core.api.criteria.PredicateFactory.greaterThan;
import static org.kuali.rice.core.api.criteria.PredicateFactory.or;


/**
 * Assembles a list of current and future terms with the associated SOC state as "kuali.soc.state.published"
 */
public class PublishedTermsListBuilder extends KeyValuesBase {

	private static final long serialVersionUID = 5731515860737088740L;
    private final static Logger LOG = Logger.getLogger(PublishedTermsListBuilder.class);

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
        
		// Prepend and additional items to the list.
		if (additionalListItemsTop != null && additionalListItemsTop.size() > 0) {
			for (Map.Entry<String, String> entry : additionalListItemsTop
					.entrySet()) {
				keyValues.add(new ConcreteKeyValue(entry.getKey(), entry
						.getValue()));
			}
		}
        // Add the current term        
        List<Term> currentTerms = KsapFrameworkServiceLocator.getTermHelper().getCurrentTermsWithPublishedSOC();
        if (currentTerms.size() == 0){
            LOG.info("There is no current term.");
        }
        else if(currentTerms.size() >0 ){
            if (currentTerms.size()>1) {
                // Log an info message
                LOG.info("There is more than one current term.");
            }
            for (Term term : currentTerms) {
                keyValues.add(new ConcreteKeyValue(term.getId(), currentTerms.get(0).getName()
                    + suffix));
            }
        }

        // Fetch the future terms with published SOC state .
        List<Term> futureTerms = KsapFrameworkServiceLocator.getTermHelper().getFutureTermsWithPublishedSOC();
		// Add term info to the list if the above service call was successful.
		if (futureTerms.size() >0) {
            futureTerms=KsapFrameworkServiceLocator.getTermHelper().sortTermsByStartDate(futureTerms, true);
			// Add the individual term items.
			for (Term term : futureTerms)
				keyValues.add(new ConcreteKeyValue(term.getId(), term.getName()
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
