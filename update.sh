sudo docker container stop vivareal-extractor
sudo docker container rm vivareal-extractor
sudo docker build --tag=ivansla/vivareal-extractor:1.0-SNAPSHOT .
sudo docker run -dt --name vivareal-extractor ivansla/vivareal-extractor:1.0-SNAPSHOT
sudo docker exec -ti vivareal-extractor bash