package student.manager.service;

import student.manager.dao.StudentDao;
import student.manager.javebean.Student;
import student.manager.javebean.StudentVo;

import java.util.List;

public class StudentService {
    private static  StudentService studentService = null;

    public static StudentService getInstance() {
        if (studentService == null) {
            synchronized (StudentService.class) {
                if (studentService == null) {
                    studentService = new StudentService();
                }
            }
        }
        return studentService;
    }
    private StudentDao studentDao = new StudentDao();

    public List<Student> queryAllStudents() {
        return studentDao.queryAllStudents();
    }

    public Student queryStudentById(String id) {
        return studentDao.queryStudentById(id);
    }

    public Integer addStudent(Student student) {
        return studentDao.addStudent(student);
    }

    public List<StudentVo> queryAllStudentVos() {

        return studentDao.queryAllStudentVos();
    }

    public Integer deleteStudentById(String id) {
        return studentDao.deleteStudentById(id);
    }

    public Integer updateStudent(String id, String name, String birthday, String hometown, String sex, Integer classId) {
        return studentDao.updateStudent(id, name, birthday, hometown, sex, classId);

    }

    public List<StudentVo> queryStudentsWithParam(String name, String id, Integer classId) {
        return studentDao.queryStudentsWithParam(name, id, classId);
    }
}
