package kr.co.ordermanagement.infrastructure;

import kr.co.ordermanagement.domain.exception.EntityNotFoundException;
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

  @Override
  public Order findById(Long orderId) {
    return orders.stream()
            //걸러내야 하기 때문에 filter 쓰고
            //filter 안에는 람다식을 적는다.
            //stream 체이닝 메서드 안에는 그냥 람다식을 적는다고 생각하면 됨
            //자바 빈 규약에 따라 도메인(order)의 get, set 메서드를 가급적 드러내지 않는 것이 좋다..
            .filter(order -> order.sameId(orderId))//캡슐화(Encapsulation), 도메인 메서드 처리, tell not ask 처리
//            .filter(order -> order.getId() == orderId)
            .findFirst()
            .orElseThrow(() -> new EntityNotFoundException("Order를 찾지 못했습니다."));
  }
}
