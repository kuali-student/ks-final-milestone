package org.kuali.student.enrollment.uif.container;

import org.kuali.rice.krad.uif.container.CollectionGroup;

public class KSCollectionGroup extends CollectionGroup{

    private KSCollectionGroupBuilder collectionGroupBuilder;

    /**
     * <code>CollectionGroupBuilder</code> instance that will build the
     * components dynamically for the collection instance
     *
     * @return CollectionGroupBuilder instance
     */
    @Override
    public KSCollectionGroupBuilder getCollectionGroupBuilder() {
        if (this.collectionGroupBuilder == null) {
            this.collectionGroupBuilder = new KSCollectionGroupBuilder();
        }
        return this.collectionGroupBuilder;
    }

}
