package com.whatsub.honeybread.mgmtadmin.domain.recentaddress;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whatsub.honeybread.core.domain.recentaddress.RecentDeliveryAddress;
import com.whatsub.honeybread.core.infra.errors.ErrorCode;
import com.whatsub.honeybread.core.infra.exception.HoneyBreadException;
import com.whatsub.honeybread.mgmtadmin.domain.recentaddress.dto.RecentDeliveryAddressRequest;
import com.whatsub.honeybread.mgmtadmin.domain.recentaddress.dto.RecentDeliveryAddressResponse;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.never;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@WebMvcTest(RecentDeliveryAddressController.class)
@RequiredArgsConstructor
class RecentDeliveryAddressControllerTest {

    static final String BASE_URL = "/recent-addresses";
    final MockMvc mockMvc;
    final RecentDeliveryAddressController controller;
    final ObjectMapper objectMapper;

    @Mock
    RecentDeliveryAddressRequest mockRequest;

    @MockBean
    RecentDeliveryAddressService service;

    @MockBean
    RecentDeliveryAddressQueryService queryService;

    @Test
    void 최근배달주소_등록() throws Exception {
        //given
        유효성검사를_통과하는_Request_생성();

        //when
        ResultActions resultActions = 최근배달주소_등록_요청();

        //then
        최근배달주소가_등록되어야함();
        결과_응답이_Created_이어야함(resultActions);
    }

    @Test
    void 최근배달주소_등록실패() throws Exception {
        //given
        유효성검사를_실패하는_Request_생성();

        //when
        ResultActions resultActions = 최근배달주소_등록_요청();

        //then
        최근배달주소가_등록되지_않아야함();
        결과_응답이_BadRequest_이어야함(resultActions);
        등록_실패_에러_검증(resultActions);
    }

    @Test
    void 최근배달주소_삭제() throws Exception {
        //given
        final Long id = 1L;

        //when
        ResultActions resultActions = 최근배달주소_삭제_요청(id);

        //then
        최근배달주소가_삭제되어야함();
        결과_응답이_NoContent_이어야함(resultActions);
    }

    @Test
    void 최근배달주소_삭제시_없을경우_에러() throws Exception {
        //given
        final Long id = 1L;
        최근배달주소_삭제_실패();

        //when
        ResultActions resultActions = 최근배달주소_삭제_요청(id);

        //then
        결과_응답이_NotFound_이어야함(resultActions);
    }

    @Test
    void 최근배달주소_UserId로_검색() throws Exception {
        //given
        final Long id = 1L;
        최근배달주소_검색();

        //when
        ResultActions resultActions = 최근배달주소_검색_요청(id);

        //then
        최근배달주소가_검색되어야함();
        결과응답이_Ok_이어야함(resultActions);
    }

    private void 최근배달주소_검색() {
        List<RecentDeliveryAddressResponse> recentDeliveryAddresses = 사이즈만큼_최근배달주소Response_생성(10);
        given(queryService.getRecentDeliveryAddressesByUserId(anyLong())).willReturn(recentDeliveryAddresses);
    }

    private void 최근배달주소_삭제_실패() {
        willThrow(new HoneyBreadException(ErrorCode.RECENT_DELIVERY_ADDRESS_NOT_FOUND)).given(service).delete(anyLong());
    }

    private void 결과_응답이_NotFound_이어야함(final ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isNotFound());
    }

    private void 결과_응답이_NoContent_이어야함(final ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isNoContent());
    }

    private void 결과응답이_Ok_이어야함(final ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isOk());
    }

    private void 유효성검사를_실패하는_Request_생성() {
        given(mockRequest.getUserId()).willReturn(1L);
        given(mockRequest.getDeliveryAddress()).willReturn("");
        given(mockRequest.getStateNameAddress()).willReturn("");
        given(mockRequest.getSearchableDeliveryAddress()).willReturn("");
        given(mockRequest.getZipCode()).willReturn("00000");
    }

    private void 유효성검사를_통과하는_Request_생성() {
        given(mockRequest.getUserId()).willReturn(1L);
        given(mockRequest.getDeliveryAddress()).willReturn("서울시 강남구 수서동 500 301동 404호");
        given(mockRequest.getStateNameAddress()).willReturn("서울시 강남구 광평로101길 200 301동 404호");
        given(mockRequest.getSearchableDeliveryAddress()).willReturn("서울시 강남구 수서동");
        given(mockRequest.getZipCode()).willReturn("00000");
    }

    private ResultActions 최근배달주소_검색_요청(final Long id) throws Exception {
        return mockMvc.perform(get(BASE_URL + "/user/" + id)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print());
    }

    private ResultActions 최근배달주소_삭제_요청(final Long id) throws Exception {
        return mockMvc.perform(delete(BASE_URL + "/" + id)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print());
    }

    private ResultActions 최근배달주소_등록_요청() throws Exception {
        return mockMvc.perform(put(BASE_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(mockRequest)))
            .andDo(print());
    }

    private void 최근배달주소가_등록되어야함() {
        then(service).should().createIfAbsent(any(RecentDeliveryAddressRequest.class));
    }

    private void 최근배달주소가_등록되지_않아야함() {
        then(service).should(never()).createIfAbsent(any(RecentDeliveryAddressRequest.class));
    }

    private void 최근배달주소가_삭제되어야함() {
        then(service).should().delete(anyLong());
    }

    private void 최근배달주소가_검색되어야함() {
        then(queryService).should().getRecentDeliveryAddressesByUserId(anyLong());
    }

    private void 결과_응답이_Created_이어야함(final ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isCreated());
    }

    private void 결과_응답이_BadRequest_이어야함(final ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isBadRequest());
    }

    private void 등록_실패_에러_검증(final ResultActions resultActions) throws Exception {
        resultActions.andExpect(jsonPath("$.errors", hasSize(3)));
    }

    private List<RecentDeliveryAddressResponse> 사이즈만큼_최근배달주소Response_생성(int i) {
        return IntStream.range(0, i)
            .mapToObj((v) -> RecentDeliveryAddress.builder()
                .userId(1L)
                .deliveryAddress("서울시 강남구 수서동 500 301동 404호 " + v)
                .searchableDeliveryAddress("서울시 강남구 수서동")
                .stateNameAddress("서울시 강남구 광평로101길 200 301호 404호" + v)
                .zipCode("99999")
                .usedAt(LocalDateTime.now())
                .build())
            .map(RecentDeliveryAddressResponse::of)
            .collect(Collectors.toList());
    }
}
