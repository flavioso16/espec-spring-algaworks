# Projeto de acompanhamento do curso Especialista Spring AlgaWorks

## Projeto AlgaFood

O AlgaFood é uma API Rest para restaurante

## Dependências do Projeto

- Java 11
- Spring 5.1.9
- SpringBoot 2.1.7
- Lombok 1.18.8
- Flyway 5.2.4
- MySQL 5.7
- Modelmapper 2.3.0 - Is refactoring safe object mapping library that automatically maps objects to each other
- Squiggly 1.3.18 - Is a Jackson JSON PropertyFilter

## Rodando Projeto linha de comando

Build projeto
```
JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64 ./mvnw clean install
```

Rodando o projeto
```
JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64 ./mvnw spring-boot:run
```

Rodando o flyway
```
./mvnw flyway:repair -Dflyway.configFiles=src/main/resources/flyway.properties
```