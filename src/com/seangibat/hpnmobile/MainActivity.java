package com.seangibat.hpnmobile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;


public class MainActivity extends Activity {
    
    private WebView mWebView;
    private SwipeRefreshLayout refresher;
    private String[] styles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private SharedPreferences settings;
    private MainActivity act;
    private String theme;
    
    @SuppressLint("SetJavaScriptEnabled") @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        act = this;
        
        mWebView = (WebView) findViewById(R.id.activity_main_webview);
        refresher = (SwipeRefreshLayout) findViewById(R.id.container);
        
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);

        settings = getPreferences(0);
        theme = settings.getString("theme", "default");
        
        mWebView.setWebViewClient(new MyAppWebViewClient(MainActivity.this, refresher, theme));
        
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
        
        styles = getResources().getStringArray(R.array.styles_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        mDrawerLayout.setDrawerListener(new MyDrawerListener());
        
        // Set the adapter for the list view
        mDrawerList.setAdapter((ListAdapter) new ArrayAdapter<String>(this, R.layout.drawer_list_item, styles));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
    }
    
    class MyDrawerListener implements DrawerLayout.DrawerListener {
		@Override
		public void onDrawerClosed(View arg0) {
	        String nowTheme = settings.getString("theme", "default");
	        if (theme != nowTheme){
	        	act.recreate();
	        }
		}

		@Override
		public void onDrawerOpened(View arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onDrawerSlide(View arg0, float arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onDrawerStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}	
    }
    
    class DrawerItemClickListener implements ListView.OnItemClickListener {
    	@Override
    	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long id) {
    		SharedPreferences.Editor editor = settings.edit();
    	    editor.putString("theme", styles[(int) id]);
    	    editor.commit();
    	    mDrawerLayout.closeDrawer(mDrawerList);
    	}
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