# Dockerized SparkDriver and ES #

#What does this do for you?
-
1. Runs via a .bat `windows_start_es` or .sh `unix_start_es` script file an ElasticSearch version 5.6.6 instance via Docker using a Dockerfile and cmd arguments.
The instance runs as single node "cluster", disabled X-Pack security,`esdata` named volume that has the data and logs in pre-defined well known locations

2. The java defined SparkDriver then uses the ongoing ElasticSearch instance.
The SparkDriver defines the needed configuration settings to run itself, communicate to ElasticSearch and to handle the data processing of the `episodes.avro`.
Once the .avro file gets processed, we send it to the well known ElasticSearch instance.

#What else is there?
-

- createEpisodesIndex.sh    - does what it says, creates the episodes index, with its schema - no data
- deleteEpisodesIndex.sh    - does what it says, deletes the episodes index, with its schema and data - similar to SQL DROP Table
- elasticsearch.yml         - the elastic search configuration file
- logging.yml               - the elastic search logging configuration file
- Dockerfile                - the Docker image file descriptor
