package Test;

public class MemberDTO {
	private String id = null;
	private String name = null;
	private int age = 0;
	private String indate = null;

	// oracle 테이블 튜플에 데이터 넣으려고 DTO 만들었다. 현재 객체 만든 상태 아니다.
	// 은닉화 시키고, getter setter 만들기 >> DTO 정석
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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getIndate() {
		return indate;
	}

	public void setIndate(String indate) {
		this.indate = indate;
	}

}
