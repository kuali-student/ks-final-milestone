package org.kuali.student.ap.schedulebuilder.support;

import org.kuali.student.ap.schedulebuilder.ShoppingCartRequest;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.infc.RichText;
import org.kuali.student.r2.lum.course.dto.CourseInfo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "uniqueId", "course", "term", "primaryRegistrationCode",
		"secondaryRegistrationCode", "credits", "addToCart", "processed",
		"error", "message", "_futureElements" })
public class ShoppingCartRequestInfo implements ShoppingCartRequest,
		Serializable {

	private static final long serialVersionUID = 4958010630613010777L;

	@XmlAttribute
	private String uniqueId;
	@XmlElement
	private CourseInfo course;
	@XmlElement
	private TermInfo term;
	@XmlAttribute
	private String primaryRegistrationCode;
	@XmlElement
	private List<String> secondaryRegistrationCodes;
	@XmlAttribute
	private BigDecimal credits;
	@XmlAttribute
	private boolean addToCart;
	@XmlAttribute
	private boolean processed;
	@XmlAttribute
	private boolean error;
	@XmlElement
	private RichTextInfo message;

	@XmlAnyElement
	private List<?> _futureElements;

	public ShoppingCartRequestInfo() {
		uniqueId = UUID.randomUUID().toString();
	}

	public ShoppingCartRequestInfo(ShoppingCartRequest copy) {
		setUniqueId(copy.getUniqueId());
		setCourse(new CourseInfo(copy.getCourse()));
		setTerm(new TermInfo(copy.getTerm()));
		setPrimaryRegistrationCode(copy.getPrimaryRegistrationCode());
		List<String> regCodes = copy.getSecondaryRegistrationCodes();
		if (regCodes != null)
			setSecondaryRegistrationCodes(new ArrayList<String>(regCodes));
		setAddToCart(copy.isAddToCart());
		setProcessed(copy.isProcessed());
		setError(copy.isError());
		setMessage(copy.getMessage());
	}

	@Override
	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	@Override
	public CourseInfo getCourse() {
		return course;
	}

	public void setCourse(CourseInfo course) {
		this.course = course;
	}

	@Override
	public TermInfo getTerm() {
		return term;
	}

	public void setTerm(TermInfo term) {
		this.term = term;
	}

	@Override
	public boolean isAddToCart() {
		return addToCart;
	}

	public void setAddToCart(boolean addToCart) {
		this.addToCart = addToCart;
	}

	@Override
	public boolean isProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}

	@Override
	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	@Override
	public String getPrimaryRegistrationCode() {
		return primaryRegistrationCode;
	}

	public void setPrimaryRegistrationCode(String primaryRegistrationCode) {
		this.primaryRegistrationCode = primaryRegistrationCode;
	}

	@Override
	public List<String> getSecondaryRegistrationCodes() {
		return secondaryRegistrationCodes;
	}

	public void setSecondaryRegistrationCodes(
			List<String> secondaryRegistrationCodes) {
		this.secondaryRegistrationCodes = secondaryRegistrationCodes;
	}

	public BigDecimal getCredits() {
		return credits;
	}

	public void setCredits(BigDecimal credits) {
		this.credits = credits;
	}

	@Override
	public RichTextInfo getMessage() {
		return message;
	}

	public void setMessage(RichText message) {
		this.message = new RichTextInfo(message);
	}

	@Override
	public String toString() {
		return "ShoppingCartRequestInfo [course="
				+ (course == null ? "null" : course.getId()) + ", term="
				+ (term == null ? "null" : term.getId())
				+ ", primaryRegistrationCode=" + primaryRegistrationCode
				+ ", secondaryRegistrationCodes=" + secondaryRegistrationCodes
				+ ", credits=" + credits + ", addToCart=" + addToCart
				+ ", processed=" + processed + ", error=" + error
				+ ", message=" + message + "]";
	}

}
