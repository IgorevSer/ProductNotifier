package by.javaguru.ws.productMicroservice.service;

import by.javaguru.ws.productMicroservice.service.dto.CreateProductDto;

import java.util.concurrent.ExecutionException;

public interface ProductService {
    String createProduct(CreateProductDto createProductDto) throws ExecutionException, InterruptedException;

}
