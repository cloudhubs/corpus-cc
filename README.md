# Corpus-CC

This library is built using Spring Boot microservices for detecting types 1-3 code-clones in Java applications.

## Getting Started

These instructions will get a copy of the project up and running on your local machine

### MongoDB Prerequisites 

Before installing this project you'll want to make sure you have a MongoDB instance running. Configuration of the instance and user can be done for the cc-detection and cc-pipeline microservices.

## Architecture

The architecture for the application is divided into four main microservices -
* cc-detection : This microservice is in charge of finding clones between the corpus and the application
* cc-discovery : This microservice is in charge of finding the local source files in an application
* cc-pipeline : This microservice is in charge of loading a corpus from a source application
* cc-tokenizer : This microservice is in charge of tokenizing source files and running heuristics

### Loading the Corpus

To load the corpus you will want to send a discovery request to the "/findClones" endpoint for a full list of clones or the "/findRankedClones" for a breakdown of the clones by severity.

The discovery request is structured as follows -

```json
{
  "baseFolder" : "<path-to-application>",
  "depth" : 15,
  "basePackage" : ""
}
```

## Built With

* [Spring Boot](https://spring.io/projects/spring-boot) - Open-source Java-based framework used to create a microservices
* [Javaparser](https://javaparser.org/) - Lightweight set of tools to generate, analyze, and process Java code
* [Gson](https://github.com/google/gson) - Java library that can be used to convert Java Objects into their JSON representation

## Authors

* [**Andrew Walker**](https://github.com/walker76)
* [**Ian Laird**](https://github.com/i-laird)
