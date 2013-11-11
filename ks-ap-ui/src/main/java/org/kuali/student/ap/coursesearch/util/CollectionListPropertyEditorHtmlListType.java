package org.kuali.student.ap.coursesearch.util;

/**
 * Defines the type of list that should be rendered in a CollectionListFormatter.
 */
public enum CollectionListPropertyEditorHtmlListType {

    /** Output an unordered list. */
    UL("ul", "li"),
    /** Output an ordered list. */
    OL("ol","li"),
    /** Output a data definition list. */
    DL("dl", "dd");

    private String listElementName;
    private String itemElementName;

    CollectionListPropertyEditorHtmlListType(String listElementName, String itemElementName) {
        this.listElementName = listElementName;
        this.itemElementName = itemElementName;
    }

    public String getListElementName() {
        return this.listElementName;
    }

    public String getListItemElementName() {
        return this.itemElementName;
    }
}