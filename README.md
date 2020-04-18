# Country Information Reporting

## Overview

This repository showcases how I would tackle an application to make a report 
from country information coming from external services over several days.

## Architecture

The idea was to have two applications that would share a maven module
for common code (model, persistence layer,...).

The first application would be in charge of extracting the data from the 
external services and writing it to a DB.
Once the extraction is done, the application will exit.  
To be able to obtain data over multiple days this application would need to be
scheduled by an external system.  
This way we can be flexible in how many times we want to extract data and the timing
in between. Also we don't waste any resources by not having an idle application running
all the time.

The second application has the responsibility to extract the persisted data and build
a report from it. This one can also be run independently and can be configured in the 
amount of days we should show the history of the data.

Due to the fact that timing is limited, and this kind of architecture would be more
time consuming, I choose for a Spring Boot application with two profiles:
* extraction
* report

This way we can still simulate two separate applications without spending the time 
creating them and building the shared module.

## Data

The data in the DB can be of two types: static data and time based data.  
Static data is data that doesn't change over time. In this case that would be
the general country information (name, flag, currencies,...).  
Time based data is data that does change over time. This would be the currency
rate, weather information, population,...

Static data will just be persisted each time and would override pre-existing values.  
Time based data will be written as new records and will keep a reference to the extraction time.

The extraction time is the date and time of when the extraction application was started.  
All time based data extracted at the same application run will have a reference to the 
same extraction time.

## Report

The report is build as a markdown report and rendered to HTML.  
I choose for a generic solution that would make it easy to define a tree-like structure
of the data that should be in the report.  
The application then renders this tree-like structure by applying the Visitor pattern to
each individual node.  
This way we have a flexible and generic framework that allows us to easily add new
data or change the structure.

A sample of the rendered markdown report (extract of two days) can be found [here](example-report.html).