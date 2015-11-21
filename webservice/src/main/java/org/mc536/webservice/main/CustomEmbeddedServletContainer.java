package org.mc536.webservice.main;

import org.eclipse.jetty.server.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainer;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class CustomEmbeddedServletContainer {

    @Value("${http-server.name}")
    private String httpServerName;

    @Value("${http-server.threadPool:4}")
    private Integer threadPoolNumber;

    @Value("${http-server.threadPool-minThreads:10}")
    private Integer minThreads;

    @Value("${http-server.threadPool-maxThreads:100}")
    private Integer maxThreads;

    @Value("${http-server.port:12000}")
    private Integer httpPort;

    @Value("${http-server.maxIdleTime:30000}")
    private Integer maxIdleTime;

    @Value("${http-server.acceptQueueSize:1000}")
    private Integer acceptQueueSize;

    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        JettyEmbeddedServletContainerFactory factory = new JettyEmbeddedServletContainerFactory() {
            @Override
            protected JettyEmbeddedServletContainer getJettyEmbeddedServletContainer(Server server) {

                Integer acceptorsSize = Runtime.getRuntime().availableProcessors();
                ServerConnector connector = new ServerConnector(server, acceptorsSize, -1);

                connector.setPort(httpPort);
                connector.setIdleTimeout(maxIdleTime);
                connector.setAcceptQueueSize(acceptQueueSize);
                server.setConnectors(new Connector[]{connector});
                for (ConnectionFactory connectionFactory : connector.getConnectionFactories()) {
                    if (connectionFactory instanceof HttpConnectionFactory) {
                        HttpConnectionFactory httpConnectionFactory = (HttpConnectionFactory) connectionFactory;
                        httpConnectionFactory.getHttpConfiguration().setSendServerVersion(false);
                        httpConnectionFactory.getHttpConfiguration().setSendXPoweredBy(false);
                    }
                }

                server.setAttribute("org.eclipse.jetty.server.Request.maxFormContentSize", "2000000");

                return super.getJettyEmbeddedServletContainer(server);
            }
        };

        factory.setPort(httpPort);

        return factory;
    }

}
