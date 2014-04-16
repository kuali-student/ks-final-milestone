package org.kuali.rice.krad.uif.container.extension;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.util.type.TypeUtils;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.field.DataFieldBase;

import java.util.List;

/**
 * Addresses three issues
 *
 * 1. Supports DL/DD
 * 2. Applies the styleClasses on the filed to the list container and provides a property to allow the list item value to be applied as a style also
 * 3. Uses StringBuilder instead of String concatinations to improve memory usage
 *
 * @Author KSAP Team
 * Date: 3/22/13
 */
public class ListDataField extends DataFieldBase {


    private static final String DL = "DL";

    private boolean applyCssOnItem = false;

    public boolean isApplyCssOnItem() {
        return applyCssOnItem;
    }

    public void setApplyCssOnItem(boolean applyCssOnItem) {
        this.applyCssOnItem = applyCssOnItem;
    }

    /**
     * Besides the default list items, DD/DL is also now supported.
     * The style classes are also not hard coded now and can be picked up from the dataFields styleClasses method
     *
     * @param list readonly list content to be displayed as a HTML definition list
     * @return 
     */
    @Override
    protected String generateReadOnlyListDisplayReplacement(List<?> list) {
        StringBuilder generatedHtml = new StringBuilder();

        //Default to delimited if nothing is set
        if (getReadOnlyListDisplayType() == null) {
            this.setReadOnlyListDisplayType(UifConstants.ReadOnlyListTypes.DELIMITED.name());
        }

        //begin generation setup
        if (getReadOnlyListDisplayType().equalsIgnoreCase(UifConstants.ReadOnlyListTypes.UL.name())) {
            generatedHtml.append("<ul class='uif-readOnlyStringList ").append(getStyleClassesAsString()).append("'>");
        } else if (getReadOnlyListDisplayType().equalsIgnoreCase(UifConstants.ReadOnlyListTypes.OL.name())) {
            generatedHtml.append("<ol class='uif-readOnlyStringList ").append(getStyleClassesAsString()).append("'>");
        } else if (getReadOnlyListDisplayType().equalsIgnoreCase(DL)) {
            generatedHtml.append("<dl class='uif-readOnlyStringList ").append(getStyleClassesAsString()).append("'>");
        } else if (getReadOnlyListDisplayType().equalsIgnoreCase(UifConstants.ReadOnlyListTypes.BREAK.name())) {
            setReadOnlyListDelimiter("<br/>");
        } else if (this.getReadOnlyListDelimiter() == null) {
            setReadOnlyListDelimiter(", ");
        }

        //iterate through each value
        for (Object value : list) {
            //if blank skip
            if (!TypeUtils.isSimpleType(value.getClass()) || StringUtils.isBlank(value.toString())) {
                continue;
            }

            //handle mask if any
            if (isApplyMask()) {
                value = getMaskFormatter().maskValue(value);
            }


            // TODO KSAP-740: Investigate using something similar to CrudMessageMatrixFormatter style tag generation
            // As well as a string formatter
            // the value should use the formatted text property value we would expect to see instead of toString
            //two types - delimited and html list
            if (getReadOnlyListDisplayType().equalsIgnoreCase(UifConstants.ReadOnlyListTypes.UL.name())
                    || getReadOnlyListDisplayType().equalsIgnoreCase(UifConstants.ReadOnlyListTypes.OL.name())) {
                generatedHtml.append("<li");
                if(applyCssOnItem) generatedHtml.append(" '").append(value.toString()).append("'");
                generatedHtml.append(">").append(value.toString()).append("</li>");
            } else if (getReadOnlyListDisplayType().equalsIgnoreCase(DL)) {
                generatedHtml.append("<dd");
                if(applyCssOnItem) generatedHtml.append(" '").append(value.toString()).append("'");
                generatedHtml.append(">").append(value.toString()).append("</dd>");
            } else {
                //no matching needed - delimited is always the fallback and break uses same logic
                generatedHtml.append(value.toString()).append(this.getReadOnlyListDelimiter());
            }
        }

        //end the generation
        if (getReadOnlyListDisplayType().equalsIgnoreCase(UifConstants.ReadOnlyListTypes.UL.name())) {
            generatedHtml.append("</ul>");
        } else if (getReadOnlyListDisplayType().equalsIgnoreCase(UifConstants.ReadOnlyListTypes.OL.name())) {
            generatedHtml.append("</ol>");
        } else if (getReadOnlyListDisplayType().equalsIgnoreCase(DL)) {
            generatedHtml.append("</dl>");
        }else {
            generatedHtml.delete(generatedHtml.length() - this.getReadOnlyListDelimiter().length(), generatedHtml.length() );
        }

        if (generatedHtml.length() > 0) {
            this.setReadOnlyDisplayReplacement(generatedHtml.toString());
        } else {
            //this must be done or the ftl will skip and throw error
            this.setReadOnlyDisplayReplacement("&nbsp;");
        }
        return getReadOnlyDisplayReplacement();
    }
}
