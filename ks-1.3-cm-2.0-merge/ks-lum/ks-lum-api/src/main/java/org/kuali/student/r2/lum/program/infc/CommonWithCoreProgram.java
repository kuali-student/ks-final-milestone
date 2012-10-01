package org.kuali.student.r2.lum.program.infc;

import org.kuali.student.r2.common.infc.RichText;

import java.util.List;

/*
 * Fields that are common with the credential program
 * 
 * @author Kuali Student Team (Sambit)
 */
public interface CommonWithCoreProgram extends CommonWithCredentialProgram {

    /**
     * An URL for additional information about this program.
     * 
     * @name reference URL
     */
    public String getReferenceURL();

    /**
     * Narrative description of this program that will show up in Catalog
     */
    public RichText getCatalogDescr();

    /**
     * List of catalog targets where this program's information will be published.
     * 
     * Examples might include on-line catalog, printed catalog, departmental catalog, 
     * or freshman catalog.
     */
    public List<String> getCatalogPublicationTargets();
}
