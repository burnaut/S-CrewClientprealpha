package walla.dev.s_crewclientprealpha;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

import walla.dev.s_crewclientprealpha.R;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class AdminConsoleActivity extends Activity {
	
	Socket serverside = null;
	   InputStream i=null;
	   OutputStream o=null;
	   ObjectOutputStream oos = null;
	   ObjectInputStream ois=null;
	   Thread t;
	   Thread t1;
	   TextView tv;
	   String GesamtBestellung;
	   char[] cbuf= new char[1000];
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_console);
        tv=(TextView) findViewById(R.id.txtviewbottomgesamtbestellung);
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
	public void setRestaurant(View view){
		t=new Thread(new Runnable(){
			public void run(){
				try{
			 		serverside=new Socket("veteran1.ez.lv",5544);//serverside ist der server
		 			i= serverside.getInputStream();
					o= serverside.getOutputStream();
					oos = new ObjectOutputStream(o);
					ois= new ObjectInputStream(i); 
				    oos.writeInt(2);
				    oos.flush();
				    EditText et= (EditText) findViewById(R.id.txtfieldrestaurant);
				    String Restaurant;
				    Restaurant=et.getText().toString();
				    oos.writeObject(Restaurant);
				    oos.flush();
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
			
		});
		t.start();	
	tv=(TextView) findViewById(R.id.textViewtopadminconsole);
	tv.setText("Restaurant Set");
     }
	public void getGesamt(View view){
		int x=5;//5=getGesamtbestellung()
		tv=(TextView)findViewById(R.id.txtviewbottomgesamtbestellung);
		tv.setText("Retrieving the Order");
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
	
}
