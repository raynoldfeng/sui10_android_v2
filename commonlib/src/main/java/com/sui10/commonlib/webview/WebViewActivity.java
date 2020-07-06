package com.sui10.commonlib.webview;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.MimeTypeMap;
import android.widget.SeekBar;

import com.sui10.commonlib.R;
import com.sui10.commonlib.base.constants.NetConstant;
import com.sui10.commonlib.log.LogManager;
import com.sui10.commonlib.ui.presenter.BasePresenter;
import com.sui10.commonlib.ui.view.base.BaseActivity;
import com.sui10.commonlib.utils.ResourceUtils;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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

        mWebView.setWebViewClient(new WebViewClient()
        {
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest) {
                String url = webResourceRequest.getUrl().toString();
                return getNewResponse(url);
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

    public void parseExtaIntent() {
        Bundle bundle = getIntent().getBundleExtra("bundle");
        mUrl = bundle.getString("url");
        mTitle = bundle.getString("title", ResourceUtils.getString(R.string.app_name));
    }


    private void loadUrl()
    {
        LogManager.i(TAG,"url :"+mUrl);
        mWebView.loadUrl(mUrl);
    }

    private WebResourceResponse getNewResponse(String url) {
        try {
            OkHttpClient httpClient = new OkHttpClient();
            final Request request = new Request.Builder()
                    .url(url.trim())
                    .addHeader(NetConstant.REFERER_HEADER_KEY, NetConstant.REFERER_HEADER_VALUE) // Example header
                    .build();

            Response response = httpClient.newCall(request).execute();

            return new WebResourceResponse(
                    getMimeType(url), // <- Change here
                    response.header("content-encoding", "utf-8"),
                    response.body().byteStream()
            );

        } catch (Exception e) {
            return null;
        }
    }

    private String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);

        if (extension != null) {

            switch (extension) {
                case "js":
                    return "text/javascript";
                case "woff":
                    return "application/font-woff";
                case "woff2":
                    return "application/font-woff2";
                case "ttf":
                    return "application/x-font-ttf";
                case "eot":
                    return "application/vnd.ms-fontobject";
                case "svg":
                    return "image/svg+xml";
            }

            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }

        return type;
    }
}
