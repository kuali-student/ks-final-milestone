call mvn -N -extraReleaseArguments=-N release:clean
call mvn --batch-mode -N -extraReleaseArguments=-N release:prepare
call mvn --batch-mode -N -extraReleaseArguments=-N release:perform
