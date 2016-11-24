# rest-service-consumer
rest-service-consumer

## vm arguments

-Dserver.port=8080 -agentpath:"C:\Program Files\dynaTrace\dynaTrace 6.3\agent\lib64\dtagent.dll"=name=rest-service-consumer,server=localhost

## web request
Use none daemon thread for network data transimitting
http://localhost:8080/consumer/greeting

Use daemon thread for network data transimitting
http://localhost:8080/consumer/greeting?daemon=true

