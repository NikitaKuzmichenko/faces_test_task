package com.test.jsf.page.reader;

import com.test.jsf.exception.PageLoadingException;

public interface PageReader<E>{

    E readPage(String pageUrl) throws PageLoadingException;
}
