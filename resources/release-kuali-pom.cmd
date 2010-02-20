cd ..\kuali
call mvn --batch-mode -Pkuali-release-lab release:prepare
call mvn --batch-mode -Pkuali-release-lab release:perform
cd ..\resources