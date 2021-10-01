package array;

public class CreateByNewExample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// new 키워드를 이용하여 생성

		int[] arrInt = new int[3]; // 크기가 3인 int 배열 생성
		System.out.println(arrInt[0]); // 0초기화 되어있음
		System.out.println(arrInt[1]); // 0초기화 되어있음
		System.out.println(arrInt[2]); // 0초기화 되어있음

		for (int i = 0; i < 3; i++) {
			System.out.println(arrInt[i]);
		}

		String[] arrStr = new String[3]; // 크기가 3인 String 배열 생성 String은 참조기 때문에 기본값이 null
		System.out.println(arrInt[0]); // null 초기화 되어있음
		System.out.println(arrInt[1]); // null 초기화 되어있음
		System.out.println(arrInt[2]); // null 초기화 되어있음

		// for-(enhanced(advanced) for loop)
		// for (요소변수타입 요소변수 : 배열변수명) {
		// }
		for (String str : arrStr) {
			str = "test";
			System.out.println(str);
		}
	}

}
