package kr.co.ordermanagement.presentation.controller;

import kr.co.ordermanagement.application.SimpleOrderService;
import kr.co.ordermanagement.presentation.dto.ChangeOrderStateRequestDto;
import kr.co.ordermanagement.presentation.dto.OrderProductRequestDto;
import kr.co.ordermanagement.presentation.dto.OrderProductResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderRestController {

  private SimpleOrderService simpleOrderService;

  @Autowired
  public OrderRestController(SimpleOrderService simpleOrderService) {
    this.simpleOrderService = simpleOrderService;
  }

  //1. 상품 주문 API
  @PostMapping("/orders")
  public OrderProductResponseDto createOrder(
          @RequestBody List<OrderProductRequestDto> orderProductRequestDtos //List는 관례상 변수명을 복수로 쓴다..
  ) {
    OrderProductResponseDto orderProductResponseDto = simpleOrderService.createOrder(orderProductRequestDtos);

    return orderProductResponseDto; //상태 코드가 무조건 200일것 같으면 ResponseEntity.ok(없어도 됨)
  }

  //2. 주문 상태 강제 변경 API
  @PatchMapping("/orders/{orderId}")
  //보통 도메인 전체를 바꿀 때는 update~(), 도메인의 일부를 바꿀 때는 change~()
  public OrderProductResponseDto changeOrderById(
          @PathVariable Long orderId,
          @RequestBody ChangeOrderStateRequestDto changeOrderStateRequestDto
  ){
    OrderProductResponseDto orderProductResponseDto = simpleOrderService.changeState(orderId, changeOrderStateRequestDto);
    return orderProductResponseDto; //cmd + opt + n -> 하면 코드 한 줄 됨
  }

  //3. 주문 번호로 조회 API
  @GetMapping("/orders/{orderId}")
  public OrderProductResponseDto getOrderById(@PathVariable Long orderId) {
    OrderProductResponseDto orderProductResponseDto = simpleOrderService.findById(orderId);
    return orderProductResponseDto;
  }


  //4. 주문 상태로 조회
  //5. 주문 취소 API
}
