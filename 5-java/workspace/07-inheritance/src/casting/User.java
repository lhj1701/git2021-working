package casting;

// 사용자
public class User {
	private String id;
	private String name;
	private String phone;

	// final 메서드 : 재정의 불가능한 메소드
//	public final void printUserInfo() {
	public void printUserInfo() {
		System.out.println("\n" + name + ", " + phone);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
