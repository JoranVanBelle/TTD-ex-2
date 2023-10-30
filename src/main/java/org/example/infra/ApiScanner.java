package org.example.infra;

import org.json.JSONObject;

public interface ApiScanner {

    JSONObject getApi(String location);

}
