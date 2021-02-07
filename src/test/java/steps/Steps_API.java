package steps;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import apiUtil.JSONUtils;
import apiUtil.RestAPIUtils;
import util.UserModel;
import org.junit.Assert;
import io.cucumber.datatable.DataTable;
import io.cucumber.datatable.dependency.com.fasterxml.jackson.core.JsonParseException;
import io.cucumber.datatable.dependency.com.fasterxml.jackson.databind.JsonMappingException;
import io.cucumber.datatable.dependency.com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Then;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.cucumber.java.en.Given;

public class Steps_API {

    private static String bearerToken;
    private static Response response;
    private static Headers headers;
    private static String baseUrl;
    private static String postBody;
    private static String queryString;
    private static HashMap<String, String> bodyParams = new HashMap<String, String>();

    @Given("^I generated bearer token number using token request URL \"([^\"]*)\" and token number \"([^\"]*)\"$")
    public static void i_upload_file_to_server(String url, String token) {
        bearerToken = RestAPIUtils.getToken(url, token);
    }

    @Given("I reset API variable data")
    public static void I_Rest_Objects() {
        bearerToken = null;
        headers = null;
        baseUrl = null;
        postBody = null;
        queryString = null;
        bodyParams = new HashMap<String, String>();
    }

    @Given("^I set api base url \"([^\"]*)\"$")
    public static void i_set_base_url(String url) {
        baseUrl = url;
    }

    @Given("^I set post body text from json file \"([^\"]*)\"$")
    public static void i_set_post_body_text(String jsonFile) throws Exception {
        postBody = JSONUtils.readJsonDataFromDefaltFolder(jsonFile).toString();
    }

    @Given("^I set post from params$")
    public static void I_set_Post_From_Param(DataTable dataTable) {
        List<Map<String, String>> listHeaders = dataTable.asMaps(String.class, String.class);
        Map<String, String> listHeader = listHeaders.get(0);
        for (Map.Entry<String, String> entry : listHeader.entrySet()) {
            bodyParams.put(entry.getKey(), entry.getValue());
        }
    }

    @Given("^I set URI query string$")
    public static void I_set_URI_Query_String(DataTable dataTable) {
        List<Map<String, String>> listQueries = dataTable.asMaps(String.class, String.class);
        Map<String, String> queries = listQueries.get(0);
        if(queries.size() >0 ) {
            for (Map.Entry<String, String> entry : queries.entrySet()) {
                if(queryString == null)
                    queryString = entry.getKey()+"="+entry.getValue();
                else
                    queryString = queryString+ "&"+entry.getKey()+"="+entry.getValue();
            }
        }
    }

    @Given("^I perform post reqest using base URL \"([^\"]*)\" uri path \"([^\"]*)\" with body json file \"([^\"]*)\" and verify response code (\\d+)$")
    public static void i_perform_post_request(String url, String uriPath, String fileName, int statusCode)
            throws Throwable {
        Object jsonBody = JSONUtils.readJsonDataFromDefaltFolder(fileName);
        System.out.println(jsonBody);
        response = RestAPIUtils.postRequest(url, bearerToken, uriPath, jsonBody.toString());
        Assert.assertEquals("response code is not equal to " + statusCode, response.getStatusCode(), statusCode);
    }

    @Given("^I perform get request using URI path \"([^\"]*)\"$")
    public static void I_perform_get_request_using_URI_path(String uriPath)
            throws JsonParseException, JsonMappingException, IOException {
        setBaseUrl();
        if(queryString != null) {
            uriPath = uriPath + "?"+queryString;
        }
        response = RestAPIUtils.getResponse(uriPath, headers, bodyParams);
        System.out.println(response.asString());
    }

    @Given("^I perform delete request using URI path \"([^\"]*)\"$")
    public static void I_perform_delete_request_using_URI_path(String uriPath)
            throws JsonParseException, JsonMappingException, IOException {
        setBaseUrl();
        if(queryString != null) {
            uriPath = uriPath + "?"+queryString;
        }
        response = RestAPIUtils.deleteResponse(uriPath, headers, bodyParams);
        System.out.println(response.asString());
    }

    @Given("^I perform post request using URI path \"([^\"]*)\"$")
    public static void I_perform_post_request_using_URI_path(String uriPath)
            throws JsonParseException, JsonMappingException, IOException {
        setBaseUrl();
        if(queryString != null)
            uriPath = uriPath + "?"+queryString;
        response = RestAPIUtils.postOrPutResponse(uriPath, headers, bodyParams, postBody, "Post");
        System.out.println("response :"+response.asString());
    }

