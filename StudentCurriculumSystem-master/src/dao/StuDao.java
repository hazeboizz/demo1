package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.*;

public class StuDao extends BaseDao {

	public StuDao() {
		// TODO Auto-generated constructor stub
	}

	public StudentBean getStudent(String num) {
		String sql = "SELECT * FROM 学生,班级,均绩 WHERE 学生.学号=? and 班级.班级编号=学生.班级编号 and 学生.学号=均绩.学号\r\n" + 
				"";
		try {
			Connection connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, num);
			ResultSet resultSet = preparedStatement.executeQuery();
			StudentBean studentBean = new StudentBean();
			while (resultSet.next()) {

				studentBean.setNum(resultSet.getString("学号").trim());
				studentBean.setName(resultSet.getString("姓名").trim());
				studentBean.setSex(resultSet.getString("性别").trim());
				studentBean.setAge(resultSet.getInt("年龄"));
				studentBean.setPlace(resultSet.getString("生源所在地").trim());
				studentBean.setCredit(resultSet.getFloat("已修学分总数"));
				studentBean.setClassname(resultSet.getString("班级名称").trim());
				studentBean.setClassnum(resultSet.getString("班级编号").trim());
				studentBean.setGpa(resultSet.getFloat("均绩"));
			}
			connection.close();
			return studentBean;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<CourseBean> getCourse(String num) {
		String sql = "select  学生所学课程及学分统计.课程编号,教师任课查询.姓名,教师任课查询.课程编号,教师任课查询.课程名称,教师任课查询.开课学年,教师任课查询.开课学期,教师任课查询.学时,教师任课查询.考试或考察,教师任课查询.学分,教师任课查询.开课编号 from 学生所学课程及学分统计,教师任课查询\r\n"
				+ " where 学号=? and 教师任课查询.开课编号=学生所学课程及学分统计.开课编号";
		try {
			Connection connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, num);
			ResultSet resultSet = preparedStatement.executeQuery();
			ArrayList<CourseBean> cList = new ArrayList<>();
			while (resultSet.next()) {
				CourseBean courseBean = new CourseBean();
				courseBean.setCoursenum(resultSet.getString("课程编号").trim());
				courseBean.setTmpnum(resultSet.getString("开课编号").trim());
				courseBean.setTeacher(resultSet.getString("姓名").trim());
				courseBean.setCoursenum(resultSet.getString("课程编号").trim());
				courseBean.setCoursename(resultSet.getString("课程名称").trim());
				// courseBean.setTeacher(resultSet.getString("姓名").trim());
				courseBean.setYear(resultSet.getString("开课学年").trim());
				if (resultSet.getBoolean("开课学期")) {
					courseBean.setTerm("上学期");//1 上
				} else {
					courseBean.setTerm("下学期");// 0下
				}

				courseBean.setTime(resultSet.getInt("学时"));
				if (resultSet.getBoolean("考试或考察")) {
					courseBean.setTestway("考查");
				} else {
					courseBean.setTestway("考试");
				}
				courseBean.setCredit(resultSet.getFloat("学分"));
				cList.add(courseBean);

			}
			connection.close();
			return cList;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public ArrayList<CourseBean> getNoSelect(String num) {
		Connection connection;
		try {
			String sql = "exec 查找未选课程 ?,'2019',0";
			connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, num);
			preparedStatement.executeUpdate();
			sql = "SELECT 开课编号,未选课.课程编号,课程名称,开课学年,开课学期,学时,考试或考查,学分,任课教师编号,姓名 FROM 未选课,课程,教师\r\n"
					+ "WHERE 未选课.课程编号=课程.课程编号 AND 教师.教师编号=未选课.任课教师编号";
			preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			ArrayList<CourseBean> cList = new ArrayList<>();
			while (resultSet.next()) {
				CourseBean courseBean = new CourseBean();
				courseBean.setTmpnum(resultSet.getString("开课编号").trim());
				courseBean.setCoursenum(resultSet.getString("课程编号").trim());
				courseBean.setCoursename(resultSet.getString("课程名称").trim());
				courseBean.setTeachernum(resultSet.getString("任课教师编号").trim());
				courseBean.setYear(resultSet.getString("开课学年").trim());
				if (resultSet.getBoolean("开课学期")) {
					courseBean.setTerm("上学期");
				} else {
					courseBean.setTerm("下学期");
				}
				courseBean.setTime(resultSet.getInt("学时"));
				if (resultSet.getBoolean("考试或考查")) {
					courseBean.setTestway("考查");
				} else {
					courseBean.setTestway("考试");
				}
				courseBean.setCredit(resultSet.getFloat("学分"));
				courseBean.setTeacher(resultSet.getString("姓名").trim());
				cList.add(courseBean);
			}
			connection.close();
			return cList;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public boolean chooseCourse(String num, int i) {
		boolean flag = false;
		ArrayList<CourseBean> cList = getNoSelect(num);
		String sql = "insert into 选课情况\r\n" + "values(?,?,null)";
		String sql1 = "select * from 选课情况\r\n" + "WHERE 开课编号=? and 学号=?";
		Connection connection;
		try {
			connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, cList.get(i).getTmpnum());
			preparedStatement.setString(2, num);
			preparedStatement.executeUpdate();
			preparedStatement = connection.prepareStatement(sql1);
			preparedStatement.setString(1, cList.get(i).getTmpnum());
			preparedStatement.setString(2, num);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {flag = true;}
			connection.close();
			return flag;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return flag;
		}
	}

	public boolean Withdrawal(String num, String colnum) {
		ArrayList<CourseBean> cList = getNoSelect(num);
		String sql = "DELETE FROM 选课情况\r\n" + "WHERE 学号=? and 开课编号=?";
		Connection connection;
		try {
			connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, num);
			preparedStatement.setString(2, colnum);
			preparedStatement.executeUpdate();
			connection.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public TeacherBean getTeacher(String num) {
		String sql = "SELECT * FROM 教师\r\n" + "where 教师编号=?";
		Connection connection;
		try {
			connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, num);
			ResultSet resultSet = preparedStatement.executeQuery();
			TeacherBean teacherBean = new TeacherBean();
			while (resultSet.next()) {
				teacherBean.setName(resultSet.getString("姓名").trim());
				teacherBean.setSex(resultSet.getString("性别").trim());
				teacherBean.setAge(resultSet.getInt("年龄"));
				teacherBean.setRank(resultSet.getString("职称").trim());
				teacherBean.setPhone(resultSet.getString("电话").trim());
			}
			connection.close();
			return teacherBean;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<CourseBean> getGrade(String num, String year, String term) {
		String sql = "select distinct 学号,学年,学期,课程名称,成绩,教师.姓名,课程编号 from 学生成绩统计,教师\r\n"
				+ "where 学生成绩统计.教师编号=教师.教师编号 and 学号=? and 学年=? AND 学期 = ? AND 成绩 IS NOT NULL";
		try {
			Connection connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, num);
			preparedStatement.setString(2, year);
			if(term.equals("上学期")) {boolean x = true;preparedStatement.setBoolean(3, x);}
			else {boolean x = false;preparedStatement.setBoolean(3, x);}
			ResultSet resultSet = preparedStatement.executeQuery();
			ArrayList<CourseBean> cList = new ArrayList<>();
			while (resultSet.next()) {
				CourseBean courseBean = new CourseBean();
				courseBean.setCoursenum(resultSet.getString("课程编号").trim());
				courseBean.setCoursename(resultSet.getString("课程名称").trim());
				courseBean.setTeacher(resultSet.getString("姓名").trim());
				courseBean.setYear(resultSet.getString("学年").trim());
				if (resultSet.getBoolean("学期")) {
					courseBean.setTerm("上学期");
				} else {
					courseBean.setTerm("下学期");
				}
				courseBean.setGrade(resultSet.getFloat("成绩"));
				cList.add(courseBean);
			}
			connection.close();
			return cList;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	
	public float getTotalGrade(String num, String year, String term) {
		float sum = 0;
		Connection connection;
		try {
			String sql = "exec 各学生指定学年学期选修课程的总学分数 ?,?";
			connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, year);
			if(term.equals("上学期")) {boolean x = true;preparedStatement.setBoolean(2, x);}
			else {boolean x = false;preparedStatement.setBoolean(2, x);}
			preparedStatement.executeUpdate();
			String sql1 = "select * from 学生某学年某学期学分数 where 学生某学年某学期学分数.学号 = ?";
			preparedStatement = connection.prepareStatement(sql1);
			preparedStatement.setString(1, num);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				sum = resultSet.getFloat("学分");
			}
			connection.close();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sum;

	}
	
	public boolean changePasscode(String passcode,String num) {
		String sql="UPDATE 学生 SET 登录密码=? WHERE 学号=?";
		Connection connection;
		try {
			connection=dataSource.getConnection();
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1,passcode);
			preparedStatement.setString(2,num);
			preparedStatement.executeUpdate();
			connection.close();
			return true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	
	public ArrayList<CourseBean> getStunochoose(String num) {
		Connection connection;
		try {
			connection = dataSource.getConnection();
			String sql = "select * from 学年未被选修的课程2020_2021";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			ArrayList<CourseBean> cList = new ArrayList<>();
			while (resultSet.next()) {
				CourseBean courseBean = new CourseBean();
				courseBean.setCoursenum(resultSet.getString("课程编号").trim());
				courseBean.setCoursename(resultSet.getString("课程名称").trim());
				courseBean.setTime(resultSet.getInt("学时"));
				if (resultSet.getBoolean("考试或考查")) {
						courseBean.setTestway("考查");
				}
				else {
						courseBean.setTestway("考试");
				}
				courseBean.setCredit(resultSet.getFloat("学分"));
				cList.add(courseBean);
			}
			connection.close();
			return cList;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
}
