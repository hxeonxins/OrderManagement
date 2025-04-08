package kr.co.ordermanagement.domain.order;

import kr.co.ordermanagement.domain.product.Product;
import kr.co.ordermanagement.presentation.dto.ProductDto;

import java.util.List;

public class Order {
  private Long id;
  private List<Product> orderProducts;
  private Integer totalPrice;
  private String state;

  public Order(List<Product> orderProducts) {
    this.orderProducts = orderProducts;
    this.totalPrice = calculateTotalPrice(orderProducts);
    this.state = "CREATED";
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

  public String getState() {
    return this.state;
  }

  public boolean sameId(Long orderId) {
    return this.id.equals(orderId);
  }

  public void changeStateForce(String state) {
    this.state = state;
  }

  public boolean sameState(String state) {
    return this.state.equals(state);
  }

  public void cancel() {
    this.state = "CANCELED";
  }
}
