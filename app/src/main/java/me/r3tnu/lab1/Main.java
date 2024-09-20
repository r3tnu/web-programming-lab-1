package me.r3tnu.lab1;
import com.fastcgi.FCGIInterface;

import java.time.DateTimeException;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import jakarta.json.*;
import me.r3tnu.lab1.exceptions.ValidationExeption;
import me.r3tnu.lab1.interfaces.*;

public class Main {

    public static Map<String, String> getParametersFromStringQuery(String query) {
        String[] paramsArr = query.split("&");
        Map<String, String> params = new HashMap<String, String>();
        
        for (String i : paramsArr) {
            String[] kv = i.split("=");
            params.put(kv[0], kv[1]);
        }
        return params;
    }

    public static void main (String args[]) {  
        var fcgiInterface = new FCGIInterface();
        while (fcgiInterface.FCGIaccept() >= 0) {
            ResponseGenerator responseGenerator = new ResponseGenerator();
            try {
                Instant start = Instant.now();

                PointChecker pointChecker = new PointCheckerImp();
                PointValidator pointValidator = new PointValidatorImp();
                
                String query = fcgiInterface.request.params.getProperty("QUERY_STRING"); 
                Map<String, String> params = getParametersFromStringQuery(query);
                
                Double x = Double.parseDouble(params.get("x"));
                Double y = Double.parseDouble(params.get("y"));
                Double r = Double.parseDouble(params.get("r"));
                
                Point point = new Point(x, y, r);
                pointValidator.validate(point);

                Boolean result = pointChecker.checkPoint(point);
                
                Instant end = Instant.now();
                Duration executionTime = Duration.between(start, end);

                String json = Json.createObjectBuilder()
                    .add("result", result)
                    .add("execution_time", String.valueOf(executionTime.getSeconds()) + "." + String.valueOf(executionTime.getNano()))
                    .build()
                    .toString();
                
                System.out.println(responseGenerator.generateResponseString(200, "application/json", json));
            } catch(ValidationExeption e) {
                System.out.println(responseGenerator.generateResponseString(400, "text/plain", e.getMessage()));
            } catch (DateTimeException e) {
                System.out.println(responseGenerator.generateResponseString(400, "text/plain", e.getMessage()));
            } catch (NumberFormatException e) {
                System.out.println(responseGenerator.generateResponseString(400, "text/plain", "Couldn't parse input values " + e.getMessage()));
            } catch (Exception e) {
                System.out.println(responseGenerator.generateResponseString(500, "text/plain", e.getMessage()));
            }

        }
  }
}
