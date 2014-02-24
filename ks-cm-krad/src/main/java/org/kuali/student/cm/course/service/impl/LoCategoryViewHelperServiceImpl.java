package org.kuali.student.cm.course.service.impl;


import org.apache.commons.beanutils.BeanUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.service.impl.ViewHelperServiceImpl;
import org.kuali.rice.krad.uif.util.ObjectPropertyUtils;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.cm.common.util.CurriculumManagementConstants;
import org.kuali.student.logging.FormattedLogger;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.LearningObjectiveServiceConstants;
import org.kuali.student.r2.lum.lo.dto.LoCategoryInfo;
import org.kuali.student.r2.lum.lo.service.LearningObjectiveService;

import javax.xml.namespace.QName;
import java.util.Collection;
import java.util.List;

public class LoCategoryViewHelperServiceImpl extends ViewHelperServiceImpl {

    private transient LearningObjectiveService loService;
    public final String ACTIVE = "Active";

    @Override
    protected void processBeforeAddLine(View view, CollectionGroup collectionGroup, Object model, Object addLine) {
        LoCategoryInfo loCategory = (LoCategoryInfo) addLine;
        loCategory.setStateKey(ACTIVE);
        loCategory.setLoRepositoryKey(CurriculumManagementConstants.KUALI_LO_REPOSITORY_KEY_SINGLE_USE);
        try {
            LoCategoryInfo savedLoCat = getLoService().createLoCategory(loCategory.getTypeKey(), loCategory,
                    ContextUtils.getContextInfo());
            BeanUtils.copyProperties(loCategory, savedLoCat);
        } catch (DataValidationErrorException e) {
            GlobalVariables.getMessageMap().putErrorForSectionId(CurriculumManagementConstants.KS_LO_CAT_TABLE, CurriculumManagementConstants.MessageKeys.ERROR_LO_CATEGORY_DUPLICATE);
        } catch (Exception e) {
            FormattedLogger.error("An error occurred while trying to create a new Learning Objective Category: %s",
                    e.getMessage());
        }
    }

    @Override
    protected void processBeforeSaveLine(View view, CollectionGroup collectionGroup, Object model, Object updateLine) {
        LoCategoryInfo loCategory = (LoCategoryInfo) updateLine;
        //The 'loRepositoryKey' is not retrieved on the initial load of the existing Categories, so we are setting it again.
        loCategory.setLoRepositoryKey(CurriculumManagementConstants.KUALI_LO_REPOSITORY_KEY_SINGLE_USE);
        try {
            LoCategoryInfo updatedLoCat = getLoService().updateLoCategory(loCategory.getId(), loCategory,
                    ContextUtils.getContextInfo());
            BeanUtils.copyProperties(loCategory, updatedLoCat);
        } catch (DataValidationErrorException e) {
            GlobalVariables.getMessageMap().putErrorForSectionId(CurriculumManagementConstants.KS_LO_CAT_TABLE, CurriculumManagementConstants.MessageKeys.ERROR_LO_CATEGORY_DUPLICATE);
        } catch (Exception e) {
            FormattedLogger.error("An error occurred while updating the Learning Objective Category: %s",
                    e.getMessage());
        }
    }

    /**
     * This method was overridden to perform the database delete action. Since there's no
     * 'processBeforeDeleteLine' method and the 'processAfterDeleteLine' method doesn't
     * contain the deleted item anymore, the original method was copied and the database
     * delete method call was inserted.
     * 
     * @see org.kuali.rice.krad.uif.service.impl.ViewHelperServiceImpl#processCollectionDeleteLine(org.kuali.rice.krad.uif.view.View,
     * java.lang.Object, java.lang.String, int)
     */
    @Override
    public void processCollectionDeleteLine(View view, Object model, String collectionPath, int lineIndex) {
        // get the collection group from the view
        CollectionGroup collectionGroup = view.getViewIndex().getCollectionGroupByPath(collectionPath);
        if (collectionGroup == null) {
            logAndThrowRuntime(CurriculumManagementConstants.MessageKeys.ERROR_UNABLE_TO_GET_COLLECTION_GROUP + collectionPath);
        }

        // get the collection instance for adding the new line
        Collection<Object> collection = ObjectPropertyUtils.getPropertyValue(model, collectionPath);
        if (collection == null) {
            logAndThrowRuntime(CurriculumManagementConstants.MessageKeys.ERROR_UNABLE_TO_GET_COLLECTION_PROPERTY + collectionPath);
        }

        // TODO: look into other ways of identifying a line so we can deal with
        // unordered collections
        if (collection instanceof List) {
            Object deleteLine = ((List<Object>) collection).get(lineIndex);

            // validate the delete action is allowed for this line
            boolean isValid = performDeleteLineValidation(view, collectionGroup, deleteLine);
            if (isValid) {
                //This part was altered to include the database delete method call.
                LoCategoryInfo loCategory = (LoCategoryInfo) deleteLine;
                try {
                    getLoService().deleteLoCategory(loCategory.getId(), ContextUtils.getContextInfo());
                } catch (Exception e) {
                    FormattedLogger.error("An error occurred while trying to delete a Learning Objective Category: %s",
                            e.getMessage());
                }
                ((List<Object>) collection).remove(lineIndex);
                processAfterDeleteLine(view, collectionGroup, model, lineIndex);
            }
        } else {
            logAndThrowRuntime(CurriculumManagementConstants.MessageKeys.ERROR_LIST_COLLECTION_IMPLEMENTATIONS_SUPPORTED_FOR_DELETE_INDEX);
        }
    }

    protected LearningObjectiveService getLoService() {
        if (loService == null) {
            loService = GlobalResourceLoader.getService(new QName(LearningObjectiveServiceConstants.NAMESPACE,
                    LearningObjectiveService.class.getSimpleName()));
        }
        return loService;
    }

}
