import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class RequestHandler implements Runnable {

    private final Socket socket;
    private final ResponseService responseService = new ResponseService();
    private final RequestTool requestTool = new RequestTool();

    public RequestHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream()) {

            BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

            RequestMethod requestMethod = responseService.readFirstLIneAndImplementRequestMethod(
                bufferedReader);
            System.out.println(requestMethod.toString());
            switch (requestMethod.getMethod()) {
                case "GET":
                    switch (requestMethod.getRequestType()) {
                        case "time":
                            byte[] timeJsonBytes = responseService.responseFromGETAndTime();
                            responseService.sendResponseFromGETAndTime(dataOutputStream,
                                timeJsonBytes);
                            break;
                        case "image":
                            byte[] imageInByte = responseService.responseFromGETAndImage();
                            responseService.sendResponseFromGETAndImage(dataOutputStream,
                                imageInByte);
                            break;
                        case "text":
                            String storageString = responseService.responseFromGETAndText(
                                requestMethod);
                            responseService.sendResponseFromGETAndText(dataOutputStream,
                                requestMethod, storageString);
                            break;
                    }
                    break;
                case "POST":
                    switch (requestMethod.getRequestType()) {
                        case "text":
                            String textId = requestMethod.getTextId();
                            String messageBody;
                            if (textId != null) {
                                Map<String, String> headerInformation = requestTool.readHeader(
                                    bufferedReader);
                                int messageBodyLength
                                    = Integer.parseInt(
                                    headerInformation.get("Content-Length").trim());
                                messageBody = requestTool.readDate(bufferedReader,
                                    messageBodyLength);
                            } else {
                                messageBody = null;
                            }
                            TreatStateCode treatStateCode =
                                responseService.treatFromPostAndText(messageBody, requestMethod);
                            responseService.responseFromPostAndText(treatStateCode,
                                dataOutputStream);
                            break;
                    }
                    break;
                case "DELETE":
                    switch (requestMethod.getRequestType()) {
                        case "text":
                            TreatStateCode treatStateCode = responseService.responseFromDELETEAndText(
                                requestMethod);
                            responseService.sendResponseFromDELETEAndText(treatStateCode,
                                dataOutputStream);
                            break;
                    }
            }
            System.out.println("checking StringStorage: " + responseService.stringStorage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
