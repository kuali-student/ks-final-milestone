/*
 * Copyright 2014 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.ap.coursesearch.dataobject;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.student.ap.coursesearch.CourseSearchItem;
import org.kuali.student.ap.coursesearch.FacetIndex;
import org.kuali.student.ap.coursesearch.FacetIndexImpl;
import org.kuali.student.ap.coursesearch.util.CollectionListPropertyEditorHtmlListType;
import org.kuali.student.ap.coursesearch.util.FacetKeyFormatter;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.infc.Term;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Wrapper for CourseInfo data.
 */
public class CourseSearchItemImpl implements CourseSearchItem {

	private String courseId;
	private String code;
    private String versionIndependentId;

	private String number;
	private String subject;
	private String level;
	private String courseName;

	private String credit;
	private float creditMin;
	private float creditMax;
    private List<BigDecimal> multipleCredits;
	private CreditType creditType;

	private List<String> genEduReqs;

    private boolean planned;
    private boolean saved;
    private boolean searchExceeded;

	/* Facet keys used for filtering in the view. */
	private Set<String> curriculumFacetKeys = new HashSet<String>();
	private Set<String> courseLevelFacetKeys = new HashSet<String>();
	private Set<String> genEduReqFacetKeys = new HashSet<String>();
	private Set<String> termsFacetKeys = new HashSet<String>();
	private Set<String> scheduledFacetKeys = new HashSet<String>();
	private Set<String> creditsFacetKeys = new HashSet<String>();

	private List<String> termInfoList;

    private List<String> campuses;

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

    private String sessionid;

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
        if(credit==null){
            credit="";
        }
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

    public List<BigDecimal> getMultipleCredits() {
        return multipleCredits;
    }

    public void setMultipleCredits(List<BigDecimal> multipleCredits) {
        this.multipleCredits = multipleCredits;
    }

	public CreditType getCreditType() {
		return creditType;
	}

	public void setCreditType(CreditType creditType) {
		this.creditType = creditType;
	}

