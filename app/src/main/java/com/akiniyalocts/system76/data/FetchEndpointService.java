package com.akiniyalocts.system76.data;

import android.app.IntentService;
import android.content.Intent;

import com.akiniyalocts.system76.System76App;
import com.akiniyalocts.system76.model.Item;
import com.akiniyalocts.system76.model.ItemsWrapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FetchEndpointService extends IntentService {

    public static final String KEY_ENDPOINT = "key::endpoint";

    public static final String ENDPOINT_LAPTOP = "laptops";

    public static final String ENDPOINT_DESKTOPS = "desktops";

    public static final String ENDPOINT_SERVERS = "servers";

    public static final String ENDPOINT_SWAG = "swag";

    public static final String BASE = "http://system76.com/";


    private String currentEndpoint;

    private List<Item> items;

    public FetchEndpointService() {
        super("FetchDataService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            currentEndpoint = intent.getStringExtra(KEY_ENDPOINT);

            if (currentEndpoint == null)
                currentEndpoint = ENDPOINT_LAPTOP;

            fetchDataForEndpoint();
        }

    }


    private void fetchDataForEndpoint() {
        try {

            items = new ArrayList<>();

            Document doc = Jsoup.connect(BASE + currentEndpoint).get();

            Elements links = doc.select("a[href]");


            for (Element link : links) {
                Item item = new Item();
                String imageLink = null;
                String itemDescr = null;

                if (link.attr("href").contains(currentEndpoint)) {
                    item.setTitle(link.attr("href").substring(link.attr("href").lastIndexOf("/") + 1));

                }
                for (Element child : link.children()) {
                    itemDescr = child.html().replaceAll("(learn more)", "<br><br>");
                    itemDescr = itemDescr.replaceAll("(<span class=\"meter\").*(</span>)", "");
                    itemDescr = itemDescr.replaceAll("(<div class=\"progress\").*", "");
                    itemDescr = itemDescr.replaceAll("(<h5).*","");

                    System.out.println(itemDescr);
                    for (Element more : child.children()) {
                        if (more.hasClass("product-image")) {
                            imageLink = more.html().substring(more.html().indexOf("/"), more.html().lastIndexOf("\""));
                        }
                    }
                }


                item.setImageLink(imageLink);
                item.setDescription(itemDescr);

                if(currentEndpoint.equalsIgnoreCase(ENDPOINT_SWAG)){
                    item.setItemUrl(BASE + "cart/configure/" + item.getTitle());
                }
                else {
                    item.setItemUrl(BASE + currentEndpoint + "/" + item.getTitle());
                }

                if (item.getImageLink() != null)
                    items.add(item);
            }


        } catch (IOException io) {

        } finally {
            postEvent();
        }
    }



    public void postEvent(){
        ItemsWrapper itemsWrapper = new ItemsWrapper();
        itemsWrapper.setItemList(items);
        System76App.getBus().post(itemsWrapper);
    }
}
