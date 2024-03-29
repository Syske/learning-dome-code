// Copyright (c) 2019, 2020, Oracle and/or its affiliates.
// Licensed under the Universal Permissive License v 1.0 as shown at https://oss.oracle.com/licenses/upl.

package com.oracle.wls.exporter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.oracle.wls.exporter.ServletConstants.MESSAGES_PAGE;

/**
 * A collector of REST requests and replies, that can be viewed to diagnose problems.
 */
@WebServlet("/" + MESSAGES_PAGE)
public class MessagesServlet extends HttpServlet {
    static final int MAX_EXCHANGES = 5;
    private static final String TEMPLATE = "REQUEST to %s:%n%s%nREPLY:%n%s%n";

    private static final Queue<String> messages = new ConcurrentLinkedDeque<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (ServletOutputStream out = resp.getOutputStream()) {
            for (String message : messages)
                out.println(message);
        }
    }

    static void clear() {
        messages.clear();
    }

    static void addExchange(String url, String request, String response) {
        messages.add(String.format(TEMPLATE, url, request, response));
        if (messages.size() > MAX_EXCHANGES) messages.remove();
    }

    static List<String> getMessages() {
        return new ArrayList<>(messages);
    }
}
