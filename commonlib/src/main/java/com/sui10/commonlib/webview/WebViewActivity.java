package com.sui10.commonlib.webview;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.widget.SeekBar;

import com.sui10.commonlib.R;
import com.sui10.commonlib.ui.presenter.BasePresenter;
import com.sui10.commonlib.ui.view.base.BaseActivity;
import com.sui10.commonlib.utils.ResourceUtils;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

public class WebViewActivity extends BaseActivity {
    private static final String TAG = "WebViewActivity";
    private WebView mWebView;
    private SeekBar mSeekBar;
    private String mUrl;
    private String mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_webview_activity);
        parseExtaIntent();
        initView();
        loadUrl();
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onResume()
    {
        super.onResume();
        mWebView.onResume();
    }

    @Override
    public void  onPause()
    {
        super.onPause();
        mWebView.onPause();
        mWebView.getSettings().setLightTouchEnabled(false);
    }

    @Override
    protected void onDestroy() {
        if(mWebView != null)
            mWebView.destroy();
        super.onDestroy();
    }

    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected void onNetworkConnected(int type) {

    }

    private void initView()
    {
        setTitle(mTitle);
        mWebView = findViewById( R.id.web_view_area);
        mSeekBar = findViewById(R.id.web_sbr);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setWebViewSettings();
    }

    private void setWebViewSettings()
    {
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        mWebView.getSettings().setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        mWebView.getSettings().setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        mWebView.getSettings().setDisplayZoomControls(true); //隐藏原生的缩放控件
        mWebView.getSettings().setBlockNetworkImage(false);//解决图片不显示
        mWebView.getSettings().setLoadsImagesAutomatically(true); //支持自动加载图片
        mWebView.getSettings().setDefaultTextEncodingName("utf-8");//设置编码格式
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setSupportMultipleWindows(true);
        mWebView.getSettings().setGeolocationEnabled(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String s1, String s2, String s3, long l) {
                downloadByBrowser(url);
            }
        });
    }

    private void downloadByBrowser(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    public void parseExtaIntent() {
        Bundle bundle = getIntent().getBundleExtra("bundle");
        mUrl = bundle.getString("url");
        mTitle = bundle.getString("title", ResourceUtils.getString(R.string.app_name));
    }


    private void loadUrl()
    {
        mWebView.loadUrl(mUrl);

        mWebView.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                webView.loadUrl(s);
                return true;
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView webView, int i)
            {
                mSeekBar.setProgress(i);
            }
        });
    }
}
