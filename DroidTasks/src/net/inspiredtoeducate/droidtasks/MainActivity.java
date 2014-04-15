package net.inspiredtoeducate.droidtasks;


import java.util.ArrayList;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends ListActivity {
	
	String testURL = null;
	protected ArrayList<DroidTask> droidTaskList = null;
	Activity currentActivity;
	
	public void onCreate(Bundle icicle) {
		testURL = new DataService().GetURL();
		
		this.setTitle("Droid Task List");
		super.onCreate(icicle);

		currentActivity = this;
		
		
		//add header .........................
		LayoutInflater inflater = this.getLayoutInflater();
		LinearLayout headerView = (LinearLayout)inflater.inflate(R.layout.tasks_button_bar, null);
		ListView list = this.getListView();
		list.addHeaderView(headerView);
		
		// get some data .......................
		new GetListData().execute(this);

		
		Button addButton = (Button)headerView.findViewById(R.id.add_button);		
		addButton.setOnClickListener(new OnClickListener(){
            public void onClick(View arg0) 
            {
            	Intent i = new Intent(getApplicationContext(), AddTask.class);           	
            	currentActivity.startActivity(i);
    	    }
        });		
		
		
		
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		String itemID = this.droidTaskList.get(position-1).Id;
    	Intent i = new Intent(getApplicationContext(), ViewTask.class);
    	i.putExtra("record_id", itemID);
    	currentActivity.startActivity(i);
	}
  
	private class GetListData extends AsyncTask<MainActivity, Void, ArrayList<DroidTask>> {

		MainActivity[] mainActivityRef;
	  
		protected ArrayList<DroidTask> doInBackground(MainActivity... mainScreen) {
    	    this.mainActivityRef = mainScreen;    	  
    	    DroidTasksRepo tasks = new DroidTasksRepo(testURL);		
    		ArrayList<DroidTask> taskList = tasks.GetAll();
    		return taskList;
		}

		@Override
		protected void onPostExecute(ArrayList<DroidTask> taskList) 
		{
			//Store reference to task list back on activity ...
			droidTaskList = taskList;
			
			//Convert task list to string array ....
			ArrayList<String> strList = new ArrayList<String>();
			for(DroidTask aTask : taskList)
			{
				String s =  aTask.Task;
				strList.add(s);
			}
			String[] values = strList.toArray(new String[strList.size()]);
    	    //Bind data to list ...
    	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.mainActivityRef[0],R.layout.rowlayout, R.id.label, values);
    	    setListAdapter(adapter);
      }

      @Override
      protected void onPreExecute() {
    	  
      }

      @Override
      protected void onProgressUpdate(Void... values) {}
  }  
} 