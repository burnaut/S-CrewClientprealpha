package walla.dev.s_crewclientprealpha;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class StuermerPdfActivity extends Activity {
	  
	
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    WebView mWebView=new WebView(StuermerPdfActivity.this);
	    mWebView.getSettings().setJavaScriptEnabled(true);
	    mWebView.loadUrl("https://docs.google.com/gview?embedded=true&url=www.metzgerei-stuermer.de/files/Stuermer_Imbissflyer_09-2011.pdf");
	    setContentView(mWebView);
	  }
	}