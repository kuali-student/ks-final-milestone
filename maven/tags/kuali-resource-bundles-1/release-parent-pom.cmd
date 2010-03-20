cd ..\%1
call mvn clean install
call mvn --batch-mode release:prepare
call mvn --batch-mode release:perform
cd ..\resources