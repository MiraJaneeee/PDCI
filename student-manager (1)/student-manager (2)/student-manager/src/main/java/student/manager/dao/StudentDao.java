package student.manager.dao;

import student.manager.javebean.Student;
import student.manager.javebean.StudentVo;
import student.manager.utils.BaseDao;

import java.sql.SQLException;
import java.util.List;

public class StudentDao extends BaseDao {
    public List<Student> queryAllStudents() {
        String sql = "select * from student";
        try {
            return query(Student.class, sql);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Student queryStudentById(String id) {
        String sql = "select * from student where id=?";
        try {
            return queryBean(Student.class, sql, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Integer addStudent(Student student) {
        String sql = "insert into student(id, name, sex, hometown, birthday, classId) VALUES (?,?,?,?,?,?)";
        try {
            return update(sql, student.getId(), student.getName(), student.getSex(), student.getHometown(), student.getBirthday(), student.getClassId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<StudentVo> queryAllStudentVos() {

        String sql = "select student.id,student.name,student.birthday,student.hometown,student.sex,student.classId,classInfo.className from student left join classInfo on student.classId = classInfo.classId";
        try {
            return query(StudentVo.class, sql);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Integer deleteStudentById(String id) {
        String sql = "delete from student where id=?";
        try {
            return update(sql, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Integer updateStudent(String id, String name, String birthday, String hometown, String sex, Integer classId) {
        String sql = "update student set name = ?,birthday = ?,hometown = ?,sex = ?,classId = ? where id = ?";
        try {
            return update(sql, name, birthday, hometown, sex, classId, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<StudentVo> queryStudentsWithParam(String name, String id, Integer classId) {
        String sql = "select student.id,student.name,student.birthday,student.hometown,student.sex,student.classId,classInfo.className from student left join classInfo on student.classId = classInfo.classId where 1=1";
        if (name != null && !name.equals("")) {
            sql += " and student.name like '%" + name + "%'";
        }
        if (id != null && !id.equals("")) {
            sql += " and student.id =" + id;
        }
        if (classId != null) {
            sql += " and student.classId =" + classId;
        }
        try {
            return query(StudentVo.class, sql);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
