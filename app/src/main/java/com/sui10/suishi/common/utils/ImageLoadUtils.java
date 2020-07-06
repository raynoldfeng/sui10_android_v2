package com.sui10.suishi.common.utils;

import android.widget.ImageView;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.sui10.commonlib.R;
import com.sui10.commonlib.base.CommonApplication;
import com.bumptech.glide.load.model.Headers;
import com.sui10.commonlib.utils.DensityUtils;
import com.sui10.commonlib.base.constants.NetConstant;

import java.util.HashMap;
import java.util.Map;

import jp.wasabeef.glide.transformations.BlurTransformation;


public class ImageLoadUtils {

    private static GlideUrl getGilderUrlWithRefererHeader(String url)
    {
        return new GlideUrl(url, new Headers() {
            @Override
            public Map<String, String> getHeaders() {
                Map<String,String> headers = new HashMap<>();
                headers.put(NetConstant.REFERER_HEADER_KEY, NetConstant.REFERER_HEADER_VALUE);
                return headers;
            }
        });
    }

    private static void loadImgWithHeader(String url, final Map<String,String> headers, ImageView imageView,RequestOptions options)
    {
        if(url!= null && !url.isEmpty() && imageView != null) {
            GlideUrl glideUrl = new GlideUrl(url, new Headers() {
                @Override
                public Map<String, String> getHeaders() {
                    return headers;
                }
            });

            if(options == null)
                options = new RequestOptions();
            GlideApp.with(CommonApplication.getContext())
                    .load(glideUrl)
                    .placeholder(R.drawable.img_load_fail)
                    .error(R.drawable.body_def_head)
                    .apply(options)
                    .into(imageView);
        }
    }

    /**
     * 描  述:加载图片
     * @param url
     * @param imageView
     */
    public static void loadImage(String url ,ImageView imageView)
    {
        if(url!= null && !url.isEmpty() && imageView != null)
        {
            Map<String,String> headers = new HashMap<>();
            headers.put(NetConstant.REFERER_HEADER_KEY,NetConstant.REFERER_HEADER_VALUE);
            loadImgWithHeader(url,headers,imageView,null);
        }
    }


    /**
     * 描  述:加载角有弧度的图片
     * @param url
     * @param imageView
     */
    public static void loadRoundImg(String url ,ImageView imageView, float roundingRadius)
    {
        if(url!= null && !url.isEmpty() && imageView != null)
        {
            Map<String,String> headers = new HashMap<>();
            headers.put(NetConstant.REFERER_HEADER_KEY,NetConstant.REFERER_HEADER_VALUE);
            loadImgWithHeader(url,headers,imageView,new RequestOptions().transforms(new CenterCrop(),
                    new RoundedCorners(DensityUtils.dip2px(CommonApplication.getContext(), roundingRadius))));
        }
    }


    public static void loadImageWithBlur(String url , int blur,ImageView imageView){
        if(url!= null && !url.isEmpty() && imageView != null)
        {
            Map<String,String> headers = new HashMap<>();
            headers.put(NetConstant.REFERER_HEADER_KEY,NetConstant.REFERER_HEADER_VALUE);
            loadImgWithHeader(url,headers,imageView,RequestOptions.bitmapTransform(new BlurTransformation(blur)));
        }
    }


}

