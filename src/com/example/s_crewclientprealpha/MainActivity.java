package com.example.s_crewclientprealpha;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.OutputStream;
import java.io.StreamCorruptedException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	 Socket serverside = null;
	   InputStream i=null;
	   OutputStream o=null;
	   Thread t=null;
	   Thread update=null;
	   Thread txtviewable=null;
	   String Restaurant=null;
	   String UserID=null;
	   String UserBestellung=null;
	   CharSequence text=null;
	   TextView tv=null;
	   ObjectOutputStream oos = null;
	   ObjectInputStream ois=null;
	   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public void fetchUpdate(View view){
		update=new Thread(new Runnable(){
		public void run(){
			connecttoServer();
			try {
				oos.writeObject("getRestaurant()");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		try {
			Restaurant=(String) ois.readObject();
		} catch (OptionalDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       	//exit();
		}
		});
		update.start();
		tv=(TextView) findViewById(R.id.textViewHeadline);
		tv.setText(Restaurant);
		tv.setVisibility(0);
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			  oos.writeObject("setOrder()");
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
				tv.setText("Server currently unreachable"+"/n"+"Please try again later");
				e.printStackTrace();
			}
		 	
			
	}
	public void toAdminConsole(View view){
		Intent intent= new Intent(this,LogInConsoleActivity.class);
		startActivity(intent);
	}
}
