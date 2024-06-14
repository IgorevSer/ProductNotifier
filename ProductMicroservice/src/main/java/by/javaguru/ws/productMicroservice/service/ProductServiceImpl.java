package by.javaguru.ws.productMicroservice.service;

//import by.javaguru.ws.productMicroservice.Entity.Product;
//import by.javaguru.ws.productMicroservice.Repository.ProductRepository;
import by.javaguru.ws.productMicroservice.service.dto.CreateProductDto;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.examp.core.ProductCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
public class ProductServiceImpl implements ProductService {
    private KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public ProductServiceImpl(KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public String createProduct(CreateProductDto createProductDto) throws ExecutionException, InterruptedException {
        //TODO save DB
        String productId = UUID.randomUUID().toString();
        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent(productId, createProductDto.getTitle(),
                createProductDto.getPrice(), createProductDto.getQuantity());

        ProducerRecord<String,ProductCreatedEvent> record = new ProducerRecord<>(
                "product-created-events-topic",
                productId,
                productCreatedEvent
        );

        record.headers().add("messageId","PRIVET".getBytes());



        SendResult<String, ProductCreatedEvent> result = kafkaTemplate.
                send(record).get();

        LOGGER.info("topic: {}", result.getRecordMetadata().topic());
        LOGGER.info("partition: {}", result.getRecordMetadata().partition());
        LOGGER.info("offset: {}", result.getRecordMetadata().offset());


        LOGGER.info("RETURN: {}", productId);
        return productId;
    }
}

//@Service
//public class ProductServiceImpl implements ProductService {
//    private final KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;
//    private final ProductRepository productRepository;
//    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
//
//    public ProductServiceImpl(KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate,
//                              ProductRepository productRepository) {
//        this.kafkaTemplate = kafkaTemplate;
//        this.productRepository = productRepository;
//    }
//
//    @Override
//    public String createProduct(CreateProductDto createProductDto) throws ExecutionException, InterruptedException {
//        // Save product to DB
//        Product product = new Product();
//        String productId = UUID.randomUUID().toString();
//        product.setProductId(productId);
//        product.setTitle(createProductDto.getTitle());
//        product.setPrice(createProductDto.getPrice());
//        product.setQuantity(createProductDto.getQuantity());
//        product = productRepository.save(product);
//
//        // Send event to Kafka
//        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent(product.getProductId(), product.getTitle(),
//                product.getPrice(), product.getQuantity());
//        SendResult<String, ProductCreatedEvent> result = kafkaTemplate.
//                send("product-created-events-topic", productId, productCreatedEvent).get();
//        LOGGER.info("topic: {}", result.getRecordMetadata().topic());
//        LOGGER.info("partition: {}", result.getRecordMetadata().partition());
//        LOGGER.info("offset: {}", result.getRecordMetadata().offset());
//
//        LOGGER.info("RETURN: {}", productId);
//        return productId;
//    }
//}
