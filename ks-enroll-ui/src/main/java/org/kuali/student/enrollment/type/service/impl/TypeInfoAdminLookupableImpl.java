package org.kuali.student.enrollment.type.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.enrollment.common.util.ContextBuilder;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.constants.TypeServiceConstants;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;

public class TypeInfoAdminLookupableImpl
        extends LookupableImpl {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(TypeInfoAdminLookupableImpl.class);
    private transient TypeService typeService;
    private final static String SEARCH_VALUE = "searchValue";

    @Override
    protected List<TypeInfo> getSearchResults(LookupForm lookupForm, Map<String, String> fieldValues, boolean unbounded) {

        String searchValue = fieldValues.get(SEARCH_VALUE);
        searchValue = searchValue.toLowerCase();
        return this.findMatchingTypes(searchValue);
    }

    private List<TypeInfo> findMatchingTypes(String searchValue) {
        // TODO: replace this with a search
        List<TypeInfo> allTypes = this.getAllTypes();
        List<TypeInfo> list = new ArrayList<TypeInfo>();
        for (TypeInfo info : allTypes) {
            if (this.matches(info, searchValue)) {
                list.add(info);
            }
        }
        return list;
    }

    private boolean matches(TypeInfo info, String searchValue) {
        if (searchValue == null) {
            return true;
        }
        if (searchValue.isEmpty()) {
            return true;
        }
        if (contains(info.getKey(), searchValue)) {
            return true;
        }
        if (contains(info.getName(), searchValue)) {
            return true;
        }
        if (contains(info.getRefObjectUri(), searchValue)) {
            return true;
        }

        if (contains(info.getDescr(), searchValue)) {
            return true;
        }
        return false;
    }

    private boolean contains(RichTextInfo fieldValue, String searchValue) {
        if (fieldValue == null) {
            return false;
        }
        if (contains(fieldValue.getPlain(), searchValue)) {
            return true;
        }
        if (contains(fieldValue.getFormatted(), searchValue)) {
            return true;
        }
        return false;
    }

    private boolean contains(Object fieldValue, String searchValue) {
        if (fieldValue == null) {
            return false;
        }
        String fieldValueString = fieldValue.toString().toLowerCase();
        if (fieldValueString.contains(searchValue)) {
            return true;
        }
        return false;
    }

    private List<TypeInfo> getAllTypes() {
        List<TypeInfo> list = new ArrayList<TypeInfo>();
        List<String> refObjectUris;
        try {
            refObjectUris = this.getTypeService().getRefObjectUris(getContextInfo());
        } catch (InvalidParameterException ex) {
            throw new RuntimeException(ex);
        } catch (MissingParameterException ex) {
            throw new RuntimeException(ex);
        } catch (OperationFailedException ex) {
            throw new RuntimeException(ex);
        } catch (PermissionDeniedException ex) {
            throw new RuntimeException(ex);
        }
        for (String refObjectUri : refObjectUris) {
            try {
                list.addAll(this.getTypeService().getTypesByRefObjectUri(refObjectUri, getContextInfo()));
            } catch (DoesNotExistException ex) {
                throw new RuntimeException(ex);
            } catch (InvalidParameterException ex) {
                throw new RuntimeException(ex);
            } catch (MissingParameterException ex) {
                throw new RuntimeException(ex);
            } catch (OperationFailedException ex) {
                throw new RuntimeException(ex);
            } catch (PermissionDeniedException ex) {
                throw new RuntimeException(ex);
            }
        }
        return list;
    }

    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

    public TypeService getTypeService() {
        if (typeService == null) {
            typeService = (TypeService) GlobalResourceLoader.getService(new QName(TypeServiceConstants.NAMESPACE,
                    TypeServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.typeService;
    }

    private ContextInfo getContextInfo() {
        return ContextBuilder.loadContextInfo();
    }
}
