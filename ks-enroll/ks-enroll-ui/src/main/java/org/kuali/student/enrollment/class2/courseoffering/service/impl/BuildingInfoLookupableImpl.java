package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import edu.emory.mathcs.backport.java.util.Collections;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.lookup.Lookupable;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.lookup.LookupForm;
import org.kuali.student.common.uif.service.impl.KSLookupableImpl;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingManagementUtil;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r2.core.room.dto.BuildingInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Building lookup used by activity and final exam quickfinders.
 *
 * Goes with BuildingLookupAndInquiry.xml
 */
public class BuildingInfoLookupableImpl extends KSLookupableImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(BuildingInfoLookupableImpl.class);

    @Override
    public List<?> performSearch(LookupForm lookupForm, Map<String, String> searchCriteria, boolean bounded) {

        List<BuildingInfo> buildingInfos = null;

        try {
            //  Get the (potentially partial) name from the fieldValues.
            String nameString = searchCriteria.get("name");
            //  Getting all rows is quite fast so not checking for an empty query.
            //  Put some wildcards around it so that it matches anywhere in the field.
            String name = "*" + StringUtils.upperCase(nameString) + "*";

            //  Build the query and execute.
            QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
            qbcBuilder.setPredicates(PredicateFactory.like("name", name));
            QueryByCriteria criteria = qbcBuilder.build();
            buildingInfos = CourseOfferingManagementUtil.getRoomService().searchForBuildings(criteria, ContextUtils.createDefaultContextInfo());
        } catch (Exception e) {
            //  If something goes wrong just log the error and return an empty list.
            String errorTxt = "Query to RoomService failed. See the application log for additional details.";
            LOGGER.error(errorTxt, e);
            GlobalVariables.getMessageMap()
                    .putError(KRADConstants.GLOBAL_MESSAGES, RiceKeyConstants.ERROR_CUSTOM, errorTxt);
            buildingInfos = Collections.emptyList();
        }

        return buildingInfos;
    }
}
