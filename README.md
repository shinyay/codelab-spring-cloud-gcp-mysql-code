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

#### Enable Cloud SQL API
```shell
$ gcloud services enable sqladmin.googleapis.com
```
```shell
$ vim ~/.config/fish/config.fish

set PATH /usr/local/share/google-cloud-sdk/bin $PATH
```

#### Install Cloud SQL Proxy
```shell
$ sudo gcloud components install cloud_sql_proxy
```

#### Create MySQL Instance
```shell
$ gcloud sql instances create my-mysql \
    --database-version=MYSQL_5_7 \
    --region=us-central1 \
    --cpu=2 \
    --memory=4G \
    --root-password=[ROOT_PASSWORD]
```

#### Create Database
```shell
$ gcloud sql databases create my-db --instance=my-mysql
```

#### Connect MySQL Instance
```shell
$ gcloud beta sql connect my-mysql --user=root --quiet
```

#### Create Table
```shell
mysql> use my-db
```
```mysql
DROP TABLE IF EXISTS employee, department;

CREATE TABLE employee (
    employee_id decimal(4,0) NOT NULL,
    employee_name varchar(10) DEFAULT NULL,
    role varchar(9) DEFAULT NULL,
    department_id decimal(2,0) DEFAULT NULL
);

CREATE TABLE department (
    department_id decimal(2,0) DEFAULT NULL,
    department_name varchar(14) DEFAULT NULL
);
```

#### Add User to MySQL Instance
The MySQL instance has the only built-in root user.
```shell
$ gcloud sql users list --instance=my-mysql

NAME  HOST  TYPE
root  %     BUILT_IN
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
