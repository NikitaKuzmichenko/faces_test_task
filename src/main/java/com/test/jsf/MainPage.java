package com.test.jsf;

import com.test.jsf.exception.PageLoadingException;
import com.test.jsf.page.component.PageLink;
import com.test.jsf.page.matcher.PageMatcher;
import com.test.jsf.page.matcher.implementation.LinkPageMatcher;
import com.test.jsf.page.reader.PageReader;
import com.test.jsf.page.reader.implementation.StringPageReader;
import com.test.jsf.utils.HTMLLinkFormatter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "MainPage")
@SessionScoped
public class MainPage implements Serializable {

	@ManagedProperty(value = "#{StringPageReader}")
	private StringPageReader pageReader;

	@ManagedProperty(value = "#{LinkPageMatcher}")
	private LinkPageMatcher pageMatcher;

	private List<PageLink> links = new ArrayList<>();

	private String selectedLinkUrl = "";

	private String errorMsg = "";

	public void selectNewUrl(String url){
		selectedLinkUrl = url;
	}

	public void findLinksOnPage(){
		errorMsg="";
		if(selectedLinkUrl.isEmpty()){
			errorMsg = "Unable to analyze empty string";
			return;
		}
		String url = selectedLinkUrl;
		try {
			String page = pageReader.readPage(url);
			List<PageLink> result = pageMatcher.match(page);
			HTMLLinkFormatter.formatUrls(result,url);
			links = result;
		} catch (PageLoadingException e) {
			errorMsg = e.getReason();
		}
	}

	public void clearLinks(){
		errorMsg="";
		links.clear();
	}

	public PageReader<String> getPageReader() {
		return pageReader;
	}

	public void setPageReader(StringPageReader pageReader) {
		this.pageReader = pageReader;
	}

	public PageMatcher<PageLink, String> getPageMatcher() {
		return pageMatcher;
	}

	public void setPageMatcher(LinkPageMatcher pageMatcher) {
		this.pageMatcher = pageMatcher;
	}

	public List<PageLink> getLinks() {
		return links;
	}

	public void setLinks(List<PageLink> links) {
		this.links = links;
	}

	public String getSelectedLinkUrl() {
		return selectedLinkUrl;
	}

	public void setSelectedLinkUrl(String selectedLinkUrl) {
		this.selectedLinkUrl = selectedLinkUrl;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
