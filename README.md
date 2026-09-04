To run this project you need java 17 , I used 17 as windows is a pain to change java versions and I have personal projects still on 17.

run mvn clean install -f pom.xml -DskipTests=true

Then start the main java app from com/cactus/disco/DiscoApplication.java

Intellij will generate the run configs.

To execute the tests just click the play button from intellij and it will execute fine.

Start the main app, do a post request too http://localhost:8085/api/v1/Sensor with the bellow body

{

"sensorId": "12",
"temperature": "5",
"humidity": "4",
"windspeed": "44",
"timestamp": "2026-09-04T22:37:00Z"

}

and it will save and persist.

Features:
- Api validation for findAllSensors and findSensorsByid if required Metric or Metric type is missing an error is returned to the user
- Api inputs are dynamic and you can provide multiple metric and metrictypes as long as theyre comma separated i.e MAX,MIN TEMPERATURE,WIND_SPEED
- -Example error {
  "errorMessage": "Invalid metric type provided: MEDIAN, supported types are Max, Min, Average and Sum",
  "errorCode": 400
  }

**Testing**
- To test this you must have data in the db first to insert data, run the docker compose yml within com/cactus/disco/docker/docker-compose.yml
- Hit play button on controller test and it will run a spring boot test 