    @Given("^I perform put request using URI path \"([^\"]*)\"$")
    public static void I_perform_put_request_using_URI_path(String uriPath)
            throws JsonParseException, JsonMappingException, IOException {
        setBaseUrl();
        if(queryString != null)
            uriPath = uriPath+ "?"+queryString;
        response = RestAPIUtils.postOrPutResponse(uriPath, headers, bodyParams, postBody, "Put");
        System.out.println(response.asString());
    }

    @Given("^I verify response code (\\d+)$")
    public static void I_verify_response_code(int statusCode) throws Exception {
        if (response == null)
            throw new Exception("Response value is null");
        Assert.assertEquals(response.getStatusCode(), statusCode);
    }

    @Given("^I verify response content type \"([^\"]*)\"$")
    public static void I_verify_response_content_type(String contentType) throws Exception {
        if (response == null)
            throw new Exception("Response value is null");
        Assert.assertEquals(contentType, response.getContentType());
    }

    @Given("^I set header values$")
    public static void I_set_header_values(DataTable dataTable) {
        List<Map<String, String>> listHeaders = dataTable.asMaps(String.class, String.class);
        Header header1 = new Header("Authorization", "Bearer " + bearerToken);
        List<Header> list = new LinkedList<Header>();
        list.add(header1);
        if (listHeaders.size() > 0) {
            if (listHeaders.get(0).containsKey("ContentType")) {
                Header header3 = new Header("Content-Type", listHeaders.get(0).get("ContentType"));
                list.add(header3);
            }
            if (listHeaders.get(0).containsKey("Accept")) {
                Header header2 = new Header("accept", listHeaders.get(0).get("Accept"));
                list.add(header2);
            }
        }
        headers = new Headers(list);
    }

    // Get Response Body and Verify Response Code
    @Given("^I perform get request using base URI \"([^\"]*)\" and URI path \"([^\"]*)\" and verify response code (\\d+)$")
    public static void I_perform_get_request_using_base_URI_and_URI_path_and_verify_response_code(String baseUri,
                                                                                                  String uriPath, int statusCode) throws JsonParseException, JsonMappingException, IOException {
        response = RestAPIUtils.getResponse(baseUri, uriPath, bearerToken);
        // System.out.println(response.asString());
        ObjectMapper mapper = new ObjectMapper();
        String json = response.asString();
        System.out.println(json);
        UserModel[] staff2 = mapper.readValue(json, UserModel[].class);
        System.out.println(staff2.length);
        System.out.println(staff2[0]);
        Assert.assertEquals(response.getStatusCode(), statusCode);
    }

    @Then("I verify user data in response using user name")
    public void i_verify_user_data_in_JSON_reponse(io.cucumber.datatable.DataTable dataTable) throws Exception {
        if (response == null)
            throw new Exception("Response value is null, Please call API method before this steps");
        List<Map<String, String>> userDetails = dataTable.asMaps(String.class, String.class);
        ObjectMapper mapper = new ObjectMapper();
        String json = response.asString();
        System.out.println(json);
        UserModel[] usermodels = mapper.readValue(json, UserModel[].class);
        System.out.println(usermodels.length);
        System.out.println(usermodels[0]);
        for (Map<String, String> userDetail : userDetails) {
            boolean status = false;
            for (UserModel usermodel : usermodels) {
                String userName = usermodel.getUserName();
                if (userName.equals(userDetail.get("UserName"))) {
                    Assert.assertEquals(usermodel.getFirstName(), userDetail.get("FirstName"));
                    Assert.assertEquals(usermodel.getLastName(), userDetail.get("LastName"));
                    Assert.assertEquals(usermodel.getEmail(), userDetail.get("Email"));
                    Assert.assertEquals(usermodel.getAssociationCount(), userDetail.get("AssociationsCount"));
                    Assert.assertEquals(usermodel.getUserRoles().get(0).getSafetyRoleName(), userDetail.get("Role"));
                    Assert.assertEquals(usermodel.getStatus(), userDetail.get("UserStatus"));
                    status = true;
                }
            }
            Assert.assertTrue("record not found in request for user name" + userDetail.get("UserName"), status);
        }
    }

    @Then("I verify data in JSON reponse")
    public void i_verify_data_in_JSON_reponse(io.cucumber.datatable.DataTable dataTable) throws Exception {
        List<Map<String, String>> list = dataTable.asMaps(String.class, String.class);
        System.out.println(list.size());
        for (Map<String, String> jsonData : list) {
            JSONUtils.verifyValueInResponse(response, jsonData.get("NodePath"), jsonData.get("NodeName"),
                    jsonData.get("Value"));
        }
    }

    private static void setBaseUrl() {
        RestAPIUtils.setBaseUrl(baseUrl);
    }

}