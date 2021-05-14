package com.alejandro.ws.config;

import javax.servlet.Servlet;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@Configuration
@EnableWs
@EnableWebMvc
public class WebServicesConfig extends WsConfigurerAdapter {

	@Bean
	public ServletRegistrationBean<Servlet> 
			servletRegistrationBean(ApplicationContext context) {
		MessageDispatcherServlet mds = new MessageDispatcherServlet();
		mds.setApplicationContext(context);
		mds.setTransformSchemaLocations(true);
		return new ServletRegistrationBean<Servlet>(mds, "/ws/*");
	}
	
	@Bean(name = "product")
	public DefaultWsdl11Definition sefaultWsdl11Definition(XsdSchema productSchema) {
		DefaultWsdl11Definition dw11d = new DefaultWsdl11Definition();
		dw11d.setPortTypeName("ProductPort");
		dw11d.setLocationUri("/ws");
		dw11d.setTargetNamespace("http://www.example.org/Product");
		dw11d.setSchema(productSchema);
		return dw11d;
	}
	
	@Bean
	public XsdSchema xsdSchema() {
		return new SimpleXsdSchema(new ClassPathResource("xsd/Product.xsd"));
	}

}
