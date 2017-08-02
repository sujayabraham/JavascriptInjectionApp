package firebase.sample.enstage.com.demojsapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private WebView Wv;
    private TextView myTextView;
    final Handler myHandler = new Handler();

    /**
     * Called when the activity is first created.
     */

    @SuppressLint("JavascriptInterface")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Wv = (WebView) findViewById(R.id.webView1);
        myTextView = (TextView) findViewById(R.id.textView1);
        final JavaScriptInterface myJavaScriptInterface
                = new JavaScriptInterface(this);

        Wv.getSettings().setLightTouchEnabled(true);
        Wv.getSettings().setJavaScriptEnabled(true);
        Wv.addJavascriptInterface(myJavaScriptInterface, "AndroidFunction");
        Wv.loadUrl("file:///android_asset/index.html");
    }


    public class JavaScriptInterface {

        Context mContext;

        JavaScriptInterface(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public void showToast(String webMessage) {
            final String msgeToast = webMessage;
            myHandler.post(new Runnable() {
                @Override
                public void run() {
                    // This gets executed on the UI thread so it can safely modify Views
                    myTextView.setText(msgeToast);
                }
            });

            Toast.makeText(mContext, webMessage, Toast.LENGTH_SHORT).show();
        }

        @JavascriptInterface
        public void shareTxt(String webMessage) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, webMessage);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }

    }

}


