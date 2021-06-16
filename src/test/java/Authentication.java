import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class Authentication {
    private static String token;
    private static String userID;
    private static Map<String, String> cookies;
    private String chatId;
    private String messageId;

    @BeforeTest
    public void sign_up(){
        JSONObject requestBody = new JSONObject();
        requestBody.put("email", "js+0@useverb.com");
        requestBody.put("password","Passwd000!");
        requestBody.put("device","console");
        String user1 = requestBody.toJSONString();
        RestAssured.baseURI = "https://api.useverb.com/v1/";

        RequestSpecification request = given();
        request.header("Content-Type", "application/json");
        request.body(user1);
        Response response = request.post("/auth/"+"signin");

        int statusCode = response.getStatusCode();
        Assert.assertEquals(200, statusCode);
        System.out.println("The status code recieved: " + statusCode);

        token = response.getBody().jsonPath().get("csrfToken").toString();
        System.out.println("csrfToken ="+ token);
        userID = response.getBody().jsonPath().get("userId").toString();
        System.out.println("UserId = "+userID);
        cookies = response.getCookies();
    }
    @Test(priority = 1)
    public void get_the_user_chat(){
        RestAssured.baseURI = "https://api.useverb.com/v1/";

        RequestSpecification request = given();
        request.header("X-CSRF-Token", token);
        request.cookies(cookies);
        Response response = request.get("users/"+userID+"/chats");

        int statusCode = response.getStatusCode();
        Assert.assertEquals(200, statusCode);
        System.out.println("The status code recieved: " + statusCode);

        chatId = ((Map<String, Object>)response.jsonPath().getList("chats").get(0)).get("_id").toString();
        System.out.println("chatId ="+ chatId);
    }
    @Test(priority = 2)
    public void create_message(){
        JSONObject requestBody = new JSONObject();
        requestBody.put("message", "APITest");
        requestBody.put("type","text");
        String message = requestBody.toJSONString();
        RestAssured.baseURI = "https://api.useverb.com/v1/";

        RequestSpecification request = given();
        request.header("Content-Type", "application/json");
        request.header("X-CSRF-Token", token);
        request.cookies(cookies);
        request.body(message);
        Response response = request.post("chats/"+chatId+"/messages");

        int statusCode = response.getStatusCode();
        Assert.assertEquals(200, statusCode);
        System.out.println("The status code recieved: " + statusCode);

        messageId = response.jsonPath().getMap("message").get("_id").toString();
        System.out.println("messageId ="+ messageId);
    }
    @Test(priority = 3)
    public void get_message(){
        RestAssured.baseURI = "https://api.useverb.com/v1/";

        RequestSpecification request = given();
        request.header("X-CSRF-Token", token);
        request.cookies(cookies);
        Response response = request.get("chats/"+chatId+"/messages/"+messageId);

        int statusCode = response.getStatusCode();
        Assert.assertEquals(200, statusCode);
        System.out.println("The status code recieved: " + statusCode);
    }
}
