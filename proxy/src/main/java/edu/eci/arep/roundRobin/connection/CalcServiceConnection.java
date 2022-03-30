package edu.eci.arep.roundRobin.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.Gson;

import edu.eci.arep.roundRobin.response.StandardResponse;
import edu.eci.arep.roundRobin.response.StatusResponse;

public class CalcServiceConnection {
	
	public static String getResult( String serviceUrl, String operation, String number) {
		try {
			URL url = new URL(serviceUrl+"/"+operation+"?value="+number);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			StringBuffer content = new StringBuffer();
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			in.close();
			connection.disconnect();
			String response = content.toString();
			System.out.println("\n  GET RESPONSE: "+response+"\n");
			return response;
		} catch (MalformedURLException e) {
			System.out.println("  -- CATCH IN: MalformedURLException --");
			e.printStackTrace();
			return new Gson().toJson(new StandardResponse(StatusResponse.ERROR));
		} catch (IOException e) {
			System.out.println("  -- CATCH IN: IOException --");
			e.printStackTrace();
			return new Gson().toJson(new StandardResponse(StatusResponse.ERROR));
		}
	}
}