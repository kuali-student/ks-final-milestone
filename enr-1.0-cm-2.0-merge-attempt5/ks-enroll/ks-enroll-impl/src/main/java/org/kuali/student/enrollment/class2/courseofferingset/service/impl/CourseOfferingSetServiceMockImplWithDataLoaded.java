/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.class2.courseofferingset.service.impl;

import java.util.*;
import org.kuali.student.enrollment.class2.courseofferingset.service.decorators.CourseOfferingSetServiceDecorator;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultItemInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;

public class CourseOfferingSetServiceMockImplWithDataLoaded extends CourseOfferingSetServiceMockImpl {

    
    public CourseOfferingSetServiceMockImplWithDataLoaded() {
        super();
        Integer year = 2001;
        for (int i = 0; i < 10; i++) {
            SocRolloverResultInfo result = new SocRolloverResultInfo();
            result.setMessage(new RichTextHelper().toRichTextInfo("message plain " + i, "message formatted " + i));
            result.setTypeKey(CourseOfferingSetServiceConstants.ROLLOVER_RESULT_TYPE_KEY);
            result.setStateKey(CourseOfferingSetServiceConstants.SUBMITTED_RESULT_STATE_KEY);
            result.setItemsExpected(5);
            result.setItemsProcessed(2);
            result.setCourseOfferingsSkipped(3);
            result.setSourceSocId("sourceSocId" + i);
            result.setTargetSocId("targetSocId" + i);
            result.setSourceTermId("Fall " + year);
            result.setTargetTermId("Fall " + ++year);
            result.getOptionKeys().add("my first option");
            result.getOptionKeys().add("my 2nd option");
            Exception ex = null;
            try {
                result = createSocRolloverResult(result.getTypeKey(), result, new ContextInfo());
                List<SocRolloverResultItemInfo> socRolloverResultItemInfos = new ArrayList<SocRolloverResultItemInfo>();
                SocRolloverResultItemInfo socRolloverResultItemInfo1 = new SocRolloverResultItemInfo();
                socRolloverResultItemInfo1.setSocRolloverResultId(result.getSourceSocId() + result.getTargetSocId());
                socRolloverResultItemInfo1.setMessage(new RichTextHelper().toRichTextInfo("Cancelled;Cancelled during source term", "Cancelled;Cancelled during source term"));
                socRolloverResultItemInfo1.setSourceCourseOfferingId("ENG428");
                socRolloverResultItemInfo1.setTargetCourseOfferingId("ENG428");
                socRolloverResultItemInfo1.setTypeKey(CourseOfferingSetServiceConstants.ROLLOVER_RESULT_TYPE_KEY);
                socRolloverResultItemInfos.add(socRolloverResultItemInfo1);
                SocRolloverResultItemInfo socRolloverResultItemInfo2 = new SocRolloverResultItemInfo();
                socRolloverResultItemInfo2.setSocRolloverResultId(result.getSourceSocId() + result.getTargetSocId());
                socRolloverResultItemInfo2.setMessage(new RichTextHelper().toRichTextInfo("New version;New version of the course exists", "New version;New version of the course exists"));
                socRolloverResultItemInfo2.setSourceCourseOfferingId("ENG364");
                socRolloverResultItemInfo2.setTargetCourseOfferingId("ENG364");
                socRolloverResultItemInfo2.setTypeKey(CourseOfferingSetServiceConstants.ROLLOVER_RESULT_TYPE_KEY);
                socRolloverResultItemInfos.add(socRolloverResultItemInfo2);
                SocRolloverResultItemInfo socRolloverResultItemInfo3 = new SocRolloverResultItemInfo();
                socRolloverResultItemInfo3.setSocRolloverResultId(result.getSourceSocId() + result.getTargetSocId());
                socRolloverResultItemInfo3.setMessage(new RichTextHelper().toRichTextInfo("Retired;No longer offered,as of January 1, 2012", "Retired;No longer offered,as of January 1, 2012"));
                socRolloverResultItemInfo3.setSourceCourseOfferingId("MATH140");
                socRolloverResultItemInfo3.setTargetCourseOfferingId("MATH140");
                socRolloverResultItemInfo3.setTypeKey(CourseOfferingSetServiceConstants.ROLLOVER_RESULT_TYPE_KEY);
                socRolloverResultItemInfos.add(socRolloverResultItemInfo3);
                SocRolloverResultItemInfo socRolloverResultItemInfo4 = new SocRolloverResultItemInfo();
                socRolloverResultItemInfo4.setSocRolloverResultId(result.getSourceSocId() + result.getTargetSocId());
                socRolloverResultItemInfo4.setMessage(new RichTextHelper().toRichTextInfo("Cancelled;Cancelled during source term", "Cancelled;Cancelled during source term"));
                socRolloverResultItemInfo4.setSourceCourseOfferingId("MATH140");
                socRolloverResultItemInfo4.setTargetCourseOfferingId("MATH140");
                socRolloverResultItemInfo4.setTypeKey(CourseOfferingSetServiceConstants.ROLLOVER_RESULT_TYPE_KEY);
                socRolloverResultItemInfos.add(socRolloverResultItemInfo4);
                createSocRolloverResultItems(result.getSourceSocId() + result.getTargetSocId(), CourseOfferingSetServiceConstants.ROLLOVER_RESULT_TYPE_KEY, socRolloverResultItemInfos, new ContextInfo());
            } catch (InvalidParameterException e) {
                ex = e;
            } catch (DoesNotExistException e) {
                ex = e;
            } catch (OperationFailedException e) {
                ex = e;
            } catch (ReadOnlyException e) {
                ex = e;
            } catch (PermissionDeniedException e) {
                ex = e;
            } catch (DataValidationErrorException e) {
                ex = e;
            } catch (MissingParameterException e) {
                ex = e;
            }
            if (ex != null) {
                throw new RuntimeException(ex);
            }
        }
    }
}
