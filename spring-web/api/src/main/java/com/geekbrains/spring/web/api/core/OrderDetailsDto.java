package com.geekbrains.spring.web.api.core;

import io.swagger.v3.oas.annotations.media.Schema;

public class OrderDetailsDto {

    @Schema(description = "адрес", required = true, example = "ул. Набережнаяб д. 146")
    private String address;

    @Schema(description = "номер телефона", required = true, example = "+7(980)345-12-34")
    private String phone;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public OrderDetailsDto() {
    }

    public OrderDetailsDto(String address, String phone) {
        this.address = address;
        this.phone = phone;
    }
}
