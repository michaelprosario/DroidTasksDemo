package net.inspiredtoeducate.droidtasks.test;


import java.util.ArrayList;
import net.inspiredtoeducate.droidtasks.DroidTask;
import net.inspiredtoeducate.droidtasks.DroidTasksRepo;
import net.inspiredtoeducate.droidtasks.HttpClient;
import junit.framework.Assert;
import junit.framework.TestCase;

public class TasksRepo extends TestCase {

	String testURL = "http://inspiredToEducate.net/droidTasks/task/";
	
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void testTasksRepo__Add__ItShouldWork()
	{
		//arrange
		DroidTasksRepo tasks = new DroidTasksRepo(testURL);
		
		DroidTask aTask = new DroidTask();
		aTask.Task = "Save the world.";
		aTask.Priority = 1;
		aTask.IsDone = false;
			
		//act 
		String id = tasks.Add(aTask);
		
		//assert
		Assert.assertTrue(tasks.RecordExists(id));
	}	
	
	public void testTasksRepo__Add__FailWhenTaskEmpty()
	{

		try{
			//arrange
			DroidTasksRepo tasks = new DroidTasksRepo(testURL);
			
			DroidTask aTask = new DroidTask();
			aTask.Task = "";
			aTask.Priority = 1;
			aTask.IsDone = false;
				
			//act 
			tasks.Add(aTask);
			Assert.fail();
		}
		catch(IllegalArgumentException iae)
		{
			
		
		}
	}	
	
	
	public void testTasksRepo__Delete__ItShouldWork()
	{
		//arrange
		DroidTasksRepo tasks = new DroidTasksRepo(testURL);		
		DroidTask aTask = new DroidTask();
		aTask.Task = "Save the world.";
		aTask.Priority = 1;
		aTask.IsDone = false;
		String id = tasks.Add(aTask);
		
		//act 
		tasks.Delete(id);

		//assert
		Assert.assertTrue(!tasks.RecordExists(id));
	}	
	
	public void testTasksRepo__GetAll__ItShouldWork()
	{
		//arrange
		DroidTasksRepo tasks = new DroidTasksRepo(testURL);		

		DroidTask aTask = new DroidTask();
		aTask.Task = "Save the world.";
		aTask.Priority = 1;
		aTask.IsDone = false;
		tasks.Add(aTask);
		
		//act 
		ArrayList<DroidTask> results = tasks.GetAll();
		
		
		//assert
		Assert.assertTrue(results != null);
		Assert.assertTrue(results.size() > 0);
		
	}	
	
	
	
	
	public void testHttpClient__GetRequest__MakeSureItWorks()
	{
		String url = "http://slashdot.org";
		HttpClient client = new HttpClient();
		String response = client.getRequest(url);
		Assert.assertTrue(response != null);
		Assert.assertTrue(response.length() > 0 );
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
