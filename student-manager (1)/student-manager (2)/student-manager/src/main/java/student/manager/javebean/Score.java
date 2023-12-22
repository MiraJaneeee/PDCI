package student.manager.javebean;

public class Score {
    private Integer id;
    private String studentId;

    private String studentName;
    private String courseName;
    private Integer grade;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Score(String studentId, String courseName, Integer grade) {
        this.studentId = studentId;
        this.courseName = courseName;
        this.grade = grade;
    }

    public Score() {
    }

    public Score(Integer id, String studentId, String courseName, Integer grade) {
        this.id = id;
        this.studentId = studentId;
        this.courseName = courseName;
        this.grade = grade;
    }
}
