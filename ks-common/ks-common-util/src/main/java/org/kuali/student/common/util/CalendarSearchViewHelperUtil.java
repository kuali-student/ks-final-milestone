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
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;

import java.util.ArrayList;
import java.util.List;

public class CalendarSearchViewHelperUtil {

    public static List<TermInfo> searchForTerms(String nameParam, String yearParam, ContextInfo context, AtpService atpService, TypeService typeService) throws Exception {

        List<TypeTypeRelationInfo> typeRelations= typeService.getTypeTypeRelationsByOwnerAndType("kuali.atp.type.group.term", "kuali.type.type.relation.type.group", context);
        List<String> termTypeKeys = new ArrayList<String>(typeRelations.size());
        for(TypeTypeRelationInfo typeRelation:typeRelations){
            termTypeKeys.add(typeRelation.getRelatedTypeKey());
        }

        List<TermInfo> termInfoList = new ArrayList<TermInfo>();

        SearchRequestInfo searchRequest = new SearchRequestInfo("atp.search.advancedAtpSearch");
        searchRequest.addParam("atp.advancedAtpSearchParam.atpType", termTypeKeys);
        if(nameParam!=null&&!nameParam.isEmpty()){
            searchRequest.addParam("atp.advancedAtpSearchParam.atpShortName", nameParam);
        }
        if(yearParam!=null&&!yearParam.isEmpty()){
            searchRequest.addParam("atp.advancedAtpSearchParam.atpYear", yearParam);
        }
        SearchResultInfo searchResults = atpService.search(searchRequest, context);


        for(SearchResultRowInfo row : searchResults.getRows()){
            String id = null;
            String name = null;
            String startDate = null;
            String endDate = null;
            String stateKey = null;

            for(SearchResultCellInfo cell : row.getCells()){
                if("atp.resultColumn.atpId".equals(cell.getKey())){
                    id = cell.getValue();
                }else if("atp.resultColumn.atpShortName".equals(cell.getKey())){
                    name = cell.getValue();
                }else if("atp.resultColumn.atpStartDate".equals(cell.getKey())){
                    startDate = cell.getValue();
                }else if("atp.resultColumn.atpEndDate".equals(cell.getKey())){
                    endDate = cell.getValue();
                }else if("atp.resultColumn.atpState".equals(cell.getKey())){
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

        SearchRequestInfo searchRequest = new SearchRequestInfo("atp.search.advancedAtpSearch");
        searchRequest.addParam("atp.advancedAtpSearchParam.atpType", AcademicCalendarServiceConstants.ACADEMIC_CALENDAR_TYPE_KEY);
        if(nameParam!=null&&!nameParam.isEmpty()){
            searchRequest.addParam("atp.advancedAtpSearchParam.atpShortName", nameParam);
        }
        if(yearParam!=null&&!yearParam.isEmpty()){
            searchRequest.addParam("atp.advancedAtpSearchParam.atpYear", yearParam);
        }
        SearchResultInfo searchResults = atpService.search(searchRequest, context);


        for(SearchResultRowInfo row : searchResults.getRows()){
            String id = null;
            String name = null;
            String startDate = null;
            String endDate = null;
            String stateKey = null;

            for(SearchResultCellInfo cell : row.getCells()){
                if("atp.resultColumn.atpId".equals(cell.getKey())){
                    id = cell.getValue();
                }else if("atp.resultColumn.atpShortName".equals(cell.getKey())){
                    name = cell.getValue();
                }else if("atp.resultColumn.atpStartDate".equals(cell.getKey())){
                    startDate = cell.getValue();
                }else if("atp.resultColumn.atpEndDate".equals(cell.getKey())){
                    endDate = cell.getValue();
                }else if("atp.resultColumn.atpState".equals(cell.getKey())){
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

        SearchRequestInfo searchRequest = new SearchRequestInfo("atp.search.advancedAtpSearch");
        searchRequest.addParam("atp.advancedAtpSearchParam.atpType", AcademicCalendarServiceConstants.HOLIDAY_CALENDAR_TYPE_KEY);
        if(nameParam!=null&&!nameParam.isEmpty()){
            searchRequest.addParam("atp.advancedAtpSearchParam.atpShortName", nameParam);
        }
        if(yearParam!=null&&!yearParam.isEmpty()){
            searchRequest.addParam("atp.advancedAtpSearchParam.atpYear", yearParam);
        }
        SearchResultInfo searchResults = atpService.search(searchRequest, context);


        for(SearchResultRowInfo row : searchResults.getRows()){
            String id = null;
            String name = null;
            String startDate = null;
            String endDate = null;
            String stateKey = null;

            for(SearchResultCellInfo cell : row.getCells()){
                if("atp.resultColumn.atpId".equals(cell.getKey())){
                    id = cell.getValue();
                }else if("atp.resultColumn.atpShortName".equals(cell.getKey())){
                    name = cell.getValue();
                }else if("atp.resultColumn.atpStartDate".equals(cell.getKey())){
                    startDate = cell.getValue();
                }else if("atp.resultColumn.atpEndDate".equals(cell.getKey())){
                    endDate = cell.getValue();
                }else if("atp.resultColumn.atpState".equals(cell.getKey())){
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
