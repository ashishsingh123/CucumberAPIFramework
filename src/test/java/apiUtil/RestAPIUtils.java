package apiUtil;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/*
@Author : Ashish Kr Singh
 */
public class RestAPIUtils {

    public static void setBaseUrl(String baseUrl) {
        RestAssured.baseURI = baseUrl;
    }

    public static String getToken(String autheticationUrl, String token) {
        RestAssured.baseURI = autheticationUrl;
        Response response = given().header("Authorization", "Bearer " + token).when().get("/Token");
        System.out.println(response.asString());
        return response.asString();
    }

    public static Response postRequest(String baseUrl, String tokenNumber, String uriPath, String jsonBody) {
        Header header1 = new Header("accept", "application/json");
        Header header2 = new Header("Authorization", "Bearer " + tokenNumber);
        Header header3 = new Header("Content-Type", "application/json");
        List<Header> list = new LinkedList<Header>();
        list.add(header1);
        list.add(header2);
        list.add(header3);
        Headers headers = new Headers(list);
        RestAssured.baseURI = baseUrl;
        Response responsePost = given().headers(headers).body(jsonBody).when().post(uriPath);
        System.out.println(responsePost.statusCode());
        return responsePost;
    }

    public static void main(String[] str) throws Exception {
        String apiToken = getToken("https://authapi-qa.epharmasolutions.com", "U0xERS1Dcm8wMTpTTERFLUNybzAx");
        System.out.println(apiToken);
        Object jsonBody = JSONUtils.readJsonDataFromDefaltFolder("TC887");
        System.out.println(jsonBody);
        postRequest("https://coreapi-qa.epharmasolutions.com/", apiToken, "api/v1/Sponsor/AddSponsorsAsync",
                jsonBody.toString());
    }

    public static Response getResponse(String baseUri, String uriPath, String bearerToken) {
        Header header = new Header("Authorization", "Bearer " + bearerToken);
        RestAssured.baseURI = baseUri;
        Response responseGet = (Response) given().header(header).when().get(uriPath);
        System.out.println(responseGet.getStatusCode());
        return responseGet;
    }
       
    public static Response postOrPutResponse(String uriPath, Headers headers, HashMap<String, 
            String> bodyParams, String bodyText, String requestType) {
        Response response = null;       
        RequestSpecBuilder  builder = new RequestSpecBuilder ();
        for(Header header: headers ) {
            builder.addHeader(header.getName(), header.getValue());
        }
        if(bodyText != null) {
            builder.setBody(bodyText);
        }
        if(bodyParams.size() > 0)
        {
            for (Map.Entry<String, String> entry : bodyParams.entrySet()) {
                builder.addFormParam( entry.getKey(), entry.getValue());            
            }
        }       
        RequestSpecification requestSpec = builder.build();
        switch(requestType) {
        case "Post":
            response = (Response) given().spec(requestSpec).when().post(uriPath);
            break;
        case "Put":
            response = (Response) given().spec(requestSpec).when().put(uriPath);
            break;
        default:
            new Exception("Please provide either Post or Put for request Type");
        }        
        System.out.println(response.getStatusCode());
        return response;
    }

    /**
     * Get response using Url path and header
     * 
     * @param uriPath : URI path
     * @param header  : headers values
     * @return
     */
    public static Response getResponse(String uriPath, Headers headers, HashMap<String, 
            String> bodyParams) {
        System.out.println(uriPath);  
        RequestSpecBuilder  builder = new RequestSpecBuilder ();
        for(Header header: headers ) {
            builder.addHeader(header.getName(), header.getValue());
        }       
        if(bodyParams.size() > 0)
        {
            for (Map.Entry<String, String> entry : bodyParams.entrySet()) {
                builder.addFormParam( entry.getKey(), entry.getValue());  
                System.out.println(entry.getValue());
            }
        }       
        RequestSpecification requestSpec = builder.build();
        Response responseGet = (Response) given().spec(requestSpec).when().get(uriPath);
        return responseGet;
    }
    
    
    public static Response deleteResponse(String uriPath, Headers headers,  HashMap<String, 
            String> bodyParams) {

        System.out.println(uriPath);  
        RequestSpecBuilder  builder = new RequestSpecBuilder ();
        for(Header header: headers ) {
            builder.addHeader(header.getName(), header.getValue());
        }       
        if(bodyParams.size() > 0)
        {
            for (Map.Entry<String, String> entry : bodyParams.entrySet()) {
                builder.addFormParam( entry.getKey(), entry.getValue());  
                System.out.println(entry.getValue());
            }
        }       
        RequestSpecification requestSpec = builder.build();
        Response responseGet =  (Response) given().spec(requestSpec).when().delete(uriPath);
        System.out.println(responseGet.getStatusCode());
        return responseGet;
    }

}
