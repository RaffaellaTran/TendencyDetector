package com.app.raffaellatran.tendencydetector.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RequestJSON {
    String jsonRequest;

    public String getJsonRequest() {
        return jsonRequest;
    }

    public void setJsonRequest(String jsonRequest) {
        this.jsonRequest = jsonRequest;
    }

    public RequestJSON(String api_key, String text, String username)  {

        JSONObject jsonApi = new JSONObject();
        JSONObject jsonData = new JSONObject();
        JSONObject jsonDataCollection = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonCall = new JSONObject();

        try {
            jsonData.put("text",text);
            jsonData.put("language_iso","eng");
            jsonData.put("user", username);
            jsonArray.put(0,jsonData);
            jsonCall.put("sentiment_classifier","subjective");
            jsonCall.put("data",jsonArray);


            jsonApi.put("api_key", api_key);
            jsonApi.put("call",jsonCall);
            jsonDataCollection.put("data", jsonApi);
        } catch (JSONException e) {
            e.printStackTrace();
        }

         String j= jsonDataCollection.toString();
        //String r ="request={\"data\":{\"api_key\":\"528d5710d5c85c1d130416db21fae66303491b70bbc91ec61b1716205afe2098\",\"call\":{\"data\":[{\"text\":\"I love you\",\"language_iso\":\"eng\",\"user\":\"mary\"}]}}}";

      //  jsonRequest = "request=" + j;


        jsonRequest ="request={" +
                "   \"data\":{" +
                "      \"api_key\":\"528d5710d5c85c1d130416db21fae66303491b70bbc91ec61b1716205afe2098\","+
                "      \"call\":{" +
                "         \"return_original\":false," +
                "         \"sentiment_classifier\": \"subjective\"," +
                "         \"data\":[" +
                "            {" +
                "               \"text\":\"" + text+"\","+
                "               \"language_iso\":\"eng\"," +
                "               \"user\":\"" + username +"\""+
                "            }" +
                "         ]" +
                "      }" +
                "   }" +
                "}";
    }
}
