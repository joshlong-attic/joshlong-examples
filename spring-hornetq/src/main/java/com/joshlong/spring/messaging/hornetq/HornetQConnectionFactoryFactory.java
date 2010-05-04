package com.joshlong.spring.messaging.hornetq;

import org.apache.commons.lang.StringUtils;
import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.integration.transports.netty.NettyConnectorFactory;
import org.hornetq.jms.client.HornetQConnectionFactory;
import org.springframework.beans.factory.config.AbstractFactoryBean;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * This builds a {@link org.hornetq.jms.client.HornetQConnectionFactory} to handle connections to a HornetQ broker. 
 *
 * @author <a href="mailto:josh@joshlong.com">Josh Long</a>
 */
public class HornetQConnectionFactoryFactory extends AbstractFactoryBean<HornetQConnectionFactory>   {

    private String host = "localhost", backupHost = "localhost";
    private int port =  5455 , backupPort = 5455 ;
    private boolean failoverOnServerShutdown = true;
    private int reconnectAttempts = 0;


    /**
     * Builds a {@link org.hornetq.api.core.TransportConfiguration}
     *
     * @param host the host of the server
     * @param port the port of the server
     *
     * @return     a {@link org.hornetq.api.core.TransportConfiguration} that can be used to vend a {@link org.hornetq.jms.client.HornetQConnectionFactory}
     *
     * @throws Exception thrown if anything out of the ordinary should trespass
     */
    private TransportConfiguration factoryTransportConfiguration(String host, int port) throws Exception {

        if (StringUtils.isEmpty(host) || port < 1) {
            return null;
        }

        Map<String, Object> parms = new HashMap<String, Object>();
        parms.put("host", host);
        parms.put("port", port);
        return new TransportConfiguration( NettyConnectorFactory.class.getName(), parms);
    }

    private HornetQConnectionFactory factoryHornetQConnectionFactory() throws Exception {

        TransportConfiguration main = factoryTransportConfiguration(this.host, this.port);
        TransportConfiguration backup = factoryTransportConfiguration(this.backupHost, this.backupPort);

        HornetQConnectionFactory conn = null;

        if (main == null && backup == null) {
            throw new RuntimeException("both the configuration for the main and backup servers are null! You must specify at least 'host' and 'port'");
        }

        if (main != null && backup != null) {
            conn = new HornetQConnectionFactory(main, backup);
        }

        if (main != null && backup == null) {
            conn = new HornetQConnectionFactory(main);
        }

        if (null != conn) {
            conn.setFailoverOnServerShutdown(this.failoverOnServerShutdown);
            conn.setReconnectAttempts( this.reconnectAttempts );
        }
        return conn;
    }

    @Override
    public Class<?> getObjectType() {
     return HornetQConnectionFactory.class ;
    }

    @Override
    protected HornetQConnectionFactory createInstance() throws Exception {
      return this.factoryHornetQConnectionFactory();
    }

    // properties 
    public void setHost(final String host) {
        this.host = host;
    }

    public void setBackupHost(final String backupHost) {
        this.backupHost = backupHost;
    }

    public void setPort(final int port) {
        this.port = port;
    }

    public void setBackupPort(final int backupPort) {
        this.backupPort = backupPort;
    }

    public void setFailoverOnServerShutdown(final boolean failoverOnServerShutdown) {
        this.failoverOnServerShutdown = failoverOnServerShutdown;
    }

    public void setReconnectAttempts(final int reconnectAttempts) {
        this.reconnectAttempts = reconnectAttempts;
    }
}
