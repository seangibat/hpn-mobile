package com.seangibat.hpnmobile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;


public class MainActivity extends Activity {
	protected FrameLayout webViewPlaceholder;
    private WebView mWebView;
    private SwipeRefreshLayout refresher;
    
    @SuppressLint("SetJavaScriptEnabled") @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_main);

        initUI();
        
        refresher = (SwipeRefreshLayout) findViewById(R.id.container);
        refresher.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initiateRefresh();
            }
        });
        refresher.setColorSchemeResources(R.color.color1,
                R.color.color2,
                R.color.color3,
                R.color.color4);
    }
    
    private void initUI(){
    	webViewPlaceholder = (FrameLayout) findViewById(R.id.activity_main_webview);
    	
    	if (mWebView == null){
    		mWebView = new WebView(this);
    		mWebView.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
            WebSettings webSettings = mWebView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setSupportZoom(true);
            webSettings.setBuiltInZoomControls(true);
            webSettings.setDisplayZoomControls(false);
            mWebView.loadUrl("http://forums.hipinion.com/");
            mWebView.setWebViewClient(new MyAppWebViewClient(MainActivity.this, refresher));
    	}
    	
    	webViewPlaceholder.addView(mWebView);    	
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
      if (mWebView != null)
      {
        // Remove the WebView from the old placeholder
        webViewPlaceholder.removeView(mWebView);
      }
   
      super.onConfigurationChanged(newConfig);
       
      // Load the layout resource for the new configuration
      setContentView(R.layout.activity_main);
   
      // Reinitialize the UI
      initUI();
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
      super.onSaveInstanceState(outState);
   
      // Save the state of the WebView
      mWebView.saveState(outState);
    }
     
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
      super.onRestoreInstanceState(savedInstanceState);
   
      // Restore the state of the WebView
      mWebView.restoreState(savedInstanceState);
    }
    
    private void initiateRefresh() {
    	refresher.setRefreshing(true);
    	mWebView.reload();
    }
    
    @Override
    public void onBackPressed() {
        if(mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    
}