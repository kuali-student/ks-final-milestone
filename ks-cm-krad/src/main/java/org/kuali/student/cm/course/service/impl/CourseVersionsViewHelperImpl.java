package org.kuali.student.cm.course.service.impl;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.uif.service.impl.ViewHelperServiceImpl;
import org.kuali.student.cm.course.form.wrapper.VersionWrapper;
import org.kuali.student.cm.course.service.CourseVersionsViewHelper;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.core.search.dto.SortDirection;
import org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;
import org.kuali.student.r2.lum.util.constants.CourseServiceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.kuali.student.r2.lum.util.constants.CourseServiceConstants.COURSE_NAMESPACE_URI;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class implementation for the course versions view.
 */
public class CourseVersionsViewHelperImpl extends ViewHelperServiceImpl implements CourseVersionsViewHelper {

    private static final Logger LOG = LoggerFactory.getLogger(CourseVersionsViewHelperImpl.class);

    private CluService cluService;
    private CourseService courseService;
    private ContextInfo contextInfo =  ContextUtils.createDefaultContextInfo();

    public List<VersionWrapper> getVersions(String viCluId) {

        SearchRequestInfo sr = new SearchRequestInfo();
        List<SearchParamInfo> params = new ArrayList<SearchParamInfo>();
        SearchParamInfo param = new SearchParamInfo();
        param.setKey("lu.queryParam.cluVersionIndId");
        param.getValues().add(viCluId);
        params.add(param);
        sr.setSortDirection(SortDirection.DESC);
        sr.setParams(params);
        sr.setSearchKey("lu.search.clu.versions");
        sr.setSortColumn("lu.resultColumn.luOptionalVersionSeqNum");

        try {
            SearchResultInfo searchResult = getCluService().search(sr, contextInfo);
            return parseSearchResults(searchResult);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getCourseTitle(String viCluId) {
        String courseTitle = "";
        // Get id of current version of course given the version independent id
        try {
            VersionDisplayInfo curVerDisplayInfo = getCourseService().getCurrentVersion(COURSE_NAMESPACE_URI, viCluId, contextInfo);
            CourseInfo currentCourse = getCourseService().getCourse(curVerDisplayInfo.getId(), contextInfo);
            courseTitle = currentCourse.getCourseTitle();
        } catch (DoesNotExistException |
                InvalidParameterException |
                MissingParameterException |
                OperationFailedException |
                PermissionDeniedException e) {
            LOG.error("Query current course title failed.", e);
        }
        return courseTitle;
    }

    public List<VersionWrapper> parseSearchResults(SearchResultInfo searchResult) {
        List<VersionWrapper> courseVersionWrappers = new ArrayList<>();
        List<SearchResultRowInfo> rows = searchResult.getRows();
        for (SearchResultRowInfo row : rows) {
            List<SearchResultCellInfo> cells = row.getCells();
            VersionWrapper courseVersionWrapper = new VersionWrapper();
            for (SearchResultCellInfo cell : cells) {
                if (cell.getKey().equals("lu.resultColumn.cluId")) {
                    courseVersionWrapper.setCluId(cell.getValue());
                } else if (cell.getKey().equals("lu.resultColumn.luOptionalVersionSeqNum")) {
                    courseVersionWrapper.setSequence(Long.valueOf(cell.getValue()));
                } else if (cell.getKey().equals("lu.resultColumn.luOptionalState")) {
                    courseVersionWrapper.setCourseStatus(cell.getValue());
                } else if (cell.getKey().equals("lu.resultColumn.luOptionalExpFirstAtpDisplay")) {
                    courseVersionWrapper.setStartTerm(cell.getValue());
                } else if (cell.getKey().equals("lu.resultColumn.luOptionalLastAtpDisplay")) {
                    courseVersionWrapper.setEndTerm(cell.getValue());
                }
            }
            courseVersionWrappers.add(courseVersionWrapper);
        }
        return courseVersionWrappers;
    }

    protected CluService getCluService() {
        if (cluService == null) {
            cluService = GlobalResourceLoader.getService(new QName(CluServiceConstants.CLU_NAMESPACE, CluServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return cluService;
    }

    protected CourseService getCourseService() {
        if (courseService == null) {
            courseService = GlobalResourceLoader.getService(new QName(CourseServiceConstants.COURSE_NAMESPACE, CourseServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return courseService;
    }
}
