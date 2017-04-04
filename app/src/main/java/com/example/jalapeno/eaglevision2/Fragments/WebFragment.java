package com.example.jalapeno.eaglevision2.Fragments;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.jalapeno.eaglevision2.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class WebFragment extends Fragment {

boolean oneTime = true;
    public WebFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_web, container, false);
        //This goes
//
        String urlstring = "https://fgcu.instructure.com/login/oauth2/auth?client_id=10510000000000132&response_type=code&redirect_uri=https://fgcu.instructure.com/api/v1/courses?per_page=100";
        final WebView myWebView = (WebView) view.findViewById(R.id.webview);
        myWebView.getSettings().setAppCacheEnabled(true);
        myWebView.loadUrl(urlstring);
        myWebView.getSettings().setJavaScriptEnabled(true);

        myWebView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url1) {
                // do your stuff here
                if (oneTime) {//?per_page=100&
                    if (url1.startsWith("https://fgcu.instructure.com/api/v1/courses?per_page=100&code=")) {
                        myWebView.setVisibility(View.GONE);
                        myWebView.loadUrl(url1 + ".html");
                        oneTime = false;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            myWebView.evaluateJavascript(
                                    "(function() {\n" +
                                            "    return (document.documentElement.innerHTML);\n" +
                                            "})();",
                                    new ValueCallback<String>() {
                                        @Override
                                        public void onReceiveValue(String html) {
                                            ArrayList<Class> semester = new ArrayList<Class>();
                                          //  Class currClass = new Class();
                                            Log.d("output", "mateee we done it");
                                            html = html.replace('\\',' ');
                                            html = html.replaceAll("null", " null ");
                                            // html.replaceAll("\\\"","");
                                            myWebView.removeAllViews();

                                            if(myWebView != null) {
                                                myWebView.clearHistory();
                                                myWebView.clearCache(true);
                                                myWebView.loadUrl("about:blank");
                                                myWebView.freeMemory();
                                                myWebView.pauseTimers();
                                                myWebView.destroy();
                                            }
                                            System.out.println(html);
                                        }
                                    });
                        }
                    }
                }
            }
        });
        return view;
    }

    public static WebFragment newInstance() {
        WebFragment fragment = new WebFragment();
        return fragment;
    }
}
