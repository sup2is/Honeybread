package com.whatsub.honeybread.mgmtadmin.domain.recentaddress.dto;

import com.sun.istack.NotNull;
import com.whatsub.honeybread.core.domain.recentaddress.RecentDeliveryAddress;
import io.swagger.annotations.ApiModel;
import lombok.Value;

import javax.validation.constraints.NotBlank;

@ApiModel("최근배달주소 등록 요청")
@Value
public class RecentDeliveryAddressRequest {

    @NotNull
    Long userId;

    @NotBlank(message = "배달주소는 반드시 입력해야합니다.")
    String deliveryAddress;

    @NotBlank(message = "검색 가능한 주소는 반드시 입력해야합니다.")
    String searchableDeliveryAddress;

    @NotBlank(message = "도로명주소는 반드시 입력해야합니다.")
    String stateNameAddress;

    @NotBlank(message = "우편번호는 반드시 입력해야합니다.")
    String zipCode;

    public RecentDeliveryAddress toRecentDeliveryAddress() {
        return RecentDeliveryAddress.builder()
            .zipCode(this.zipCode)
            .stateNameAddress(this.stateNameAddress)
            .searchableDeliveryAddress(this.searchableDeliveryAddress)
            .deliveryAddress(this.deliveryAddress)
            .userId(userId)
            .build();
    }
}
