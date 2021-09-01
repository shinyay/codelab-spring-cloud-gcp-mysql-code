# Getting Started with Spring Cloud GCP for Cloud SQL - MySQL

Spring Cloud GCP offers a wide collection of libraries that make it easier to use Google Cloud Platform from Spring Framework applications.

This lab shows Spring Cloud GCP with Cloud SQL - MySQL.

## Description
### Dependencies
- com.google.cloud
  - `spring-cloud-gcp-starter-sql-mysql`
- org.springframework.boot
  - `spring-boot-starter-data-jpa`
  - `spring-boot-starter-web`

```kotlin
dependencyManagement {
	imports {
		mavenBom("com.google.cloud:spring-cloud-gcp-dependencies:${property("springCloudGcpVersion")}")
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
        mavenBom("org.testcontainers:testcontainers-bom:${property("testcontainersVersion")}")
	}
}
```

### Prerequisite for Cloud SQL - MySQL Instance
#### 1. Cloud SQL - MySQL Instance
By the following steps, we will create Cloud SQL - MySQL instance, Database and Table on it.

#### 1.1. Login to Google Cloud
It authorizes gcloud-cli to access the Cloud Platform with Google user credentials.

```shell
$ gcloud auth login
```

Refresh local Application Default Credentials (ADC), `~/.config/gcloud/application_default_credentials.json`
```shell
$ gcloud auth application-default
```

#### 1.2. Set Project ID
You can set the default value for gcloud-cli.

```shell
$ gcloud config set project ${PROJECT_ID}
```

Verification
```shell
$ gcloud config get-value project
```

#### 1.3. Enable Cloud SQL API
Before starting Cloud SQL, you must enable the `Cloud SQL Admin API`.

```shell
$ gcloud services enable sqladmin.googleapis.com
```

Enable Compute Engine API
```shell
$ gcloud services enable compute.googleapis.com
$ gcloud services enable oslogin.googleapis.com
```


#### 1.4. Install Cloud SQL Proxy

Cloud SQL Proxy provides the following functions:

- **Secure Connections**
  - It automatically encrypts traffic to and from the database using TLS 1.2 with 128-bit AES cipher
- **Connection Management with ease**
  - It handles authentication with Cloud SQL

