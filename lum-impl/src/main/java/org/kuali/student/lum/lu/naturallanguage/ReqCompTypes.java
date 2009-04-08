package org.kuali.student.lum.lu.naturallanguage;

public class ReqCompTypes {

	public enum VelocityToken {
		CLU_SET_KEY("cluSet"),
		EXPECTED_VALUE_KEY("expectedValue"),
		OPERATOR_KEY("relationalOperator");

		private String key;
		
		VelocityToken(String key) {
			this.key = key;
		}
		
		String getKey() {
			return this.key;
		}
	}

	public enum CountDefinitionType {
		EXPECTED_VALUE_KEY("reqCompFieldType.expectedValue"),
		OPERATOR_KEY("reqCompFieldType.operator"),
		COUNT_TYPE_KEY("reqCompFieldType.countType"),
		COUNT_TARTGET_KEY("reqCompFieldType.countTarget"),
		INCLUSION_FILTER_TYPE_KEY("reqCompFieldType.inclusionFilter.type"),
		INCLUSION_FILTER_VALUE_KEY("reqCompFieldType.inclusionFilter.value");

		private String key;
		
		CountDefinitionType(String key) {
			this.key = key;
		}
		
		String getKey() {
			return this.key;
		}
	}
	
	public enum CustomReqComponentType {
		COURSE_LIST("kuali.reqCompType.courseList"),
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
