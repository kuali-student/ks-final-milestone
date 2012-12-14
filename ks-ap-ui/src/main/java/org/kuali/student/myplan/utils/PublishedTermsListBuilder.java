package org.kuali.student.myplan.utils;

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;
import org.kuali.student.r2.common.util.constants.AcademicCalendarServiceConstants;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.myplan.course.util.CourseSearchConstants;
import org.kuali.student.myplan.plan.PlanConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equalIgnoreCase;

/**
 *  Assembles a list of published terms.
 */
public class PublishedTermsListBuilder extends KeyValuesBase {

    private final Logger logger = Logger.getLogger(PublishedTermsListBuilder.class);

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

    private AcademicCalendarService academicCalendarService;

    /**
     *  Build and returns the list of available terms.
     *
     * @return A List of available terms as KeyValue items.
     */
    @Override
    public List<KeyValue> getKeyValues() {

        List<KeyValue> keyValues = new ArrayList<KeyValue>();

        //  Fetch the available terms from the Academic Calendar Service.
        List<TermInfo> termInfos = null;
        try {
            termInfos = getAcademicCalendarService().searchForTerms(QueryByCriteria.Builder.fromPredicates(equalIgnoreCase("query", PlanConstants.PUBLISHED)), CourseSearchConstants.CONTEXT_INFO);
        } catch (Exception e) {
            logger.error("Web service call failed.", e);
        }

        //  Prepend and additional items to the list.
        if (additionalListItemsTop != null && additionalListItemsTop.size() > 0) {
            for (Map.Entry<String, String> entry : additionalListItemsTop.entrySet()) {
                keyValues.add(new ConcreteKeyValue(entry.getKey(), entry.getValue()));
            }
        }

        //  Add term info to the list if the above service call was successful.
        if (termInfos != null) {
            //  Add the individual term items.
            for (TermInfo ti : termInfos) {
                keyValues.add(new ConcreteKeyValue(ti.getId(), ti.getName() + suffix));
            }
        }

        //  Append and additional items to the list.
        if (additionalListItemsBottom != null && additionalListItemsBottom.size() > 0) {
            for (Map.Entry<String, String> entry : additionalListItemsBottom.entrySet()) {
                keyValues.add(new ConcreteKeyValue(entry.getKey(), entry.getValue()));
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

    public void setAdditionalListItemsTop(Map<String, String> additionalListItems) {
        this.additionalListItemsTop = additionalListItems;
    }

    public Map<String, String> getAdditionalListItemsBottom() {
        return additionalListItemsBottom;
    }

    public void setAdditionalListItemsBottom(Map<String, String> additionalListItems) {
        this.additionalListItemsBottom = additionalListItems;
    }

    protected AcademicCalendarService getAcademicCalendarService() {
        if (this.academicCalendarService == null) {
            this.academicCalendarService = (AcademicCalendarService) GlobalResourceLoader
                    .getService(new QName(AcademicCalendarServiceConstants.NAMESPACE,
                            AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.academicCalendarService;
    }
}
