
# Stock Manager app

## Api-Gateway

Method | Service name  | URL
------------- | ------------- | -------------
POST | User-Service  | api/user-service/auth/login
POST | User-Service  | api/user-service/auth/register
## Discovery-Service
Eureka server which discover all other service and register it.

## User-Service
Method | URL  | Description
------------- | ------------- | -------------
POST | auth/login  | Login with **username** and **password**.
POST | auth/register  | Register with **first name**, **last name**, **username**, **password** and **email**.
GET | users | Get **all user**.
GET | users/{username} | Get all user which contains **username** part.
DELETE | users/{id} | Delete user by **id**.

## React app
Front-end application.
