[CircleCI] <img alt="CircleCI" src="https://img.shields.io/circleci/build/github/FurkanLevent/jsonPlaceHolder-apitest">
# jsonPlaceHolder API Test with Rest Assured

To perform the validations for the comments for the post made by a specific user.

## Table of contents
* [Test Features](#test-features)
* [Technologies](#technologies)
* [Requirements](#requirements)
* [Setup](#setup)
* [Reporting](#reporting)


# Test Features
1. Search for the user with username “Delphine”.
2. Use the details fetched to make a search for the posts written by the user.
3. For each post, fetch the comments and validate if the emails in the comment section are in the proper format.
4. Negative test scenarios added.



# Technologies
Project is created with:
- Java 11
- maven | version: 3.6.2
- rest-assured | version: 4.3.3
- TestNG | version: 7.4.0
- log4j | version: 2.12.0
- allure report | version: 2.13.2
- Sonar Qube | version: 8.9
- Gatling
- CircleCI
- IntelliJ IDEA 2019.1 (Community Edition)


# Requirements
In order to utilise this project you need to have the following installed locally:
- Maven 3
- Java 11
- IntelliJ IDEA 2019.1 (Community Edition)
- Sonar Qube Server | version: 8.9


# Setup

- To run, navigate to jsonPlaceHolder-apitest directory and run:

mvn clean install

- To run SonarQube in your local:

mvn sonar:sonar
-Dsonar.projectKey=jsonPlaceHolder-apitest
-Dsonar.host.url=http://localhost:9000
-Dsonar.login=Token


# Reporting

- Reports for each module are written into their respective /target directories after a successful run.

- SonarQube Quality Status:

<img width="1439" alt="image" src="https://user-images.githubusercontent.com/12385700/121608109-f5393800-ca59-11eb-8817-54f4dced7010.png">

