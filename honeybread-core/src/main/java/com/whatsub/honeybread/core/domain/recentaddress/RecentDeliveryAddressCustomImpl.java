package com.whatsub.honeybread.core.domain.recentaddress;

import com.querydsl.core.types.Predicate;
import com.whatsub.honeybread.core.support.QuerydslRepositorySupport;
import org.apache.commons.lang3.StringUtils;

import static com.whatsub.honeybread.core.domain.recentaddress.QRecentDeliveryAddress.recentDeliveryAddress;

public class RecentDeliveryAddressCustomImpl extends QuerydslRepositorySupport implements RecentDeliveryAddressCustom {

    public RecentDeliveryAddressCustomImpl() {
        super(RecentDeliveryAddress.class);
    }

    @Override
    public RecentDeliveryAddress findByDeliveryAddressOrStateNameAddress(String target) {
        return getQueryFactory()
            .selectFrom(recentDeliveryAddress)
            .where(eqDeliveryAddressOrStateNameAddress(target))
            .fetchOne();
    }

    private Predicate eqDeliveryAddressOrStateNameAddress(String target) {
        return StringUtils.isBlank(target) ?
            null :
            recentDeliveryAddress.deliveryAddress.eq(target).or(recentDeliveryAddress.stateNameAddress.eq(target));
    }
}