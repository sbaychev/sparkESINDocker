docker stop es
docker rm es
docker rmi sb/elasticsearch

docker build -f Dockerfile -t sb/elasticsearch .
docker create --name es -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" -e "xpack.security.enabled=false" sb/elasticsearch
docker start es

sleep 20
sh setup_data.sh http localhost