![sql-proxy](https://user-images.githubusercontent.com/3072734/129500202-59397ef3-c672-4ba3-8d51-ce753e747780.png)

```shell
$ sudo gcloud components install cloud_sql_proxy
```
```shell
$ vim ~/.config/fish/config.fish

set PATH /usr/local/share/google-cloud-sdk/bin $PATH
```
#### 1.5. Create MySQL Instance
Configure the Machine Type as Lightweight

The following sample:

|Instance Name|
|-------------|
|my-mysql|

```shell
$ gcloud sql instances create my-mysql \
    --database-version=MYSQL_5_7 \
    --region=us-central1 \
    --cpu=2 \
    --memory=4G \
    --root-password=[ROOT_PASSWORD]
```

#### 1.6. Create Database
The following sample:

|Database Name|
|-------------|
|codelab|

```shell
$ gcloud sql databases create codelab --instance=my-mysql
```

#### 1.7. Connect MySQL Instance
```shell
$ gcloud beta sql connect my-mysql --user=root --quiet

Connecting to database with SQL user [root].Enter password: *****
```

#### 1.8. Create Table
```shell
mysql> use codelab
```
```mysql
DROP TABLE IF EXISTS employee, department;

CREATE TABLE employee (
    employee_id decimal(4,0) NOT NULL,
    employee_name varchar(64) DEFAULT NULL,
    role varchar(32) DEFAULT NULL,
    department_id decimal(4,0) DEFAULT NULL
);

CREATE TABLE department (
    department_id decimal(4,0) DEFAULT NULL,
    department_name varchar(14) DEFAULT NULL
);
```

#### 1.9. Add User to MySQL Instance
The MySQL instance has the only built-in root user.
```shell
$ gcloud sql users list --instance=my-mysql

NAME  HOST  TYPE
root  %     BUILT_IN
```

```shell
$ gcloud sql users create mysql-guest --instance=my-mysql --host=% --password=[PASSWORD]
```

```shell
$ gcloud sql users list --instance=my-mysql

NAME         HOST  TYPE
mysql-guest  %     BUILT_IN
root         %     BUILT_IN
```

#### 1.10. Grant User to Access Database
Grant the user create above to access the specific Database.

```shell
mysql> GRANT ALL ON codelab.* TO 'mysql-guest'@'%';
```
```shell
mysql> select user, host from mysql.user;

+---------------+-----------+
| user          | host      |
+---------------+-----------+
| mysql-guest   | %         |
| root          | %         |
| mysql.session | localhost |
| mysql.sys     | localhost |
+---------------+-----------+
```

```shell
mysql> show grants for 'mysql-guest'@'%';
```

```shell
mysql> show grants for 'mysql-guest'@'%';
+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| Grants for mysql-guest@%                                                                                                                                                                                                                                                                                                                                           |
+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, RELOAD, SHUTDOWN, PROCESS, REFERENCES, INDEX, ALTER, SHOW DATABASES, CREATE TEMPORARY TABLES, LOCK TABLES, EXECUTE, REPLICATION SLAVE, REPLICATION CLIENT, CREATE VIEW, SHOW VIEW, CREATE ROUTINE, ALTER ROUTINE, CREATE USER, EVENT, TRIGGER, CREATE TABLESPACE ON *.* TO 'mysql-guest'@'%' WITH GRANT OPTION |
| GRANT ALL PRIVILEGES ON `codelab`.* TO 'mysql-guest'@'%'                                                                                                                                                                                                                                                                                                           |
+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
```

#### 1.11. Retrieve Connection Name
```shell
$ gcloud sql instances describe my-mysql --format='value(connectionName)'
```

### Spring Cloud GCP for Cloud SQL - MySQL
#### 2. Application Configuration

|Property name|Description|Default|
|-------------|-----------|-------|
|spring.cloud.gcp.sql||-|
|enabled|Enables or disables Cloud SQL auto configuration|true|
|database-name|Name of the database to connect to|-|
|instance-connection-name|`project-id`:`region`:`instance-name`<br>`$ gcloud sql instances describe my-mysql --format='value(connectionName)'`|-|
|credentials.location|File system path to the Google OAuth2 credentials private key file|Default credentials by Spring Cloud GCP|
||||

#### (Option) 1.12. Cloud SQL Proxy Docker Image
When you use `spring.cloud.gcp.sql.instance-connection-name`, the connection to database instance is created by Cloud SQL Socket Factory with JDBC Driver.
If you want to create the connection via **Cloud SQL Proxy**, you need to run it beforehand.

You can check the latest Cloud Proxy Docker Image at the following url:
- [`gcr.io/cloudsql-docker/gce-proxy`](https://gcr.io/cloudsql-docker/gce-proxy)

```shell
$ docker run --rm -d \
    -v ~/.config/gcloud/application_default_credentials.json:/config \
    -p 127.0.0.1:3306:3306 \
    gcr.io/cloudsql-docker/gce-proxy:1.24.0 /cloud_sql_proxy \
    -instances=(gcloud sql instances describe my-mysql --format='value(connectionName)')=tcp:0.0.0.0:3306 \
    -credential_file=/config
```

In addition to it, you need to modify the connection configuration as follows:

```yaml
spring:
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/${DB_NAME}
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
```

## Demo

## Features

- feature:1
- feature:2

## Requirement

## Usage

## Installation

## References
- [Getting Started with Spring Cloud GCP: Cloud SQL](link.medium.com/Y5emXHlV2ib)
## Licence

Released under the [MIT license](https://gist.githubusercontent.com/shinyay/56e54ee4c0e22db8211e05e70a63247e/raw/34c6fdd50d54aa8e23560c296424aeb61599aa71/LICENSE)

## Author

[shinyay](https://github.com/shinyay)
