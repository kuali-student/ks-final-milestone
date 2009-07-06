package org.kuali.student.lum.lu.ui.course.client.configuration;

import org.kuali.student.common.ui.client.configurable.ConfigurableLayout;
import org.kuali.student.common.ui.client.configurable.LayoutConfigurationException;
import org.kuali.student.lum.lu.ui.course.client.configuration.typemanager.CreditCourseLayoutManager;
import org.kuali.student.lum.lu.ui.course.client.service.CluProposal;

public class LULayoutFactory {

    public ConfigurableLayout<CluProposal> getLayout(String type, String state) {

        if (type.equalsIgnoreCase(LUConstants.LU_TYPE_CREDIT_COURSE)) {
            return getCreditCourseLayout(type, state);
        }
//      else if (type.equalsIgnoreCase(LUConstants.LU_TYPE_NON_CREDIT_COURSE) {
//      return getNonCreditCourseLayout(type, state);
//      }  etc....
        else {
            throw new LayoutConfigurationException("Invalid type: " + type);            
        }

    }


    /**
     * This method returns a page layout configured for creation or updating of particular state of 
     * a credit course LU
     * 
     * Current valid states listed here :- 
     *    https://test.kuali.org/confluence/display/KULSTU/LuConfig.Constraints.LuTypeLuState
     * 
     * 
     * @param type
     * @param state
     * @return
     */
    private ConfigurableLayout<CluProposal> getCreditCourseLayout(String type, String state) {

        CreditCourseLayoutManager manager = new CreditCourseLayoutManager();

        return manager.getCreateUpdateLayout(type, state);
    }

}



