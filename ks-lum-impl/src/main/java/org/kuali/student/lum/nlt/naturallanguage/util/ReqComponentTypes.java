package org.kuali.student.lum.nlt.naturallanguage.util;

/**
 * <p>Requirement component types are used to map a dot notation field key
 * (e.g. reqCompFieldType.cluSet) to a key without dot notation (cluSet) since most 
 * template engines don't allow dot notation for template variables because
 * dot notations are used to get class properties or methods 
 * (e.g. clu.getOfficialIdentifier().getShortName()).</p>
 * <p>
 * Some Common Template keys
 * <ul>
 * <li><code>reqCompFieldType.clu</code> maps to <code>clu</code></li>
 * <li><code>reqCompFieldType.cluSet</code> maps to <code>cluSet</code></li>
 * <li><code>reqCompFieldType.requiredCount</code> maps to <code>expectedValue</code></li>
 * <li><code>reqCompFieldType.operator</code> maps to <code>relationalOperator</code></li>
 * </ul>
 * Template: <code>Student must have completed all of $cluSet.getCluSetAsShortName()</code>
 * </p>
 */
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
}
