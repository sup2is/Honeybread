package com.whatsub.honeybread.mgmtadmin.domain.ordertimedeliverytip;

import com.whatsub.honeybread.core.domain.ordertimedeliverytip.OrderTimeDeliveryTip;
import com.whatsub.honeybread.core.domain.ordertimedeliverytip.OrderTimeDeliveryTipRepository;
import com.whatsub.honeybread.mgmtadmin.domain.ordertimedeliverytip.dto.OrderTimeDeliveryTipResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderTimeDeliveryTipQueryService {

    private final OrderTimeDeliveryTipRepository repository;

    public List<OrderTimeDeliveryTipResponse> getAllByStoreId(final long storeId) {
        return repository.findAllByStoreId(storeId).stream()
            .map(OrderTimeDeliveryTipResponse::of)
            .collect(Collectors.toList());
    }

    public OrderTimeDeliveryTipResponse getTipByTime(final long storeId, final LocalTime time) {
        return OrderTimeDeliveryTipResponse.of(
            repository.getTipByTime(storeId, time)
                .orElse(OrderTimeDeliveryTip.createZeroTip(storeId))
        );
    }

}
