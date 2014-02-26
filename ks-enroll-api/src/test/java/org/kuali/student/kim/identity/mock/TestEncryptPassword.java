package org.kuali.student.kim.identity.mock;

import org.junit.Test;
import static org.junit.Assert.*;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.core.api.encryption.EncryptionService;
import org.kuali.rice.core.impl.encryption.DemonstrationGradeEncryptionServiceImpl;

public class TestEncryptPassword {

    private EncryptionService service = null;

    public EncryptionService getService() throws Exception {
        if (service == null) {
            ConfigContext.init(new MockConfig());
            service = new DemonstrationGradeEncryptionServiceImpl();
        }
        return service;
    }

    public void setService(EncryptionService service) {
        this.service = service;
    }

    @Test
    public void testEncryptPassword() throws Exception {
        hashTest("admin", "0DPiKuNIrrVmD8IUCuw1hQxNqZc=");
        hashTest("testadmin1", "2fD8gwn+QpOj4ZU3VvkbQ17mOdU=");
        hash("kstone");
        hash("bharris");
        hash("criddle");
        hash("nwelch");
        hash("bmartin");
        hash("nmcdonald");
        hash("kthompson");
        hash("ahopkins");
        hash("jmanning");
        hash("epittman");
        hash("tburton");

    }

    private String hash(String clear) throws Exception {
        return getService().hash(clear) + EncryptionService.HASH_POST_PREFIX;
    }

    private String hashTest(String clear, String expectedHashed) throws Exception {
        String hashed = getService().hash(clear);
//         System.out.println ("hashed value for " + clear + "=[" + hashed + "]");
        assertEquals(hashed, expectedHashed);
        return hashed;
    }
}
