package org.kuali.student.cm.course.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krms.dto.AgendaEditor;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.dto.RuleTypeInfo;
import org.kuali.student.cm.common.util.CurriculumManagementConstants;
import org.kuali.student.cm.common.util.CurriculumManagementConstants.Export.FileType;
import org.kuali.student.cm.course.form.wrapper.*;
import org.kuali.student.cm.proposal.form.wrapper.ProposalElementsWrapper;
import org.kuali.student.cm.proposal.form.wrapper.SupportingDocumentInfoWrapper;
import org.kuali.student.common.ui.client.util.ExportElement;
import org.kuali.student.r1.core.workflow.dto.CollaboratorWrapper;
import org.kuali.student.r2.lum.clu.dto.CluInstructorInfo;
import org.kuali.student.r2.lum.course.dto.*;
import org.kuali.student.r2.lum.lo.dto.LoCategoryInfo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Kuali Student Team
 */
public class ExportRetireCourseHelperImpl extends AbstractExportCourseHelperImpl {

    /**
     * Constructor. Sets headers to "save as".
     *
     * @param retireCourseWrapper The data object to export.
     * @param exportFileType The metadata for the type of file to output.
     */
    public ExportRetireCourseHelperImpl(RetireCourseWrapper retireCourseWrapper, FileType exportFileType) {
        super(retireCourseWrapper, "Filename" ,exportFileType, true);
    }

    /**
     * Constructor.
     *
     * @param retireCourseWrapper The data object to export.
     * @param exportFileType The metadata for the type of file to output.
     * @param saveDocument If 'true' sets the document headers to values which encourage the web browers to display a
     *                     "save as" dialog. Otherwise, sets them values which encourage the browser to open the document..
     */
    public ExportRetireCourseHelperImpl(RetireCourseWrapper retireCourseWrapper, FileType exportFileType, boolean saveDocument) {
        super(retireCourseWrapper, "Filename", exportFileType, saveDocument);
    }

    /**
     * This method constructs list of exportElement based on each field in courseInfoWrapper.
     *
     * @param wrapper
     * @return List of constructed exportElement based on course/proposal.
     */
    public List<ExportElement> constructExportElementBasedOnView(ProposalElementsWrapper wrapper, boolean isProposal ) {

        RetireCourseWrapper retireCourseWrapper = (RetireCourseWrapper) wrapper;

        List<ExportElement> exportElements = new ArrayList<ExportElement>();

        //Here comes the logic for building List<ExportElement>

        return exportElements;
    }

}
