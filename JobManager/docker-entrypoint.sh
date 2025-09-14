#!/bin/sh

# Watch for changes in src/ and trigger Gradle compile
while inotifywait -r -e modify,create,delete /app/src; do
    echo "Changes detected. Recompiling..."
    gradle compileJava --no-daemon
done &

# Run Spring Boot in bootRun mode (DevTools will reload on class changes)
gradle bootRun --no-daemon
