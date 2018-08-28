
# Kafka Connect and Spring Kafka Consumer (2)


## Setup Kafka Connect

### build connector

```bash
git clone https://github.com/jcustenborder/kafka-connect-spooldir
cd kafka-connect-spooldir
mvn clean package -DskipTests

export CLASSPATH="target/kafka-connect-target/usr/share/kafka-connect/kafka-connect-spooldir/*"
```

### Generate schema

```bash
mkdir /tmp/spooldir-crimes/input
mkdir /tmp/spooldir-crimes/error
mkdir /tmp/spooldir-crimes/finished
```

* create template `config/CrimesCSV.properties`

```bash
name=CrimesCSV
tasks.max=1
connector.class=com.github.jcustenborder.kafka.connect.spooldir.SpoolDirCsvSourceConnector
input.file.pattern=^Crimes.*\.csv$
input.path=/tmp/spooldir-crimes/input
error.path=/tmp/spooldir-crimes/error
finished.path=/tmp/spooldir-crimes/finished
halt.on.error=false
topic=crimes
csv.first.row.as.header=true
```

* run generate command
* adjust to your kafka_2.11-2.0.0 directory

```bash
~/kafka_2.11-2.0.0/bin/kafka-run-class.sh \
    com.github.jcustenborder.kafka.connect.spooldir.SchemaGenerator \
    -t csv \
    -f Crimes_-_2001_to_present_head.csv \
    -c config/CrimesCSV.properties \
    -i ID
```

* save the output to `config/connect-spooldir-crimes.properties`

```bash
#Configuration was dynamically generated. Please verify before submitting.
#Fri Aug 24 11:27:28 JST 2018
name=CrimesCSV
key.schema={"name"\:"com.github.jcustenborder.kafka.connect.model.Key","type"\:"STRUCT","isOptional"\:false,"fieldSchemas"\:{"ID"\:{"type"\:"STRING","isOptional"\:true}}}
connector.class=com.github.jcustenborder.kafka.connect.spooldir.SpoolDirCsvSourceConnector
value.schema={"name"\:"com.github.jcustenborder.kafka.connect.model.Value","type"\:"STRUCT","isOptional"\:false,"fieldSchemas"\:{"ID"\:{"type"\:"STRING","isOptional"\:true},"Case Number"\:{"type"\:"STRING","isOptional"\:true},"Date"\:{"type"\:"STRING","isOptional"\:true},"Block"\:{"type"\:"STRING","isOptional"\:true},"IUCR"\:{"type"\:"STRING","isOptional"\:true},"Primary Type"\:{"type"\:"STRING","isOptional"\:true},"Description"\:{"type"\:"STRING","isOptional"\:true},"Location Description"\:{"type"\:"STRING","isOptional"\:true},"Arrest"\:{"type"\:"STRING","isOptional"\:true},"Domestic"\:{"type"\:"STRING","isOptional"\:true},"Beat"\:{"type"\:"STRING","isOptional"\:true},"District"\:{"type"\:"STRING","isOptional"\:true},"Ward"\:{"type"\:"STRING","isOptional"\:true},"Community Area"\:{"type"\:"STRING","isOptional"\:true},"FBI Code"\:{"type"\:"STRING","isOptional"\:true},"X Coordinate"\:{"type"\:"STRING","isOptional"\:true},"Y Coordinate"\:{"type"\:"STRING","isOptional"\:true},"Year"\:{"type"\:"STRING","isOptional"\:true},"Updated On"\:{"type"\:"STRING","isOptional"\:true},"Latitude"\:{"type"\:"STRING","isOptional"\:true},"Longitude"\:{"type"\:"STRING","isOptional"\:true},"Location"\:{"type"\:"STRING","isOptional"\:true}}}
error.path=/tmp/spooldir-crimes/error
topic=crimes
input.file.pattern=^Crimes.*.csv$
csv.first.row.as.header=true
input.path=/tmp/spooldir-crimes/input
halt.on.error=false
tasks.max=1
finished.path=/tmp/spooldir-crimes/finished
```

### Start Connect worker

