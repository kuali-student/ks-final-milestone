date /t
time /t
sqlplus system/gw570229 < src/main/resources/sql/database.sql
cd ..\..\..\kul-cfg-dbs\impex
call ant check-import-properties create-ddl dataxml-to-sql apply-ddl apply-data-sql apply-constraint-ddl
cd ..\..\1.0.0-m4\ks-cfg-dbs\ks-embedded-db
date /t
time /t
