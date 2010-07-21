package org.kuali.student.deployment.monitor.ui.client;

import java.util.Date;

import com.google.gwt.core.client.GWT;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


public class WebMonitorComponent extends UpdateableComponent {

    //~ Static fields/initializers ---------------------------------------------

    private static final int UPDATE_SECONDS = 30;

    //~ Instance fields --------------------------------------------------------

    private Label hostName = new Label();
    private Label serviceName = new Label();
    private Label threadCount = new Label();
    private Label availableProcessors = new Label();
    private Label startDate = new Label();
    private Label buildDate = new Label();
    private Label version = new Label();
    private Label buildNumber = new Label();
    private Label totalSessionCount = new Label();
    private Label activeSessionCount = new Label();
    private Label maxConcurrentSessionCount = new Label();
    private Label lastUpdateDate = new Label();
    private Label totalRequests = new Label();
    private Label averageRequestTime = new Label();
    private Label maxRequestTime = new Label();
    private Label requestStartTime = new Label();
    private final Button timerButton = new Button("Start Auto Update");
    private Image memorySnapshot;
    private VerticalPanel snapshotPanel;
    private final Application app;
    private boolean running = false;

    //~ Constructors -----------------------------------------------------------

    public WebMonitorComponent(Application app) {
        super();
        this.app = app;
        initUI();
    }

    //~ Methods ----------------------------------------------------------------

    @Override public void update() {
        app.fetchWebMonitorData();

    }

    public void update(WebMonitorData data) {
        hostName.setText(data.serverData.hostName);
        serviceName.setText(data.serverData.serviceName);
        startDate.setText(data.serverData.startDate.toString());
        threadCount.setText(String.valueOf(data.serverData.threadCount));
        availableProcessors.setText(String.valueOf(
                data.serverData.availableProcessors));

        version.setText(data.versionData.version);
        buildDate.setText(data.versionData.buildDate.toString());
        buildNumber.setText(data.versionData.buildNumber);

        totalSessionCount.setText(String.valueOf(
                data.sessionData.totalSessionCount));
        activeSessionCount.setText(String.valueOf(
                data.sessionData.activeSessionCount));
        maxConcurrentSessionCount.setText(String.valueOf(
                data.sessionData.maxConcurrentSessionCount));
        totalRequests.setText(data.requestData.totalRequests);
        averageRequestTime.setText(data.requestData.averageRequestTime);
        maxRequestTime.setText(data.requestData.maxRequestTime);
        requestStartTime.setText(data.requestData.startTime);
        lastUpdateDate.setText("Last updated on: " + new Date().toString());
        reloadMemoryMonitor();


    }

    private void initUI() {
        VerticalPanel rootPanel = new VerticalPanel();
        initWidget(rootPanel);
        rootPanel.setWidth("100%");
        rootPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);

        VerticalPanel statsPanel = new VerticalPanel();
        statsPanel.setWidth("100%");
        statsPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);

        createServiceInfo(statsPanel);
        createSessionInfo(statsPanel);
        createRequestInfo(statsPanel);
        createBuildInfo(statsPanel);

        HorizontalPanel infoPanel = new HorizontalPanel();
        infoPanel.add(statsPanel);
        createMemoryMonitor(infoPanel);
        rootPanel.add(infoPanel);

        Button updateButton = new Button("Manual Update");
        
        updateButton.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent sender) {
                    update();
                }
            });
        rootPanel.add(updateButton);

        timerButton.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent sender) {

                    if (running) {
                        stopAutoUpdate();

                    } else {
                        startAutoUpdate();
                    }
                }
            });
        rootPanel.add(timerButton);
        rootPanel.add(lastUpdateDate);

    }

    private void stopAutoUpdate() {
        timerButton.setText("Start Auto Update");
        stopUpdateTimer();
        running = false;

    }

    private void startAutoUpdate() {
        timerButton.setText("Stop Auto Update");
        startUpdateTimer(UPDATE_SECONDS);
        running = true;
    }

    /**
     * @param composite
     */
    private void createMemoryMonitor(HorizontalPanel parent) {
        snapshotPanel = new VerticalPanel();
        snapshotPanel.setWidth("100%");
        snapshotPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);

        Label titleBar = new Label("Memory Monitor");
        titleBar.setStyleName("titlebar");
        memorySnapshot = new Image(GWT.getModuleBaseURL() +
                Constants.MEMORY_SNAPSHOT_NAME);
        memorySnapshot.getElement().setId("pc-template-img");
        snapshotPanel.add(titleBar);
        snapshotPanel.add(memorySnapshot);
        parent.add(snapshotPanel);

    }

    private void reloadMemoryMonitor() {

        memorySnapshot.setUrl(GWT.getModuleBaseURL() +
            Constants.MEMORY_SNAPSHOT_NAME + "?" + System.currentTimeMillis());

    }

    private void createSessionInfo(Panel composite) {
        Label titleBar = new Label("Session Stats");
        titleBar.setStyleName("titlebar");

        Grid table = new Grid(3, 2);
        addDataRow(table, "Active Sessions", activeSessionCount, 0);
        addDataRow(table, "Total Sessions", totalSessionCount, 1);
        addDataRow(table, "MaxConcurrent Sessions", maxConcurrentSessionCount,
            2);
        composite.add(titleBar);
        composite.add(table);

    }

    private void createRequestInfo(Panel composite) {
        Label titleBar = new Label("Request Stats");
        titleBar.setStyleName("titlebar");

        Grid table = new Grid(4, 2);
        addDataRow(table, "Total Requests", totalRequests, 0);
        addDataRow(table, " Average Request Time (ms)", averageRequestTime, 1);
        addDataRow(table, "Max Request Time (ms)", maxRequestTime, 2);
        addDataRow(table, "Request Collection Start", requestStartTime, 3);
        composite.add(titleBar);
        composite.add(table);

    }

    private void createBuildInfo(Panel composite) {

        Label titleBar = new Label("Version");
        titleBar.setStyleName("titlebar");

        Grid table = new Grid(3, 2);
        addDataRow(table, "Version", version, 0);
        addDataRow(table, "Build Number", buildNumber, 1);
        addDataRow(table, "Build Date", buildDate, 2);
        composite.add(titleBar);
        composite.add(table);
    }

    private void createServiceInfo(Panel composite) {
        Label titleBar = new Label("Service");
        titleBar.setStyleName("titlebar");

        Grid table = new Grid(5, 2);
        addDataRow(table, "Host", hostName, 0);
        addDataRow(table, "Service", serviceName, 1);
        addDataRow(table, "Start Date", startDate, 2);
        addDataRow(table, "Available Processors", availableProcessors, 3);
        addDataRow(table, "Thread Count", threadCount, 4);
        composite.add(titleBar);
        composite.add(table);

    }

    private void addDataRow(Grid table, String name, Widget value, int row) {
        table.setWidget(row, 0, new Label(name));
        table.setWidget(row, 1, value);
        table.getCellFormatter().setStyleName(row, 0, "stat-name");
        table.getCellFormatter().setStyleName(row, 1, "stat-value");
    }


}
