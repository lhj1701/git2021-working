package conversion;

public class PromotionExample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		byte byteValue = 10; // -128~0~127
		// 값의 손실이 없기 때문에 바로 변환 가능
		int intValue = byteValue; // -21억 ~ 0 ~ 21억
		System.out.println(intValue);
	}

}
