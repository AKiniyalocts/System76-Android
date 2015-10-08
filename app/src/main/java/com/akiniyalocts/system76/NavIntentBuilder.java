package com.akiniyalocts.system76;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.akiniyalocts.system76.data.FetchEndpointService;

/**
 * Created by anthony on 10/6/15.
 */
public class NavIntentBuilder {

    public static void startFetchService(Context context, String endpoint){
        Intent service = new Intent(context, FetchEndpointService.class);

        service.putExtra(FetchEndpointService.KEY_ENDPOINT, endpoint);
        context.startService(service);
    }

    public static void startSocialIntent(Context context, String url){
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        }catch (Exception ex){}
    }

}
