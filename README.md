# hitscountapp
This repository contains a hits counter app which counts the number of unique user hits for a specific url.
The application uses `redis` to cache data.
It accepts json data in the format below -
```

{
  "userid": "b20524ae-befe-11e3-b335-425861b86ab6:b20527e2-befe-11e3-b335-425 861b86ab6",
  "url": "http://www.someamazingwebsite.com/county-2014/engine/match/69272 1.html?cluster=undefined;view=comparison;wrappertype=none#live",
  "type": "GET",
  "timestamp": 1360662163000 
}

userid: A unique ID representing the user
url : The URL the visitor has accessed
type: The HTTP method used to access the URL timestamp: The timestamp for when the action occurred
```

It has three entry points
###### 1. App.java
Application that accept user input in json string 
###### 2. HitCountReaderApp.java
Application which automate the hit creation. It generates a hit every 5 seconds.
###### 3. RandomHitsProducerApp.java
Application which reads hit counts every 10 seconds 

##### To run the app
The whole app is deployed via docker containers. 
###### Step 1
Clone the repo
###### Step 2
Run with docker compose
```
cd hitscounterapp
docker-compose build --no-cache
docker-compose up -d
```
You should see following containers on successful deployment
```
hitscounterapp_client_1
hitscounterapp_reader_1
hitscounterapp_random-generator_1
```

###### Step 3
Go to bash of `hitscounterapp_client_1` 
Run following command to start `App.java`
```
java -cp /app/target/hitscounterapp-1.0-SNAPSHOT.jar:/app/target/dependen
cy/* com.hitscounter.app.App redis
```
Now enter json formatted data.

###### Step 4
Look for bash logs `hitscounterapp_reader_1` to view updated count.

#### Note:
`hitscounterapp_random-generator_1` is created to demonstrate parallel execution capability of the app. It can be deployed removed or deployed in multiple containers.


#Part 2
Ideal solution for the problem described in the use case requires an event processing system.  
A a good solution with `Apache Kafka` `stream api`  
