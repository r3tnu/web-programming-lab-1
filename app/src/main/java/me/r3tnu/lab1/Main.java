package me.r3tnu.lab1;
import com.fastcgi.FCGIInterface;
import com.fastcgi.FCGIRequest;

import java.time.DateTimeException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import jakarta.json.*;
import me.r3tnu.lab1.exceptions.QueryExeption;
import me.r3tnu.lab1.exceptions.ValidationExeption;
import me.r3tnu.lab1.interfaces.*;

public class Main {

    static Map<String, List<String>> getParametersFromStringQuery(String query) {
        String[] paramsArr = query.split("&");
        Map<String, List<String>> params = new HashMap<>();
        
        for (String i : paramsArr) {
            String[] kv = i.split("=");
            if (params.containsKey(kv[0])) {
                params.get(kv[0]).add(kv[1]);
            } else {
                params.put(kv[0], new ArrayList<>(Arrays.asList(kv[1])));
            }
        }
        return params;
    }

    static boolean checkParam(List<String> param) {
        if (Objects.isNull(param)) {
            return false;
        }
        if (param.size() != 1) {
            return false;
        }
        return true;
    }

    static Point deserializeRequest(FCGIRequest request) throws QueryExeption, NumberFormatException {
        String query = request.params.getProperty("QUERY_STRING");
        Pattern queryStringPattern = Pattern.compile("^([\\w-]+(=[\\w-]+)(&[\\w-]+(=[\\w-]+))*)?$");
        Matcher matcher = queryStringPattern.matcher(query);
        if (matcher.find()) {
            Map<String, List<String>> params = getParametersFromStringQuery(query);
            for (String param : Arrays.asList("x", "y", "r")) {
                if (!checkParam(params.get(param))) {
                    throw new QueryExeption("The %s parameter has duplicates or is empty".formatted(param));
                }
            }
            double x = Double.parseDouble(params.get("x").get(0));
            double y = Double.parseDouble(params.get("y").get(0));
            double r = Double.parseDouble(params.get("r").get(0));
            return new Point(x, y, r);
        } else {
            throw new QueryExeption("Query string \"%s\" is malformed".formatted(query));
        }
    }

    static Result check(Point point, PointValidator validator, PointChecker checker) throws ValidationExeption{
        validator.validate(point);
        boolean result = checker.check(point);
        return new Result(result, point.getX(), point.getY(), point.getR());
    }

    static JsonObject serializeToResponseJson(Result result, Duration executionTime) {
        JsonObject json = Json.createObjectBuilder()
                    .add("result", result.getResult())
                    .add("x", result.getX())
                    .add("y", result.getY())
                    .add("r", result.getR())
                    .add("executionTime", String.valueOf(executionTime.getNano()))
                    .build();
        return json;
    }

    static void sendResponse(ResponseGenerator responseGenerator, JsonObject json) {
        System.out.println(responseGenerator.generateResponseString(200, "application/json", json.toString()));
    }

    public static void main (String args[]) {  
        FCGIInterface fcgiInterface = new FCGIInterface();
        ResponseGenerator responseGenerator = new ResponseGenerator();
        PointChecker pointChecker = new PointCheckerImp();
        PointValidator pointValidator = new PointValidatorImp();

        while (fcgiInterface.FCGIaccept() >= 0) {
            try {
                Instant start = Instant.now();

                Point point = deserializeRequest(FCGIInterface.request);
                
                Result result = check(point, pointValidator, pointChecker);
                
                Instant end = Instant.now();
                Duration executionTime = Duration.between(start, end);

                JsonObject json = serializeToResponseJson(result, executionTime);

                sendResponse(responseGenerator, json);

            } catch(ValidationExeption e) {
                System.out.println(responseGenerator.generateResponseString(400, "text/plain", e.getMessage()));
            } catch (DateTimeException e) {
                System.out.println(responseGenerator.generateResponseString(400, "text/plain", e.getMessage()));
            } catch (NumberFormatException e) {
                System.out.println(responseGenerator.generateResponseString(400, "text/plain", "Couldn't parse input values " + e.getMessage()));
            } catch (QueryExeption e) {
                System.out.println(responseGenerator.generateResponseString(400, "text/plain", e.getMessage()));
            } catch (Exception e) {
                System.out.println(responseGenerator.generateResponseString(500, "text/plain", "Internal server error"));
            }
        }
    }
}
