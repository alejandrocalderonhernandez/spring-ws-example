package com.alejandro.ws.endpoint;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

import javax.xml.bind.JAXBElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.alejandro.ws.entity.Product;
import com.alejandro.ws.resources.ObjectFactory;
import com.alejandro.ws.resources.ProductDetails;
import com.alejandro.ws.resources.ProductRequest;
import com.alejandro.ws.resources.ProductResponse;
import com.alejandro.ws.service.AbstractService;

@Endpoint
public class ProductEndpoint<productResponse> {
	
	private static final Logger log = LoggerFactory.getLogger(ProductEndpoint.class);
	
	private static final String NAMESPACE_URI = "http://www.example.org/Product";
	
	@Autowired
	AbstractService service;
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "productRequest")
	@ResponsePayload
	public JAXBElement<ProductResponse> getProduct(@RequestPayload JAXBElement<ProductRequest> request) {
		ObjectFactory objectFactory = new ObjectFactory();
		ProductResponse responseFactory = objectFactory.createProductResponse();
		JAXBElement<ProductResponse> response = objectFactory.createProductResponse(responseFactory);
		try {
			int action = request.getValue().getAction();	
			if(action == 1) {
				ProductResponse productResponse = new ProductResponse();
				Set<Product> products = this.service.findAll();
				if(products != null && !products.isEmpty()) {
					products.forEach(p -> {
						ProductDetails pd = new ProductDetails();
						pd.setId(p.getId().intValue());
						pd.setName(p.getName());
						pd.setPrice(BigDecimal.valueOf(p.getPrice()));
						pd.setAvailable(p.getAvailable());
						pd.setImg(p.getImg());
						productResponse.getProductDetails().add(pd);
					});
				}
				response.setValue(productResponse);
			}
			if(action == 2) {
				ProductResponse productResponse = new ProductResponse();
				Optional<Product> product = this.service.findByName(request.getValue().getName());
				if(product.isPresent()) {
					Product p = product.get();
					ProductDetails pd = new ProductDetails();
					pd.setId(p.getId().intValue());
					pd.setName(p.getName());
					pd.setPrice(BigDecimal.valueOf(p.getPrice()));
					pd.setAvailable(p.getAvailable());
					pd.setImg(p.getImg());
					productResponse.getProductDetails().add(pd);
				}
				response.setValue(productResponse);
			}
			
		} catch (Exception e) {
			log.error("Error into endpoint", e);
		}
		return response;
	}
}
