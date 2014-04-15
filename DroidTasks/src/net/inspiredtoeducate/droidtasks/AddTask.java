package net.inspiredtoeducate.droidtasks;

import java.util.ArrayList;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AddTask extends Activity {

	
	Activity currentActivity;
	Context currentContext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_task);
		
		currentActivity = this;
		currentContext = this;
		Button addButton = (Button)this.findViewById(R.id.btnAddTask);	
		addButton.setOnClickListener(new OnClickListener(){
            public void onClick(View arg0) 
            {
        		EditText txtTask = (EditText)currentActivity.findViewById(R.id.txtTask);
        		Spinner cmbPriority = (Spinner)currentActivity.findViewById(R.id.cmbPriority);
	
            	DroidTask aTask = new DroidTask();
            	aTask.Task = txtTask.getText().toString();
            	aTask.Priority = Integer.parseInt(cmbPriority.getSelectedItem().toString());
            	    
            	
            	new SaveData(currentContext).execute(aTask);
            	
            	Intent i = new Intent(currentContext, MainActivity.class);           	
            	currentContext.startActivity(i);
            	
    	    }
        });	
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_task, menu);
		return true;
	}
	
	
	private class SaveData extends AsyncTask<DroidTask, Void, Void> {

		Context context;
		
	    private SaveData(Context context) {
	        this.context = context;
	    }		
		
		protected Void doInBackground(DroidTask... input) 		
		{
			DroidTask aTask = input[0];
		
    	    String url = new DataService().GetURL();
    	    DroidTasksRepo tasks = new DroidTasksRepo(url);		
    		tasks.Add(aTask);
    		
			return null;
		}

		protected void onPostExecute() 
		{
        	
        
		}
	}

}
