package com.whatsub.honeybread.core.domain.ordertimedeliverytip;

import com.whatsub.honeybread.core.domain.model.Money;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@DataJpaTest
@RequiredArgsConstructor
class OrderTimeDeliveryTipRepositoryTest {

    final OrderTimeDeliveryTipRepository repository;

    @Test
    void 시간별_배달팁_storeId_등록여부_체크() {
        //given
        final OrderTimeDeliveryTip orderTimeDeliveryTip = OrderTimeDeliveryTip.builder()
            .storeId(anyLong())
            .tip(Money.wons(anyLong()))
            .deliveryTimePeriod(DeliveryTimePeriod.builder()
                .from(LocalTime.of(anyInt(), anyInt()))
                .to(LocalTime.of(anyInt(), anyInt()))
                .build())
            .build();

        repository.save(orderTimeDeliveryTip);

        //when
        final boolean result = repository.existsByStoreId(anyLong());

        //then
        assertTrue(result);
    }

    @Test
    void 시간별_배달팁_storeId로_검색() {
        //given
        final long storeId = anyLong();
        final OrderTimeDeliveryTip orderTimeDeliveryTip = OrderTimeDeliveryTip.builder()
            .storeId(storeId)
            .tip(Money.wons(anyLong()))
            .deliveryTimePeriod(DeliveryTimePeriod.builder()
                .from(LocalTime.of(anyInt(), anyInt()))
                .to(LocalTime.of(anyInt(), anyInt()))
                .build())
            .build();

        repository.save(orderTimeDeliveryTip);

        //when
        final OrderTimeDeliveryTip findTip = repository.findByStoreId(storeId).get();

        //then
        assertEquals(orderTimeDeliveryTip, findTip);
    }

    @Test
    void 시간별_배달팁_storeId_time_으로_검색() {
        //given
        final long storeId = 1L;
        final OrderTimeDeliveryTip orderTimeDeliveryTip = OrderTimeDeliveryTip.builder()
            .storeId(storeId)
            .tip(Money.wons(anyLong()))
            .deliveryTimePeriod(DeliveryTimePeriod.builder()
                .from(LocalTime.of(23, 0))
                .to(LocalTime.of(8, 0))
                .build())
            .build();

        repository.save(orderTimeDeliveryTip);

        //when
        final OrderTimeDeliveryTip findTip = repository.getTipByTime(storeId, LocalTime.of(3, 57)).get();

        //then
        assertEquals(orderTimeDeliveryTip, findTip);
    }

    @Test
    void 시간별_배달팁_storeId_으로_전체검색() {
        //given
        final long storeId = 1L;
        final int size = 10;
        final List<OrderTimeDeliveryTip> orderTimeDeliveryTips = 시간별_배달팁_size만큼_생성(storeId, size);

        repository.saveAll(orderTimeDeliveryTips);

        //when
        final List<OrderTimeDeliveryTip> findList = repository.findAllByStoreId(storeId);

        //then
        assertEquals(10, findList.size());
    }

    private List<OrderTimeDeliveryTip> 시간별_배달팁_size만큼_생성(final long storeId, final int size) {
        return IntStream.range(0, size)
            .mapToObj(i -> OrderTimeDeliveryTip.builder()
                .storeId(storeId)
                .tip(Money.ZERO)
                .deliveryTimePeriod(DeliveryTimePeriod.builder()
                    .to(LocalTime.now())
                    .from(LocalTime.now()).build()
                ).build())
            .collect(toList());

    }


}