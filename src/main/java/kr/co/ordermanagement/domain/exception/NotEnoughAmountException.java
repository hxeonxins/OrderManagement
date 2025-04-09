package kr.co.ordermanagement.domain.exception;

public class NotEnoughAmountException extends RuntimeException {
  //super() -> 부모 클래스 생성자를 호출 함
  public NotEnoughAmountException(String message) {
    super(message);
  }
}