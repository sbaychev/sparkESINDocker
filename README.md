# Dockerized SparkDriver and ES #

What does this do for you?
-
1. Runs via a .bat `windows_start_es` or .sh `unix_start_es` script file an ElasticSearch version 5.6.6 instance via Docker using a Dockerfile and cmd arguments.
The instance runs as single node "cluster", disabled X-Pack security,`esdata` named volume that has the data and logs in pre-defined well known locations

2. The java defined SparkDriver then uses the ongoing ElasticSearch instance.
The SparkDriver defines the needed configuration settings to run itself, communicate to ElasticSearch and to handle the data processing of the `episodes.avro`.
Once the .avro file gets processed, we send it to the well known ElasticSearch instance.

What else is there?
-

- createEpisodesIndex.sh    - does what it says, creates the episodes index, with its schema - no data
- deleteEpisodesIndex.sh    - does what it says, deletes the episodes index, with its schema and data - similar to SQL DROP Table
- elasticsearch.yml         - the elastic search configuration file
- logging.yml               - the elastic search logging configuration file
- Dockerfile                - the Docker image file descriptor

Good to know?
-
- the SparkDriver has some code documentation and depending on it certain workings can be tweaked
- curl usage is needed, if not having it, you need to! Thank me later, it is a must have tool https://curl.haxx.se/h2c/
- [TODO] Maven Wrapper used, so no need to setup any Maven, paths and what not. Maven gets wrapped within the project.
- Java, you know, for coding?
- Docker ...

Sample Walk Through?
-

1. Execute `windows_start_es.bat` or `unix_start_es.sh`
2. [TODO] Execute script file xyz or wtp and the SparkDriver gets to run.. 
!! For now via the IDE run the SparkDriver and all the magic would happen !!

Et Voaila!

Use the below given and would see some nice json command line readable `Doctor Who` Episodes funtopia!!

curl http://localhost:9200/episodes/_search | json_pp
