package kr.co.ordermanagement.domain.order;

import kr.co.ordermanagement.domain.product.Product;

import java.util.List;

import static kr.co.ordermanagement.domain.order.State.CREATED;

public class Order {
  private Long id;
  private List<Product> orderProducts;
  private Integer totalPrice;
  private State state;

  public Order(List<Product> orderProducts) {
    this.orderProducts = orderProducts;
    this.totalPrice = calculateTotalPrice(orderProducts);
    this.state = CREATED;
  }

  //Getter, Setter 남발 하면 안됨

  private static int calculateTotalPrice(List<Product> orderProducts) {
    return orderProducts
            .stream()
            .mapToInt(orderProduct -> orderProduct.getPrice()*orderProduct.getAmount())
            .sum();
  }

  public void setId(long andAdd) {
    this.id = andAdd;
  }

  public List<Product> getOrderedProducts() {
    return this.orderProducts;
  }

  public Long getId() {
    return this.id;
  }

  public Integer getTotalPrice() {
    return this.totalPrice;
  }

  public State getState() {
    return this.state;
  }

  public boolean sameId(Long orderId) {
    return this.id.equals(orderId);
  }

  public void changeStateForce(State state) {
    this.state = state;
  }

  public boolean sameState(State state) {
    return this.state.equals(state);
  }

  public void cancel() {
//    if(!State.CREATED.equals(this.state)) { //if 문 자제하는게 좋음..
//      throw new CancelNotAllowedException("이미 취소되었거나 취소할 수 없는 주문상태입니다.");
//    }
    this.state.checkCancelability(); //Enum에 따로 오버라이딩 시킴
    this.state = State.CANCELED;
  }
}
