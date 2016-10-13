import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.io.*;

/**
 * Created by igor on 02.10.16.
 */
public class Court {

    private String name;
    private String idInNumber;
    private String courtId;
    private String url;
    private String host;
    private String referer;

    public Court(String name, String idInNumber, String courtId, String url, String host, String referer) {
        this.name = name;
        this.idInNumber = idInNumber;
        this.courtId = courtId;
        this.url = url;
        this.host = host;
        this.referer = referer;
    }

    public String getName() {
        return name;
    }

    public String getIdInNumber() {
        return idInNumber;
    }

    public String getCourtId() {
        return courtId;
    }

    public String getUrl() {
        return url;
    }

    public String getHost() {
        return host;
    }

    public String getReferer() {
        return referer;
    }
}
