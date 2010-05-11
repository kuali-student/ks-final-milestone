UBC Cdm Kuali Student Embedded Readme
FS TESTING2 svn commit as  dchew

Notes:
- configured for M5, need to run impex for ks-embedded for M5
- ks-embedded-config is located in src\main\resources, place that in {user_home}\kuali\main\dev
- pom.xml is configured to compile instituion specific GWT entry point: src\main\java\ca\ubc\student\kuali\lum\lu\ui\main\CDMLUMMain.gwt.xml
-- doesn't work as LUMMain.jsp needs to point to ca.ubc.student.kuali.lum.lu.ui.main.CDMLUMMain.nocache.js and other paths to resources are hard coded to old GWT entry point
