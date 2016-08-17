package com.naziksoft.fstoviever;
import android.app.Activity;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public final static String MY_LOG = "My";
    Button buttFiles, buttSeasone, buttLang, buttTranslate, buttParse;
    WebView webView;
    TextView tv;
    Elements text;
    String stringHTML;
    final String PAGE_URL = "http://fs.to/video/serials/i1JyOT1po21gODTFfZEmkdq-igra-prestolov.html";

    @JavascriptInterface
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(MY_LOG,"---onCreate---");
        webView = (WebView) findViewById(R.id.webView);
        tv = (TextView) findViewById(R.id.textView);
        buttFiles = (Button) findViewById(R.id.buttFiles);
        buttLang = (Button) findViewById(R.id.buttLang);
        buttSeasone = (Button) findViewById(R.id.buttSeasone);
        buttTranslate = (Button) findViewById(R.id.buttTranslate);
        buttParse = (Button) findViewById(R.id.buttParse);

        buttFiles.setOnClickListener(this);
        buttTranslate.setOnClickListener(this);
        buttSeasone.setOnClickListener(this);
        buttLang.setOnClickListener(this);
        buttParse.setOnClickListener(this);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.addJavascriptInterface(new MyJavascriptInterfase(), "myParser");
        webView.setWebViewClient(new MyWebViewClient());

        Log.d(MY_LOG,"start loading a page");

        webView.loadUrl(PAGE_URL);
        Toast.makeText(MainActivity.this,"load web page", Toast.LENGTH_SHORT).show();

    }

    private class MyWebViewClient extends WebViewClient{

        @Override
        public void onPageFinished(WebView view, String url) {

            Log.d(MY_LOG,"---onPageFinished---");Log.d(MY_LOG, "parse");
            Toast.makeText(MainActivity.this,"parsing...", Toast.LENGTH_SHORT).show();
            webView.loadUrl("javascript:window.myParser.handleHtml"+
                    "('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
            Toast.makeText(MainActivity.this,"loaded", Toast.LENGTH_SHORT).show();

        }
    }

    private class MyJavascriptInterfase {
        private String html;

        @JavascriptInterface
        public void handleHtml(String html) {
            Log.d(MY_LOG,"---MyJavaInterface---");
            Document doc = Jsoup.parse(html);
            Toast.makeText(MainActivity.this,doc.select("div.b-files-folders").toString(), Toast.LENGTH_SHORT).show();
            tv.setText(doc.select("div.b-files-folders").toString());

        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.buttParse:
                Log.d(MY_LOG, "parse");
                Toast.makeText(MainActivity.this,"parsing...", Toast.LENGTH_SHORT).show();
                webView.loadUrl("javascript:window.myParser.handleHtml"+
                        "('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
                break;
            case R.id.buttFiles:
            {
                Log.d(MY_LOG,"butFiles");

                webView.loadUrl("javascript(function(){" +
                        "i=document.getElementsByClassName('m-themed-border')[0];" +
                        "e=document.createEvent('HTMLEvents');" +
                        "e.initEvent('click',true,true);" +
                        "i.dispatchEvent(e);" +
                        "})()");
                break;
            }
            case R.id.buttSeasone: {
                Log.d(MY_LOG, "butSeasones");

                for (int i = 0; i < 20; i++)
                    webView.loadUrl("javascript(function(){" +
                            "i=document.getElementsByClassName('link-simple title')[" + i + "];" +
                            "e=document.createEvent('HTMLEvents');" +
                            "e.initEvent('click',true,true);" +
                            "i.dispatchEvent(e);" +
                            "})()");
                break;
            }
            case R.id.buttLang: {
                Log.d(MY_LOG, "butLanguages");

                for (int i = 0; i < 4; i++)
                    webView.loadUrl("javascript(function(){" +
                            "i=document.getElementsByClassName('folder folder-language')[" + i + "];" +
                            "e=document.createEvent('HTMLEvents');" +
                            "e.initEvent('click',true,true);" +
                            "i.dispatchEvent(e);" +
                            "})()");
                break;
            }
            case R.id.buttTranslate: {
                Log.d(MY_LOG, "butTranslates");

                for (int i = 0; i < 7; i++)
                    webView.loadUrl("javascript(function(){" +
                            "i=document.getElementsByClassName('folder folder-translation')[" + i + "];" +
                            "e=document.createEvent('HTMLEvents');" +
                            "e.initEvent('click',true,true);" +
                            "i.dispatchEvent(e);" +
                            "})()");
                break;
            }
        }
    }

 /*   String getStringFromAssetFile(Activity activity) throws IOException {
        AssetManager am = activity.getAssets();
        InputStream is = am.open("myhttp.html");
        String s = is.toString();
        is.close();
        return s;
    }

    class MyAsyncTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            Document doc = null;
            try{
               // doc = Jsoup.connect("http://"+stringURL).get();
                doc = Jsoup.connect("http://fs.to/video/serials/iLeJFrY8aRWtm9ofAV43u0-kosti.html").get();
            }
            catch (IOException e){
                e.printStackTrace();
            }
            if (doc != null) {
                text = doc.select("div.l-body-inner-inner");
                stringURL = text.text();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
                tv.setText(text.toString());
                 webView.loadUrl("http://fs.to/video/serials/iLeJFrY8aRWtm9ofAV43u0-kosti.html");
        }
    }
*/

}
