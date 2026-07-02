package com.example.demo.config;

import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.webserver.WebServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.service.TutukaServiceImpl;

@Configuration
public class XmlRpcConfig {
	
    @Bean
    public WebServer webServer() throws Exception {

        WebServer server = new WebServer(8083);

        PropertyHandlerMapping mapping = new PropertyHandlerMapping();

        mapping.addHandler("Tutuka", TutukaServiceImpl.class);

        server.getXmlRpcServer().setHandlerMapping(mapping);

        server.start();

        System.out.println("XMLRPC Server Started");

        return server;
    }

}
