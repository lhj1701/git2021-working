package singleton;

//static  멤버만 있음
//static 필드, 메서드

//객체로 찍어내는 클래스가 아님! 필드값, 메서드를 기능적인 관점에서 실행하는 클래스
// singleton 클래스로 만들어 외부에서 객체 생성을 못하게 함
// 객체접근 메서드를 이용해서 사전에 생성된 객체만 접근해서 사용하게 함
// -> 사전에 객체 생성은 private static 클래스타입 객체명 = new 생성자;
public class Calculator {

	// 2.private static 필드로 객체를 생성함 <- 프로그램이 실행될 때 변수 초기화가 일어남
	// Calculator를 사용하지 않더라도 메모리 공간에 초기화됨
//	private static Calculator calc = new Calculator();
	private static Calculator calc;
	private final static double PI = 3.141592;

	// 1.기본 생성자를 외부에서 접근 못하게 함
	private Calculator() {
	}

	// 3. 외부에서 private static으로 생성한 객체를 접근할 수 있게 함
	public static Calculator getInstance() {
		// Calculator 객체를 사용할 시점에 메모리 공간을 초기화함
		if (calc == null) {
			calc = new Calculator();
		}
		return calc;
	}

	public int plus(int a, int b) {
		return a + b;
	}

	public int minus(int a, int b) {
		return a - b;
	}

	public double getAreaCircle(int r) {
		return r * r * this.PI;
	}
	// 싱글턴 : 프로그램 내부에 유일한 객체를 가지기 위한 객체 생성 패턴
}
