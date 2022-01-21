package com.geekbrains.spring.web.endpoints;

import com.geekbrains.spring.web.services.ProductService;
import com.geekbrains.spring.web.soap.products.GetAllProductsRequest;
import com.geekbrains.spring.web.soap.products.GetAllProductsResponse;
import com.geekbrains.spring.web.soap.products.GetProductByIdRequest;
import com.geekbrains.spring.web.soap.products.GetProductByIdResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
@RequiredArgsConstructor
public class ProductEndpoint {
    private static final String NAMESPACE_URI = "http://www.geekbrains.com/spring/web/soap/products/productXML";
    private final ProductService productService;

    /*
        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
          xmlns:f="http://www.geekbrains.com/spring/web/soap/products/productXML">
            <soapenv:Header/>
            <soapenv:Body>
                <f:getProductByIdRequest>
                    <f:id>1</f:id>
                </f:getProductByIdRequest>
            </soapenv:Body>
        </soapenv:Envelope>
     */

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductByIdRequest")
    @ResponsePayload
    @Transactional
    public GetProductByIdResponse getProductById(@RequestPayload GetProductByIdRequest request) {
        GetProductByIdResponse response = new GetProductByIdResponse();
        response.setProductXML(productService.getById(request.getId()));
        return response;
    }

        /*
        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
          xmlns:f="http://www.geekbrains.com/spring/web/soap/products/productXML">
            <soapenv:Header/>
            <soapenv:Body>
                <f:getAllProductsRequest/>
            </soapenv:Body>
        </soapenv:Envelope>
     */

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllProductsRequest")
    @ResponsePayload
    public GetAllProductsResponse getAllProducts(@RequestPayload GetAllProductsRequest request) {
        GetAllProductsResponse response = new GetAllProductsResponse();
        productService.getAllProducts().forEach(response.getProductsXML()::add);
        return response;
    }
}
