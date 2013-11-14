package org.kuali.student.ap.coursesearch.dataobject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.course.CourseSearchItem;
import org.kuali.student.ap.framework.course.FacetIndex;
import org.kuali.student.ap.framework.course.FacetIndexBuilder;
import org.kuali.student.ap.coursesearch.util.CollectionListPropertyEditorHtmlListType;
import org.kuali.student.ap.coursesearch.util.FacetKeyFormatter;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.springframework.util.StringUtils;

/**
 * Created by IntelliJ IDEA. User: kmuthu Date: 11/3/11 Time: 11:08 AM
 * <p/>
 * Wrapper for CourseInfo data.
 */
public class CourseSearchItemImpl implements CourseSearchItem {

	private String courseId;
	private String code;

	private String number;
	private String subject;
	private String level;
	private String courseName;

	private String credit;
	private float creditMin;
	private float creditMax;
	private CreditType creditType;

	private String genEduReq = EMPTY_RESULT_VALUE_KEY;

	private PlanState status = PlanState.UNPLANNED;

	/* Facet keys used for filtering in the view. */
	private Set<String> curriculumFacetKeys = new HashSet<String>();
	private Set<String> courseLevelFacetKeys = new HashSet<String>();
	private Set<String> genEduReqFacetKeys = new HashSet<String>();
	private Set<String> termsFacetKeys = new HashSet<String>();
	private Set<String> scheduledFacetKeys = new HashSet<String>();
	private Set<String> creditsFacetKeys = new HashSet<String>();

	private List<String> termInfoList;

	private List<String> keywords;

	/**
	 * Lazy initialized column data for supporting server side result caching.
	 */
	private transient String[] searchColumns;

	/**
	 * Lazy initialized column data for supporting server side result caching.
	 */
	private transient String[] sortColumns;

