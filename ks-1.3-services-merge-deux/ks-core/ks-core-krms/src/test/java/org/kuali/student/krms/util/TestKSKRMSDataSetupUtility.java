package org.kuali.student.krms.util;

import org.junit.Ignore;
import org.junit.Test;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.student.krms.KSKRMSTestCase;

/**
 * Created by IntelliJ IDEA.
 * User: NWUuser
 * Date: 2012/07/05
 * Time: 11:51 AM
 * To change this template use File | Settings | File Templates.
 */
@Ignore
public class TestKSKRMSDataSetupUtility extends KSKRMSTestCase {

    @Test
    public void testUtility() {
        System.out.println("test");
        KSKRMSDataSetupUtility utility = new KSKRMSDataSetupUtility();
        setupKRMSServices(utility);
        utility.createKSKRMSData();
    }

    private void setupKRMSServices(KSKRMSDataSetupUtility utility) {
        utility.setAgendaBoService(agendaBoService);
        utility.setContextRepository(contextRepository);
        utility.setKrmsTypeRepository(krmsTypeRepository);
        utility.setRuleBoService(ruleBoService);
        utility.setTermBoService(termBoService);
        utility.setBoService(KRADServiceLocator.getBusinessObjectService());
    }

}
