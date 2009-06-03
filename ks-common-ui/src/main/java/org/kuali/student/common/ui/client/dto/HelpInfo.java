package org.kuali.student.common.ui.client.dto;

public class HelpInfo {

    private String id;
    private String url;
    private String shortVersion;
    private String title;

    public HelpInfo() {
        super();
    }

    
    public HelpInfo(String id) {
        super();
        this.id = id;
        //TODO Load help info based on this id when we have a help system. For now just set default values
        this.shortVersion = "Short help information";
        this.title = "Help title";
        this.url = "http://www.google.com/";
    }


    public HelpInfo(String id,  String shortVersion, String title, String url) {
        super();
        this.id = id;
        this.shortVersion = shortVersion;
        this.title = title;
        this.url = url;
    }

    public String getId() {
        return id;
    }
    public HelpInfo setId(String id) {
        this.id = id;
        return this;
    }
    public String getTitle() {
        return title;
    }
    public HelpInfo setTitle(String title) {
        this.title = title;
        return this;
    }
    public String getUrl() {
        return url;
    }
    public HelpInfo setUrl(String url) {
        this.url = url;
        return this;
    }
    public String getShortVersion() {
        return shortVersion;
    }
    public HelpInfo setShortVersion(String shortVersion) {
        this.shortVersion = shortVersion;
        return this;
    }


}
