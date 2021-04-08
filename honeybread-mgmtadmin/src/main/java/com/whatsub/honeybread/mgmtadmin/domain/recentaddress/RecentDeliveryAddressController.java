package com.whatsub.honeybread.mgmtadmin.domain.recentaddress;

import com.whatsub.honeybread.common.support.HoneyBreadSwaggerTags;
import com.whatsub.honeybread.core.infra.exception.ValidationException;
import com.whatsub.honeybread.mgmtadmin.domain.recentaddress.dto.RecentDeliveryAddressRequest;
import com.whatsub.honeybread.mgmtadmin.domain.recentaddress.dto.RecentDeliveryAddressResponse;
import com.whatsub.honeybread.mgmtadmin.support.MgmtAdminSwaggerTags;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Api(tags = HoneyBreadSwaggerTags.ALL)
@RequestMapping("recent-addresses")
@RestController
@RequiredArgsConstructor
public class RecentDeliveryAddressController {

    private final RecentDeliveryAddressService service;
    private final RecentDeliveryAddressQueryService queryService;

    @ApiOperation(
        value = "최근배달주소 등록 OR 사용시간 업데이트",
        tags = MgmtAdminSwaggerTags.RECENT_DELIVERY_ADDRESS
    )
    @PutMapping
    public ResponseEntity<Void> createIfAbsent(@Valid @RequestBody RecentDeliveryAddressRequest request, BindingResult result) {
        if(result.hasErrors()) {
            throw new ValidationException(result);
        }
        service.createIfAbsent(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiOperation(
        value = "최근배달주소 삭제",
        tags = MgmtAdminSwaggerTags.RECENT_DELIVERY_ADDRESS
    )
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(
        value = "최근배달주소 검색",
        tags = MgmtAdminSwaggerTags.RECENT_DELIVERY_ADDRESS
    )
    @GetMapping("user/{id}")
    public ResponseEntity<List<RecentDeliveryAddressResponse>> getAllByUserId(@PathVariable("id") Long userId) {
        List<RecentDeliveryAddressResponse> responses = queryService.getRecentDeliveryAddressesByUserId(userId);
        return ResponseEntity.ok(responses);
    }
}
