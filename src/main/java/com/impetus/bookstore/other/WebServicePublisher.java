package com.impetus.bookstore.other;

import javax.xml.ws.Endpoint;

import com.impetus.bookstore.service.SearchServiceImpl;

// TODO: Auto-generated Javadoc
/**
 * The Class WebServicePublisher.
 */
public class WebServicePublisher {

    /**
     * The main method.
     * 
     * @param args
     *            the arguments
     */
    public static void main(String[] args) {
        Endpoint.publish("http://localhost:8882/webservice/search",
                new SearchServiceImpl());
    }

}
