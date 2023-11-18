package bg.softuni.springdataautomappinglab.services;

import bg.softuni.springdataautomappinglab.entities.Address;
import bg.softuni.springdataautomappinglab.entities.dtos.CreateAddressDTO;

public interface AddressService {
    Address create(CreateAddressDTO data);
}
