package net.inspiredtoeducate.droidtasks;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class HttpClient //extends Activity 
{
		private static final String TAG = "httpclient";
		
		public HttpClient ()
		{
	
		}
	 
		 public String getRequest(String aUrl)
		 {
			 String out = "";
			 try {
				  URL url = new URL(aUrl);
				  HttpURLConnection con = (HttpURLConnection) url.openConnection();
				  out = readStream(con.getInputStream());
				  } catch (Exception e) {
				  e.printStackTrace();
				}
			 
			 return out;
		 }
		   
	   private String readStream(InputStream in) {
		   
		   String response = "";
		   BufferedReader reader = null;
		   try {
		     reader = new BufferedReader(new InputStreamReader(in));
		     String line = "";
		     while ((line = reader.readLine()) != null) {
		    	 response += line;
		     }
		   } catch (IOException e) {
		     e.printStackTrace();
		   } finally {
		     if (reader != null) {
		       try {
		         reader.close();
		       } catch (IOException e) {
		         e.printStackTrace();
		         }
		     }
		   }
		   
		   return response;
		 } 		   
	


    
}