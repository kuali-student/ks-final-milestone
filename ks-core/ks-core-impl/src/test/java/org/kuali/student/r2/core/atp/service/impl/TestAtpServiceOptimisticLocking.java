/*
 * Copyright 2013 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.kuali.student.r2.core.atp.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * Verifies that optimistic locking is working as it should for updating Atp's
 * 
 * @author Kuali Student Team 
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:atp-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = false)
public class TestAtpServiceOptimisticLocking {
    private static final Logger log = LoggerFactory
            .getLogger(TestAtpServiceOptimisticLocking.class);

    @Resource(name="AtpService")
    private AtpService atpService;

    private ContextInfo callContext;
    
    /**
     * 
     */
    public TestAtpServiceOptimisticLocking() {
        this.callContext = new ContextInfo();
        
        this.callContext.setAuthenticatedPrincipalId("admin1");
        this.callContext.setPrincipalId("admin1");
        this.callContext.setCurrentDate(new Date());
        
    }
    
    private AtpInfo createAtp(String code, String name) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        AtpInfo origA = new AtpInfo();
        origA.setCode(code);
        origA.setName(name);
        origA.setTypeKey(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY);
        origA.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        origA.setStartDate(new Date());
        origA.setEndDate(new Date(new Date().getTime() + 1000));
        origA.setDescr(new RichTextHelper().fromPlain("test description"));
        AttributeInfo attr = new AttributeInfo();
        attr.setKey("attribute.key");
        attr.setValue("attribute value");
        origA.getAttributes().add(attr);
        
        return origA;
    }
    
 
    
    @Test
    public void checkForOptomisticLockingException () throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        
        AtpInfo original = createAtp("A123", "A123");
        
        original = atpService.createAtp(original.getTypeKey(), original, callContext);
        
        AtpInfo copy = new AtpInfo (original);
    
        
        original.setCode("BBB");
        
        AtpInfo updated = null;
        
        try {
           
            updated = atpService.updateAtp(original.getId(), original, callContext);
        } catch (DoesNotExistException e) {
            log.error ("Exception = ", e);
        } catch (VersionMismatchException e) {
            log.error ("Exception = ", e);
        }
        

        boolean versionMissmatchException = false;
        
        try {
            atpService.updateAtp(copy.getId(), copy, callContext);
        } catch (DoesNotExistException e) {
            log.error ("Exception = ", e);
        } catch (VersionMismatchException e) {
            versionMissmatchException = true;
        }
        
        Assert.assertTrue(versionMissmatchException);
        
        AtpInfo newAtp = createAtp("F1234", "F1234");
        
        newAtp.setId("aFakeId");
        
        boolean updateFailed = false;
        try {
            atpService.updateAtp(newAtp.getId(), newAtp, callContext);
        } catch (DoesNotExistException e) {
            updateFailed = true;
        } catch (VersionMismatchException e) {
            log.error ("Exception = ", e);
        }
        
        Assert.assertTrue(updateFailed);
    }
}
