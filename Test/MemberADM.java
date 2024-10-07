package Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MemberADM {
	// ojdb6로 개발하는 순서
	// 1. 드라이버로드는 초기 1회
	// 2. CRUD작업이 있을 때 마다 다음 과정을 생각한다.
	// 2-1. 커넥션 가져오기
	// 자바에서 오라클에 접속하려면 커넥션을 가져와야한다. 커넥션 자원은 Oracle이 준다. 원할때마다 주면 성능이 낮아짐.
	// 오라클에서 작업하기 전에 커넥션 자원을 획득해야한다.
	// 커넥션 자원은 한정적이기에 쓰고나면 반납하는 것이 좋다. 2-7
	// 오라클은 커넥션 자원을 유한으로 만들어 놓고 공유해서 사용하도록 한다.
	// 2-2. 쿼리문 만들기
	// 2-3. 쿼리문 완성하기(Mapping)
	// 2-4. 쿼리문 전송하여 오라클에서 실행
	// 2-5. 오라클에서 리턴값 전송
	// 2-6. 2-5에서 받은 리턴값 자바에서 처리
	// 2-7. 커넥션 자원 반납(중요)
	// idle type(자원을 쓰지 않는 상태)가 길어지면 성능 저하로 이어지므로 꼭 반납해야 한다.
	// 가져오고 반납 안하면 독점현상. 누군가가 사용할 수 없다.

	MemberADM() {
		init(); // 드라이버 로드는 한 번하면 되므로 생성자에서 작업
		insert();
	}

	private void init() { // 오라클 드라이버 로드 코딩
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("오라클 드라이버 로드 성공");
			// 드라이버 로드가 제대로 됐다면(빌드가 제대로 됐다면), 오라클 드라이버 로드 성공 메세지 출력
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void insert() {
		// TODO Auto-generated method stub
		MemberDTO m = new MemberDTO();
		m.setId("a");
		m.setName("Lee");
		m.setAge(32);
		// DTO 객체를 만들었음(JVM에 저장된 상태) >> 오라클에 저장해보자
		// 2-1. 커넥션 자원 가져오기
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "11111111");
//																						             아이디, 비밀번호
			System.out.println("커넥션 자원 획득 성공");
			// 2-2. 쿼리문 만들기
			String sql = "insert into memberone values(?,?,?,default)";
			// 쿼리문을 커넥션을 통해서 연결해라
			PreparedStatement pstmt = conn.prepareStatement(sql);
			// 2-3. 쿼리문 완성하기(Mapping: ?에 해당하는 것을 정하자. ?정보는 DTO가 준다.)
			pstmt.setString(1, m.getId());
			pstmt.setString(2, m.getName());
			pstmt.setInt(3, m.getAge());
			// 실행 후 리턴 값 가져오기
			int result = pstmt.executeUpdate();
			if (result == 0) {
				conn.rollback();
				// 쿼리문 취소시켜라
			} else {
				conn.commit();
				// 커밋
			}

		} catch (SQLException e) {

		} finally {
			if (conn != null) {
				try {
					conn.close(); // 자원 반납
				} catch (Exception e2) {
					// TODO: handle exception
				}

			}
		}
	}

}
