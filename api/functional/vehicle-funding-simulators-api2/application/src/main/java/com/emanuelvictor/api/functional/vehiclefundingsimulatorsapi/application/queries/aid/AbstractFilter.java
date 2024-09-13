package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.queries.aid;

public abstract class AbstractFilter {

    static final int DEFAULT_SIZE_OF_PAGE = 10;

    private int sizeOfPage = DEFAULT_SIZE_OF_PAGE;
    private String defaultFilter;


    public int getSizeOfPage() {
        return sizeOfPage == 0 ? DEFAULT_SIZE_OF_PAGE : sizeOfPage;
    }

    public void setSizeOfPage(int sizeOfPage) {
        this.sizeOfPage = sizeOfPage == 0 ? DEFAULT_SIZE_OF_PAGE : sizeOfPage;
    }

    public String getDefaultFilter() {
        return defaultFilter;
    }

    public void setDefaultFilter(String defaultFilter) {
        this.defaultFilter = defaultFilter;
    }
}