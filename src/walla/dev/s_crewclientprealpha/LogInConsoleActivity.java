package walla.dev.s_crewclientprealpha;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import walla.dev.s_crewclientprealpha.R;


import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class LogInConsoleActivity extends Activity{
		Thread t;
		Socket serverside = null;
		   InputStream i=null;
		   OutputStream o=null;
		   ObjectOutputStream oos = null;
		   ObjectInputStream ois=null;
		   String ID;
		   String Pw;
	 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_log_in);
	        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
	            // Show the Up button in the action bar.
	            getActionBar().setDisplayHomeAsUpEnabled(true);
	        }
	 }
	 public boolean onOptionsItemSelected(MenuItem item) {
	        switch (item.getItemId()) {
	        case android.R.id.home:
	            NavUtils.navigateUpFromSameTask(this);
	            return true;
	        }
	        return super.onOptionsItemSelected(item);
	    }
	 public void log_in(View view){
		 t=new Thread(new Runnable(){
			 public void run(){
				 try{
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
			  EditText et=(EditText) findViewById(R.id.txtfieldadminname);
			  ID=et.getText().toString();
			  EditText et2=(EditText) findViewById(R.id.txtfieldpw);
			  Pw=et2.getText().toString();
			  try {
				oos.writeInt(4);
				oos.flush();
				oos.writeObject(ID);
				oos.flush();
				oos.writeObject(Pw);
				oos.flush();
				String answer=(String) ois.readObject();
				if(answer.equals("Access Granted")){
					to_admin_console();
				}
				
					
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			 }
		 });
	     t.start();
	     try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
	    
	 }
public void to_admin_console(){
	Intent intent= new Intent(this,AdminConsoleActivity.class);
	startActivity(intent);
}
}
