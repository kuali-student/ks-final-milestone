package org.kuali.student.enrollment.class2.examoffering.service.batch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseOfferingServiceTestDataLoader;
import org.kuali.student.enrollment.class2.examoffering.service.impl.ExamOfferingServiceTestDataLoader;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LuServiceConstants;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.support.AbstractItemStreamItemReader;
import org.springframework.beans.factory.InitializingBean;

/**
 * Created by helium on 2014/04/25.
 */
public class CourseOfferingMockItemReader extends AbstractItemStreamItemReader<CourseOfferingInfo> implements InitializingBean {

    private static final Log logger = LogFactory.getLog(CourseOfferingMockItemReader.class);

    private String socId;
    private Iterator<String> courseOfferingIds;

    private CourseOfferingService courseOfferingService;

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public CourseOfferingInfo read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        if (courseOfferingIds.hasNext()) {
            String coId = courseOfferingIds.next();
            return courseOfferingService.getCourseOffering(coId, ContextUtils.createDefaultContextInfo());
        } else {
            return null; // end of data
        }
    }

    /**
     * No-op.
     *
     * @see org.springframework.batch.item.ItemStream#close()
     */
    @Override
    public void close() {
    }

    /**
     * No-op.
     *
     * @see org.springframework.batch.item.ItemStream#open(org.springframework.batch.item.ExecutionContext)
     */
    @Override
    public void open(ExecutionContext executionContext) {
        try {
            List<String> coIdList = new ArrayList<String>();
            coIdList.add(CourseOfferingServiceTestDataLoader.CHEM123_COURSE_OFFERING_ID);
            coIdList.add(CourseOfferingServiceTestDataLoader.ENG101_COURSE_OFFERING_ID);
            courseOfferingIds = coIdList.iterator();

        } catch (Exception e) {
            //Don't know what do to yet.
        }
    }

    public String getSocId() {
        return socId;
    }

    public void setSocId(String socId) {
        this.socId = socId;
    }

    public CourseOfferingService getCourseOfferingService() {
        return courseOfferingService;
    }

    public void setCourseOfferingService(CourseOfferingService courseOfferingService) {
        this.courseOfferingService = courseOfferingService;
    }
}
