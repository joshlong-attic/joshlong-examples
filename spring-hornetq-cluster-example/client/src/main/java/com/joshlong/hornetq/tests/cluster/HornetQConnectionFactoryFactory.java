package com.joshlong.hornetq.tests.cluster;

import org.apache.commons.lang.StringUtils;
import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.api.core.client.loadbalance.RoundRobinConnectionLoadBalancingPolicy;
import org.hornetq.jms.client.HornetQConnectionFactory;
import org.springframework.beans.factory.FactoryBean;

import javax.jms.ConnectionFactory;
import java.util.HashMap;
import java.util.Map;


/**
 * This is a simple place to test building the {@link javax.jms.ConnectionFactory}
 *
 * @author <a href="mailto:josh@joshlong.com">Josh Long</a>
 */
public class HornetQConnectionFactoryFactory implements FactoryBean {
    
    private TransportConfiguration factoryTransportConfiguration(String host, int port)
        throws Exception {
        if (StringUtils.isEmpty(host) || (port < 1)) {
            return null;
        }

        Map<String, Object> parms = new HashMap<String, Object>();
        parms.put("host", host);
        parms.put("port", port);

        return new TransportConfiguration("org.hornetq.integration.transports.netty.NettyConnectorFactory", parms);
    }

    private ConnectionFactory connectionFactory() throws Exception {

/*    Map<String, Integer> hosts = new HashMap<String, Integer>();
        hosts.put("localhost", 4551);
        hosts.put("localhost", 4552);

        List<Pair<TransportConfiguration, TransportConfiguration>> pairs = new ArrayList<Pair<TransportConfiguration, TransportConfiguration>>();

        for (String host : hosts.keySet()) {
            pairs.add(new Pair<TransportConfiguration, TransportConfiguration>(factoryTransportConfiguration(host, hosts.get(host)), null));
        }
*/

        HornetQConnectionFactory hornetQConnectionFactory = new HornetQConnectionFactory( "231.7.7.7", 9876);
        hornetQConnectionFactory.setConnectionLoadBalancingPolicyClassName(RoundRobinConnectionLoadBalancingPolicy.class.getName());
        return hornetQConnectionFactory;
    }

    @Override
    public Object getObject() throws Exception {
        return this.connectionFactory();
    }

    @Override
    public Class<?> getObjectType() {
        return ConnectionFactory.class;
    }

    @Override
    public boolean isSingleton() {
        return false ;
    }
}
