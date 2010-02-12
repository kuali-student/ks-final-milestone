package com.google.gwt.user.client.ui;

import com.google.gwt.resources.client.ResourcePrototype;
import com.google.gwt.core.client.GWT;

public class com_google_gwt_user_client_ui_DisclosurePanel_DefaultImages_default_InlineClientBundleGenerator implements com.google.gwt.user.client.ui.DisclosurePanel.DefaultImages {
  public com.google.gwt.resources.client.ImageResource disclosurePanelClosed() {
    if (disclosurePanelClosed == null) {
      disclosurePanelClosed = new com.google.gwt.resources.client.impl.ImageResourcePrototype(
        "disclosurePanelClosed",
        com.google.gwt.i18n.client.LocaleInfo.getCurrentLocale().isRTL() ?internedUrl2 : internedUrl1,
        0, 0, 16, 16, false, false
      );
    }
    return disclosurePanelClosed;
  }
  public com.google.gwt.resources.client.ImageResource disclosurePanelOpen() {
    if (disclosurePanelOpen == null) {
      disclosurePanelOpen = new com.google.gwt.resources.client.impl.ImageResourcePrototype(
        "disclosurePanelOpen",
        internedUrl0,
        0, 0, 16, 16, false, false
      );
    }
    return disclosurePanelOpen;
  }
  private static final java.lang.String internedUrl0 = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAjUlEQVR42mNgGD6gsLCQMy0t7TAQXyICn0lOThbCMCQ1NTUfKPmfEAaq68XqitDQUGaggqsEDHgFxPw4vZKenu6BzwCgfDLB8AAq3IjDgPNEBSgwgFSAin9iMcCG6FgBBRSa5qUkRWtWVhYPUNNzqOZvQCxHctoABRg02urITmCgAAUlMrINAKWNwZ2HAAhGkVd3k7/tAAAAAElFTkSuQmCC";
  private static final java.lang.String internedUrl1 = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAfklEQVR42mNgoDZITk4WosiAtLS0M6mpqb1Amp9cAy4B8X8gfpWenp5MiQEwfB6IbSgxAIaXArEcJQaA8Ddg+NQVFhZykmsADG8MDQ1lJseA5wQDFocBP0FRm5WVxUNOGGwEJi4VcmLhKtC5HuSkg8NA5+bjDCRCAG8UDUoAAIw8kVdwMG+3AAAAAElFTkSuQmCC";
  private static final java.lang.String internedUrl2 = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAkElEQVR42mNgoCdITk4WIktjWloaf2pqai+QPkOy5vT09GSgxldA/B+IL5Fiqw0Qn4dq/E+0AUBFckC8FE0jYQMKCws5gf6sAyr6hkMzbgNCQ0OZgZIb8WgkzgvQAHtOtgEgkJWVxQONsp9kGYCUaFSweOsSOenAA6jxKtkGwAIZ6K18oAGHKcoLoKhmGHQAAOyGkVflBH+sAAAAAElFTkSuQmCC";
  private static com.google.gwt.resources.client.ImageResource disclosurePanelClosed;
  private static com.google.gwt.resources.client.ImageResource disclosurePanelOpen;
  
  public ResourcePrototype[] getResources() {
    return new ResourcePrototype[] {
      disclosurePanelClosed(), 
      disclosurePanelOpen(), 
    };
  }
  public native ResourcePrototype getResource(String name) /*-{
    switch (name) {
      case 'disclosurePanelClosed': return this.@com.google.gwt.user.client.ui.DisclosurePanel.DefaultImages::disclosurePanelClosed()();
      case 'disclosurePanelOpen': return this.@com.google.gwt.user.client.ui.DisclosurePanel.DefaultImages::disclosurePanelOpen()();
    }
    return null;
  }-*/;
}
