# Laboratorio 2

Implementación sobre un servidor de fachada que consume una API REST que traera datos de las películas que se encuentren en el sistema.

## Compile

Primero vamos a clonar el repositorio usando el comando
```
git clone https://github.com/SantiagoC27/AREP-2023-2.git
```
Acontinuacion abriremos el projecto con el editor de codigo de su preferencia, este puede ser visual studio, intelliJ entre otro y ejecutamos los siguiente comando
```
mvn package -Dskiptests
```
```
mvn compile
```

## Run server

1. Para ejecutar el servidor, una vez realizados los pasos anteriores use el siguiente comando en caso de fallo usar el segundo

```
mvn exec:java
```

```
mvn compile exec:java
```

2. Para acceder a la pagina una vez realizado el paso anterior abre el index.html, y ya puede buscar la informacion de la pelicula que desee.


## Test

Para ejecutar las pruebas unitarias

```
mvn test
```

## Author

Santiago Cárdenas Amaya


