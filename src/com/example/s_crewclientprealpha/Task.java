package com.example.s_crewclientprealpha;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamConstants;
import java.io.OptionalDataException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import android.os.AsyncTask;
import android.widget.TextView;

public class Task extends AsyncTask<Void,Void,String>{
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
	protected String doInBackground(Void... params) {
		connecttoServer();
		System.out.println("connecting to server...");
		try {
			oos.writeInt(1);
			System.out.println("next step is flushing");
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    try {
			Restaurant=(String) ois.readObject();
	} catch (OptionalDataException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return Restaurant;
				
	}
	@Override
	protected void onPostExecute(String result){
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
				
				e.printStackTrace();
			}
		 	
			
	}
	
	
}