package com.kwetter.filters;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.kwetter.filters.interfaces.IKeyGenerator;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.UUID;

public class KeyGenerator implements IKeyGenerator {

    @Override
    public Key generateKey() {
        String keyString = "Kwetteren-in-bad";
        return new SecretKeySpec(keyString.getBytes(), 0, keyString.getBytes().length, "DES");
    }

    @Override
    public UUID getIdFromToken(String token) {
        String[] split_string = token.split("\\.");
        String base64EncodedBody = split_string[1];
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String body = new String(decoder.decode(base64EncodedBody));
        Gson gson = new Gson();
        String id = gson.fromJson(body, JsonObject.class).get("id").getAsString();
        return !id.isEmpty() ? UUID.fromString(id) : null;
    }
}
