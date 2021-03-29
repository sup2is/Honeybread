package com.whatsub.honeybread.core.domain.recentaddress;

import com.whatsub.honeybread.core.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@DataJpaTest
@RequiredArgsConstructor
class RecentDeliveryAddressRepositoryTest {

    final RecentDeliveryAddressRepository repository;

    @Test
    void 배달주소로_최근배달주소검색() {
        //given
        String deliveryAddress = "서울시 강남구 수서동 500 301동 404호";
        RecentDeliveryAddress recentDeliveryAddress = RecentDeliveryAddress.builder()
            .deliveryAddress(deliveryAddress)
            .searchableDeliveryAddress("서울시 강남구 수서동")
            .stateNameAddress("서울시 강남구 광평로101길 200 301호 404호")
            .zipCode("99999")
            .usedAt(LocalDateTime.now())
            .build();

        repository.save(recentDeliveryAddress);

        //when
        RecentDeliveryAddress findRecentDeliveryAddress = repository.findByDeliveryAddressOrStateNameAddress(deliveryAddress);

        //then
        assertNotNull(findRecentDeliveryAddress);
        assertEquals(recentDeliveryAddress, findRecentDeliveryAddress);
    }

    @Test
    void 도로명주소로로_최근배달주소검색() {
        //given
        String stateNameAddress = "서울시 강남구 광평로101길 200 301호 404호";

        RecentDeliveryAddress recentDeliveryAddress = RecentDeliveryAddress.builder()
            .deliveryAddress("서울시 강남구 수서동 500 301동 404호")
            .searchableDeliveryAddress("서울시 강남구 수서동")
            .stateNameAddress(stateNameAddress)
            .zipCode("99999")
            .usedAt(LocalDateTime.now())
            .build();

        repository.save(recentDeliveryAddress);

        //when
        RecentDeliveryAddress findRecentDeliveryAddress = repository.findByDeliveryAddressOrStateNameAddress(stateNameAddress);

        //then
        assertNotNull(findRecentDeliveryAddress);
        assertEquals(recentDeliveryAddress, findRecentDeliveryAddress);
    }
}