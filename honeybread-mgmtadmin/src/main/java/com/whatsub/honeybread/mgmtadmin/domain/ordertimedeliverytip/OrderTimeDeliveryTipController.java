package com.whatsub.honeybread.mgmtadmin.domain.ordertimedeliverytip;

import com.whatsub.honeybread.core.infra.exception.ValidationException;
import com.whatsub.honeybread.mgmtadmin.domain.ordertimedeliverytip.dto.OrderTimeDeliveryTipRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("stores/{storeId}/order-time-delivery-tips")
public class OrderTimeDeliveryTipController {

    private final OrderTimeDeliveryTipService service;

    @PostMapping
    public ResponseEntity<Void> create(@PathVariable("storeId") long storeId,
                                       @Valid @RequestBody OrderTimeDeliveryTipRequest request,
                                       BindingResult result) {
        if(result.hasErrors()) {
            throw new ValidationException(result);
        }
        service.create(storeId, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
