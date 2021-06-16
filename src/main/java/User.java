import org.json.simple.JSONObject;

import java.util.Date;

public class User {
    public void create_user(){
        String someRandomString = String.format("%1$TH%1$TM%1$TS", new Date());
        JSONObject requestBody = new JSONObject();
        requestBody.put("email", someRandomString + "@useverb.com");
        String user1 = requestBody.toJSONString();
    }
}
