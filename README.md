# bank-app

This app is run from the command line and simulates banking transactions. The data is read from and written to a simple text file, with some error catching for incorrectly formatted data. 

There are several folders excluded (since they are third party packages), especially the HTTP and JSON handlers, seen in the folder under /lib in the project root directory:

![](https://github.com/csjoshc/bank-app/blob/master/man/libref.PNG?raw=true)

The libraries and related code can be found at 

https://coinmarketcap.com/api/documentation/v1/#section/Quick-Start-Guide

HTTP jar files are from apache.org, specifically HttpClient 4.5.10 (GA) from https://hc.apache.org/downloads.cgi

The response from servers are in JSON format, so I'm also including a jar for
parsing json into Java hashmap style objects, json-20190722.jar

https://github.com/stleary/JSON-java