package com.seangibat.hpnmobile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.webkit.WebSettings;
import android.webkit.WebView;


public class MainActivity extends Activity {
    
    private WebView mWebView;
    private SwipeRefreshLayout refresher;
    
    @SuppressLint("SetJavaScriptEnabled") @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mWebView = (WebView) findViewById(R.id.activity_main_webview);
        refresher = (SwipeRefreshLayout) findViewById(R.id.container);
        
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);

        
        mWebView.setWebViewClient(new MyAppWebViewClient(MainActivity.this, refresher));
        
        if (savedInstanceState == null)
        {
        	mWebView.loadUrl("http://forums.hipinion.com/");
        }
        
        refresher.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initiateRefresh();
            }
        });
        refresher.setColorSchemeResources(R.color.color1, R.color.color2, R.color.color3, R.color.color4);
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
    	super.onSaveInstanceState(outState);
    	mWebView.saveState(outState);
    }
     
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
    	super.onRestoreInstanceState(savedInstanceState);
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