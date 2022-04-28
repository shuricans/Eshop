# Welcome to the useless store!

### Hi everyone!
This is another study pet-project of eshop.  
Course [gb.ru](https://gb.ru/) "Developing eshop, Spring Boot"    
Teacher: [Alexey](https://github.com/usharik)

### Many modules, many stuff. Short review.

1. shop-database
   > common persistence module
* Spring Data JPA with Hibernate
* [Postgresql](https://www.postgresql.org/)
* [Liquibase](https://docs.liquibase.com/home.html) - version control for DB
2. shop-picture-service-api-app
   > REST API for pictures, uses **picture-service** module
3. spring-eureka
   > [Eureka service](https://spring.io/guides/gs/service-registration-and-discovery/) - service registration and discovery
4. spring-cloud-config
   > [Spring Cloud Config](https://cloud.spring.io/spring-cloud-config/reference/html/) for externalized configuration
5. spring-cloud-gateway
   > [Spring Cloud Gateway](https://spring.io/projects/spring-cloud-gateway), reverse proxy & load balancing
6. shop-admin-app
* Template engine - [Thymeleaf](https://www.thymeleaf.org/)
7. shop-backend-api-app
* [Spring Session Data Redis](https://spring.io/projects/spring-session-data-redis) - session repository
* [RabbitMQ](https://www.rabbitmq.com/) - message broker
* [springdoc-openapi](https://springdoc.org/) - automate the generation of API documentation using spring boot projects, see Swagger-ui
* [spring-boot-starter-websocket](https://spring.io/guides/gs/messaging-stomp-websocket/) for sends messages back and forth between a browser and a server
8. shop-frontend-app
* [Angular](https://angular.io/)
* [Bootstrap](https://getbootstrap.com/)
* [RxJS](https://rxjs.dev/guide/overview) - is a library for composing asynchronous and event-based programs by using observable sequences
* [stompjs](https://github.com/stomp-js/stompjs) - his library provides a STOMP over WebSocket client for Web browser and node.js applications
* [Font-Awesome](https://github.com/FortAwesome/Font-Awesome) - icon library and toolkit
* [ngx-spinner](https://github.com/Napster2210/ngx-spinner) - A library with more than 50 different loading spinners
9. shop-delivery-service
   > Example of a simple app for changing status of an order by [RabbitMQ](https://www.rabbitmq.com/)
10. spring-integration-demo
    > Example of a simple app for integration demonstration.  
    > Here we use [integration via files](https://docs.spring.io/spring-integration/reference/html/file.html), just add new Brands.
11. shop-ui-tests
    > Module for testing ui  
* [Selenium](https://www.selenium.dev/) - automates browsers. That's it!
* [Cucumber](https://cucumber.io/) - BDD testing
* [webdrivermanager](https://github.com/bonigarcia/webdrivermanager)

### Other common things:
* [Spring Security](https://spring.io/projects/spring-security)
* [MapStruct](https://mapstruct.org/) Java bean mappings, the easy way!
* [Lombok](https://projectlombok.org/)
* [Docker](https://www.docker.com/)
* [Jib](https://github.com/GoogleContainerTools/jib) - Containerize java applications
* [nginx](https://nginx.org/)

### How run this project?
##### 1. First way - docker compose:
> Here we won't use eureka & gateway.  
> Firstly, we will use [nginx](https://nginx.org/) as reverse proxy.  
> See settings in [nginx.conf](https://github.com/shuricans/geek-eshop-02-15/blob/master/shop-frontend-app/nginx.conf)  
> Secondly, scaling services pretty simple with docker compose.  
> They give us load balancing which is great.  
> All images already have example data, no need mappings volumes.

* Be sure you have [docker](https://docs.docker.com/engine/install/) installed.
* Grab [`docker-compose.yml`](https://github.com/shuricans/geek-eshop-02-15/blob/master/docker-compose.yml) from root directory.
* Create network `docker network create geek-eshop`
* _Next use `docker-compose` or `docker compose` command._
* Simple run `docker compose up -d` that's all.
* Navigate to [http://localhost:8080](http://localhost:8080)
* Admin part available on [http://localhost:8080/admin](http://localhost:8080/admin)
* Default frontend user - `customer/password`
* Default admin user - `admin/admin`
* See logs via `docker compose logs -f`
* See stats via `docker stats`
* Several containers will wait for the services they depend on, this behavior implements by `entrypoint.sh` & `wait-for-service.sh`.
##### 2. Second way - docker & IntelliJ IDEA:
> Now let's use eureka & gateway.  
> I recommend using docker images for Redis, RabbitMQ and Postgres.  
> You can simple take this from previous part.

* required versions or mb higher:
  * JDK 11
  * maven 3.8.4
  * postgres 14.1
  * node v16.13.2
  * npm 8.4.0
  * Angular CLI: 13.2.0
* Clone project from github
* First of all, in parent module run `mvn clean install`
* Create network `docker network create geek-eshop`
* Then run containers: (use this [docker-compose.yml](https://gist.github.com/shuricans/33cde6daa6f22d158321d3335c1702e2))
  * Postgres
  * Redis
  * RabbitMQ
* In root folder create package 'storage' `mkdir storage`
  * paste in [example data](https://drive.google.com/drive/folders/1JY1xq6FxAIE9wZ1XuMVAAUNHKEMTQl7p?usp=sharing) 
* Then run spring-eureka
  > see [http://localhost:8761](http://localhost:8761)
* Run cloud config
  > For example see [admin configs](http://localhost:8888/shop-admin-app/cloud)
* Run cloud gateway
  > see registration in [eureka](http://localhost:8761)
* Run delivery service
  > see registration in [eureka](http://localhost:8761)
* Run picture service
  > see registration in [eureka](http://localhost:8761)  
  > check for example [http://localhost:8080/api/v1/picture/1](http://localhost:8080/api/v1/picture/1)  
  > you can run multiple instance of this app
* Run backend app
  > see registration in [eureka](http://localhost:8761)  
  > check for example [http://localhost:8080/api/v1/product/1](http://localhost:8080/api/v1/product/1)
* Run admin app
  > see all registrations in [eureka](http://localhost:8761)  
  > check for example [http://localhost:8080/admin/product](http://localhost:8080/admin/product)
* Run `cd shop-frontend-app`
  * run `npm i`
  * run `ng serve`
  > navigate to [http://localhost:4200](http://localhost:4200)

* That's all, great job!

Have questions?  
Write to me in [https://t.me/shuricans](https://t.me/shuricans)

Make love, not war!  
See ya!

### Screenshots:
![image](https://user-images.githubusercontent.com/10568936/165842068-0ab5bfa7-4b9b-4d6a-bef6-785217594899.png)
![image](https://user-images.githubusercontent.com/10568936/165842157-3d4d2dcf-005c-4dad-8d16-a4d688c44f92.png)
![image](https://user-images.githubusercontent.com/10568936/165842650-3cd997fa-b740-4a4b-99e2-b04bfd36b79d.png)
![image](https://user-images.githubusercontent.com/10568936/165842268-1959df93-3194-49f3-8633-33783fc58ccb.png)
![image](https://user-images.githubusercontent.com/10568936/165842414-c882b669-99c3-4ffc-834f-7182ed7e602b.png)
![image](https://user-images.githubusercontent.com/10568936/165842504-58c5339f-7925-438e-a37e-80f83c865d4f.png)
