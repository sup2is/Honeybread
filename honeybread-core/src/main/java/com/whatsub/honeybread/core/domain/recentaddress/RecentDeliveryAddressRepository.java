package com.whatsub.honeybread.core.domain.recentaddress;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RecentDeliveryAddressRepository extends JpaRepository<RecentDeliveryAddress, Long>, RecentDeliveryAddressCustom {
}
