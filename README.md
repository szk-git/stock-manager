
# Stock Manager app

## Api-Gateway

Service name  | URL
------------- | -------------
User-Service  | api/user/auth/login
User-Service  | api/user/auth/register
## Discovery-Service
Eureka server which discover all other service and register it.

## User-Service
URL  | Description
------------- | -------------
auth/login  | Login with **username** and **password**.
auth/register  | Register with **first name**, **last name**, **username**, **password** and **email**.

## React app
Front-end application.
