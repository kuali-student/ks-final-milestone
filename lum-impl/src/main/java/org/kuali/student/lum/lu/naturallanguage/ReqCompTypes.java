package org.kuali.student.lum.lu.naturallanguage;

public class ReqCompTypes {

	/**
	 * These tokens are needed since velocity doesn't allow periods in tokens.
	 * E.g. reqCompFieldType.totalCredits must either be convert to 
	 * totalCredits or reqCompFieldType_totalCredits so a template would look 
	 * like: 
	 * 
	 * 'Student must take $totalCredits of MATH 100'
	 * or
	 * 'Student must take $reqCompFieldType_totalCredits of MATH 100'
	 */
	public enum VelocityToken {
		CLU_SET_KEY("cluSet"),
		EXPECTED_VALUE_KEY("expectedValue"),
		OPERATOR_KEY("relationalOperator"),
		CLU("clu"),
		GPA("gpa"),
		TOTAL_CREDITS("totalCredits");

		private String key;
		
		VelocityToken(String key) {
			this.key = key;
		}
		
		String getKey() {
			return this.key;
		}
	}

	public enum ReqCompFieldDefinitions {
		CLU_KEY("reqCompFieldType.clu"),
		CLUSET_KEY("reqCompFieldType.cluSet"),
		REQUIRED_COUNT_KEY("reqCompFieldType.requiredCount"),
        GPA_KEY("reqCompFieldType.gpa"),
        TOTAL_CREDIT_KEY("reqCompFieldType.totalCredits"),
		OPERATOR_KEY("reqCompFieldType.operator"),
		COUNT_TYPE__KEY("reqCompFieldType.countType"),
		INCLUSION_FILTER_TYPE_KEY("reqCompFieldType.inclusionFilter.type"),
		INCLUSION_FILTER_VALUE_KEY("reqCompFieldType.inclusionFilter.value");

		private String key;
		
		ReqCompFieldDefinitions(String key) {
			this.key = key;
		}
		
		String getKey() {
			return this.key;
		}
	}
		
	public enum CustomReqComponentType {
		COURSE_LIST_NONE("kuali.reqCompType.courseList.none"),
		COURSE_LIST_ALL("kuali.reqCompType.courseList.all"),
		COURSE_LIST_NOF("kuali.reqCompType.courseList.nof"),
		COURSE_LIST_1OF2("kuali.reqCompType.courseList.1of2"),
		GRADE_CONDITION_COURSE_LIST("kuali.reqCompType.grdCondCourseList"),
		GRADE_CHECK("kuali.reqCompType.gradecheck");
		
		
		private String key;
		
		CustomReqComponentType(String key) {
			this.key = key;
		}
		
		String getKey() {
			return this.key;
		}
		
		static CustomReqComponentType valueOfKey(String key) {
	        if (key == null) {
	            throw new NullPointerException("Key is null");
	        }
			for(CustomReqComponentType type : values()) {
				if(type.getKey().equals(key)) {
					return type;
				}
			}
	        throw new IllegalArgumentException("No enum constant found for key '"+key+"'");
		}
	}

	/*public enum ReqComponentType {
		ACHIEVED_CLU("reqComponentType.achievedClu"), 
		LR_DEFINITION("reqComponentType.lrDefinition"), 
		COUNT_DEFINITION("reqComponentType.countDefinition "), 
		SUM_DEFINITION("reqComponentType.sumDefinition "), 
		AVERAGE_DEFINITION("reqComponentType.averageDefinition "), 
		ATTRIBUTE_EVAL("reqComponentType.attributeEvaluation ");
		
		private String key;
		
		ReqComponentType(String key) {
			this.key = key;
		}
		
		String getKey() {
			return this.key;
		}
	}*/

	public enum RelationalOperatorType {
		EQUAL_TO("equal_to"), 
		NOT_EQUAL_TO("not_equal_to"), 
		GREATER_THAN("greater_than"), 
		LESS_THAN("less_than"), 
		GREATER_THAN_OR_EQUAL_TO("greater_than_or_equal_to"), 
		LESS_THAN_OR_EQUAL_TO("less_than_or_equal_to");
		
		private String operator;
		
		RelationalOperatorType(String operator) {
			this.operator = operator;
		}
		
		String getKey() {
			return this.operator;
		}
	}	
}
