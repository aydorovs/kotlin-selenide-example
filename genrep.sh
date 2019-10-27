#!/bin/bash
cp -R ./allure-report/history ./target/allure-results/
allure generate ./target/allure-results/ -o ./allure-report/ --clean