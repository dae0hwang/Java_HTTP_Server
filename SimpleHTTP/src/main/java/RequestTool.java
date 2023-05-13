import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestTool {

    public String readDate(BufferedReader br, int contentLength) throws IOException {
        char[] messageBody = new char[contentLength];
        br.read(messageBody, 0, contentLength);
        System.out.println("messageBody="+ messageBody);
        return String.copyValueOf(messageBody);
    }

    public Map<String, String> readHeader(BufferedReader br) throws IOException {
        Map<String, String> headerInformation = new HashMap<>();
        while (true) {
            String headerLine = br.readLine();
            System.out.println("headerLine= "+headerLine);
            if (!headerLine.equals("")) {
                String key = headerLine.split(":")[0];
                String value = headerLine.split(":")[1].trim();
                headerInformation.put(key, value);
            } else {
                break;
            }
        }
        return headerInformation;
    }
}
