package com.ankurpathak.springsessionreactivetest;

import org.springframework.context.ApplicationListener;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;



@Component
class PaginatedResultsRetrievedEventDiscoverabilityListener implements ApplicationListener<PaginatedResultsRetrievedEvent>{
    @Override
    public void onApplicationEvent(PaginatedResultsRetrievedEvent ev) {
        addLinkHeaderOnPagedResourceRetrieval(ControllerUtil.headers(ev.getExchange()), ev.getSource(), ev.getUriBuilder());
        addTotalPageCountHeader(ControllerUtil.headers(ev.getExchange()), ev.getSource().getTotalElements());
    }


    private void addTotalPageCountHeader(HttpHeaders headers, long totalElement) {
        headers.set(HEADER_TOTAL_COUNT, String.valueOf(totalElement));
    }

    private void addLinkHeaderOnPagedResourceRetrieval(final HttpHeaders headers, Page<?> page, UriComponentsBuilder uriBuilder) {
        final StringBuilder linkHeader = new StringBuilder();
        if (hasNextPage(page)) {
            final String uriForNextPage = constructNextPageUri(page, uriBuilder);
            linkHeader.append(LinkUtil.createLinkHeader(uriForNextPage, LinkUtil.REL_NEXT));
        }
        if (hasPreviousPage(page)) {
            final String uriForPrevPage = constructPrevPageUri(page, uriBuilder);
            appendCommaIfNecessary(linkHeader);
            linkHeader.append(LinkUtil.createLinkHeader(uriForPrevPage, LinkUtil.REL_PREV));
        }
        if (hasFirstPage(page)) {
            final String uriForFirstPage = constructFirstPageUri(page, uriBuilder);
            appendCommaIfNecessary(linkHeader);
            linkHeader.append(LinkUtil.createLinkHeader(uriForFirstPage, LinkUtil.REL_FIRST));
        }
        if (hasLastPage(page)) {
            final String uriForLastPage = constructLastPageUri(page,uriBuilder);
            appendCommaIfNecessary(linkHeader);
            linkHeader.append(LinkUtil.createLinkHeader(uriForLastPage, LinkUtil.REL_LAST));
        }
        headers.set(HttpHeaders.LINK, linkHeader.toString());
    }

    String constructNextPageUri(final Page<?> page, UriComponentsBuilder uriBuilder)  {
        return uriBuilder.replaceQueryParam(QUERY_PARAM_PAGE, page.getNumber() + 1).replaceQueryParam(QUERY_PARAM_SIZE, page.getSize()).build().encode().toUriString();
    }

    String constructPrevPageUri(final Page<?> page, UriComponentsBuilder uriBuilder) {
        return uriBuilder.replaceQueryParam(QUERY_PARAM_PAGE, page.getNumber() - 1).replaceQueryParam(QUERY_PARAM_SIZE, page.getSize()).build().encode().toUriString();
    }

    String constructFirstPageUri(final Page<?> page, UriComponentsBuilder uriBuilder) {
        return uriBuilder.replaceQueryParam(QUERY_PARAM_PAGE, 0).replaceQueryParam(QUERY_PARAM_SIZE, page.getSize()).build().encode().toUriString();
    }

    String constructLastPageUri(final Page<?> page, UriComponentsBuilder uriBuilder) {
        return uriBuilder.replaceQueryParam(QUERY_PARAM_PAGE, page.getTotalPages()).replaceQueryParam(QUERY_PARAM_SIZE, page.getSize()).build().encode().toUriString();
    }

    boolean hasNextPage(Page<?> page) {
        //return page.getNumber() < page.getTotalPages() - 1;
        return page.hasNext();
    }

    boolean hasPreviousPage(Page<?> page) {
       // return page.getNumber() > 0;
        return page.hasPrevious();
    }

    boolean hasFirstPage(Page<?> page) {
        return hasPreviousPage(page);
    }

    boolean hasLastPage(Page<?> page) {
        return page.getTotalPages() > 1 && hasNextPage(page);
    }

    void appendCommaIfNecessary(final StringBuilder linkHeader) {
        if (linkHeader.length() > 0) {
            linkHeader.append(", ");
        }
    }

    public static String QUERY_PARAM_SIZE = "size";
    public static String QUERY_PARAM_PAGE = "page";
    public static String HEADER_TOTAL_COUNT = "X-Total-Count";


}

