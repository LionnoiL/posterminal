package ua.gaponov.posterminal.server;

import com.sun.net.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Andriy Gaponov
 */
public class PosHttpServer {

    private static final Logger LOG = LoggerFactory.getLogger(PosHttpServer.class);
    private HttpServer server;
    private ExecutorService threadPool;

    public PosHttpServer(int port, String path, int nThreads) throws IOException {
        String localIpAddress = getLocalIpAddress();
        InetSocketAddress localhost = new InetSocketAddress(localIpAddress, port);
        HttpServer httpServer = HttpServer.create(localhost, 0);
        httpServer.createContext(path, new ServerHandler());
        threadPool = Executors.newFixedThreadPool(nThreads);
        httpServer.setExecutor(threadPool);
        server = httpServer;
    }

    public void start() {
        server.start();
        LOG.info("Started http server, listening port:{} on {}",
                server.getAddress().getPort(),
                server.getAddress().getHostString());
    }

    public void stop() {
        server.stop(1);
        threadPool.shutdownNow();
    }

    private String getLocalIpAddress() {
        try {
            Enumeration<NetworkInterface> networkInterfaceEnumeration = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaceEnumeration.hasMoreElements()) {
                for (InterfaceAddress interfaceAddress : networkInterfaceEnumeration.nextElement().getInterfaceAddresses()) {
                    if (interfaceAddress.getAddress().isSiteLocalAddress()) {
                        return interfaceAddress.getAddress().getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        return "localhost";
    }
}
