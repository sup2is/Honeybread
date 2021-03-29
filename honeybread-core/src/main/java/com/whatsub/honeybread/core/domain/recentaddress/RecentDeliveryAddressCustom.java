package com.whatsub.honeybread.core.domain.recentaddress;

public interface RecentDeliveryAddressCustom {
    RecentDeliveryAddress findByDeliveryAddressOrStateNameAddress(String target);
}
