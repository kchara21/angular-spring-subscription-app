﻿# Subscription's App Practice

<p align="center">
 <img src="https://www.vectorlogo.zone/logos/springio/springio-ar21.svg" height="100" alt="Spring Boot" />
 <img src="https://www.vectorlogo.zone/logos/angular/angular-ar21.svg" height="110" alt="Angular Logo" />
</p>

## Description

Plataforma web gestor de cursos, donde un "creador" (aprobado por el administrador) puede crear sus cursos, y un "cliente" suscribirse a ellos.

## Technologies

Server:

- Java v17
- Spring Boot v2.7.4
- JWT
- MySQL
- JPA
- Spring Security
- I/O Validation

Client:

- Angular v13.3.0
- TypeScript
- Angular Material
- CSS
- Reactive Forms
- Others technologies of angular's ecosystem

## Installation

Server:

- Estar seguro de que el proyecto tenga las siguientes dependencias en el pom.xml: Spring Web, MySQL Driver, Spring Data JPA, Spring Boot DevTools, Spring Security.

- En el archivo "application.properties" especificar: El nombre de la DB asi como el username y password.

- Inicializar el servidor...

- Nota: Las tablas de la BD se cargan por defecto gracias a JPA y su persistencia de datos.

Client:

- Correr 'npm i' para instalar todas las dependencias del proyecto.

- Correr 'ng serve' para inicializar la aplicacion...

## Use

Server:

- En POSTMAN crear al usuario administrador de la siguiente forma:

![home](screenshots/back/creating_admin.png)

Client:

### Register Page

![home](screenshots/front/register_page.png)

### Login Page

![home](screenshots/front/login_page.png)

### Admin Page

En este apartado el administrador puede dar de alta/baja a los usuarios

![home](screenshots/front/admin_users_page.png)

### Creator Page

En este apartado el usuario "creador" puede gestionar sus cursos.

![home](screenshots/front/creator_courses_page.png)

![home](screenshots/front/creater_courses_create_page.png)

### Consumer Page

En este apartado el usuario "creador" puede gestionar sus subscripciones.

![home](screenshots/front/consumer_courses_page.png)

![home](screenshots/front/consumer_courseSubscribe_page.png)

![home](screenshots/front/consumer_subscriptions_page.png)

## Support

De tener alguna duda o recomendacion, no dudar en contactarme:

- email: kalebdavidchara@hotmail.com
- celular: 0960609798

## License

Desarrollado por Kaleb David Chara Toala. Ecuador.
