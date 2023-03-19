package com.example.demowithtests.dto.address;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AddressDto {

    public Long id;
    public String country;
    public String city;
    public String street;
    @JsonIgnore
    public Boolean addressHasActive = Boolean.TRUE;
}
