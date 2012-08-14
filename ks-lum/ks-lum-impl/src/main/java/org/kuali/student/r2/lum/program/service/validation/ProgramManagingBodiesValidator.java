package org.kuali.student.r2.lum.program.service.validation;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.kuali.student.r1.common.dictionary.dto.FieldDefinition;
import org.kuali.student.r1.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r1.common.search.dto.SearchRequest;
import org.kuali.student.r1.common.search.dto.SearchResult;
import org.kuali.student.r1.common.search.dto.SearchResultCell;
import org.kuali.student.r1.common.search.dto.SearchResultRow;
//import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.validator.DefaultValidatorImpl;
import org.kuali.student.r2.lum.program.dto.MajorDisciplineInfo;

public class ProgramManagingBodiesValidator extends DefaultValidatorImpl {

    @Override
    public List<ValidationResultInfo> validateObject(FieldDefinition field,
            Object o, ObjectStructureDefinition objStructure,
            Stack<String> elementStack, ContextInfo contextInfo) {
        List<ValidationResultInfo> validationResults = new ArrayList<ValidationResultInfo>();

        String element = getElementXpath(elementStack) + "/" + field.getName();

        if (o instanceof MajorDisciplineInfo) {
            MajorDisciplineInfo majorDisciplineInfo = (MajorDisciplineInfo) o;

            if (field.getName().equalsIgnoreCase("unitsContentOwner") && null != majorDisciplineInfo.getUnitsContentOwner()
                    && !majorDisciplineInfo.getUnitsContentOwner().isEmpty()) {
                validationResults = validateObject(element, majorDisciplineInfo.getUnitsContentOwner(), majorDisciplineInfo.getDivisionsContentOwner(), contextInfo);
            } else if (field.getName().equalsIgnoreCase("unitsDeployment") && null != majorDisciplineInfo.getUnitsDeployment()
                    && !majorDisciplineInfo.getUnitsDeployment().isEmpty()) {
                validationResults = validateObject(element, majorDisciplineInfo.getUnitsDeployment(), majorDisciplineInfo.getDivisionsDeployment(), contextInfo);
            } else if (field.getName().equalsIgnoreCase("unitsFinancialControl") && null != majorDisciplineInfo.getUnitsFinancialControl()
                    && !majorDisciplineInfo.getUnitsFinancialControl().isEmpty()) {
                validationResults = validateObject(element, majorDisciplineInfo.getUnitsFinancialControl(), majorDisciplineInfo.getDivisionsFinancialControl(), contextInfo);
            } else if (field.getName().equalsIgnoreCase("unitsFinancialResources") && null != majorDisciplineInfo.getUnitsFinancialResources()
                    && !majorDisciplineInfo.getUnitsFinancialResources().isEmpty()) {
                validationResults = validateObject(element, majorDisciplineInfo.getUnitsFinancialResources(), majorDisciplineInfo.getDivisionsFinancialResources(), contextInfo);
            } else if (field.getName().equalsIgnoreCase("unitsStudentOversight") && null != majorDisciplineInfo.getUnitsStudentOversight()
                    && !majorDisciplineInfo.getUnitsStudentOversight().isEmpty()) {
                validationResults = validateObject(element, majorDisciplineInfo.getUnitsStudentOversight(), majorDisciplineInfo.getDivisionsStudentOversight(), contextInfo);
            }
        }

        return validationResults;
    }

    public List<ValidationResultInfo> validateObject(String element, List<String> departmentIds, List<String> collegeIds, ContextInfo contextInfo) {
        List<ValidationResultInfo> validationResults = new ArrayList<ValidationResultInfo>();

        List<String> departmentRelatedCollegeIds = getDepartmentRelatedColleges(departmentIds);

        if (null != collegeIds) {
            for (String collegeId : collegeIds) {
                if (!departmentRelatedCollegeIds.contains(collegeId)) {
                    validationResults.addAll(getValidationResultInfo(element, collegeId, departmentIds, contextInfo));
                }
            }
        }

        return validationResults;
    }

    private List<String> getDepartmentRelatedColleges(List<String> departmentIds) {
        List<String> departmentRelatedCollegeIds = new ArrayList<String>();
        SearchRequest searchRequest = new SearchRequest("org.search.orgQuickViewByRelationTypeOrgTypeRelatedOrgIds");

        List<String> orgTypes = new ArrayList<String>();
        orgTypes.add("kuali.org.College");

        searchRequest.addParam("org.queryParam.optionalOrgTypeList", orgTypes);
        searchRequest.addParam("org.queryParam.optionalRelationType", "kuali.org.Contain");
        searchRequest.addParam("org.queryParam.relatedOrgIds", departmentIds);

        SearchResult searchResult = getSearchDispatcher().dispatchSearch(searchRequest);

        if (null != searchResult) {
            for (SearchResultRow row : searchResult.getRows()) {
                for (SearchResultCell cell : row.getCells()) {
                    if ("org.resultColumn.orgId".equals(cell.getKey())) {
                        departmentRelatedCollegeIds.add(cell.getValue());
                    }
                }
            }
        }

        return departmentRelatedCollegeIds;
    }

    private List<ValidationResultInfo> getValidationResultInfo(String element, String collegeId, List<String> departmentIds, ContextInfo contextInfo) {
        List<ValidationResultInfo> validationResults = new ArrayList<ValidationResultInfo>();

        String message = getMessage("validation.programManagingBodiesMatch", contextInfo );
        String collegeName = getCollegeName(collegeId);
        List<String> departments = getDepartments(departmentIds);

        for (String departmentName : departments) {
            ValidationResultInfo validationResultInfo = new ValidationResultInfo(element);
            validationResultInfo.setWarning(MessageFormat.format(message, collegeName, departmentName));
            validationResults.add(validationResultInfo);
        }

        return validationResults;
    }

    private String getCollegeName(String collegeId) {
        String collegeName = "";
        SearchRequest searchRequest = new SearchRequest("org.search.generic");

        List<String> orgTypes = new ArrayList<String>();
        orgTypes.add("kuali.org.College");

        searchRequest.addParam("org.queryParam.orgOptionalType", orgTypes);
        searchRequest.addParam("org.queryParam.orgOptionalId", collegeId);

        SearchResult searchResult = getSearchDispatcher().dispatchSearch(searchRequest);
        if (null != searchResult) {
            for (SearchResultRow result : searchResult.getRows()) {
                for (SearchResultCell resultCell : result.getCells()) {
                    if ("org.resultColumn.orgOptionalLongName".equals(resultCell.getKey())) {
                        collegeName = resultCell.getValue();
                    }
                }
            }
        }

        return collegeName;
    }

    private List<String> getDepartments(List<String> departmentIds) {
        List<String> departments = new ArrayList<String>();
        SearchRequest searchRequest = new SearchRequest("org.search.generic");

        List<String> orgTypes = new ArrayList<String>();
        orgTypes.add("kuali.org.Department");

        searchRequest.addParam("org.queryParam.orgOptionalType", orgTypes);
        searchRequest.addParam("org.queryParam.orgOptionalIds", departmentIds);

        SearchResult searchResult = getSearchDispatcher().dispatchSearch(searchRequest);

        if (null != searchResult) {
            for (SearchResultRow result : searchResult.getRows()) {
                for (SearchResultCell resultCell : result.getCells()) {
                    if ("org.resultColumn.orgOptionalLongName".equals(resultCell.getKey())) {
                        departments.add(resultCell.getValue());
                    }
                }
            }
        }

        return departments;
    }
}
