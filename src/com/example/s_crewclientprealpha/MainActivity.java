package com.example.s_crewclientprealpha;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	 Socket serverside = null;
	   InputStream i=null;
	   OutputStream o=null;
	   Thread t=null;
	   String UserID=null;
	   String UserBestellung=null;
	   CharSequence text=null;
	   TextView tv=null;
	   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public void setOrder(View view){
		text=(CharSequence) "Connecting";
		  tv = (TextView) findViewById(R.id.textView1);
		  tv.setText(text);
		t= new Thread(new Runnable(){
		   public void run(){
		   
		   try {
			serverside=new Socket("veteran1.ez.lv",5544);//serverside ist der server
		     } catch (UnknownHostException e) {
		    	 e.printStackTrace();
			
			// TODO Auto-generated catch block
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			

		}
		//Ende try catch
		   
		  try {
			i= serverside.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  try {
			o= serverside.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		  
		  EditText et= (EditText) findViewById(R.id.UsersName);
		  UserID=et.getText().toString();
		  EditText et2=(EditText) findViewById(R.id.bestellungstext);
		  UserBestellung=et2.getText().toString();
		  HashMap<String,String> behelfsmap=new HashMap<String,String>();
		  behelfsmap.put(UserID, UserBestellung);
		  ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(o);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  try {
			  oos.writeObject(UserID);
			  oos.writeObject(behelfsmap);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		   }
		   });
		   t.start();
		   text=(CharSequence) "Data sent succesfully";
			  tv.setText(text);
	   }

}
