/**
 * 
 */
package org.kuali.rice.student.bo;

import java.util.LinkedHashMap;
import java.util.List;

import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

/**
 * @author delyea
 *
 */
public class LearningUnit extends PersistableBusinessObjectBase {

	private static final long serialVersionUID = -7212910335472959976L;

	private Integer id;
	private String state;
	private String title;
	private List<Department> departments;

	public LearningUnit() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}

	/* (non-Javadoc)
	 * @see org.kuali.rice.kns.bo.BusinessObjectBase#toStringMapper()
	 */
	@Override
	protected LinkedHashMap toStringMapper() {
        LinkedHashMap m = new LinkedHashMap();
        m.put("id", this.id);
        m.put("state", this.state);
        m.put("title", this.title);
        return m;
	}

}