```bash
bin/connect-standalone.sh config/connect-standalone.properties \
    config/connect-file-source.properties \
    config/connect-file-sink.properties \
    config/connect-spooldir-crimes.properties

# check
curl http://localhost:8083/connectors/
["local-file-source","local-file-sink","CrimesCSV"]

curl http://localhost:8083/connectors/CrimesCSV
curl http://localhost:8083/connectors/CrimesCSV/tasks
curl http://localhost:8083/connectors/CrimesCSV/status

curl -s "http://localhost:8083/connectors" | jq '.[]' | \
    xargs -I{name} curl -s "http://localhost:8083/connectors/"{name}"/status" | \
    jq -c -M '[.name,.connector.state,.tasks[].state]|join(":|:")' | \
    column -s : -t | sed 's/\"//g' | sort
```


## Note

### Create config from rest API

```bash
echo '{"name"\:"com.github.jcustenborder.kafka.connect.model.Value"}' | sed 's/\\:/:/g' | sed 's/\"/\\\"/g'
```

* combine above (replace '=', add ',')

```bash
"tasks.max": "1",
"connector.class": "com.github.jcustenborder.kafka.connect.spooldir.SpoolDirCsvSourceConnector",
"input.file.pattern": "^Crimes.*.csv$",
"finished.path": "/tmp/finished",
"error.path": "/tmp/error",
"halt.on.error": "false",
"topic": "crimes",
"csv.first.row.as.header": "true",
"value.schema": "{\"name\":\"com.github.jcustenborder.kafka.connect.model.Value\",\"type\":\"STRUCT\",\"isOptional\":false,\"fieldSchemas\":{\"ID\":{\"type\":\"STRING\",\"isOptional\":true},\"Case Number\":{\"type\":\"STRING\",\"isOptional\":true},\"Date\":{\"type\":\"STRING\",\"isOptional\":true},\"Block\":{\"type\":\"STRING\",\"isOptional\":true},\"IUCR\":{\"type\":\"STRING\",\"isOptional\":true},\"Primary Type\":{\"type\":\"STRING\",\"isOptional\":true},\"Description\":{\"type\":\"STRING\",\"isOptional\":true},\"Location Description\":{\"type\":\"STRING\",\"isOptional\":true},\"Arrest\":{\"type\":\"STRING\",\"isOptional\":true},\"Domestic\":{\"type\":\"STRING\",\"isOptional\":true},\"Beat\":{\"type\":\"STRING\",\"isOptional\":true},\"District\":{\"type\":\"STRING\",\"isOptional\":true},\"Ward\":{\"type\":\"STRING\",\"isOptional\":true},\"Community Area\":{\"type\":\"STRING\",\"isOptional\":true},\"FBI Code\":{\"type\":\"STRING\",\"isOptional\":true},\"X Coordinate\":{\"type\":\"STRING\",\"isOptional\":true},\"Y Coordinate\":{\"type\":\"STRING\",\"isOptional\":true},\"Year\":{\"type\":\"STRING\",\"isOptional\":true},\"Updated On\":{\"type\":\"STRING\",\"isOptional\":true},\"Latitude\":{\"type\":\"STRING\",\"isOptional\":true},\"Longitude\":{\"type\":\"STRING\",\"isOptional\":true},\"Location\":{\"type\":\"STRING\",\"isOptional\":true}}}",
"input.path": "/tmp/source",
"key.schema": "{\"name\":\"com.github.jcustenborder.kafka.connect.model.Key\",\"type\":\"STRUCT\",\"isOptional\":false,\"fieldSchemas\":{\"ID\":{\"type\":\"STRING\",\"isOptional\":true}}}",
"finished.path": "/tmp/finished"
```

```bash
curl -i -X POST -H "Accept:application/json" \
    -H  "Content-Type:application/json" http://localhost:8083/connectors/ \
    -d '{
  "name": "csv-source-crimes",
  "config": {
    # above config here
  }
}'
```


## Reference

* https://kafka.apache.org/documentation/#connect
* https://github.com/jcustenborder/kafka-connect-spooldir
* https://www.confluent.io/blog/ksql-in-action-enriching-csv-events-with-data-from-rdbms-into-AWS/
