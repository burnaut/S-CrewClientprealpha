package walla.dev.s_crewclientprealpha;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import walla.dev.s_crewclientprealpha.R;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity {
	 Socket serverside = null;
	   InputStream i=null;
	   OutputStream o=null;
	   Thread t=null;
	   Thread update=null;
	   Thread txtviewable=null;
	  // String Restaurant=null;
	   String UserID=null;
	   String UserBestellung=null;
	   CharSequence text=null;
	   static TextView tv=null;
	   ObjectOutputStream oos = null;
	   ObjectInputStream ois=null;
	   
	   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv=(TextView) findViewById(R.id.textViewHeadline);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void fetchUpdate(View view){
		int x=1;//1=getRestaurant();
		tv=(TextView)findViewById(R.id.textViewHeadline);
		tv.setText("Retrieving the Restaurant");
		AsyncTask<Integer, Void, String> at= new Task();
		at.execute(x);
//		at.getStatus();
		try {
			tv.setText(at.get());
		} catch (InterruptedException e1) {
     		// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 		
	}
	
	
	public void setOrder(View view){
		text=(CharSequence) "Connecting";
		  tv = (TextView) findViewById(R.id.textViewBottom);
		  tv.setText(text);
		t= new Thread(new Runnable(){
		   public void run(){
		   connecttoServer();		  
		  EditText et= (EditText) findViewById(R.id.UsersName);
		  UserID=et.getText().toString();
		  EditText et2=(EditText) findViewById(R.id.bestellungstext);
		  UserBestellung=et2.getText().toString();
		  HashMap<String,String> behelfsmap=new HashMap<String,String>();
		  behelfsmap.put(UserID, UserBestellung);
		  

		  try {
			  oos.writeInt(3);
			  oos.writeObject(UserID);
			  oos.writeObject(behelfsmap);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 //exit();
		   }
		   });
		   t.start();
		   try {
				Thread.sleep(320);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   txtviewable=new Thread(new Runnable(){
			   public void run(){
				   
					  tv.setText("Data Sent->");
					  try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			   
			   }
		   });
		   txtviewable.start();
		   tv.setVisibility(4);
	   }
	public void exit(){
		try {
			i.close();
			o.close();
			oos.close();
			ois.close();
			serverside.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void connecttoServer(){
		 try {
				serverside=new Socket("veteran1.ez.lv",5544);//serverside ist der server
				i= serverside.getInputStream();
				o= serverside.getOutputStream();
				oos = new ObjectOutputStream(o);
				ois= new ObjectInputStream(i);
			     } catch (UnknownHostException e) {
			    	 e.printStackTrace();
				
				// TODO Auto-generated catch block
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NullPointerException e){
				tv=(TextView) findViewById(R.id.textViewBottom);
				tv.setText("Server currently unreachable"+"-"+"Please try again later");
				e.printStackTrace();
			}
		 	
			
	}
	
	public void dropdown_vorbereiten(){
		ArrayList<String> al=new ArrayList<String>();
		al.add("Schweinebraten");
		al.add("Currypenis");
		Spinner spinner = (Spinner) findViewById(R.id.spinner1);
		ArrayAdapter<String> adapter =new ArrayAdapter<String>(this ,android.R.layout.simple_spinner_item ,al);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new Spinnerlistener());
		System.out.println("Spinner vorbereitet");
	}
	
	public void stuermerpdf(View view){
		Intent intent=new Intent(this,StuermerPdfActivity.class);
		startActivity(intent);
		
	}
	public void toAdminConsole(View view){
		Intent intent= new Intent(this,LogInConsoleActivity.class);
		startActivity(intent);
	}
}


