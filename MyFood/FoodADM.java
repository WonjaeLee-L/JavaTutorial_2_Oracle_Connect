package MyFood;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class FoodADM {
	ArrayList<String> foodlist = new ArrayList<>();

	FoodADM() {
		init();
		main();
	}

	private void main() {
		while (true) {
			Scanner in = new Scanner(System.in);
			System.out.println("내가 좋아하는 음식");
			System.out.println("1. 음식 추가하기");
			System.out.println("2. 음식 삭제하기");
			System.out.println("3. 음식 수정하기");
			System.out.println("4. 음식 전체보기");
			System.out.println("0. 종료");
			System.out.print(" >> 선택 ");
			int selNum = in.nextInt();
			if (selNum == 1) {
				insert();
			} else if (selNum == 2) {
				del();
			} else if (selNum == 3) {
				mod();
			} else if (selNum == 4) {
				list();
			} else if (selNum == 0) {
				break;
			}
		}
	}

	private void list() {
		System.out.println("과일 리스트");
		Connection n = null;
		try {
			n = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "11111111");
			String sql = "select * from food";
			Statement stmt = n.createStatement();
			ResultSet select = stmt.executeQuery(sql);

			while (select.next()) {
				System.out.println("과일 이름 : " + select.getString(1));
			}

		} catch (Exception e) {

		} finally {
			if (n != null) {
				try {
					n.close();

				} catch (Exception e2) {

				}
			}
		}

	}

	private void mod() {
		FoodDTO fooddto = new FoodDTO();
		Scanner in = new Scanner(System.in);
		System.out.print("수정할 과일 이름 입력 >> ");
		String modName = in.nextLine();
		System.out.print("새로운 과일 이름 입력 >> ");
		String foodName = in.nextLine();
		fooddto.setName(foodName);

		Connection n = null;
		try {
			n = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "11111111");
//			System.out.println("커넥션 자원 획득");
			String sql = "update food set name = ? where name = ?";
			PreparedStatement food = n.prepareStatement(sql);
			food.setString(1, fooddto.getName());
			food.setString(2, modName);
			int result = food.executeUpdate();
			if (result == 0) {
				n.rollback();
			} else {
				n.commit();
			}

		} catch (Exception e) {

		} finally {
			if (n != null) {
				try {
					n.close();
				} catch (Exception e2) {

				}
			}
		}

	}

	private void del() {
		FoodDTO fooddto = new FoodDTO();
		Scanner in = new Scanner(System.in);
		System.out.print("삭제할 과일 이름 입력 >> ");
		String foodName = in.nextLine();
		fooddto.setName(foodName);

		Connection n = null;
		try {
			n = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "11111111");
//			System.out.println("커넥션 자원 획득");
			String sql = "delete from food where name=?";
			PreparedStatement food = n.prepareStatement(sql);
			food.setString(1, fooddto.getName());
			int result = food.executeUpdate();
			if (result == 0) {
				n.rollback();
			} else {
				n.commit();
			}

		} catch (Exception e) {

		} finally {
			if (n != null) {
				try {
					n.close();
				} catch (Exception e2) {

				}
			}
		}

	}

	private void insert() {
		FoodDTO fooddto = new FoodDTO();
		Scanner in = new Scanner(System.in);
		System.out.print("추가할 과일 이름 입력 >> ");
		String foodName = in.nextLine();
		fooddto.setName(foodName);

		Connection n = null;
		try {
			n = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "11111111");
//			System.out.println("커넥션 자원 획득");
			String sql = "insert into food values(?)";
			PreparedStatement food = n.prepareStatement(sql);
			food.setString(1, fooddto.getName());
			int result = food.executeUpdate();
			if (result == 0) {
				n.rollback();
			} else {
				n.commit();
			}

		} catch (Exception e) {

		} finally {
			if (n != null) {
				try {
					n.close();
				} catch (Exception e2) {

				}
			}
		}
	}

	private void init() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("오라클 드라이버 로드 성공");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

}
