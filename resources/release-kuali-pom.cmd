cd ..\kuali
call mvn --batch-mode -Dparent.pom.release=true release:prepare
call mvn --batch-mode -Dparent.pom.release=true release:perform
cd ..\resources