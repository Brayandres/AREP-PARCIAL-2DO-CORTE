package edu.eci.arep.roundRobin;

import static spark.Spark.*;

import com.google.gson.Gson;

import edu.eci.arep.roundRobin.connection.CalcServiceConnection;
import edu.eci.arep.roundRobin.front.Front;
import edu.eci.arep.roundRobin.model.RoundRobin;
import edu.eci.arep.roundRobin.response.StandardResponse;
import edu.eci.arep.roundRobin.response.StatusResponse;
import spark.Request;
import spark.Response;

public class SparkWebServer {
    
    public static void main(String... args){
          port(getPort());
          get("/hello", (request, response) -> "Hello Docker!");
          get("/", (request, response) -> Front.HTML);
          get("/sqrt", (request, response) -> calculate(request, response, "sqrt"));
          get("/exp", (request, response) -> calculate(request, response, "exp"));
    }
    
    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }
    
    private static String calculate(Request request, Response response, String operation) {
    	response.type("application/json");
    	try {
    		String value = request.queryParams("value");
    		String[] values = RoundRobin.getInstance().getNext();
    		String turn = values[0];
    		String url = values[1];
    		System.out.println("send("+value+", "+turn+", "+url+")");
    		System.out.println("{operation:'"+operation+"', value:'"+value+"'}");
    		response.body("{operation:"+operation+", value:"+value+"}");
    		return CalcServiceConnection.getResult(url, operation, value);
    	} catch (Exception e) {
    		return new Gson().toJson(new StandardResponse(StatusResponse.ERROR));
    	}
    }
}