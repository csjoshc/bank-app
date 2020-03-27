/*

This java file formats and submits GET requests to online databases of asset prices (cryptocurrency)

Code for submitting GET request modified from
https://coinmarketcap.com/api/documentation/v1/#section/Quick-Start-Guide

HTTP jar files are from apache.org, specifically HttpClient 4.5.10 (GA) from https://hc.apache.org/downloads.cgi

The response from servers are in JSON format, so I'm also including a jar for
parsing json into Java hashmap style objects, json-20190722.jar
https://github.com/stleary/JSON-java

*/

package bankapp;

// open source apache http jar libraries
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

// Packages to take user input and add parameters to GET request
import java.io.IOException;
import java.net.URISyntaxException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.io.Console;

// open source 3rd party libraries to parse a string JSON into an appropriate format
import org.json.JSONArray;
import org.json.JSONObject;

// needed to read in the apiKey which is stored in /lib
import java.io.File;
import java.util.Scanner;


public class CryptoAPI {

  String url = "https://min-api.cryptocompare.com/data/pricehistorical";

  protected Double submitRequest(long ts){
    // submit a request for USD price of Bitcoin at the unix timestamp
    List<NameValuePair> params = new ArrayList<NameValuePair>();

    // add parameters to params
    params.add(new BasicNameValuePair("fsym","BTC"));
    params.add(new BasicNameValuePair("tsyms","USD"));
    params.add(new BasicNameValuePair("ts", Long.toString(ts)));


    // try to execute request, with response saved to my_json
    // catch various errors
    try{
      JSONObject my_json = new JSONObject(makeAPICall(url, params));
      double price = Double.parseDouble(my_json.getJSONObject("BTC").get("USD").toString());
      return price;
    } catch (IOException e) {
      System.out.println("Error: cannot access content - " + e.toString());

    } catch (URISyntaxException e) {
      System.out.println("Error: Invalid URL " + e.toString());
    }
    return 0.0;
  }


  protected String makeAPICall(String url, List<NameValuePair> parameters)
      throws URISyntaxException, IOException, FileNotFoundException  {
    /*
    This method actually executes the request on a Http client
    */

    Scanner scanner = new Scanner(new File("lib/apiKey.txt"));

    String apiKey = new String(scanner.nextLine());


    String response_content = "";

    URIBuilder query = new URIBuilder(url);
    query.addParameters(parameters);

    CloseableHttpClient client = HttpClients.createDefault();
    HttpGet request = new HttpGet(query.build());

    request.setHeader(HttpHeaders.ACCEPT, "application/json");
    request.addHeader("X-CMC_PRO_API_KEY", apiKey);

    CloseableHttpResponse response = client.execute(request);

    try {
      //System.out.println(response.getStatusLine());
      HttpEntity entity = response.getEntity();
      response_content = EntityUtils.toString(entity);
      EntityUtils.consume(entity);
    } finally {
      response.close();
    }
      return response_content;
    }
}
