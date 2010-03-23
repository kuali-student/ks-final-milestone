date /t
time /t
sqlplus system/gw570229 < src/main/resources/sql/database.sql
cd ..\..\..\kul-cfg-dbs\impex
rem
rem ant import causes disk thrashing because it generates and then imports each .xml file one at a time
rem
rem This method is much quicker because it translates all the .xml files into .sql, then imports them all
rem
call ant check-import-properties create-ddl dataxml-to-sql apply-ddl apply-data-sql apply-constraint-ddl
cd ..\..\1.0.0-m4\ks-cfg-dbs\ks-embedded-db
date /t
time /t
