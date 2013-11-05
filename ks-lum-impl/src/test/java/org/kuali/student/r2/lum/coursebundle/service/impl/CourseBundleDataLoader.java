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
package org.kuali.student.r2.lum.coursebundle.service.impl;


import org.kuali.student.common.test.mock.data.AbstractMockServicesAwareDataLoader;
import org.kuali.student.lum.coursebundle.dto.CourseBundleInfo;
import org.kuali.student.lum.coursebundle.service.CourseBundleService;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CourseBundleDataLoader extends AbstractMockServicesAwareDataLoader {

    public static final String COURSE_BUNDLE_ACTIVE_STATE_KEY = "kuali.course.bundle.state.course.bundle.active";
    public static final String COURSE_BUNDLE_INACTIVE_STATE_KEY = "kuali.course.bundle.state.course.bundle.inactive";
    public static final String COURSE_BUNDLE_TYPE_KEY = "kuali.course.bundle.type.course.bundle";

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

    @Resource
    private CourseBundleService courseBundleService;


    @Override
    protected void initializeData() throws Exception {
        createCourseBundles();
    }


    public void createCourseBundles() throws ParseException, DoesNotExistException, PermissionDeniedException,
            OperationFailedException, InvalidParameterException, ReadOnlyException,
            MissingParameterException, DataValidationErrorException {
        for (int i = 0; i < 10; i++) {
            CourseBundleInfo courseBundleInfo = new CourseBundleInfo();
            courseBundleInfo.setStateKey(COURSE_BUNDLE_ACTIVE_STATE_KEY);
            courseBundleInfo.setTypeKey(COURSE_BUNDLE_TYPE_KEY);
            List<String> ids = new ArrayList<String>();
            for(int j = 0; j <= i; j++) {
                ids.add(String.valueOf(j));
            }
            courseBundleInfo.setAdminOrgIds(ids);
            courseBundleInfo.setName("NAME" + i);
            courseBundleInfo.setDescr(RichTextHelper.buildRichTextInfo("PLAIN_DESCR" + i, "FORMATTED_DESCR" + i));
            courseBundleInfo.setSubjectAreaOrgId("SUBJECT_AREA_ORG_ID" + i);
            courseBundleInfo.setCourseBundleCode("CB_CODE" + i);
            courseBundleInfo.setCourseBundleCodeSuffix("CODE_SUFFIX" + i);
            courseBundleInfo.setCourseIds(ids);
            courseBundleInfo.setEndTermId(String.valueOf(i + 1));
            courseBundleInfo.setStartTermId(String.valueOf(i));
            courseBundleInfo.setEffectiveDate(dateFormat.parse("20130611"));
            courseBundleInfo.setExpirationDate(dateFormat.parse("20500101"));

            courseBundleService.createCourseBundle(courseBundleInfo.getTypeKey(), courseBundleInfo, context);
        }
    }
}
