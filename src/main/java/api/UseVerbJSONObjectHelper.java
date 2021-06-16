package api;

import org.json.simple.JSONObject;

public class UseVerbJSONObjectHelper {
    public static String generateJSONForLogin() {
        JSONObject credentials = new JSONObject();
        credentials.put("username", Authorization.email);
        credentials.put("password", Authorization.password);
        credentials.put("device", Authorization.device);
        credentials.put("isRecruiteroptional", Authorization.isRecruiteroptional);
        credentials.put("keepOlderAccounts", Authorization.keepOlderAccounts);

        return credentials.toJSONString();
    }
}
