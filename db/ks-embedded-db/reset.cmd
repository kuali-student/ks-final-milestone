call mvn clean
call mvn -o torque:sql antrun:run torque:datadtd torque:datasql
set NLS_DATE_FORMAT=yyyymmddHH24miss