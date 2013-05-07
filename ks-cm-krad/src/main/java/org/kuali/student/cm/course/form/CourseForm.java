/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 *
 */
package org.kuali.student.cm.course.form;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.mapping.Array;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.core.proposal.dto.ProposalInfo;
import org.kuali.student.r2.lum.clu.dto.CluInstructorInfo;
import org.kuali.student.r2.lum.course.dto.CourseCrossListingInfo;
import org.kuali.student.r2.lum.course.dto.CourseJointInfo;
import org.kuali.student.r2.lum.course.dto.CourseVariationInfo;

/**
 * This is the helper class for CourseView
 * 
 * @author OpenCollab/rSmart KRAD CM Conversion Alliance!
 */

public class CourseForm extends UifFormBase {

	private static final long serialVersionUID = -988885314122936950L;

	private ProposalInfo proposalInfo;

	private String courseTitle;

	private String transcriptCourseTitle;

	private String subjectArea;

	private String courseNumberSuffix;

	private List<CourseCrossListingInfo> crossListings;

	private List<CourseJointInfo> joints;

	private List<CourseVariationInfo> variations;

	private List<CluInstructorInfo> instructors;

	private RichTextInfo descr;

	public CourseForm() {
		this.proposalInfo = new ProposalInfo();
		this.crossListings = new ArrayList<CourseCrossListingInfo>();
		this.joints = new ArrayList<CourseJointInfo>();
		this.variations = new ArrayList<CourseVariationInfo>();
		this.instructors = new ArrayList<CluInstructorInfo>();
		this.descr = new RichTextInfo();
	}

	public String getCourseTitle() {
		return courseTitle;
	}

	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}

	public String getTranscriptCourseTitle() {
		return transcriptCourseTitle;
	}

	public void setTranscriptCourseTitle(String transcriptCourseTitle) {
		this.transcriptCourseTitle = transcriptCourseTitle;
	}

	public String getSubjectArea() {
		return subjectArea;
	}

	public void setSubjectArea(String subjectArea) {
		this.subjectArea = subjectArea;
	}

	public String getCourseNumberSuffix() {
		return courseNumberSuffix;
	}

	public void setCourseNumberSuffix(String courseNumberSuffix) {
		this.courseNumberSuffix = courseNumberSuffix;
	}

	public List<CourseCrossListingInfo> getCrossListings() {
		return crossListings;
	}

	public void setCrossListings(List<CourseCrossListingInfo> crossListings) {
		this.crossListings = crossListings;
	}

	public List<CourseJointInfo> getJoints() {
		return joints;
	}

	public void setJoints(List<CourseJointInfo> joints) {
		this.joints = joints;
	}

	public List<CourseVariationInfo> getVariations() {
		return variations;
	}

	public void setVariations(List<CourseVariationInfo> variations) {
		this.variations = variations;
	}

	public List<CluInstructorInfo> getInstructors() {
		return instructors;
	}

	public void setInstructors(List<CluInstructorInfo> instructors) {
		this.instructors = instructors;
	}

	public RichTextInfo getDescr() {
		return descr;
	}

	public void setDescr(RichTextInfo descr) {
		this.descr = descr;
	}

	public ProposalInfo getProposalInfo() {
		return proposalInfo;
	}

	public void setProposalInfo(ProposalInfo proposalInfo) {
		this.proposalInfo = proposalInfo;
	}

}