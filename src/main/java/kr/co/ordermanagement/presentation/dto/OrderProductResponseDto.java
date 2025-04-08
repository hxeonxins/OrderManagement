package kr.co.ordermanagement.presentation.dto;

import kr.co.ordermanagement.domain.order.Order;
import kr.co.ordermanagement.domain.order.State;

import java.util.List;

public class OrderProductResponseDto {
  private Long id;
  private List<ProductDto> orderedProducts;
  private Integer totalPrice;
  private State state; //추후에 Enum으로 바꿀것

  public OrderProductResponseDto(Long id, List<ProductDto> orderedProductDtos, Integer totalPrice, State state) {
    this.id = id;
    this.orderedProducts = orderedProductDtos;
    this.totalPrice = (Integer) totalPrice;
    this.state = state;
  }

  public static OrderProductResponseDto toDto(Order order) {

    List<ProductDto> orderedProductDtos = order.getOrderedProducts()
            .stream()
            .map(orderedProduct -> ProductDto.toDto(orderedProduct))
            .toList();

    OrderProductResponseDto orderProductResponseDto = new OrderProductResponseDto(
            order.getId(),
            orderedProductDtos,
            order.getTotalPrice(),
            order.getState()
            );
            return orderProductResponseDto;
  }

  //요청을 주고 받을 때 JSON으로 주고 받아야 하므로 Getter 사용
  public Long getId() {
    return id;
  }

  public List<ProductDto> getOrderedProducts() {
    return orderedProducts;
  }

  public Integer getTotalPrice() {
    return totalPrice;
  }

  public State getState() {
    return state;
  }
}
