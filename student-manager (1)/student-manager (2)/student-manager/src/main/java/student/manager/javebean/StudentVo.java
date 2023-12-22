package student.manager.javebean;

public class StudentVo {
    // 姓名、学号、性别、籍贯、出生年月、所在班级
    private String id;
    private String name;
    private String sex;
    private String hometown;
    private String birthday;
    private Integer classId;
    private String className;

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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public StudentVo(String id, String name, String sex, String hometown, String birthday, Integer classId, String className) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.hometown = hometown;
        this.birthday = birthday;
        this.classId = classId;
        this.className = className;
    }

    public StudentVo() {
    }
}
