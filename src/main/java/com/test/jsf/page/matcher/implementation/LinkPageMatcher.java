package com.test.jsf.page.matcher.implementation;

import com.test.jsf.page.component.PageLink;
import com.test.jsf.page.matcher.PageMatcher;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ManagedBean(name = "LinkPageMatcher", eager = true)
@SessionScoped
public class LinkPageMatcher implements PageMatcher<PageLink,String> {

    private static final Pattern LINK_PATTERN =
            Pattern.compile("(<a)(.*?href=\")(.*?)(\".*?)(>)(.*?)(</a>)",Pattern.CASE_INSENSITIVE);

    private static final String HTML_TAG_PATTERN = "<[^>]*>";

    private static final String HTML_BODY_START = "<body";
    private static final String HTML_BODY_END = "</body>";

    private static final String HTML_SCRIPT_START = "<script";
    private static final String HTML_SCRIPT_END = "</script>";

    @Override
    public List<PageLink> match(String page) {
        String bodyTags = removeScript(removeBody(page));
        Matcher matcher = LINK_PATTERN.matcher(bodyTags);
        List<PageLink> listMatches = new ArrayList<>();

        int id = 1;
        while(matcher.find()) {
            listMatches.add(new PageLink(id,removeAllHTMLElements(matcher.group(6)),matcher.group(3)));
            id++;
        }
        return listMatches;
    }

    private String removeAllHTMLElements(String str){
        return str.replaceAll(HTML_TAG_PATTERN,"");
    }

    private String removeBody(String page){
        return page.substring(page.indexOf(HTML_BODY_START) + HTML_BODY_START.length(),page.lastIndexOf(HTML_BODY_END));
    }

    private String removeScript(String page){
        return page.substring(page.indexOf(HTML_SCRIPT_START) + HTML_SCRIPT_START.length(),page.lastIndexOf(HTML_SCRIPT_END));
    }
}
