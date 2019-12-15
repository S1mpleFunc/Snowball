package ru.func.snowball.server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @author func 10.12.2019
 */
public class Server {

    private static final int BACKLOG = 0;

    public void start(String host, int port) throws IOException {
        HttpServer httpServer = HttpServer.create();
        httpServer.bind(new InetSocketAddress(host, port), BACKLOG);
        httpServer.createContext("/", new RootHandler());
        httpServer.start();
    }

    public static void main(String[] args) throws IOException {
        new Server().start("0.0.0.0", Integer.parseInt(System.getenv("PORT")));
    }
}