    public String getScheduledTerms() {
        //Return only the scheduled terms
        CollectionListPropertyEditorHtmlListType listType = CollectionListPropertyEditorHtmlListType.DL;

        Element termsList = DocumentHelper.createElement(listType.getListElementName()); // dl

        if (scheduledTermsList != null && scheduledTermsList.size() > 0) {
            Element termsListItem = termsList.addElement(listType.getListItemElementName()); // dd

            termsListItem.addAttribute("class", "scheduled");

            Element scheduledListElement = termsListItem.addElement(listType.getListElementName()); // dl

            List<TermInfo> scheduledTermsListIds;
            try {
                scheduledTermsListIds = KsapFrameworkServiceLocator.getAcademicCalendarService().getTermsByIds(scheduledTermsList, KsapFrameworkServiceLocator.getContext().getContextInfo());
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

            //sort scheduledTermsListIds
            List<Term> terms = new ArrayList<Term>(scheduledTermsListIds);
            List<Term> scheduledTermsListSorted = KsapFrameworkServiceLocator.getTermHelper().sortTermsByStartDate(terms, true);

            Integer displayLimit = Integer.valueOf(ConfigContext.getCurrentContextConfig().getProperty("ks.ap.search.terms.scheduled.limit"));

            //list greater than 3, truncate
            if ( scheduledTermsListSorted.size() >  displayLimit )
                scheduledTermsListSorted = scheduledTermsListSorted.subList(0, displayLimit);

            for (Term scheduledTermId : scheduledTermsListSorted) {
                Element scheduledListItem = scheduledListElement.addElement(listType.getListItemElementName()); // dd
                String scheduledTerm;
                scheduledTerm = scheduledTermId.getName();
                String termAbbreviation = scheduledTerm.substring(0, 2).toUpperCase();
                scheduledListItem.addAttribute("class", termAbbreviation);
                String year = scheduledTerm.substring(scheduledTerm.length() - 2);
                scheduledListItem.setText(String.format("%s %s", termAbbreviation, year));
            }
        }
        return termsList.asXML();
    }

    public String getOfferedTerms() {
        //Return only the offered terms
        CollectionListPropertyEditorHtmlListType listType = CollectionListPropertyEditorHtmlListType.DL;

        Element termsList = DocumentHelper.createElement(listType
                .getListElementName()); // dl

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

	public List<String> getGenEduReqs() {
		return genEduReqs;
	}

	public void setGenEduReqs(List<String> genEduReqs) {
	    this.genEduReqs = genEduReqs;
	}

    public boolean isPlanned() {
        return planned;
    }

    public void setPlanned(boolean plannedValue) {
        this.planned = plannedValue;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean savedValue) {
        this.saved = savedValue;
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

    public List<String> getCampuses() {
        return campuses;
    }

    public void setCampuses(List<String> campuses) {
        this.campuses = campuses;
    }

    public void addCampuses(List<String> campuses) {
        if(campuses==null) campuses=new ArrayList<String>();
        this.campuses.addAll(campuses);
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
		return new HashMap<String,String>();
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
        // Only send part of the session id to create session specific link for security
        String sid = sessionid.substring(0,sessionid.length()/2);
        ap.put("sid",sid);
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

        Element actionDiv = DocumentHelper.createElement("div");
        actionDiv.addAttribute("class", "actions");

        Element bookmarkSpan = actionDiv.addElement("span");
        Element plannedSpan = actionDiv.addElement("span");
        Element addBookmarkLink = bookmarkSpan.addElement("a");
        Element addToPlanLink = plannedSpan.addElement("a");

        //Bookmark action or status "is bookmarked"
        bookmarkSpan.addAttribute("id", cid + "_bookmark_status");
        bookmarkSpan.addAttribute("class", "bookmark");


        addBookmarkLink.addAttribute("id", cid + "_bookmark_anchor");
//        addBookmarkLink.addAttribute("class", "add-bookmark-link");
        addBookmarkLink.addAttribute("data-courseid", courseId);
        addBookmarkLink.setText(" ");

        if (isSaved()) {
            //Currently bookmarked
            addBookmarkLink.addAttribute("class", "ks-fontello-icon-star");
            addBookmarkLink.addAttribute("onclick", "deleteBookmarkCourse('', jQuery(this).data('courseid'), event);");
            addBookmarkLink.addAttribute("title", "Remove Bookmark");
        } else {
            //Not bookmarked
            addBookmarkLink.addAttribute("class", "ks-fontello-icon-star-empty");
            addBookmarkLink.addAttribute("onclick", "bookmarkCourse(jQuery(this).data('courseid'), event);");
            addBookmarkLink.addAttribute("title", "Bookmark");
        }

        //Add to plan action or status "is planned"
        plannedSpan.addAttribute("id", cid + "_planned_status");
        plannedSpan.addAttribute("class", "plan");


        addToPlanLink.addAttribute("id", cid + "_add_to_plan_anchor");
        addToPlanLink.addAttribute("class", "add-to-plan-link");

        addToPlanLink.addAttribute("data-courseid", courseId);
        addToPlanLink.addAttribute("data-coursexid", cid);
        addToPlanLink.setText(" ");

        if (isPlanned()) {
            //Currently Added to planner, somewhere
            addToPlanLink.addAttribute("class", "ks-fontello-icon-ok-circled");
            addToPlanLink.addAttribute("title", "Planned");
        } else {
            //Not planned anywhere
            addToPlanLink.addAttribute("class", "ks-fontello-icon-hollow-circled-plus");
            addToPlanLink.addAttribute("onclick", "ksapOpenPlanDialog('course_add_course_page','planner','startAddCourseToPlanDialog', this, event);");
            addToPlanLink.addAttribute("title", "Add to Plan");
        }

		return actionDiv.asXML();
	}

	/**
	 * Convert a set of facet keys to a string for passing as a column in the
	 * results for this item.
	 * 
	 * @param facetKeys
	 *            The facet keys related to this item.
	 * @return A string for passing as a column in the results.
	 */
	protected List<KeyValue> facetString(Collection<String> facetKeys) {
		List<KeyValue> facetValues = new ArrayList<KeyValue>();
		for (final String fk : facetKeys)
			facetValues.add(new KeyValue() {
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
		return Collections.synchronizedList(Collections.unmodifiableList(facetValues));
	}

	@Override
	public String[] getSearchColumns() {
		return new String[] {
				getCode(), getInquiryLink(), getCredit(),
				getScheduledTerms(), getOfferedTerms(), getGenEduReqForUI(),
				getStatusColumn(), };
	}

	@Override
	public String[] getSortColumns() {
		return sortColumns == null ? sortColumns = new String[] { getCode(),
				getCourseName() } : sortColumns;
	}

	@Override
	public FacetIndex getFacetColumns() {
		if (facetColumns == null) {
            Map<Object, List<KeyValue>> map = new HashMap<Object, List<KeyValue>>();
            FacetIndexImpl m = new FacetIndexImpl(map);
			m.put("facet_quarter", facetString(getTermsFacetKeys()));
			m.put("facet_genedureq", facetString(getGenEduReqFacetKeys()));
			m.put("facet_credits", facetString(getCreditsFacetKeys()));
			m.put("facet_level", facetString(getCourseLevelFacetKeys()));
			m.put("facet_curriculum", facetString(getCurriculumFacetKeys()));
			facetColumns = m;
		}
		return facetColumns;
	}

	@Override
	public String toString() {
		return String.format("%s: %s", getCode(), getCourseId());
	}

    public String getCampusString(){
        StringBuilder campusBuilder = new StringBuilder("");
        for(String campus : getCampuses()){
            campusBuilder.append(campus);
            campusBuilder.append(" ");
        }
        return campusBuilder.toString();
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    @Override
    public String getVersionIndependentId() {
        return versionIndependentId;
    }

    public void setVersionIndependentId(String versionIndependentId) {
        this.versionIndependentId = versionIndependentId;
    }

    @Override
    public boolean isSearchExceeded() {
        return searchExceeded;
    }

    public void setSearchExceeded(boolean searchExceeded) {
        this.searchExceeded = searchExceeded;
    }

    /**
     * Creates a formatted string for the display of the general education requirements in the results table.
     *
     * @return formatted general education output
     */
    private String getGenEduReqForUI(){
        List<String> genEdReqCodes = this.getGenEduReqs();
        if(genEdReqCodes==null || genEdReqCodes.isEmpty()){
            return EMPTY_RESULT_VALUE_KEY;
        }

        Element genEdContainer = DocumentHelper.createElement("div");
        for(String genEd : genEdReqCodes){
             /* Doing this to fix a bug in IE8 which is trimming off the I&S as I */
            if (genEd.contains("&")) {
                genEd = genEd.replace("&", "&amp;");
            }
            if (!genEdContainer.elements().isEmpty()) {
                genEdContainer.addElement("br");
            }
            Element newGenEd = genEdContainer.addElement("abbr");
            newGenEd.setText(genEd);
            String genEdTitle = KsapFrameworkServiceLocator.getCourseSearchStrategy().getGenEdMap().get(genEd);
            newGenEd.addAttribute("title",genEdTitle);
        }

        return genEdContainer.asXML();
    }

}
