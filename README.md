# api-rest-jersey-spring
API REST de ejemplo

### Tecnologías utilizadas
- Jersey
- Spring
- EhCache
- Jetty
- Slf4j + Log4j2
- Swagger
- MongoDB

### Cómo arrancar el API
```
mvn jetty:run
```

En el pom.xml se encuentra la configuración del plugin "jetty-maven-plugin"

### Cómo probar el API

Abrir en el navegador la siguiente URL
```
http://localhost:8080/api/student
```

Debería devolver un estudiante de ejemplo en formato JSON:
```
[ {
  "id" : "1",
  "name" : "Iñaki",
  "age" : 34
} ]
```

### Cómo guardar trazas de las peticiones y respuestas en MongoDB

Abrir el fichero api.properties y cambiar la siguiente propiedad para activar el log en mongo:
```
log.mongo.active=true
```

A continuación, editar el fichero mongo.properties para configurar el host, puerto, nombre de la base de datos y nombre de la colección para los logs.
```
mongo.host=localhost
mongo.port=27017
mongo.database=mydatabase
mongo.collection.apilogs=apiLogs
```

### Cómo consultar la documentación del API
 
Abrir en el navegador la siguiente URL
```
http://localhost:8080/
```

Debería mostrarse la documentación del API en formato web
 
### Cómo generar el fichero swagger.json 

```
mvn compile swagger:generate
```

En el pom.xml se encuentra la configuracion del plugin "swagger-maven-plugin"

### Cómo consultar el fichero swagger.json generado al vuelo

Acceder a la siguiente URL:
```
http://localhost:8080/api/swagger.json
``` 

### Cómo consultar el fichero swagger.yaml generado al vuelo

Acceder a la siguiente URL:
```
http://localhost:8080/api/swagger.yaml
``` 