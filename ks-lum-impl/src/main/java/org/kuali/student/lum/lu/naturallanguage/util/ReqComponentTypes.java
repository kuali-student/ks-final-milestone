package org.kuali.student.lum.lu.naturallanguage.util;

public class ReqComponentTypes {

	public enum ReqCompFieldTypes {
		CLU_KEY("reqCompFieldType.clu"),
		CLUSET_KEY("reqCompFieldType.cluSet"),
		REQUIRED_COUNT_KEY("reqCompFieldType.requiredCount"),
        GPA_KEY("reqCompFieldType.gpa"),
        TOTAL_CREDIT_KEY("reqCompFieldType.totalCredits"),
		OPERATOR_KEY("reqCompFieldType.operator"),
		COUNT_TYPE_KEY("reqCompFieldType.countType"),
		INCLUSION_FILTER_TYPE_KEY("reqCompFieldType.inclusionFilter.type"),
		INCLUSION_FILTER_VALUE_KEY("reqCompFieldType.inclusionFilter.value");

		private String key;
		
		ReqCompFieldTypes(String key) {
			this.key = key;
		}
		
		public String getKey() {
			return this.key;
		}
	}
		
	public enum ReqCompTypes {
		COURSE_LIST_NONE("kuali.reqCompType.courseList.none"),
		COURSE_LIST_ALL("kuali.reqCompType.courseList.all"),
		COURSE_LIST_NOF("kuali.reqCompType.courseList.nof"),
		COURSE_LIST_1OF2("kuali.reqCompType.courseList.1of2"),
		GRADE_CONDITION_COURSE_LIST("kuali.reqCompType.grdCondCourseList"),
		GRADE_CHECK("kuali.reqCompType.gradecheck");
		
		
		private String key;
		
		ReqCompTypes(String key) {
			this.key = key;
		}
		
		public String getKey() {
			return this.key;
		}
		
		public static ReqCompTypes valueOfKey(String key) {
	        if (key == null) {
	            throw new NullPointerException("Key is null");
	        }
			for(ReqCompTypes type : values()) {
				if(type.getKey().equals(key)) {
					return type;
				}
			}
	        throw new IllegalArgumentException("No enum constant found for key '"+key+"'");
		}
	}

//	public enum RelationalOperatorTypes {
//		EQUAL_TO("equal_to"), 
//		NOT_EQUAL_TO("not_equal_to"), 
//		GREATER_THAN("greater_than"), 
//		LESS_THAN("less_than"), 
//		GREATER_THAN_OR_EQUAL_TO("greater_than_or_equal_to"), 
//		LESS_THAN_OR_EQUAL_TO("less_than_or_equal_to");
//		
//		private String operator;
//		
//		RelationalOperatorTypes(String operator) {
//			this.operator = operator;
//		}
//		
//		public String getKey() {
//			return this.operator;
//		}
//	}	
}
