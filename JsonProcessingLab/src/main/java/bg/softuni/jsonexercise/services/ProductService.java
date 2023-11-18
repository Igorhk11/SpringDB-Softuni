package bg.softuni.jsonexercise.services;

import bg.softuni.jsonexercise.domain.models.ProductNameAndPriceDto;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    void seedData() throws IOException;

    List<ProductNameAndPriceDto> findProductInRangeOrderByPrice(BigDecimal bigDecimal, BigDecimal bigDecimal1);
}
