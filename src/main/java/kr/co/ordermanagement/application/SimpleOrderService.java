package kr.co.ordermanagement.application;

import kr.co.ordermanagement.domain.order.Order;
import kr.co.ordermanagement.domain.order.OrderRepository;
import kr.co.ordermanagement.domain.order.State;
import kr.co.ordermanagement.domain.product.Product;
import kr.co.ordermanagement.domain.product.ProductRepository;
import kr.co.ordermanagement.presentation.dto.ChangeOrderStateRequestDto;
import kr.co.ordermanagement.presentation.dto.OrderProductRequestDto;
import kr.co.ordermanagement.presentation.dto.OrderProductResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimpleOrderService {

  private ProductRepository productRepository;
  private OrderRepository orderRepository;

  @Autowired
  public SimpleOrderService(ProductRepository productRepository, OrderRepository orderRepository) {
    this.productRepository = productRepository;
    this.orderRepository = orderRepository;
  }

  public OrderProductResponseDto createOrder(List<OrderProductRequestDto> orderProductRequestDtos) {
    //OrderProductRequestDto의 상품 번호에 해당하는 주문이 있는지? 해당 수량이 있는지 확인
    //없다먄 -> 예외 발생
    //있다면 -> 해당 프로덕트의 list를 생성

    List<Product> orderProducts = makeOrderProducts(orderProductRequestDtos);
//        orderProductRequestDtos.stream().map(orderProductRequestDto -> {
//            Long productId = orderProductRequestDto.getId();
//        }) //코드가 너무 길어짐


    //해당 상품의 상품 재고를 차감
//    orderProducts.stream()
//            .forEach(product -> {
//
//            }); //-> 코드 길어짐 -> 메서드 화

    decreaseProductsAmount(orderProducts);


    // 차감된 정보를 바탕으로 Order
    Order order = new Order(orderProducts);
    orderRepository.add(order);

    //생성된 주문을 OrderResponseDto 변환해서 컨트롤러에 반환
    OrderProductResponseDto orderProductResponseDto = OrderProductResponseDto.toDto(order);
    return orderProductResponseDto;

  }

  private void decreaseProductsAmount(List<Product> orderProducts) {
    orderProducts.forEach(orderProduct -> {
      Long productId = orderProduct.getId();
      Product product = productRepository.findById(productId);

      Integer orderedAmount = orderProduct.getAmount();
      product.decreaseAmount(orderedAmount);
//      productRepository.update(product); //굳이 필요 없음
//      Integer stockAmount = product.getAmount();
//
//      product.setAmount(stockAmount - orderedAmount);
    });
  }

  private List<Product> makeOrderProducts(List<OrderProductRequestDto> orderProductRequestDtos) {

    return orderProductRequestDtos
            .stream()
            .map(orderProductRequestDto -> {
              Long productId = orderProductRequestDto.getId();
              Product product = productRepository.findById(productId);

              Integer orderedAmount = orderProductRequestDto.getAmount();
              product.checkEnoughAmount(orderedAmount);

              return new Product(
                      productId,
                      product.getName(),
                      product.getPrice(),
                      orderedAmount
              );

//              Integer stockAmount = product.getAmount();
//              if (orderedAmount > stockAmount) {
//                //throw new -> 지저분하게 씀
//              }
            }).toList();
  }

  public OrderProductResponseDto findById(Long orderId) {
    Order order = orderRepository.findById(orderId);
    return OrderProductResponseDto.toDto(order);
  }

  public OrderProductResponseDto changeState(Long orderId, ChangeOrderStateRequestDto changeOrderStateRequestDto) {
    Order order = orderRepository.findById(orderId);
    State state = changeOrderStateRequestDto.getState();

    order.changeStateForce(state); //Force:강제
    return OrderProductResponseDto.toDto(order);
  }

  public List<OrderProductResponseDto> findByState(State state) {
    List<Order> orders = orderRepository.findByState(state);
    return orders.stream()
            .map(order -> OrderProductResponseDto.toDto(order))
            .toList();
  }

  public OrderProductResponseDto cancelById(Long orderId) {
    Order order = orderRepository.findById(orderId);
    order.cancel();
//    orderRepository.update(order); //할 필요 없음. 주소를 공유하기 때문~! +디비 안씀

    return OrderProductResponseDto.toDto(order);
  }
}
