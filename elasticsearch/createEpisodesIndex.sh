#!/bin/bash

curl -X PUT localhost:9200/episodes/ -d '{"settings" : {"index" : {"number_of_shards" : 3, "number_of_replicas" : 2 } }, "mappings" : {"the-doctors" : {"properties" : {"air_date" : {"type" : "text", "fields" : {"keyword" : {"type" : "keyword", "ignore_above" : 256 } } }, "doctor" : {"type" : "long"}, "title" : {"type" : "text", "fields" : {"keyword" : {"type" : "keyword", "ignore_above" : 256 } } } } } } }'
 > result.json
