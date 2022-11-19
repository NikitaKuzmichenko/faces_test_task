package com.test.jsf.page.matcher;

import java.util.List;

public interface PageMatcher<R,E> {
    List<R> match(E page);
}
