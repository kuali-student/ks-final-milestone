package org.kuali.rice.student.lookup;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import org.apache.commons.lang.StringUtils;

import org.kuali.rice.core.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kns.authorization.BusinessObjectRestrictions;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.util.GlobalVariables;
import org.kuali.rice.kns.util.KNSConstants;
import org.kuali.rice.kns.util.ObjectUtils;
import org.kuali.rice.kns.web.struts.form.LookupForm;
import org.kuali.rice.kns.web.ui.Column;
import org.kuali.rice.kns.web.ui.ResultRow;
import org.kuali.rice.kns.web.comparator.CellComparatorHelper;

import org.kuali.rice.student.bo.CourseTransient;
import org.kuali.student.core.search.dto.SearchParam;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;
import org.kuali.student.core.search.dto.SearchResultRow;
import org.kuali.student.core.search.dto.SearchResultCell;
import org.kuali.student.lum.lu.service.LuService;


public class CourseTransientLookupableHelperServiceImpl extends KualiLookupableHelperServiceImpl {

    private LuService luService = null;
    
    /**
     * This overridden method calls luService.search to perform the lookup. 
     * It also construct the result Table based on the search results from luService.search.
     * 
     * @see org.kuali.rice.kns.lookup.AbstractLookupableHelperServiceImpl#performLookup(org.kuali.rice.kns.web.struts.form.LookupForm, java.util.Collection, boolean)
     */
    @Override
    public Collection performLookup(LookupForm lookupForm, Collection resultTable, boolean bounded) {

        Map<String, String> fieldValues = lookupForm.getFieldsForLookup();

        String courseTitle = fieldValues.get("courseTitle");
        String courseCode = fieldValues.get("courseCode");
        String subjectArea = fieldValues.get("subjectArea");
        
        List<SearchParam> queryParamValues = new ArrayList<SearchParam>();
        SearchParam searchParam = new SearchParam();
        searchParam.setKey("lu.queryParam.luOptionalLongName");
        searchParam.setValue(courseTitle);
        queryParamValues.add(searchParam);
       
        searchParam = new SearchParam();
        searchParam.setKey("lu.queryParam.luOptionalCode");
        searchParam.setValue(courseCode);
        queryParamValues.add(searchParam);
        
        searchParam = new SearchParam();
//        searchParam.setKey("lu.queryParam.luOptionalStudySubjectArea");
        searchParam.setKey("lu.queryParam.luOptionalDivision");
        searchParam.setValue(subjectArea);
        queryParamValues.add(searchParam);
        
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setSearchKey("lu.search.generic");
        searchRequest.setParams(queryParamValues);
        try {
                getTheLuService();
                SearchResult results = luService.search(searchRequest);
                List<CourseTransient> courseLookupResults = new ArrayList<CourseTransient>();
                List<SearchResultRow> rows = results.getRows();
                for(SearchResultRow row : rows) {
                    CourseTransient courseLookupResult = new CourseTransient();
                    List<SearchResultCell> cells = row.getCells();                    
                    for(SearchResultCell cell : cells) {                        
                        if(cell.getKey().equals("lu.resultColumn.cluId")) {
                            courseLookupResult.setCourseId(cell.getValue());
                        }
                        else if(cell.getKey().equals("lu.resultColumn.luOptionalLongName")) {
                            courseLookupResult.setCourseTitle(cell.getValue());
                        }
                        else if(cell.getKey().equals("lu.resultColumn.luOptionalCode")) {
                            courseLookupResult.setCourseCode(cell.getValue());
                        }
                        else if(cell.getKey().equals("lu.resultColumn.luOptionalDivision")) {
                            courseLookupResult.setSubjectArea(cell.getValue());
                        }                        
                    }
                    courseLookupResults.add(courseLookupResult);
                }
                formResultTable(lookupForm, resultTable, courseLookupResults);
                return courseLookupResults;
            
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    protected void formResultTable(LookupForm lookupForm, Collection resultTable, List<CourseTransient> displayList){
        boolean hasReturnableRow = false;
          setBackLocation((String) lookupForm.getFieldsForLookup().get(KNSConstants.BACK_LOCATION));
          setDocFormKey((String) lookupForm.getFieldsForLookup().get(KNSConstants.DOC_FORM_KEY));

          List returnKeys = getReturnKeys();

//        List pkNames = getBusinessObjectMetaDataService().listPrimaryKeyFieldNames(getBusinessObjectClass());
          List pkNames = new ArrayList <String>();
          pkNames.add("courseId");
          Person user = GlobalVariables.getUserSession().getPerson();
        
        // iterate through result list and wrap rows with return url and action
        // urls
        for (Iterator iter = displayList.iterator(); iter.hasNext();) {
            CourseTransient element = (CourseTransient) iter.next();

            final String lookupId = KNSServiceLocator.getLookupResultsService().getLookupId(element);
            if (lookupId != null) {
                lookupForm.setLookupObjectId(lookupId);
            }

            BusinessObjectRestrictions businessObjectRestrictions = getBusinessObjectAuthorizationService()
                    .getLookupResultRestrictions(element, user);

            HtmlData returnUrl = getReturnUrl(element, lookupForm, returnKeys, businessObjectRestrictions);
            String actionUrls = getActionUrls(element, pkNames, businessObjectRestrictions);
            // Fix for JIRA - KFSMI-2417
            if ("".equals(actionUrls)) {
                actionUrls = ACTION_URLS_EMPTY;
            }

            List<Column> columns = getColumns();
            
            for (Iterator iterator = columns.iterator(); iterator.hasNext();) {
                Column col = (Column) iterator.next();
                
                String propValue = ObjectUtils.getFormattedPropertyValue(element, col.getPropertyName(), col.getFormatter());
                Class propClass = getPropertyClass(element, col.getPropertyName());

                col.setComparator(CellComparatorHelper.getAppropriateComparatorForPropertyClass(propClass));
                col.setValueComparator(CellComparatorHelper.getAppropriateValueComparatorForPropertyClass(propClass));

                String propValueBeforePotientalMasking = propValue;
//                propValue = maskValueIfNecessary(element.getClass(), col.getPropertyName(), propValue,
//                        businessObjectRestrictions);
                col.setPropertyValue(propValue);

                // if property value is masked, don't display additional or alternate properties, or allow totals
                if (StringUtils.equals(propValueBeforePotientalMasking, propValue)) {
                    if (StringUtils.isNotBlank(col.getAlternateDisplayPropertyName())) {
                        String alternatePropertyValue = ObjectUtils.getFormattedPropertyValue(element, col
                                .getAlternateDisplayPropertyName(), null);
                        col.setPropertyValue(alternatePropertyValue);
                    }

                    if (StringUtils.isNotBlank(col.getAdditionalDisplayPropertyName())) {
                        String additionalPropertyValue = ObjectUtils.getFormattedPropertyValue(element, col
                                .getAdditionalDisplayPropertyName(), null);
                        col.setPropertyValue(col.getPropertyValue() + " *-* " + additionalPropertyValue);
                    }
                }
                else {
                    col.setTotal(false);
                }
                
                if (col.isTotal()) {
                    Object unformattedPropValue = ObjectUtils.getPropertyValue(element, col.getPropertyName());
                    col.setUnformattedPropertyValue(unformattedPropValue);
                }

//                if (StringUtils.isNotBlank(propValue)) {
//                    col.setColumnAnchor(getInquiryUrl(element, col.getPropertyName()));
//                }
            }


            ResultRow row = new ResultRow(columns, returnUrl.constructCompleteHtmlTag(), actionUrls);
            row.setRowId(returnUrl.getName());
            row.setReturnUrlHtmlData(returnUrl);

            // because of concerns of the BO being cached in session on the
            // ResultRow,
            // let's only attach it when needed (currently in the case of
            // export)
//            if (getBusinessObjectDictionaryService().isExportable(getBusinessObjectClass())) {
//                row.setBusinessObject(element);
//            }
//
            if (lookupId != null) {
                row.setObjectId(lookupId);
            }

            boolean rowReturnable = isResultReturnable(element);
            row.setRowReturnable(rowReturnable);
            if (rowReturnable) {
                hasReturnableRow = true;
            }
            resultTable.add(row);
        }

        lookupForm.setHasReturnableRow(hasReturnableRow);
    }

   
    protected LuService getTheLuService() {
        if (this.luService == null) {
            this.luService = (LuService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/lu","LuService")); 
        }
        return this.luService;
    }

    /**
     * This overridden method only adds "edit" but not "copy" ActionUrl to the lookup results
     * 
     * @see org.kuali.rice.kns.lookup.AbstractLookupableHelperServiceImpl#getCustomActionUrls(org.kuali.rice.kns.bo.BusinessObject, java.util.List)
     */
//    @Override
//    public List<HtmlData> getCustomActionUrls(BusinessObject businessObject, List pkNames) {
//        List<HtmlData> htmlDataList = new ArrayList<HtmlData>();     
//        htmlDataList.add(getUrlData(businessObject, KNSConstants.MAINTENANCE_EDIT_METHOD_TO_CALL, pkNames));
//        return htmlDataList;  
//    }

    /**
     * This overridden method ...
     * 
     * @see org.kuali.rice.kns.lookup.AbstractLookupableHelperServiceImpl#getReturnKeys()
     */
    @Override
    public List getReturnKeys() {
        List returnKeys = new ArrayList <String>();
//            returnKeys = getBusinessObjectMetaDataService().listPrimaryKeyFieldNames(getBusinessObjectClass());
        returnKeys.add("courseId");
        return returnKeys;
    }    
    
}
