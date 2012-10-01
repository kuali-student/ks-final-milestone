package org.kuali.student.r2.common.util.constants;

import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.core.document.dto.DocumentCategoryInfo;
import org.kuali.student.r2.core.document.dto.DocumentInfo;
import org.kuali.student.r2.core.document.dto.RefDocRelationInfo;

/**
 * This class holds the constants used by the Document service
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 *
 */
public class DocumentServiceConstants {
    /**
     * Reference Object URI's
     */
    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "document";
    public static final String REF_OBJECT_URI_DOCUMENT = NAMESPACE + "/" + DocumentInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_DOCUMENT_CATEGORY = NAMESPACE + "/" + DocumentCategoryInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_DOCUMENT_REF_DOC_RELATION = NAMESPACE + "/" + RefDocRelationInfo.class.getSimpleName();

}
