/*
 * Copyright 2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Lic+ense is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.class2.bundledoffering.service.impl;


import org.kuali.student.common.test.mock.data.AbstractMockServicesAwareDataLoader;
import org.kuali.student.enrollment.bundledoffering.dto.BundledOfferingInfo;
import org.kuali.student.enrollment.bundledoffering.infc.BundledOffering;
import org.kuali.student.enrollment.bundledoffering.service.BundledOfferingService;
import org.kuali.student.enrollment.constants.BundledOfferingServiceConstants;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.util.RichTextHelper;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class BundledOfferingDataLoader extends AbstractMockServicesAwareDataLoader {

    public static final String BUNDLED_OFFERING_ACTIVE_STATE_KEY = "kuali.bundled.offering.state.bundled.offering.active";
    public static final String BUNDLED_OFFERING_INACTIVE_STATE_KEY = "kuali.bundled.offering.state.bundled.offering.inactive";
    public static final String BUNDLED_OFFERING_TYPE_KEY = "kuali.bundled.offering.type.bundled.offering";

    @Resource
    private BundledOfferingService bundledOfferingService;


    @Override
    protected void initializeData() throws Exception {
        createBundledOfferings();
    }


    public void createBundledOfferings() throws ParseException, DoesNotExistException, PermissionDeniedException,
            OperationFailedException, InvalidParameterException, ReadOnlyException,
            MissingParameterException, DataValidationErrorException {
        for (int i = 0; i < 10; i++) {
            BundledOfferingInfo bundledOffering = new BundledOfferingInfo();
            bundledOffering.setStateKey(BUNDLED_OFFERING_ACTIVE_STATE_KEY);
            bundledOffering.setTypeKey(BUNDLED_OFFERING_TYPE_KEY);
            List<String> ids = new ArrayList<String>();
            for(int j = 0; j <= i; j++) {
                ids.add(String.valueOf(j));
            }
            bundledOffering.setAdminOrgIds(ids);
            bundledOffering.setBundledOfferingCode("BO_CODE" + i);
            bundledOffering.setCourseBundleId("COURSE_BUNDLE" + i);
            bundledOffering.setFormatOfferingIds(ids);
            bundledOffering.setRegistrationGroupIds(ids);
            bundledOffering.setBundledOfferingCodeSuffix("CODE_SUFFIX" + i);
            bundledOffering.setName("NAME" + i);
            bundledOffering.setDescr(RichTextHelper.buildRichTextInfo("PLAIN_DESCR" + i, "FORMATTED_DESCR" + i));
            bundledOffering.setSubjectAreaOrgId("SUBJECT_AREA_ORG_ID" + i);
            bundledOffering.setTermId(String.valueOf(i));

            bundledOfferingService.createBundledOffering(bundledOffering.getCourseBundleId(), bundledOffering.getTermId(), bundledOffering.getTypeKey(), bundledOffering, context);
        }
    }
}
