# Curl's examples for MealRestController

## Get all meals
```
curl -X GET 'http://localhost:8080/topjava/rest/user/meals'
```

## Get filtered
```
curl -X GET 'http://localhost:8080/topjava/rest/user/meals/between?startDate=2020-01-30&startTime=06:00:00&endDate=2020-01-30&endTime=23:00:00'
```

## Create new meal
```
curl "http://localhost:8080/topjava/rest/user/meals" \
 -X POST \
 -d "{\"calories\": 100,\"dateTime\": \"2020-11-22T22:00:00\",\"description\": \"Пример еды\"}" \
 -H "Content-Type: application/json" 
```

## Get meal by id
```
curl -X GET 'http://localhost:8080/topjava/rest/user/meals/100011'
```

## Update meal by id
```
curl "http://localhost:8080/topjava/rest/user/meals/100011" \
 -X PUT \
 -d "{\"id\": 100011,\"calories\": 200,\"dateTime\": \"2020-11-22T22:30:00\",\"description\": \"Пример еды\"}" \
 -H "Content-Type: application/json" 
```

## Delete meal by id
```
curl -X DELETE "http://localhost:8080/topjava/rest/user/meals/100011"
```