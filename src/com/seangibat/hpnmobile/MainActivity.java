package com.seangibat.hpnmobile;

import android.annotation.SuppressLint;
import android.app.Activity;
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
    	super.onCreate(Bundle savedInstanceState);
    	setContentView(R.layout.state_preserving_impl);

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