package net.inspiredtoeducate.droidtasks;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ViewTask extends Activity {

	public String RecordID;
	public ViewTask currentActivity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_task);
		
		currentActivity = this;
		
		//get parameter from intent ...
		Intent i = this.getIntent();
		RecordID = i.getStringExtra("record_id");
		
			
		
		// get data ...	
		// load data into UI ...
		new GetRecord().execute(this);
		
		
		// Wire up complete button 
		Button btnComplete = (Button)this.findViewById(R.id.btnComplete);		
		btnComplete.setOnClickListener(new OnClickListener(){
            public void onClick(View arg0) 
            {
            	new DeleteRecord().execute(currentActivity);
            	
            	Intent i = new Intent(currentActivity, MainActivity.class);           	
            	currentActivity.startActivity(i);

    	    }
        });		

	
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_task, menu);
		return true;
	}
	
	private class GetRecord extends AsyncTask<ViewTask, Void, DroidTask> {

		ViewTask[] viewTaskRef;
	  
		protected DroidTask doInBackground(ViewTask... viewTask) {
    	    this.viewTaskRef = viewTask;    	  
    	    String url = new DataService().GetURL();
    	    DroidTasksRepo tasks = new DroidTasksRepo(url);		
    		DroidTask aTask = tasks.GetTask(viewTask[0].RecordID);
    		return aTask;
		}

		@Override
		protected void onPostExecute(DroidTask aTask) 
		{
			TextView lblTask = (TextView)this.viewTaskRef[0].findViewById(R.id.lblTask);
			TextView lblPriority = (TextView)this.viewTaskRef[0].findViewById(R.id.lblPriority);
			
			lblTask.setText(aTask.Task);
			lblPriority.setText(aTask.Priority + "");
		}
  }	
	
	private class DeleteRecord extends AsyncTask<ViewTask, Void, Void> {

		ViewTask[] viewTaskRef;
	  
		protected Void doInBackground(ViewTask... viewTask) {
    	    this.viewTaskRef = viewTask;    	  
    	    String url = new DataService().GetURL();
    	    DroidTasksRepo tasks = new DroidTasksRepo(url);		
    		tasks.Delete(viewTask[0].RecordID);
    		return null;
		}
  }	
	

}
