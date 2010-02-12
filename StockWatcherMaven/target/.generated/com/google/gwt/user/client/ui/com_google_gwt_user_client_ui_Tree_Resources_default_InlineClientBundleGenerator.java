package com.google.gwt.user.client.ui;

import com.google.gwt.resources.client.ResourcePrototype;
import com.google.gwt.core.client.GWT;

public class com_google_gwt_user_client_ui_Tree_Resources_default_InlineClientBundleGenerator implements com.google.gwt.user.client.ui.Tree.Resources {
  public com.google.gwt.resources.client.ImageResource treeClosed() {
    if (treeClosed == null) {
      treeClosed = new com.google.gwt.resources.client.impl.ImageResourcePrototype(
        "treeClosed",
        internedUrl0,
        0, 0, 16, 16, false, false
      );
    }
    return treeClosed;
  }
  public com.google.gwt.resources.client.ImageResource treeLeaf() {
    if (treeLeaf == null) {
      treeLeaf = new com.google.gwt.resources.client.impl.ImageResourcePrototype(
        "treeLeaf",
        internedUrl2,
        0, 0, 16, 16, false, false
      );
    }
    return treeLeaf;
  }
  public com.google.gwt.resources.client.ImageResource treeOpen() {
    if (treeOpen == null) {
      treeOpen = new com.google.gwt.resources.client.impl.ImageResourcePrototype(
        "treeOpen",
        internedUrl1,
        0, 0, 16, 16, false, false
      );
    }
    return treeOpen;
  }
  private static final java.lang.String internedUrl0 = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAqklEQVR42mNgGAVYQVbl9v9h6WsxcHbVjv8ENScXbf6/7+iD/79+/fmPDP78/fd/9eZr/xMLN+E3JCJj3f/PX77DMQPDGTj72/cfQJesw29AONCAt+8+wjHIAGQ+UQY8ffYKrBEdg8RBYUGUATAM0wjDhA0AOvHOvUfkGxCXt+H/zEXH/p+/ePP/5au34fjshZv/J84+/B8kTzAqU0s3Y00HKcWb/w/DZA8Agl3oOCRuvDUAAAAASUVORK5CYII=";
  private static final java.lang.String internedUrl1 = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAp0lEQVR42mNgGAVYQVbl9v9h6WsxcHbVjv8ENScXbf6/7+iD/79+/fmPDP78/fd/9eZr/xMLN+E3JCJj3f/PX77D8dt3H+Hsb99/AF2yDr8B4UADQJpwYaIMePrsFVDRGQwMEgeFBVEGwDDIVmQ+YQOATrxz7xH5BsTlbfg/c9Gx/+cv3vx/+eptOD574eb/ibMP/wfJE4zK1NLNWNNBSvHm/8Mw2QMAtl7wOMv7lGEAAAAASUVORK5CYII=";
  private static final java.lang.String internedUrl2 = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAEklEQVR42mNgGAWjYBSMAggAAAQQAAGvRYgsAAAAAElFTkSuQmCC";
  private static com.google.gwt.resources.client.ImageResource treeClosed;
  private static com.google.gwt.resources.client.ImageResource treeLeaf;
  private static com.google.gwt.resources.client.ImageResource treeOpen;
  
  public ResourcePrototype[] getResources() {
    return new ResourcePrototype[] {
      treeClosed(), 
      treeLeaf(), 
      treeOpen(), 
    };
  }
  public native ResourcePrototype getResource(String name) /*-{
    switch (name) {
      case 'treeClosed': return this.@com.google.gwt.user.client.ui.Tree.Resources::treeClosed()();
      case 'treeLeaf': return this.@com.google.gwt.user.client.ui.Tree.Resources::treeLeaf()();
      case 'treeOpen': return this.@com.google.gwt.user.client.ui.Tree.Resources::treeOpen()();
    }
    return null;
  }-*/;
}
