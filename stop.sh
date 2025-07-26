#!/bin/bash

echo "🛑 Stopping Inkognito Virtual Diary..."
echo "====================================="

# Find and kill the Java process running the application
PID=$(ps aux | grep "spring-boot:run" | grep -v grep | awk '{print $2}')

if [ -n "$PID" ]; then
    echo "📱 Found application running with PID: $PID"
    kill $PID
    sleep 2
    
    # Check if process is still running
    if ps -p $PID > /dev/null; then
        echo "⚠️  Process still running, forcing termination..."
        kill -9 $PID
    fi
    
    echo "✅ Application stopped successfully!"
else
    echo "ℹ️  No running application found."
fi