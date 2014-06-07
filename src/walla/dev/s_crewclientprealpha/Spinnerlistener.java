package walla.dev.s_crewclientprealpha;

import android.view.View;
	import android.widget.AdapterView;
	import android.widget.AdapterView.OnItemSelectedListener;
	import android.widget.Toast;
 
	public class Spinnerlistener implements OnItemSelectedListener {
	 
	    @Override
	    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
	 
	        Toast.makeText(parent.getContext(), "Selected Your Happymeal : " + parent.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();
	    }
	 
	    @Override
	    public void onNothingSelected(AdapterView<?> parent) {
	    	Toast.makeText(parent.getContext(), "Selected Your Happymeal : " + "No Happymeal selected", Toast.LENGTH_SHORT).show();
	    }
	}