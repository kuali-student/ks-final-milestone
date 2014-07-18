package org.kuali.student.enrollment.class2.courseoffering.service.decorators;

import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.class1.search.ActivityOfferingSearchServiceImpl;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.core.search.service.SearchService;

/**
 * This class has overwritten some methods to be more performant. These
 * overwrites go directly against the database so do not use this decorator if you have a
 * different database backing the services.
 */
public class CourseOfferingServicePerformanceDecorator extends CourseOfferingServiceDecorator {

    SearchService searchService;

    /**
     * This performance method, on average is 130 times faster than the regular getRegistrationGroup method
     *
     * @param registrationGroupId
     * @param context
     * @return
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    @Override
    public RegistrationGroupInfo getRegistrationGroup(String registrationGroupId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        SearchRequestInfo sr = new SearchRequestInfo(ActivityOfferingSearchServiceImpl.REG_GROUPS_BY_RG_ID_SEARCH_KEY);
        sr.addParam(ActivityOfferingSearchServiceImpl.SearchParameters.RG_ID, registrationGroupId);
        SearchResultInfo searchResult = getSearchService().search(sr, context);

        RegistrationGroupInfo rgInfo;

        if(searchResult == null){
            throw new DoesNotExistException("Registration Group: [" + registrationGroupId + "] does not exist.");
        } else {
            rgInfo = new RegistrationGroupInfo();
            rgInfo.setDescr(new RichTextInfo());
            rgInfo.setMeta(new MetaInfo());
        }

        for (SearchResultRowInfo row : searchResult.getRows()) {
            for (SearchResultCellInfo cell : row.getCells()) {
                // Base lui info
                if (ActivityOfferingSearchServiceImpl.SearchResultColumns.RG_ID.equals(cell.getKey())) {
                    rgInfo.setId(cell.getValue());
                }else if (ActivityOfferingSearchServiceImpl.SearchResultColumns.RG_NAME.equals(cell.getKey())) {
                    rgInfo.setName(cell.getValue());
                    rgInfo.setRegistrationCode(cell.getValue());
                }else if (ActivityOfferingSearchServiceImpl.SearchResultColumns.RG_STATE.equals(cell.getKey())) {
                    rgInfo.setStateKey(cell.getValue());
                } else if (ActivityOfferingSearchServiceImpl.SearchResultColumns.RG_TYPE.equals(cell.getKey())) {
                    rgInfo.setTypeKey(cell.getValue());
                } else if (ActivityOfferingSearchServiceImpl.SearchResultColumns.RG_DESC_PLAIN.equals(cell.getKey())) {
                    rgInfo.getDescr().setPlain(cell.getValue());
                } else if (ActivityOfferingSearchServiceImpl.SearchResultColumns.RG_DESC_FORMATTED.equals(cell.getKey())) {
                    rgInfo.getDescr().setFormatted(cell.getValue());
                }

                // base meta info
                else if (ActivityOfferingSearchServiceImpl.SearchResultColumns.RG_VERSION.equals(cell.getKey())) {
                    rgInfo.getMeta().setVersionInd(cell.getValue());
                } else if (ActivityOfferingSearchServiceImpl.SearchResultColumns.RG_CREATE_TIME.equals(cell.getKey())) {
                    rgInfo.getMeta().setCreateTime(DateFormatters.DEFAULT_YEAR_MONTH_24HOUR_MILLISECONDS_FORMATTER.parse(cell.getValue()));
                }else if (ActivityOfferingSearchServiceImpl.SearchResultColumns.RG_CREATE_ID.equals(cell.getKey())) {
                    rgInfo.getMeta().setCreateId(cell.getValue());
                }else if (ActivityOfferingSearchServiceImpl.SearchResultColumns.RG_UPDATE_TIME.equals(cell.getKey())) {
                    rgInfo.getMeta().setUpdateTime(DateFormatters.DEFAULT_YEAR_MONTH_24HOUR_MILLISECONDS_FORMATTER.parse(cell.getValue()));
                }else if (ActivityOfferingSearchServiceImpl.SearchResultColumns.RG_UPDATE_ID.equals(cell.getKey())) {
                    rgInfo.getMeta().setUpdateId(cell.getValue());
                }

                // rg info
                else if (ActivityOfferingSearchServiceImpl.SearchResultColumns.AO_ID.equals(cell.getKey())) {
                    rgInfo.getActivityOfferingIds().add(cell.getValue());
                }else if (ActivityOfferingSearchServiceImpl.SearchResultColumns.FO_ID.equals(cell.getKey())) {
                    rgInfo.setFormatOfferingId(cell.getValue());
                } else if (ActivityOfferingSearchServiceImpl.SearchResultColumns.CO_ID.equals(cell.getKey())) {
                    rgInfo.setCourseOfferingId(cell.getValue());
                } else if (ActivityOfferingSearchServiceImpl.SearchResultColumns.ATP_ID.equals(cell.getKey())) {
                    rgInfo.setTermId(cell.getValue());
                } else if (ActivityOfferingSearchServiceImpl.SearchResultColumns.RG_IS_GENERATED.equals(cell.getKey())) {
                    rgInfo.setIsGenerated(new Boolean(cell.getValue()));
                } else if (ActivityOfferingSearchServiceImpl.SearchResultColumns.AO_CLUSTER_ID.equals(cell.getKey())) {
                    rgInfo.setActivityOfferingClusterId(cell.getValue());
                }
            }
        }

        return rgInfo;
    }

    public SearchService getSearchService() {
        return searchService;
    }

    public void setSearchService(SearchService searchService) {
        this.searchService = searchService;
    }

}
