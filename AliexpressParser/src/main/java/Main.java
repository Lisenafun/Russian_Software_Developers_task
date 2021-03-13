import java.io.File;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {

//        String url = "https://flashdeals.aliexpress.com/en.htm?";
        ArrayList<String> urlList = new ArrayList<>();
        urlList.add("https://gpsfront.aliexpress.com/getRecommendingResults.do?callback=jQuery18309705384561978649_1615644146437&widget_id=5547572&platform=pc&limit=20&offset=0&phase=1&productIds2Top=&postback=&_=1615644147546");
        urlList.add("https://gpsfront.aliexpress.com/getRecommendingResults.do?callback=jQuery18309705384561978649_1615644146437&widget_id=5547572&platform=pc&limit=40&offset=20&phase=1&productIds2Top=&postback=323f07da-8be3-49e3-b0ee-9598722286cd&_=1615644195681");
        urlList.add("https://gpsfront.aliexpress.com/getRecommendingResults.do?callback=jQuery18309705384561978649_1615644146437&widget_id=5547572&platform=pc&limit=40&offset=60&phase=1&productIds2Top=&postback=323f07da-8be3-49e3-b0ee-9598722286cd&_=1615644235174");

        MyParser parser = new MyParser();
        for(String s : urlList) {
            String content = parser.parsePage(s);
            parser.getJSON(content);
        }

        String fileName = "content.json";
        parser.writeJSONFile(fileName);

        CSVConverter converter = new CSVConverter();
        for(int i = 0; i < urlList.size(); i++) {
            converter.JSONToCSV(new File(fileName), new File("result.csv"));
        }
        parser.shutdown();

        File file = new File(fileName);
        file.delete();
    }
}
