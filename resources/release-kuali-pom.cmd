cd ..\kuali
call mvn --batch-mode -Pkuali-release-lab=true release:prepare
call mvn --batch-mode -Pkuali-release-lab=true release:perform
cd ..\resources