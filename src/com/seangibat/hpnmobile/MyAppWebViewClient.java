package com.seangibat.hpnmobile;

import java.io.IOException;
import java.io.InputStream;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyAppWebViewClient extends WebViewClient {
    
	private Context context;
	private SwipeRefreshLayout refresherView;
	
	public MyAppWebViewClient(final Context myContext, SwipeRefreshLayout refresher){
	    context = myContext;
	    refresherView = refresher;
	}

	@Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if(Uri.parse(url).getHost().endsWith("hipinion.com")) {
            return false;
        }
         
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        view.getContext().startActivity(intent);
        return true;
    }
    
    @Override
    public WebResourceResponse shouldInterceptRequest (WebView view, String url) {
        if (url.contains("click.css")) {
            return getCssWebResourceResponse();
        } else {
            return null;
        }
    }

    private WebResourceResponse getCssWebResourceResponse() {
    	InputStream is = null;
		try {
			is = context.getAssets().open("style.css");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	WebResourceResponse wrr = new WebResourceResponse("text/css","UTF-8", is);
        return wrr;
    }
    
    @SuppressLint("NewApi") @Override  
    public void onPageFinished(WebView view, String url) {
    	if (refresherView.isRefreshing()){
    		refresherView.setRefreshing(false);
    	}
    	if (android.os.Build.VERSION.SDK_INT >= 19)
	    {
	    	String jscript = "$('object').each(function(element){	var xx = this; var yt = $(this).find('embed').attr('src').split('youtube.com/v/')[1]; if (yt !== undefined) { var id = yt.slice(0,11); $.ajax({	dataType: 'json', url: 'https://www.googleapis.com/youtube/v3/videos?part=snippet&id=' + id + '&key=AIzaSyAXyRrmk_0M5uADgZM0UvKnCzO94sH4h_s', success: function(data, textStatus, jqhr){ if (data.items.length > 0){ $(xx).replaceWith(\"<div class='ytv'><a href='https://youtube.com/watch?v=\" + data.items[0].id + \"'><img src='\" + data.items[0].snippet.thumbnails.medium.url + \"'><div class='ytvtext'>\" + data.items[0].snippet.title + \"</div></a></div> \"); } else { $(xx).replaceWith(\"<div class='ytv'>Youtube video is private or has been removed.</div> \"); } }});}})";
	    	view.evaluateJavascript(jscript, null);
    	}
    }

}
