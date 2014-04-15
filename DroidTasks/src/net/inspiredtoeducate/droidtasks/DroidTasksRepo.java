package net.inspiredtoeducate.droidtasks;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class DroidTasksRepo {
	
	String _serviceURL;
	public DroidTasksRepo(String serviceURL)
	{
		_serviceURL = serviceURL;
	}
	
	public String Add(DroidTask aTask) {
		if(aTask.Task == "")
			throw new IllegalArgumentException("Task is required.");
		
		String url = _serviceURL + "save_task";
		
		try {
			url += "?Task=" + URLEncoder.encode(aTask.Task, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		};
		
		url += "&Priority=" + aTask.Priority;
		if(aTask.IsDone)
			url += "&IsDone=1";
		else
			url += "&IsDone=0";
		
		HttpClient client = new HttpClient();
		String response = client.getRequest(url);
		
		// Sample Response
		// {"result_code":0,"message":"SAVED","id":"534628d05f0a0"}
		String returnID = "";

		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(response);
		if(element.isJsonObject())
		{
			JsonObject obj = element.getAsJsonObject();
			returnID = obj.get("id").getAsString();
		}
		
		return returnID;	
	}
	
	
	
	
	public boolean RecordExists(String id) {
		String url = _serviceURL + "check_record_exists";
		
		try {
			url += "?id=" + URLEncoder.encode(id, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		};
					
		HttpClient client = new HttpClient();
		String response = client.getRequest(url);
		
		
		// sample response
		// {"record_exists":false}
		
		boolean returnValue = false;
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(response);
		if(element.isJsonObject())
		{
			JsonObject obj = element.getAsJsonObject();
			returnValue = obj.get("record_exists").getAsBoolean();
		}
		
		return returnValue;
	}
	
	
	
	public void Delete(String id) {
		String url = _serviceURL + "delete_task";		
		url += "?id=" + id;
					
		HttpClient client = new HttpClient();
		client.getRequest(url);
	}
	
	
	public java.util.ArrayList<DroidTask> GetAll()  {

		String url = _serviceURL + "list_task";		
		
		long t1 = System.currentTimeMillis();

		
		HttpClient client = new HttpClient();
		String jsonString = client.getRequest(url).trim();

		

		ArrayList<DroidTask> list = new ArrayList<DroidTask>();
		
		/*
		 * {"tasks":[{"id":"532baf07b4aef","0":"532baf07b4aef","task":"b","1":"b","priority":"1","2":"1","is_done":"0","3":"0"},{"id":"53462b6d7334e","0":"53462b6d7334e","task":"Save the world.","1":"Save the world.","priority":"1","2":"1","is_done":"0","3":"0"},{"id":"53462aafbc796","0":"53462aafbc796","task":"Save the world.","1":"Save the world.","priority":"1","2":"1","is_done":"0","3":"0"},{"id":"53462a97dd40f","0":"53462a97dd40f","task":"Save the world.","1":"Save the world.","priority":"1","2":"1","is_done":"0","3":"0"},{"id":"534628d05f0a0","0":"534628d05f0a0","task":"foo","1":"foo","priority":"1","2":"1","is_done":"1","3":"1"},{"id":"5345f6730f8c1","0":"5345f6730f8c1","task":"Save the world.","1":"Save the world.","priority":"1","2":"1","is_done":"0","3":"0"},{"id":"5345f65a6bd6a","0":"5345f65a6bd6a","task":"Save the world.","1":"Save the world.","priority":"1","2":"1","is_done":"0","3":"0"},{"id":"5345f63d2608e","0":"5345f63d2608e","task":"Save the world.","1":"Save the world.","priority":"1","2":"1","is_done":"0","3":"0"},{"id":"5345f6252b5e6","0":"5345f6252b5e6","task":"Save the world.","1":"Save the world.","priority":"1","2":"1","is_done":"0","3":"0"},{"id":"5345f3ff22a0f","0":"5345f3ff22a0f","task":"d","1":"d","priority":"1","2":"1","is_done":"0","3":"0"},{"id":"532bb024cc694","0":"532bb024cc694","task":"c","1":"c","priority":"1","2":"1","is_done":"0","3":"0"},{"id":"53462b8547379","0":"53462b8547379","task":"Save the world.","1":"Save the world.","priority":"1","2":"1","is_done":"0","3":"0"}]}
		 */
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(jsonString);
		if(element.isJsonObject())
		{
			JsonObject obj = element.getAsJsonObject();
			JsonArray objTasks = obj.getAsJsonArray("tasks");
            for (int i = 0; i < objTasks.size(); i++) {
                JsonObject jTask = objTasks.get(i).getAsJsonObject();
				DroidTask aTask = new DroidTask();
				aTask.Id = 				jTask.get("id").getAsString();
				aTask.Task = 			jTask.get("task").getAsString();
				aTask.Priority = Integer.parseInt(jTask.get("priority").getAsString());
				
				if(jTask.get("is_done").getAsString()== "1"){
					aTask.IsDone = true;
				}else{
					aTask.IsDone = false;
				}
				
				list.add(aTask);
            }			
		}
	
				
		
		return list;
	}
	
	public java.util.ArrayList<DroidTask> GetAllSLOW()  {

		String url = _serviceURL + "list_task";		
					
		HttpClient client = new HttpClient();
		String jsonString = client.getRequest(url).trim();
		System.out.println(jsonString);
		JSONObject jsonObj;
		ArrayList<DroidTask> list = new ArrayList<DroidTask>();
				
		try {
			jsonObj = new JSONObject(jsonString.trim());
			JSONArray tasks = jsonObj.getJSONArray("tasks");			
			
			for(int i=0;  i<tasks.length(); i++)
			{
				JSONObject jTask = tasks.getJSONObject(i);

				DroidTask aTask = new DroidTask();
				aTask.Id = 				jTask.getString("id");
				aTask.Task = 			jTask.getString("task");
				aTask.Priority = Integer.parseInt(jTask.getString("priority"));
				
				if(jTask.getString("is_done")== "1"){
					aTask.IsDone = true;
				}else{
					aTask.IsDone = false;
				}
				
				list.add(aTask);
			}
			
		
		} catch (JSONException e) {
			e.printStackTrace();
		}
				
		
		return list;
	}	
	
	
	public DroidTask GetTask(String id)  {

		String url = _serviceURL + "get_task?id=" + id;		
					
		HttpClient client = new HttpClient();
		String jsonString = client.getRequest(url).trim();
		
		DroidTask aTask = new DroidTask();

		
		// Sample response
		// {"task":{"Id":"5345f3ff22a0f","Task":"d","Priority":"1","IsDone":"0"}}
		
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(jsonString);
		if(element.isJsonObject())
		{
			JsonObject obj = element.getAsJsonObject();
			JsonObject jTask = obj.getAsJsonObject("task");
			aTask.Id = 				jTask.get("Id").getAsString();
			aTask.Task = 			jTask.get("Task").getAsString();
			aTask.Priority = jTask.get("Priority").getAsInt();
			aTask.IsDone = jTask.get("IsDone").getAsString()=="1";
		}									
		
		return aTask;
	}	
	
		
	

}
