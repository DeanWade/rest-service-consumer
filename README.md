# rest-service-consumer
rest-service-consumer

## vm arguments

-Dserver.port=8080 -agentpath:"C:\Program Files\dynaTrace\dynaTrace 6.3\agent\lib64\dtagent.dll"=name=rest-service-consumer,server=localhost

## URL
Use none daemon thread for rest service network data transimitting
**http://localhost:8080/consumer/greeting**
![RestService-PurePath-Connected](https://deanwade.github.io/image/dynatrace/RestService-PurePath-Connected.png)

Use daemon thread for rest service network data transimitting
**http://localhost:8080/consumer/greeting?daemon=true**
![RestService-PurePath-Disconnected](https://deanwade.github.io/image/dynatrace/RestService-PurePath-Disconnected.png)

