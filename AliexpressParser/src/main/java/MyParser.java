import com.gargoylesoftware.htmlunit.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class MyParser {

    private final WebClient webClient;
    private final ArrayList<String> resultsList;

    public MyParser() {
        webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setJavaScriptEnabled(true);
        resultsList = new ArrayList<>();
    }

    public String parsePage(String url) throws IOException {
        WebResponse webResponse = webClient.getPage(url).getWebResponse();
        String content = webResponse.getContentAsString();
        int firstIndex = content.indexOf("{");
        int lastIndex = content.lastIndexOf("}");
        content = content.substring(firstIndex, lastIndex + 1);
        return content;
    }

    public void getJSON(String content) throws ParseException {
        Object obj = new JSONParser().parse(content);
        JSONObject jsonContentObj = (JSONObject) obj;
        JSONArray results = (JSONArray) jsonContentObj.get("results");
        for(Object object : results) {
            JSONObject jsonObject = (JSONObject) object;
            jsonObject.remove("iconHeight");
            jsonObject.remove("icon");
            jsonObject.remove("promotionId");
            jsonObject.remove("trace");
            jsonObject.remove("sellerId");
            jsonObject.remove("startTime");
            jsonObject.remove("totalTranpro3");
            jsonObject.remove("phase");
            jsonObject.remove("iconWidth");
            jsonObject.remove("icons");
            jsonObject.remove("gmtCreate");
            jsonObject.remove("endTime");
        }
        String jsonString = results.toJSONString();
        jsonString = jsonString.substring(2, jsonString.length() - 2);
        String[] strings = jsonString.split("},\\{");
        resultsList.addAll(Arrays.asList(strings));
    }

    public void writeJSONFile(String fileName) throws IOException {
        FileWriter fw = new FileWriter(fileName, true);
        fw.write("[");
        for(int i = 0; i < resultsList.size(); i++) {
            if(i == resultsList.size() - 1) {
                fw.write("{" + resultsList.get(i) + "}");
            } else {
                fw.write("{" + resultsList.get(i) + "},");
            }
            fw.flush();
        }
        fw.write("]");
        fw.close();
    }

    public void shutdown() {
        webClient.close();
    }
}
