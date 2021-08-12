# Getting Started with Spring Cloud GCP for Cloud SQL - MySQL

Spring Cloud GCP offers a wide collection of libraries that make it easier to use Google Cloud Platform from Spring Framework applications.

This lab shows Spring Cloud GCP with Cloud SQL - MySQL.

## Description
### Dependencies
- com.google.cloud
  - `spring-cloud-gcp-starter`
- `mysql:mysql-connector-java`
- `org.testcontainers:junit-jupiter`
- `org.testcontainers:mysql`

```kotlin
dependencyManagement {
	imports {
		mavenBom("com.google.cloud:spring-cloud-gcp-dependencies:${property("springCloudGcpVersion")}")
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
        mavenBom("org.testcontainers:testcontainers-bom:${property("testcontainersVersion")}")
	}
}
```

### Prerequisite
#### Cloud SQL - MySQL Instance
#### Login to Google Cloud
```shell
$ gcloud auth login
```

#### Set Project ID
```shell
$ gcloud config set project ${PROJECT_ID}
```

Verification
```shell
$ gcloud config get-value project
```

## Demo

## Features

- feature:1
- feature:2

## Requirement

## Usage

## Installation

## References

## Licence

Released under the [MIT license](https://gist.githubusercontent.com/shinyay/56e54ee4c0e22db8211e05e70a63247e/raw/34c6fdd50d54aa8e23560c296424aeb61599aa71/LICENSE)

## Author

[shinyay](https://github.com/shinyay)
