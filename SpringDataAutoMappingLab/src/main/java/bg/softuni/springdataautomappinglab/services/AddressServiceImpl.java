package bg.softuni.springdataautomappinglab.services;

import bg.softuni.springdataautomappinglab.entities.Address;
import bg.softuni.springdataautomappinglab.entities.dtos.CreateAddressDTO;
import bg.softuni.springdataautomappinglab.entities.repositories.AddressRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService{

    private AddressRepository addressRepository;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Address create(CreateAddressDTO data) {
        ModelMapper mapper = new ModelMapper();

        Address address = mapper.map(data, Address.class);

        return this.addressRepository.save(address);
    }
}
