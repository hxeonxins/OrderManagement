package kr.co.ordermanagement.domain.order;

import kr.co.ordermanagement.domain.exception.CancelNotAllowedException;

public enum State {
  CREATED{
    @Override
    public void checkCancelability(){}
  },
  CANCELED,
  SHIPPING,
  COMPLETED;

  //Enum에서 메서드는 위 상태들이 모두 공유하는 메서드이다.
  //만약 위 상태 중에서 아래 메서드를 공유 안할꺼면 그상태는 따로 오버라이딩을 해줘야 한다.
  public void checkCancelability() {
    throw new CancelNotAllowedException("이미 취소되었거나 취소할 수 없는 주문상태입니다.");
  }
}
