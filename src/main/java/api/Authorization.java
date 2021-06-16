package api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;
import utils.api.APIPathes;

import static io.restassured.RestAssured.given;

public class Authorization {

    public static ValidatableResponse JSESSIONID;
    public static String BASE_URI = "https://api.useverb.com/v1";
    public static String email = "pjs+9@useverb.com";
    public static String password = "Passwd000!";
    public static String device = "mobile";
    public static boolean isRecruiteroptional = Boolean.FALSE;
    public static boolean keepOlderAccounts = Boolean.FALSE;

//    static final Logger logger = Logger.getLogger(Authorization.class);
@Test
    public static void loginToUseVerb() {
        RestAssured.baseURI = BASE_URI;

        String loginJson = UseVerbJSONObjectHelper.generateJSONForLogin();
        JSESSIONID =
                given().
                        header("Content-Type", ContentType.JSON).
                        body(loginJson).
                        when().
                        post(APIPathes.login).
                        then().
                        statusCode(200);

//                        extract().
//                        path("session.value");

//        logger.info("\nAUTHORIZATION TOKEN: " + Authorization.JSESSIONID);
    }
}
