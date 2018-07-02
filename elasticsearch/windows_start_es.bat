docker stop es
docker rm es
docker rmi sb/elasticsearch

docker build -f Dockerfile -t sb/elasticsearch .
docker create --name es -p 9200:9200 -p 9300:9300 sb/elasticsearch
docker start es
