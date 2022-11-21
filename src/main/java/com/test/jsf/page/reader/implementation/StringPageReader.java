package com.test.jsf.page.reader.implementation;

import com.test.jsf.exception.PageLoadingException;
import com.test.jsf.page.reader.PageReader;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

@ManagedBean(name = "StringPageReader", eager = true)
@SessionScoped
public class StringPageReader implements PageReader<String> {

    @Override
    public String readPage(String pageUrl) throws PageLoadingException {
        URL url;
        try {
            url = new URL(pageUrl);
        } catch (MalformedURLException e) {
            throw new PageLoadingException("Failed to load page content");
        }

        StringBuilder sb = new StringBuilder();
        try(Scanner sc = new Scanner(url.openStream())) {
            while(sc.hasNextLine()) {
                sb.append(sc.nextLine());
            }
        } catch (IOException e) {
            throw new PageLoadingException("Failed to read page content");
        }
        return sb.toString();
    }
}
