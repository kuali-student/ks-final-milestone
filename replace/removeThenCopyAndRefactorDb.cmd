echo ----------------------------------------------
echo %MODULE%
echo ----------------------------------------------
set MODULE=%1
set QUALIFIER=%2
set VERSION=%3
set DIR=https://test.kuali.org/svn/student/deploymentlab/trunk/1.0.x/ks-cfg-dbs/%MODULE%
set SCHEMA=%DIR%/src/main/resources/impex/schema
set DATA=%DIR%/src/main/resources/impex/data
set DEV=https://test.kuali.org/svn/student/ks-cfg-dbs/%MODULE%/%QUALIFIER%
rem set DEV=https://test.kuali.org/svn/student/deploymentlab/%QUALIFIER%/ks-cfg-dbs-%VERSION%/%MODULE%
svn delete -m "Removing %MODULE%" %DIR%/src
svn mkdir --parents -m "Creating schema dir" %SCHEMA%
svn copy -m "Copying ks-cfg-dbs/%MODULE%/%QUALIFIER%/%MODULE%-%VERSION% from development svn" --username jcaddel --password gw570229 %DEV% %DATA%
svn delete -m "Cleaning up %MODULE%" %DATA%/doc
svn delete -m "Cleaning up %MODULE%" %DATA%/.project
svn delete -m "Cleaning up %MODULE%" %DATA%/data.dtd
svn delete -m "Cleaning up %MODULE%" %DATA%/database.dtd
svn copy -m "Renaming/moving schema.xml" %DATA%/schema.xml %SCHEMA%/%MODULE%-schema.xml
svn delete -m "Cleaning up %MODULE%" %DATA%/schema.xml
