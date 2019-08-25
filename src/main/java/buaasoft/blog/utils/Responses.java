package buaasoft.blog.utils;

import com.alibaba.fastjson.JSONObject;

/**
 * @author Yirany
 * @version 1.0
 * @since 8/25/2019
 **/
public class Responses {
    public static String userNotFoundResponse(String username) {
        JSONObject response = new JSONObject();
        response.put(Constants.STATUS, false);
        response.put(Constants.errorMessage, "Cannot find user " + username);
        return response.toJSONString();
    }

    public static String postNotFoundResponse(long postID) {
        JSONObject response = new JSONObject();
        response.put(Constants.STATUS, false);
        response.put(Constants.errorMessage, "Cannot find post " + postID);
        return response.toJSONString();
    }

    public static String tagNotFoundResponse(String tagName) {
        JSONObject response = new JSONObject();
        response.put(Constants.STATUS, false);
        response.put(Constants.errorMessage, "Cannot find tag " + tagName);
        return response.toJSONString();
    }


}
