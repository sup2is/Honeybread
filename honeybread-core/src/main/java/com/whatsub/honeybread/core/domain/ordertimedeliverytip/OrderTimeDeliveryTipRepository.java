package com.whatsub.honeybread.core.domain.ordertimedeliverytip;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderTimeDeliveryTipRepository extends JpaRepository<OrderTimeDeliveryTip, Long> {
    boolean existsByStoreId(final long storeId);
    Optional<OrderTimeDeliveryTip> findByStoreId(final long storeId);
}