	/**
	 * Lazy initialized column data for supporting server side result caching.
	 */
	private transient FacetIndex facetColumns;

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	// aka KSLU_CLU_IDENT.DIVISION
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCredit() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}

	public float getCreditMin() {
		return creditMin;
	}

	public void setCreditMin(float creditMin) {
		this.creditMin = creditMin;
	}

	public float getCreditMax() {
		return creditMax;
	}

	public void setCreditMax(float creditMax) {
		this.creditMax = creditMax;
	}

	public CreditType getCreditType() {
		return creditType;
	}

	public void setCreditType(CreditType creditType) {
		this.creditType = creditType;
	}

	public String getScheduledAndOfferedTerms() {

		CollectionListPropertyEditorHtmlListType listType = CollectionListPropertyEditorHtmlListType.DL;

		Element termsList = DocumentHelper.createElement(listType
				.getListElementName()); // dl

		if (scheduledTermsList != null && scheduledTermsList.size() > 0) {
			Element termsListItem = termsList.addElement(listType
					.getListItemElementName()); // dd
			termsListItem.addAttribute("class", "scheduled");
			Element scheduledListElement = termsListItem.addElement(listType
					.getListElementName()); // dl
			for (String scheduledTermId : scheduledTermsList) {
				Element scheduledListItem = scheduledListElement
						.addElement(listType.getListItemElementName()); // dd
				String scheduledTerm;
				try {
					scheduledTerm = KsapFrameworkServiceLocator
							.getAtpService()
							.getAtp(scheduledTermId,
									KsapFrameworkServiceLocator.getContext()
											.getContextInfo()).getName();
				} catch (DoesNotExistException e) {
					throw new IllegalArgumentException("ATP lookup error", e);
				} catch (InvalidParameterException e) {
					throw new IllegalArgumentException("ATP lookup error", e);
				} catch (MissingParameterException e) {
					throw new IllegalArgumentException("ATP lookup error", e);
				} catch (OperationFailedException e) {
					throw new IllegalStateException("ATP lookup error", e);
				} catch (PermissionDeniedException e) {
					throw new IllegalStateException("ATP lookup error", e);
				}
				String termAbbreviation = scheduledTerm.substring(0, 2)
						.toUpperCase();
				scheduledListItem.addAttribute("class", termAbbreviation);
				String year = scheduledTerm
						.substring(scheduledTerm.length() - 2);
				scheduledListItem.setText(String.format("%s %s",
						termAbbreviation, year));
			}
		}

		if (termInfoList != null && termInfoList.size() > 0) {
			Element termsListItem = termsList.addElement(listType
					.getListItemElementName()); // dd
			termsListItem.addAttribute("class", "projected");
			Element termListElement = termsListItem.addElement(listType
					.getListElementName()); // dl
			for (String atpTypeKey : termInfoList) {
				Element scheduledListItem = termListElement.addElement(listType
						.getListItemElementName()); // dd
				String term;
				try {
					term = KsapFrameworkServiceLocator
							.getTypeService()
							.getType(
									atpTypeKey,
									KsapFrameworkServiceLocator.getContext()
											.getContextInfo()).getName();
				} catch (DoesNotExistException e) {
					throw new IllegalArgumentException("ATP lookup error", e);
				} catch (InvalidParameterException e) {
					throw new IllegalArgumentException("ATP lookup error", e);
				} catch (MissingParameterException e) {
					throw new IllegalArgumentException("ATP lookup error", e);
				} catch (OperationFailedException e) {
					throw new IllegalStateException("ATP lookup error", e);
				} catch (PermissionDeniedException e) {
					throw new IllegalStateException("ATP lookup error", e);
				}
				scheduledListItem.setText(term.substring(0, 2).toUpperCase());
			}
		}
		return termsList.asXML();
	}

	public String getGenEduReq() {
		return genEduReq;
	}

	public void setGenEduReq(String genEduReq) {
		if (StringUtils.hasText(genEduReq)) {
			this.genEduReq = genEduReq;
		}
	}

	public PlanState getStatus() {
		return this.status;
	}

	public void setStatus(PlanState status) {
		this.status = status;
	}

	public boolean isStatusSaved() {
		return status == PlanState.SAVED;
	}

	public boolean isStatusInPlan() {
		return status == PlanState.IN_PLAN;
	}

	public boolean isStatusUnplanned() {
		return status == PlanState.UNPLANNED;
	}

	public Set<String> getCurriculumFacetKeys() {
		return curriculumFacetKeys;
	}

	public Set<String> getCourseLevelFacetKeys() {
		return courseLevelFacetKeys;
	}

	public Set<String> getGenEduReqFacetKeys() {
		return genEduReqFacetKeys;
	}

	public Set<String> getTermsFacetKeys() {
		return termsFacetKeys;
	}

	public Set<String> getScheduledFacetKeys() {
		return scheduledFacetKeys;
	}

	public Set<String> getCreditsFacetKeys() {
		return creditsFacetKeys;
	}

	/**
	 * Get a combined set of terms and scheduled.
	 * 
	 * @return
	 */
	public Set<String> getQuartersFacetKeys() {
		Set<String> termsAndQuarters = new HashSet<String>();
		termsAndQuarters.addAll(scheduledFacetKeys);
		termsAndQuarters.addAll(termsFacetKeys);
		return termsAndQuarters;
	}

	public String getCourseLevelFacetKey() {
		return FacetKeyFormatter.format(courseLevelFacetKeys);
	}

	public String getCurriculumFacetKey() {
		return FacetKeyFormatter.format(curriculumFacetKeys);
	}

	public String getGenEduReqFacetKey() {
		return FacetKeyFormatter.format(genEduReqFacetKeys);
	}

	public String getTermsFacetKey() {
		return FacetKeyFormatter.format(termsFacetKeys);
	}

	public String getScheduledFacetKey() {
		return FacetKeyFormatter.format(scheduledFacetKeys);
	}

	public String getCreditsFacetKey() {
		return FacetKeyFormatter.format(creditsFacetKeys);
	}

	public String getQuartersFacetKey() {
		return FacetKeyFormatter.format(getQuartersFacetKeys());
	}

	public void setCurriculumFacetKeys(Set<String> curriculumFacetKeys) {
		this.curriculumFacetKeys = curriculumFacetKeys;
	}

	public void setCourseLevelFacetKeys(Set<String> courseLevelFacetKeys) {
		this.courseLevelFacetKeys = courseLevelFacetKeys;
	}

	public void setGenEduReqFacetKeys(Set<String> genEduReqFacetKeys) {
		this.genEduReqFacetKeys = genEduReqFacetKeys;
	}

	public void setTermsFacetKeys(Set<String> termsFacetKeys) {
		this.termsFacetKeys = termsFacetKeys;
	}

	public void setScheduledFacetKeys(Set<String> scheduledFacetKeys) {
		this.scheduledFacetKeys = scheduledFacetKeys;
	}

	public void setCreditsFacetKeys(Set<String> creditsFacetKeys) {
		this.creditsFacetKeys = creditsFacetKeys;
	}

	public List<String> getTermInfoList() {
		return termInfoList;
	}

	public void setTermInfoList(List<String> termInfoList) {
		this.termInfoList = termInfoList;
	}

	private List<String> scheduledTermsList = new ArrayList<String>();

	public List<String> getScheduledTermsList() {
		return this.scheduledTermsList;
	}

	public void setScheduledTerms(List<String> scheduledTermsList) {
		this.scheduledTermsList = scheduledTermsList;
	}

	public void addScheduledTerm(String term) {
		scheduledTermsList.add(term);
	}

	/**
	 * Override to provide additional GET parameters on the course inquiry link.
	 * 
	 * <p>
	 * Escaping should not be performed by this method.
	 * </p>
	 * 
	 * @return Additional GET parameters to send on the course inquiry link.
	 */
	protected Map<String, String> getInquiryParams() {
		return Collections.emptyMap();
	}

	/**
	 * Form a link to the course details inquiry page related to this search
	 * item.
	 * 
	 * @return A link to the course details inquiry page related to this search
	 *         item.
	 */
	protected String getInquiryLink() {
		Element iqlink = DocumentHelper.createElement("a");
		StringBuilder url = new StringBuilder();
		url.append("inquiry?methodToCall=start&viewId=CourseDetails-InquiryView&courseId=");
		try {
			url.append(URLEncoder.encode(getCourseId(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("UTF-8 is unsupported", e);
		}
		Map<String, String> ap = getInquiryParams();
		if (ap != null)
			for (Entry<String, String> e : ap.entrySet())
				try {
					url.append('&')
							.append(URLEncoder.encode(e.getKey(), "UTF-8"))
							.append('=')
							.append(URLEncoder.encode(e.getValue(), "UTF-8"));
				} catch (UnsupportedEncodingException ex) {
					throw new IllegalStateException("UTF-8 is unsupported", ex);
				}
		iqlink.addAttribute("href", url.toString());
		iqlink.addAttribute("target", "_self");
		iqlink.addAttribute("title", getCourseName());
		iqlink.addAttribute("class", "ksap-text-ellipsis");
		iqlink.setText(getCourseName());
		return iqlink.asXML();
	}

	/**
	 * Get HTML content for the status column in the search results for this
	 * item.
	 * 
	 * @return HTML content for the status column in the search results for this
	 *         item.
	 */
	protected String getStatusColumn() {
		assert getCourseId() != null;
		String cid = getCourseId().replace('.', '_');
		String statusLabel = getStatus().getLabel();

		Element stsp = DocumentHelper.createElement("span");
		stsp.addAttribute("id", cid + "_status");
		if (statusLabel.length() > 0) {
			stsp.addAttribute("class", statusLabel.toLowerCase());
			stsp.setText(statusLabel);
		} else if (KsapFrameworkServiceLocator.getUserSessionHelper()
				.isAdviser()) {
			stsp.setText(CourseSearchItem.EMPTY_RESULT_VALUE_KEY);
		} else {
			String imagePath = ConfigContext.getCurrentContextConfig()
					.getProperty("ks.ap.externalizable.images.url");
			Element addAnchor = stsp.addElement("input");
			addAnchor.addAttribute("id", cid + "_add_anchor");
			addAnchor.addAttribute("class",
					"uif-field uif-imageField ksap-add");
			addAnchor.addAttribute("type", "image");
			addAnchor.addAttribute("title", "Bookmark or Add to Plan");
			addAnchor.addAttribute("alt", "Bookmark or Add to Plan");
			addAnchor.addAttribute("src", imagePath + "pixel.gif");
			addAnchor.addAttribute("data-courseid", courseId);
			addAnchor.addAttribute("data-coursexid", cid);
			StringBuilder openMenu = new StringBuilder("openMenu('");
			openMenu.append(cid);
			openMenu.append("_add','add_course_items',null,event,'.ksap-status-column','ksap-container-75',");
			openMenu.append("{tail:{align:'middle'},align:'middle',position:'right'},false)");
			addAnchor.addAttribute("onclick", openMenu.toString());
		}
		return stsp.asXML();
	}

	@Override
	public List<String> getKeywords() {
		if (keywords == null) {
			List<String> kws = new java.util.LinkedList<String>();
			for (String kw : getCourseName().split("\\s+")) {
				StringBuilder kwsb = new StringBuilder(kw);
				int dig = 0;
				boolean wild = false;
				for (int c = 0; c < kwsb.length(); c++) {
					char kwc = kwsb.charAt(c);
					if (wild = wild || kwc == '-' || kwc == '*' || kwc == '#')
						continue;
					if (!Character.isLetterOrDigit(kwc))
						kwsb.deleteCharAt(c);
					else if (Character.isDigit(kwc) || kwc == 'x' || kwc == 'X')
						dig++;
				}
				if (wild)
					continue;
				String searchTerm = kwsb.toString().trim().toUpperCase();
				if (searchTerm.length() - dig >= 5
						&& !Character.isDigit(searchTerm.charAt(0))
						&& !NOISE_WORDS.contains(searchTerm)
						&& !kws.contains(searchTerm))
					kws.add(searchTerm);
			}
			keywords = kws;
		}
		return keywords;
	}

	/**
	 * Convert a set of facet keys to a string for passing as a column in the
	 * results for this item.
	 * 
	 * @param facetKeys
	 *            The facet keys related to this item.
	 * @return A string for passing as a column in the results.
	 */
	protected Map<String, Map<String, KeyValue>> facetString(
			Collection<String> facetKeys) {
		Map<String, Map<String, KeyValue>> rv = new java.util.LinkedHashMap<String, Map<String, KeyValue>>();
		Map<String, KeyValue> ug = new java.util.LinkedHashMap<String, KeyValue>();
		for (final String fk : facetKeys)
			ug.put(fk, new KeyValue() {
				private static final long serialVersionUID = 6620894647540404487L;

				@Override
				public String getKey() {
					return fk;
				}

				@Override
				public String getValue() {
                    return fk;
				}
			});
		rv.put("", Collections.synchronizedMap(Collections.unmodifiableMap(ug)));
		return Collections.synchronizedMap(Collections.unmodifiableMap(rv));
	}

	@Override
	public String[] getSearchColumns() {
		return searchColumns == null ? searchColumns = new String[] {
				getCode(), getInquiryLink(), getCredit(),
				getScheduledAndOfferedTerms(), getGenEduReq(),
				getStatusColumn(), } : searchColumns;
	}

	@Override
	public String[] getSortColumns() {
		return sortColumns == null ? sortColumns = new String[] { getCode(),
				getCourseName() } : sortColumns;
	}

	@Override
	public FacetIndex getFacetColumns() {
		if (facetColumns == null) {
			FacetIndexBuilder m = new FacetIndexBuilder();
			m.put("facet_quarter", facetString(getTermsFacetKeys()));
			m.put("facet_genedureq", facetString(getGenEduReqFacetKeys()));
			m.put("facet_credits", facetString(getCreditsFacetKeys()));
			m.put("facet_level", facetString(getCourseLevelFacetKeys()));
			m.put("facet_curriculum", facetString(getCurriculumFacetKeys()));
			m.put("facet_keywords", facetString(getKeywords()));
			facetColumns = m.build();
		}
		return facetColumns;
	}

	@Override
	public String toString() {
		return String.format("%s: %s", getCode(), getCourseId());
	}

}
