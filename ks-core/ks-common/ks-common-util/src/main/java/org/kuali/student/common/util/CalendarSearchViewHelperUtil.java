package org.kuali.student.common.util;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.acal.dto.AcademicCalendarInfo;
import org.kuali.student.r2.core.acal.dto.HolidayCalendarInfo;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.class1.type.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.core.constants.AtpSearchServiceConstants;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.constants.TypeServiceConstants;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;

import java.util.ArrayList;
import java.util.List;

public class CalendarSearchViewHelperUtil {

    public static List<TermInfo> searchForTerms(String nameParam, String yearParam, ContextInfo context, AtpService atpService, TypeService typeService) throws Exception {

        List<TypeTypeRelationInfo> typeRelations= typeService.getTypeTypeRelationsByOwnerAndType(AtpServiceConstants.ATP_TERM_GROUPING_TYPE_KEY, TypeServiceConstants.TYPE_TYPE_RELATION_GROUP_TYPE_KEY, context);
        List<String> termTypeKeys = new ArrayList<String>(typeRelations.size());
        for(TypeTypeRelationInfo typeRelation:typeRelations){
            termTypeKeys.add(typeRelation.getRelatedTypeKey());
        }

        List<TermInfo> termInfoList = new ArrayList<TermInfo>();

        SearchRequestInfo searchRequest = new SearchRequestInfo(AtpSearchServiceConstants.ATP_SEARCH_ADVANCED);
        searchRequest.addParam(AtpSearchServiceConstants.ATP_ADVANCED_QUERYPARAM_ATP_TYPE, termTypeKeys);
        if(nameParam!=null&&!nameParam.isEmpty()){
            searchRequest.addParam(AtpSearchServiceConstants.ATP_ADVANCED_QUERYPARAM_ATP_SHORT_NAME, nameParam);
        }
        if(yearParam!=null&&!yearParam.isEmpty()){
            searchRequest.addParam(AtpSearchServiceConstants.ATP_ADVANCED_QUERYPARAM_ATP_YEAR, yearParam);
        }
        SearchResultInfo searchResults = atpService.search(searchRequest, context);


        for(SearchResultRowInfo row : searchResults.getRows()){
            String id = null;
            String name = null;
            String startDate = null;
            String endDate = null;
            String stateKey = null;

            for(SearchResultCellInfo cell : row.getCells()){
                if(AtpSearchServiceConstants.ATP_RESULTCOLUMN_ATP_ID.equals(cell.getKey())){
                    id = cell.getValue();
                }else if(AtpSearchServiceConstants.ATP_RESULTCOLUMN_ATP_SHORT_NAME.equals(cell.getKey())){
                    name = cell.getValue();
                }else if(AtpSearchServiceConstants.ATP_RESULTCOLUMN_ATP_START_DATE.equals(cell.getKey())){
                    startDate = cell.getValue();
                }else if(AtpSearchServiceConstants.ATP_RESULTCOLUMN_ATP_END_DATE.equals(cell.getKey())){
                    endDate = cell.getValue();
                }else if(AtpSearchServiceConstants.ATP_RESULTCOLUMN_ATP_STATE.equals(cell.getKey())){
                    stateKey = cell.getValue();
                }
            }

            TermInfo termInfo = new TermInfo();
            termInfo.setId(id);
            termInfo.setName(name);
            termInfo.setStartDate(DateFormatters.DEFAULT_YEAR_MONTH_24HOUR_MILLISECONDS_FORMATTER.parse(startDate));
            termInfo.setEndDate(DateFormatters.DEFAULT_YEAR_MONTH_24HOUR_MILLISECONDS_FORMATTER.parse(endDate));
            termInfo.setStateKey(stateKey);

            termInfoList.add(termInfo);
        }

        return termInfoList;

    }

