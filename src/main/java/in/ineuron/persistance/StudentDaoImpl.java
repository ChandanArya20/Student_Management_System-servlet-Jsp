package in.ineuron.persistance;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import in.ineuron.dto.Student;
import in.ineuron.utils.JdbcUtils;

public class StudentDaoImpl implements IStudentDao {

	Connection connection=null;
	PreparedStatement stm=null;
	ResultSet resultSet=null;

	@Override
	public String addStudent(Student student) {

		String query="insert into student(sname ,sage,saddress) values(?,?,?)";
		try {
			connection=JdbcUtils.getJdbcConnection();

			if(connection!=null)
				stm=connection.prepareStatement(query);

			if(stm!=null) {
				stm.setString(1,student.getSname());
				stm.setInt(2,student.getSage());
				stm.setString(3,student.getSaddress());

				int flag=stm.executeUpdate();

				if(flag==1)
					return "success";
			}

		} catch (SQLException | IOException e) {
			return "failure";
		}
		return "failure";
	}

	@Override
	public Student searchStudent(Integer sid) {

		String query="select * from student where sid=?";
		Student stud=null;
		try {
			connection=JdbcUtils.getJdbcConnection();
			if(connection!=null)
				stm=connection.prepareStatement(query);

			if(stm!=null) {
				System.out.println("Sid : "+sid);
				stm.setInt(1, sid);
				resultSet=stm.executeQuery();
			}
			if(resultSet.next()) {
				System.out.println("RAm");
				stud=new Student();
				stud.setSid(sid);
				stud.setSname(resultSet.getString(2));
				stud.setSage(resultSet.getInt(3));
				stud.setSaddress(resultSet.getString(4));
				System.out.println("In serach method : "+stud);
				return stud;
			}

		} catch (SQLException | IOException e) {
			return stud;
		}
		return stud;
	}

	@Override
	public String updateStudent(Student student) {

		String query ="update student set sname=?, sage=?, saddress=? where sid=?";
		try {
			connection=JdbcUtils.getJdbcConnection();
			if(connection!=null)
				stm=connection.prepareStatement(query);

			if(stm!=null) {
				stm.setString(1, student.getSname());
				stm.setInt(2, student.getSage());
				stm.setString(3, student.getSaddress());
				stm.setInt(4, student.getSid());

				int rowCount=stm.executeUpdate();

				if(rowCount==1)
					return "success";
				else
					return "not found";
			}

		} catch (IOException | SQLException e) {
			return "failure";
		}
		return "failure";
	}

	@Override
	public String deleteStudent(Integer sid) {

		String query ="delete from student where sid=?";
		try {
			connection=JdbcUtils.getJdbcConnection();
			if(connection!=null)
				stm=connection.prepareStatement(query);

			if(stm!=null) {
				stm.setInt(1, sid);
				int rowCount=stm.executeUpdate();

				if(rowCount==1)
					return "success";
				else
					return "not found";
			}

		} catch (IOException | SQLException e) {
			return "failure";
		}
		return "failure";
	}

}
