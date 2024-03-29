import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

public class ResponseService {
    private ObjectMapper objectMapper = new ObjectMapper();
    protected static ConcurrentHashMap<String, String> stringStorage = new ConcurrentHashMap<>();

    public  RequestMethod readFirstLIneAndImplementRequestMethod(BufferedReader bufferedReader)
        throws IOException {
        RequestMethod requestMethod = new RequestMethod();
        String firstLine = bufferedReader.readLine();
        System.out.println("firstLine = " + firstLine);
        String[] firstLineArray = firstLine.split(" ");
        requestMethod.setMethod(firstLineArray[0]);
        String requestTypeAndTextId = firstLineArray[1].substring(1, firstLineArray[1].length());
        if (requestTypeAndTextId.contains("/")) {
            String[] requestTypeAndTextIdArray = requestTypeAndTextId.split("/");
            requestMethod.setRequestType(requestTypeAndTextIdArray[0]);
            requestMethod.setTextId(requestTypeAndTextIdArray[1]);
        } else {
            requestMethod.setRequestType(requestTypeAndTextId);
        }
        return requestMethod;
    }

    public byte[] responseFromGETAndTime() throws JsonProcessingException {
        TimeResponseMessageBody timeResponseMessageBody = new TimeResponseMessageBody();
        timeResponseMessageBody.setTime(String.valueOf(new Date()));
        byte[] timeJsonBytes = objectMapper.writeValueAsBytes(timeResponseMessageBody);
        return timeJsonBytes;
    }

    public byte[] responseFromGETAndImage() throws IOException {
        BufferedImage image = ImageIO.read(getClass().getResourceAsStream("sea.jpg"));
        System.out.println(image);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", byteArrayOutputStream);
        byteArrayOutputStream.flush();
        byte[] imageInByte = byteArrayOutputStream.toByteArray();
        return imageInByte;
    }

    public String responseFromGETAndText(RequestMethod requestMethod){
        String textId = requestMethod.getTextId();
        if (textId != null) {
            String storageString = stringStorage.get(textId);
            if (storageString != null) {
                return storageString;
            }
        }
        return null;
    }

    public TreatStateCode treatFromPostAndText(String messageBody, RequestMethod requestMethod) {
        if (messageBody == null) {
            return TreatStateCode.FAIL;
        } else {
            String textId = requestMethod.getTextId();
            stringStorage.put(textId, messageBody);
            return TreatStateCode.SUCCESS;
        }
    }

    public TreatStateCode responseFromDELETEAndText(RequestMethod requestMethod) throws IOException {
        String textId = requestMethod.getTextId();
        if (textId != null) {
            String getString = stringStorage.get(textId);
            if (getString != null) {
                stringStorage.remove(textId);
                return TreatStateCode.SUCCESS;
            } else {
                return TreatStateCode.FAIL;
            }
        } else {
            return TreatStateCode.FAIL;
        }
    }

    public void sendResponseFromGETAndTime(DataOutputStream dataOutputStream, byte[] timeJsonBytes)
        throws IOException {
        dataOutputStream.writeBytes(ResponseStateCode.response200OK.getStateMessage());
        dataOutputStream.write(timeJsonBytes, 0, timeJsonBytes.length);
        dataOutputStream.flush();
    }

    public void sendResponseFromGETAndImage(DataOutputStream dataOutputStream, byte[] imageInByte)
        throws IOException {
        dataOutputStream.writeBytes(ResponseStateCode.response200OK.getStateMessage());
        dataOutputStream.write(imageInByte,0,imageInByte.length);
        dataOutputStream.flush();
    }

    public void sendResponseFromGETAndText(DataOutputStream dataOutputStream,
        RequestMethod requestMethod, String storageString) throws IOException {
        String textId = requestMethod.getTextId();
        if (textId == null) {
            dataOutputStream.writeBytes(ResponseStateCode.response404NotFound.getStateMessage());
        } else {
            if (storageString == null) {
                dataOutputStream.writeBytes(
                    ResponseStateCode.response404NotFound.getStateMessage());
            } else {
                dataOutputStream.writeBytes(ResponseStateCode.response200OK.getStateMessage());
                dataOutputStream.writeBytes(storageString);
                dataOutputStream.flush();
            }
        }
    }

    public void responseFromPostAndText(TreatStateCode treatStateCode,
        DataOutputStream dataOutputStream) throws IOException {
        if (treatStateCode == TreatStateCode.SUCCESS) {
            dataOutputStream.writeBytes(ResponseStateCode.response201Created.getStateMessage());
        } else if (treatStateCode == TreatStateCode.FAIL) {
            dataOutputStream.writeBytes(ResponseStateCode.response404NotFound.getStateMessage());
        }
    }

    public void sendResponseFromDELETEAndText(TreatStateCode treatStateCode, DataOutputStream dataOutputStream)
        throws IOException {
        if (treatStateCode == TreatStateCode.SUCCESS) {
            dataOutputStream.writeBytes(ResponseStateCode.response204NoContent.getStateMessage());
            dataOutputStream.flush();
        } else if (treatStateCode == TreatStateCode.FAIL) {
            dataOutputStream.writeBytes(ResponseStateCode.response404NotFound.getStateMessage());
            dataOutputStream.flush();
        }
    }
}
