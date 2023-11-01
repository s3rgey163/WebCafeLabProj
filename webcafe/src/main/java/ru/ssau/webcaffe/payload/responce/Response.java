package ru.ssau.webcaffe.payload.responce;

import com.google.gson.Gson;

public enum Response {
    INVALID_LOGIN(new InvalidLoginResponse()),
    ;
    private final Gson gson = new Gson();
    private final Object jsonObj;

    Response(Object jsonObj) {
        this.jsonObj = jsonObj;
    }

    public String toJson() {
        return gson.toJson(jsonObj);
    }

    public static void main(String[] args) {
        System.out.println(INVALID_LOGIN.toJson());
    }

}
