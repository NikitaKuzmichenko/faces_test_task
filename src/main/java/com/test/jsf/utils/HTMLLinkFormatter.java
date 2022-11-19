package com.test.jsf.utils;

import com.test.jsf.page.component.PageLink;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public final class HTMLLinkFormatter {

    private static final String RELATIVE_PATH_SYMBOL ="/";
    private static final String ANCHOR_SYMBOL ="#";

    private HTMLLinkFormatter(){}

    public static void formatUrls(List<PageLink> links,String currentUrl){
        String rootUrl = getRootUrl(currentUrl);
        for(PageLink link : links){
            if(link.getUrl().startsWith(ANCHOR_SYMBOL)){
                link.setUrl(currentUrl+link.getUrl());
            }
            if(rootUrl == null){
                continue;
            }
            if(link.getUrl().startsWith(RELATIVE_PATH_SYMBOL)){
                link.setUrl(rootUrl+link.getUrl());
            }
            if(!link.getUrl().startsWith("http")){
                link.setUrl(rootUrl+"/"+link.getUrl());
            }
        }
    }

    private static String getRootUrl(String url){
        URI uri;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            return null;
        }
        return uri.getScheme()+"://"+uri.getHost();
    }
}
