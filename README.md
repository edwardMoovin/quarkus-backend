# Proyecto base en Quarkus

Este es un proyecto basado en Quarkus, de Supersonic Subatomic Java Framework.

Si se quiere ver referencias vistar el sitio web:https://quarkus.io/

## Ejecutar la aplicacion en modo debug

Puede ejecutar su aplicación en modo dev que permite la codificación en vivo usando:

```script
./mvnw compile quarkus:dev
```

> **_NOTA:_**  se ejecutara en la siguiente dirrección http://localhost:8080/q/dev/.

## Packaged y ejecución de la aplicación.

La aplicación se puede packaged usando:

```shell script
./mvnw package
```

Esto crear un archivo `quarkus-run.jar` en el directorio `target/quarkus-app/`.

La aplicación ahora se puede ejecutar usando `java -jar target/quarkus-app/quarkus-run.jar`.

Si quieres construir un _über-jar_, ejecuta el siguiente comando:

```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

La aplicación, packaged ahora es _über-jar_, ahora es ejecutable usando `java -jar target/*-runner.jar`.

## Crear un ejecutable nativo

Puede crear un ejecutable nativo usando:

```shell script
./mvnw package -Pnative
```

O, si no tiene GraalVM instalado, puede ejecutar la compilación ejecutable nativa en un contenedor usando:

```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

Luego puede ejecutar su ejecutable nativo con: `./target/spring-data-jpa-quickstart-1.0.0-SNAPSHOT-runner`
