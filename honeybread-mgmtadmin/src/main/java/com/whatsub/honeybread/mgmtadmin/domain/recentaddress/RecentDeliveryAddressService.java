package com.whatsub.honeybread.mgmtadmin.domain.recentaddress;

import com.whatsub.honeybread.core.domain.recentaddress.RecentDeliveryAddress;
import com.whatsub.honeybread.core.domain.recentaddress.RecentDeliveryAddressRepository;
import com.whatsub.honeybread.core.infra.errors.ErrorCode;
import com.whatsub.honeybread.core.infra.exception.HoneyBreadException;
import com.whatsub.honeybread.mgmtadmin.domain.recentaddress.dto.RecentDeliveryAddressRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class RecentDeliveryAddressService {

    private final RecentDeliveryAddressRepository repository;

    @Transactional
    public void createIfAbsent(RecentDeliveryAddressRequest request) {
        RecentDeliveryAddress recentDeliveryAddress =
            repository.findByUserIdAndDeliveryAddressOrStateNameAddress(request.getUserId(),
                                                                        request.getDeliveryAddress(),
                                                                        request.getStateNameAddress())
                    .orElseGet(() -> {
                        deleteIfCountGreaterThanOrEqual10(repository.countByUserId(request.getUserId()), request.getUserId());
                        return repository.save(request.toRecentDeliveryAddress());
                    });
        recentDeliveryAddress.updateUsedAt();
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(findById(id));
    }

    private RecentDeliveryAddress findById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new HoneyBreadException(ErrorCode.RECENT_DELIVERY_ADDRESS_NOT_FOUND));
    }

    private void deleteIfCountGreaterThanOrEqual10(int count, Long userId) {
        if(count >= 10) {
            repository.delete(repository.findTop1ByUserIdOrderByUsedAtAsc(userId).get());
        }
    }
}
