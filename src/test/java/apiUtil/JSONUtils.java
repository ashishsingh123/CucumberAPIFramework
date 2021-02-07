package apiUtil;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class JSONUtils {

    /**
     * @Author Ashish Kr Singh
     * @param fileName
     * @return
     * @throws Exception
     */
    public static Object readJsonDataFromDefaltFolder(String fileName) throws Exception {
        if (fileName.isEmpty() || fileName == null)
            throw new Exception("Please provide valid file name");
        JSONParser parser = new JSONParser();
        Object obj = null;
        try {
            obj = parser.parse(new FileReader("JSONFile/" + fileName + ".json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return obj;
    }    
    
    /**
     * @Author Ashish Kr Singh
     * Verify that response contains json value
     * @param response : response object
     * @param path : josn node path 
     * @param key : node name for which we required to check value
     * @param value : Node value
     * @return : It will return true if json node value exist and return false if value not exist
     * @throws Exception
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static boolean verifyValueInResponse(Response response, String path, String key, Object value) throws Exception {
        boolean isAvailable = false;
        JsonPath jsonPath = new JsonPath(response.asString());
        Object object = jsonPath.getJsonObject(path);
        if (object instanceof HashMap) {
            isAvailable = verifyValueInMap((HashMap<String, Object>) object, key, value);
        } else if (object instanceof ArrayList) {
            isAvailable = verifyValueInList((ArrayList) object, key, value);
        } else {
            if (object.equals(value)) {
                isAvailable = true;
            }
        }
        Assert.assertTrue("Value "+value+" not found for node "+key, isAvailable);
        return isAvailable;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static boolean verifyValueInMap(HashMap<String, Object> map, String key, Object value) throws Exception {
        boolean isAvailable = false;
        for (Map.Entry<String, Object> m : map.entrySet()) {
            Object object = m.getKey();
            if (object instanceof HashMap) {
                isAvailable = verifyValueInMap((HashMap<String, Object>) object, key, value);
                if (isAvailable) {
                    break;
                }
            } else if (object instanceof ArrayList) {
                isAvailable = verifyValueInList((ArrayList) object, key, value);
                if (isAvailable) {
                    break;
                }
            } else {
                if (m.getKey().equals(key)) {
                    if (m.getValue() instanceof ArrayList) {
                        isAvailable = verifyValueInList((ArrayList) m.getValue(), key, value);
                        if (isAvailable) {
                            break;
                        }
                    } else if (m.getValue() instanceof HashMap) {
                        isAvailable = verifyValueInMap((HashMap<String, Object>) m.getValue(), key, value);
                        if (isAvailable) {
                            break;
                        }
                    } else {
                        if (value == null && m.getValue() == null) {
                            isAvailable = true;
                            break;
                        } else if (m.getValue().equals(value)) {
                            isAvailable = true;
                            break;
                        }
                    }
                }
            }
        }
        return isAvailable;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static boolean verifyValueInList(ArrayList list, String key, Object value) throws Exception {
        boolean isAvailable = false;
        for (int i = 0; i < list.size(); i++) {
            Object object = list.get(i);
            if (object instanceof HashMap) {
                isAvailable = verifyValueInMap((HashMap<String, Object>) object, key, value);
                if (isAvailable) {
                    break;
                }
            } else if (object instanceof ArrayList) {
                isAvailable = verifyValueInList((ArrayList) object, key, value);
                if (isAvailable) {
                    break;
                }
            } else {
                if (object != null) {
                    if (object.equals(value)) {
                        isAvailable = true;
                        break;
                    }
                }
            }
        }
        return isAvailable;
    }

}