    public static List<AcademicCalendarInfo> searchForAcademicCalendars(String nameParam, String yearParam, ContextInfo context, AtpService atpService) throws Exception {

        List<AcademicCalendarInfo> acalInfoList = new ArrayList<AcademicCalendarInfo>();

        SearchRequestInfo searchRequest = new SearchRequestInfo(AtpSearchServiceConstants.ATP_SEARCH_ADVANCED);
        searchRequest.addParam(AtpSearchServiceConstants.ATP_ADVANCED_QUERYPARAM_ATP_TYPE, AcademicCalendarServiceConstants.ACADEMIC_CALENDAR_TYPE_KEY);
        if(nameParam!=null&&!nameParam.isEmpty()){
            searchRequest.addParam(AtpSearchServiceConstants.ATP_ADVANCED_QUERYPARAM_ATP_SHORT_NAME, nameParam);
        }
        if(yearParam!=null&&!yearParam.isEmpty()){
            searchRequest.addParam(AtpSearchServiceConstants.ATP_ADVANCED_QUERYPARAM_ATP_YEAR, yearParam);
        }
        SearchResultInfo searchResults = atpService.search(searchRequest, context);


        for(SearchResultRowInfo row : searchResults.getRows()){
            String id = null;
            String name = null;
            String startDate = null;
            String endDate = null;
            String stateKey = null;

            for(SearchResultCellInfo cell : row.getCells()){
                if(AtpSearchServiceConstants.ATP_RESULTCOLUMN_ATP_ID.equals(cell.getKey())){
                    id = cell.getValue();
                }else if(AtpSearchServiceConstants.ATP_RESULTCOLUMN_ATP_SHORT_NAME.equals(cell.getKey())){
                    name = cell.getValue();
                }else if(AtpSearchServiceConstants.ATP_RESULTCOLUMN_ATP_START_DATE.equals(cell.getKey())){
                    startDate = cell.getValue();
                }else if(AtpSearchServiceConstants.ATP_RESULTCOLUMN_ATP_END_DATE.equals(cell.getKey())){
                    endDate = cell.getValue();
                }else if(AtpSearchServiceConstants.ATP_RESULTCOLUMN_ATP_STATE.equals(cell.getKey())){
                    stateKey = cell.getValue();
                }
            }

            AcademicCalendarInfo acalInfo = new AcademicCalendarInfo();
            acalInfo.setId(id);
            acalInfo.setName(name);
            acalInfo.setStartDate(DateFormatters.DEFAULT_YEAR_MONTH_24HOUR_MILLISECONDS_FORMATTER.parse(startDate));
            acalInfo.setEndDate(DateFormatters.DEFAULT_YEAR_MONTH_24HOUR_MILLISECONDS_FORMATTER.parse(endDate));
            acalInfo.setStateKey(stateKey);

            acalInfoList.add(acalInfo);
        }

        return acalInfoList;

    }

    public static List<HolidayCalendarInfo> searchForHolidayCalendars(String nameParam, String yearParam, ContextInfo context, AtpService atpService) throws Exception {
        List<HolidayCalendarInfo> hCals = new ArrayList<HolidayCalendarInfo>();

        SearchRequestInfo searchRequest = new SearchRequestInfo(AtpSearchServiceConstants.ATP_SEARCH_ADVANCED);
        searchRequest.addParam(AtpSearchServiceConstants.ATP_ADVANCED_QUERYPARAM_ATP_TYPE, AcademicCalendarServiceConstants.HOLIDAY_CALENDAR_TYPE_KEY);
        if(nameParam!=null&&!nameParam.isEmpty()){
            searchRequest.addParam(AtpSearchServiceConstants.ATP_ADVANCED_QUERYPARAM_ATP_SHORT_NAME, nameParam);
        }
        if(yearParam!=null&&!yearParam.isEmpty()){
            searchRequest.addParam(AtpSearchServiceConstants.ATP_ADVANCED_QUERYPARAM_ATP_YEAR, yearParam);
        }
        SearchResultInfo searchResults = atpService.search(searchRequest, context);


        for(SearchResultRowInfo row : searchResults.getRows()){
            String id = null;
            String name = null;
            String startDate = null;
            String endDate = null;
            String stateKey = null;

            for(SearchResultCellInfo cell : row.getCells()){
                if(AtpSearchServiceConstants.ATP_RESULTCOLUMN_ATP_ID.equals(cell.getKey())){
                    id = cell.getValue();
                }else if(AtpSearchServiceConstants.ATP_RESULTCOLUMN_ATP_SHORT_NAME.equals(cell.getKey())){
                    name = cell.getValue();
                }else if(AtpSearchServiceConstants.ATP_RESULTCOLUMN_ATP_START_DATE.equals(cell.getKey())){
                    startDate = cell.getValue();
                }else if(AtpSearchServiceConstants.ATP_RESULTCOLUMN_ATP_END_DATE.equals(cell.getKey())){
                    endDate = cell.getValue();
                }else if(AtpSearchServiceConstants.ATP_RESULTCOLUMN_ATP_STATE.equals(cell.getKey())){
                    stateKey = cell.getValue();
                }
            }

            HolidayCalendarInfo hcalInfo = new HolidayCalendarInfo();
            hcalInfo.setId(id);
            hcalInfo.setName(name);
            hcalInfo.setStartDate(DateFormatters.DEFAULT_YEAR_MONTH_24HOUR_MILLISECONDS_FORMATTER.parse(startDate));
            hcalInfo.setEndDate(DateFormatters.DEFAULT_YEAR_MONTH_24HOUR_MILLISECONDS_FORMATTER.parse(endDate));
            hcalInfo.setStateKey(stateKey);

            hCals.add(hcalInfo);
        }

        return hCals;

    }

}
