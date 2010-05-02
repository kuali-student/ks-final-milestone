call mvn release:clean
call mvn --batch-mode release:prepare
call mvn --batch-mode release:perform
