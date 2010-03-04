date /t
time /t

cd ..\student
call mvn -Dmaven.test.skip=true -o -Pkuali-developer-properties -Dks.gwt.compile.phase=none clean install

cd ..\ks-web\ks-embedded
call mvn -Dmaven.test.skip=true -o -Pkuali-developer-properties clean install

cd ..\..\maven\resources

date /t
time /t
