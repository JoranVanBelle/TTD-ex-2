package org.example.infra;

import org.json.JSONObject;

public interface ApiScanner {

    JSONObject getApiResponse(String url, String location);

}
