package me.cleon.streamaccess.request;

import java.util.ArrayList;

public class StreamRequest {
    private float code;
    private String server;
    ArrayList< Object > streams = new ArrayList < Object > ();


    // Getter Methods

    public float getCode() {
        return code;
    }

    public String getServer() {
        return server;
    }

    // Setter Methods

    public void setCode(float code) {
        this.code = code;
    }

    public void setServer(String server) {
        this.server = server;
    }
}
