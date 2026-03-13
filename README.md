# Assignment 5 - Amazon Testing and CI

[![SE333_CI](https://github.com/furqansaddal23/assignment5_code/actions/workflows/SE333_CI.yml/badge.svg)](https://github.com/furqansaddal23/assignment5_code/actions/workflows/SE333_CI.yml)

## Project Overview
This project contains testing for the Amazon package using both unit tests and integration tests.  
It also includes a GitHub Actions CI workflow that runs Checkstyle and JUnit tests and generates JaCoCo coverage reports.

## Tests Included
- `AmazonUnitTest.java`
- `AmazonIntegrationTest.java`

The unit tests verify pricing rule behavior and Amazon class logic in isolation.  
The integration tests verify database-backed shopping cart behavior and full pricing calculation flow.

## CI Workflow
The GitHub Actions workflow:
- runs on pushes to `main`
- sets up Java 23
- runs Checkstyle
- uploads the Checkstyle artifact
- runs Maven tests
- uploads the JaCoCo coverage artifact

Workflow link:  
https://github.com/furqansaddal23/assignment5_code/actions/workflows/SE333_CI.yml

## Workflow Status
GitHub Actions workflow completed successfully on the `main` branch.

Repository:  
https://github.com/furqansaddal23/assignment5_code

## Artifacts
The workflow uploads:
- `checkstyle-report`
- `jacoco-report`