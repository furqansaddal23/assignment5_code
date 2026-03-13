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



## Part 2 - Playwright UI Testing

This part includes a traditional Playwright Java UI test for the DePaul bookstore flow. The test searches for earbuds, applies filters, opens the JBL Quantum product, adds it to cart, verifies cart totals, proceeds through guest checkout, fills contact and pickup information, and returns to the cart. A Playwright video was recorded for the successful run.

### Reflection
The traditional Playwright approach gave me more control over selectors, waits, and dynamic checkout behavior, but it required more debugging because the site used hidden fields, multiple matching buttons, and changing totals between steps.

The AI-assisted / MCP approach was useful for generating an initial structure and suggesting Playwright APIs quickly. However, it still required manual fixes because live UI behavior did not always match the generated assumptions. In particular, hidden fields, visible-only buttons, and checkout validation required human judgment to stabilize the test.

Overall, AI helped speed up the first draft, but the final reliable test required manual debugging and refinement.