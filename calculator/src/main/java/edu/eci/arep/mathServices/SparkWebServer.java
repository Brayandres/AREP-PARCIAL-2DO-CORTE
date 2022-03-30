package edu.eci.arep.mathServices;

import static spark.Spark.*;

import org.json.JSONObject;

import edu.eci.arep.mathServices.model.Calculator;
import edu.eci.arep.mathServices.response.StatusResponse;
import spark.Request;
import spark.Response;

public class SparkWebServer {
    
    public static void main(String... args){
          port(getPort());
          get("/sqrt", (request, response) -> getSqrt(request, response));
          get("/exp", (request, response) -> getExp(request, response));
    }
    
    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }
    
    private static String getSqrt(Request request, Response response) {
    	response.type("application/json");
    	JSONObject answer = new JSONObject();
    	try {
    		String value = request.queryParams("value");
    		double number = Double.parseDouble(value);
    		double result = Calculator.calculateSqrt(number);
    		answer.put("operation", "sqrt");
    		answer.put("input", number);
    		answer.put("output", result);
    	} catch (Exception e) {
    		answer.put("status", StatusResponse.ERROR);
    		answer.put("message", e.getMessage());
    	}
    	return answer.toString();
    }
    
    private static String getExp(Request request, Response response) {
    	response.type("application/json");
    	JSONObject answer = new JSONObject();
    	try {
    		String value = request.queryParams("value");
    		double number = Double.parseDouble(value);
    		double result = Calculator.calculateExp(number);
    		answer.put("operation", "exp");
    		answer.put("input", number);
    		answer.put("output", result);
    	} catch (Exception e) {
    		answer.put("status", StatusResponse.ERROR);
    		answer.put("message", e.getMessage());
    	}
    	return answer.toString();
    }
}