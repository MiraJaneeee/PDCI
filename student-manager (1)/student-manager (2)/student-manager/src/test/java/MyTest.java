import org.junit.jupiter.api.Test;
import student.manager.javebean.ClassInfo;
import student.manager.javebean.Student;
import student.manager.service.ClassInfoService;
import student.manager.service.ScoreService;
import student.manager.service.StudentService;

import java.util.List;

public class MyTest {
    private ClassInfoService classInfoService = new ClassInfoService();
    private StudentService studentService = new StudentService();
    private ScoreService scoreService = new ScoreService();

    // 能否正常添加班级信息
    @Test
    public void addClassInfo() {
        Integer rows = classInfoService.addClassInfo(new ClassInfo("软件工程", "大数据", "202009"));
        if (rows > 0) {
            System.out.println("successfully added");
        } else {
            System.out.println("addition failed");
        }
    }

    // 能否正常添加学生信息
    @Test
    public void addStudent() {
        // 查询班级信息
        List<ClassInfo> classInfos = classInfoService.queryAllClassInfo();
        ClassInfo classInfo = classInfos.get(classInfos.size() - 1);
            Integer row = studentService.addStudent(new Student("Zhang San", "2019210009", "male", "Henan", "200001", classInfo.getClassId()));
        if (row > 0) {
            System.out.println("successfully added");
        } else {
            System.out.println("addition failed");
        }
    }

    // 能否正常查询所有班级信息
    @Test
    public void queryAllClassInfo() {
        classInfoService.queryAllClassInfo().forEach(System.out::println);
    }

    // 能否正常查询所有学生信息
    @Test
    public void queryAllStudents() {
        studentService.queryAllStudents().forEach(System.out::println);
    }

    // 能否正常查询指定学生信息
    @Test
    public void queryStudentById() {
        System.out.println(studentService.queryStudentById("2019210009"));
    }

    // 2019210009 的姓名是否为张三
    @Test
    public void queryStudentById2() {
        System.out.println(studentService.queryStudentById("2019210009").getName().equals("Zhangsan"));
    }

    // 能否为学生添加成绩
    @Test
    public void addScore() {
        Integer row = scoreService.addScore("2019210009", "JAVA", 100);
        if (row > 0) {
            System.out.println("successfully added");
        } else {
            System.out.println("addition failed");
        }
    }

    // 能否删除学生 2019210009
    @Test
    public void deleteStudentById() {
        Integer row = studentService.deleteStudentById("2019210009");
        if (row > 0) {
            System.out.println("successfully delete");
            // 删除对应的成绩
            scoreService.deleteScoreByStudentId("2019210009");
        } else {
            System.out.println("fail to delete");
        }
    }

}
