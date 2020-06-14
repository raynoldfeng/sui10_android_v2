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

import java.util.HashMap;
import java.util.Map;


public class ImageLoadUtils {

    public static GlideUrl getGilderUrlWithRefererHeader(String url)
    {
        return new GlideUrl(url, new Headers() {
            @Override
            public Map<String, String> getHeaders() {
                Map<String,String> headers = new HashMap<>();
                headers.put("Referer","http://android.sui10.com");
                return headers;
            }
        });
    }

    public static void setImgSrcUrlWithHeader(String url, final Map<String,String> headers, ImageView imageView)
    {
        if(url!= null && !url.isEmpty() && imageView != null) {
            GlideUrl glideUrl = new GlideUrl(url, new Headers() {
                @Override
                public Map<String, String> getHeaders() {
                    return headers;
                }
            });

            GlideApp.with(CommonApplication.getContext())
                    .load(glideUrl)
                    .placeholder(R.drawable.img_load_fail)
                    .error(R.drawable.body_def_head)
                    .into(imageView);
        }
    }

    public static void setImgSrcUrlWithRefererHeader(String url ,ImageView imageView)
    {
        if(url!= null && !url.isEmpty() && imageView != null)
        {
            Map<String,String> headers = new HashMap<>();
            headers.put("Referer","http://android.sui10.com");
            setImgSrcUrlWithHeader(url,headers,imageView);
        }
    }

    public static void setRoundImgUrlWithHeader(String url, Map<String,String> headers, float roundingRadius,ImageView imageView)
    {
        if(url!= null && !url.isEmpty() && imageView != null) {
            GlideUrl glideUrl = new GlideUrl(url, new Headers() {
                @Override
                public Map<String, String> getHeaders() {
                    return headers;
                }
            });

            GlideApp.with(CommonApplication.getContext())
                    .load(glideUrl)
                    .placeholder(R.drawable.img_load_fail)
                    .error(R.drawable.body_def_head)
                    .apply(new RequestOptions()
                            .transforms(new CenterCrop(), new RoundedCorners(DensityUtils.dip2px(CommonApplication.getContext(), roundingRadius))
                            ))
                    .into(imageView);
        }
    }

    public static void setRoundImgUrlWithRefererHeader(String url ,ImageView imageView, float roundingRadius)
    {
        if(url!= null && !url.isEmpty() && imageView != null)
        {
            Map<String,String> headers = new HashMap<>();
            headers.put("Referer","http://android.sui10.com");
            setRoundImgUrlWithHeader(url,headers,roundingRadius,imageView);
        }
    }

    public static void setImgSrcResId(int id, ImageView imageView)
    {

        GlideApp.with(CommonApplication.getContext())
                .load(id)
                .placeholder(R.drawable.img_load_fail)
                .error(R.drawable.body_def_head)
                .into(imageView);
    }

    public static void setRoundImgSrcResId(int id, ImageView imageView, float roundingRadius)
    {

        GlideApp.with(CommonApplication.getContext())
                .load(id)
                .placeholder(R.drawable.img_load_fail)
                .apply(new RequestOptions()
                        .transforms(new CenterCrop(), new RoundedCorners(DensityUtils.dip2px(CommonApplication.getContext(), 5))
                        ))
                .error(R.drawable.body_def_head)
                .into(imageView);
    }

    public static void setImgSrcUrlWithOption(String url, ImageView imageView,RequestOptions options){
        GlideApp.with(CommonApplication.getContext())
                .load(url)
                .placeholder(R.drawable.img_load_fail)
                .apply(options)
                .error(R.drawable.body_def_head)
                .into(imageView);
    }

}

