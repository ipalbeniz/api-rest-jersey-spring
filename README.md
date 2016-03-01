# api-rest-jersey-spring
API REST de ejemplo

### Tecnologías utilizadas
- Jersey
- Spring
- EhCache
- Jetty
- Slf4j + Log4j2
- Swagger

### Cómo generar el fichero de especificación de swagger
```
mvn compile swagger:generate
```

### Cómo arrancar el API
```
mvn jetty:run
```

En el pom.xml se encuentra la configuración del servidor jetty.

### Cómo probar el API

Abrir en el navegador la siguiente URL
```
http://localhost:8080/student
```

Debería devolver un estudiante de ejemplo en formato JSON:
```
[ {
  "id" : "1",
  "name" : "Iñaki",
  "age" : 34
} ]
```