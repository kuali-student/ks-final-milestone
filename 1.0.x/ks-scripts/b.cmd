cd ..
call mvn -o -Dmaven.test.skip=true -Dks.java2ws.phase=none -Pkuali-developer-properties install
cd ks-scripts
