package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.OffersSeedRootDto;
import softuni.exam.models.entities.Offer;
import softuni.exam.repository.OfferRepository;
import softuni.exam.service.CarService;
import softuni.exam.service.OfferService;
import softuni.exam.service.SellerService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class OfferServiceImpl implements OfferService {
    private static final String OFFER_FILE_PATH = "src/main/resources/files/xml/offers.xml";
    private OfferRepository offerRepository;
    private ValidationUtil validationUtil;
    private XmlParser xmlParser;
    private ModelMapper modelMapper;

    private CarService carService;

    private SellerService sellerService;


    public OfferServiceImpl(OfferRepository offerRepository, ValidationUtil validationUtil, XmlParser xmlParser, ModelMapper modelMapper, CarService carService, SellerService sellerService) {
        this.offerRepository = offerRepository;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.carService = carService;
        this.sellerService = sellerService;
    }

    @Override
    public boolean areImported() {
        return offerRepository.count() > 0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return Files.readString(Path.of(OFFER_FILE_PATH));
    }

    @Override
    public String importOffers() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();
        OffersSeedRootDto offersSeedRootDto = xmlParser.fromFile(OFFER_FILE_PATH, OffersSeedRootDto.class);

        offersSeedRootDto.getOffers().stream().filter(
                        offerSeedDto -> {
                            boolean isValid = validationUtil.isValid(offerSeedDto);

                            sb.append(isValid ? String.format("Successfully import offer %s - %s", offerSeedDto.getAddedOn(), offerSeedDto.getHasGoldStatus())
                                            : "Invalid offer")
                                    .append(System.lineSeparator());


                            return isValid;
                        }).map(offerSeedDto -> {
                    Offer offer = modelMapper.map(offerSeedDto, Offer.class);
                    offer.setSeller(sellerService.findById(offerSeedDto.getSeller().getId()));
                    offer.setCar(carService.findById(offerSeedDto.getCar().getId()));

                    return offer;
                })
                .forEach(offerRepository::save);

        return sb.toString();
    }
}
