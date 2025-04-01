package kr.co.ordermanagement.infrastructure;

import kr.co.ordermanagement.domain.order.Order;
import kr.co.ordermanagement.domain.order.OrderRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ListOrderRepository implements OrderRepository {

  private AtomicLong sequence = new AtomicLong(1L);

  //JPA를 쓰지 않고 컬렉션 프레임웤에 저장~!
  List<Order> orders = new CopyOnWriteArrayList<>();

  @Override
  public Order add(Order order) {

    order.setId(sequence.getAndAdd(1L)); //DB가 없음.. 아니 JPA도 안쓰면 이게 수업이냐???

    orders.add(order);
    return order;
  }
}
