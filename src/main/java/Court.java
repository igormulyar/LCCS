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
    private String referer;

    public Court(String name, String idInNumber, String courtId, String url, String referer) {
        this.name = name;
        this.idInNumber = idInNumber;
        this.courtId = courtId;
        this.url = url;
        this.referer = referer;
    }

    public Court(String idInNumber) {
        this.idInNumber = idInNumber;
        String courtsInfo = getFileInString("courts");
        JsonParser parser = new JsonParser();

    }

    private String getFileInString(String path) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new FileReader("path")))
        {
            String s;
            while((s = in.readLine()) != null){
                sb.append(s);
                sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
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

    public String getReferer() {
        return referer;
    }
}
