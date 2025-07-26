#!/bin/bash

echo "🔒 Starting Inkognito Virtual Diary..."
echo "======================================"

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "❌ Java is not installed. Please install Java 17 or higher."
    exit 1
fi

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven is not installed. Please install Maven."
    exit 1
fi

# Create data directory if it doesn't exist
mkdir -p data

echo "📦 Building the application..."
mvn clean compile -q

if [ $? -eq 0 ]; then
    echo "✅ Build successful!"
    echo "🚀 Starting the application..."
    echo "📱 Open your browser and go to: http://localhost:8080"
    echo "🛑 Press Ctrl+C to stop the application"
    echo ""
    
    mvn spring-boot:run
else
    echo "❌ Build failed. Please check the error messages above."
    exit 1
fi