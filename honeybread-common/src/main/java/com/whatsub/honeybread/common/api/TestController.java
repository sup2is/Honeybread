package com.whatsub.honeybread.common.api;

import com.whatsub.honeybread.common.support.HoneyBreadSwaggerTags;
import com.whatsub.honeybread.core.infra.errors.ErrorCode;
import com.whatsub.honeybread.core.infra.exception.HoneyBreadException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = HoneyBreadSwaggerTags.ALL)
@RestController
public class TestController {

    @ApiOperation(
        value = "테스트 API",
        tags = HoneyBreadSwaggerTags.TEST
    )
    @GetMapping("/test")
    public String test() {
        throw new HoneyBreadException(ErrorCode.INTERNAL_SERVER_ERROR);
//        return "Test";
    }
}
