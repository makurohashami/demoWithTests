package com.example.demowithtests.dto;

import java.time.Instant;
import java.util.Date;

public class AddressDto {

    public Long id;
    public Boolean addressHasActive = Boolean.TRUE;
    public String country;
    public String city;
    public String street;
    //todo: dfhgjkdfhg Jira - 5544
    public Date date = Date.from(Instant.now());
}
