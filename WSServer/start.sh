# Define variables
mvn clean package

# Build the Docker image
docker build -t collector .

docker-compose up -d