date /t
time /t
cd ..\..\..\kul-cfg-dbs\impex
call ant check-import-properties create-ddl dataxml-to-sql
cd ..\..\1.0.0-m4\ks-cfg-dbs\ks-embedded-db
call mvn -Dks.embedded.retain.db=false process-sources
date /t
time /t

