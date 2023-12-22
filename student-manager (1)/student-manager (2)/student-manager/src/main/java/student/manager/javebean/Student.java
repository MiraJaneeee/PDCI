package student.manager.javebean;

public class Student {
    // 姓名、学号、性别、籍贯、出生年月、所在班级
    private String id;
    private String name;
    private String sex;
    private String hometown;
    private String birthday;
    private Integer classId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", sex='" + sex + '\'' +
                ", hometown='" + hometown + '\'' +
                ", birthday='" + birthday + '\'' +
                ", classId='" + classId + '\'' +
                '}';
    }

    public Student() {
    }

    public Student(String name, String id, String sex, String hometown, String birthday, Integer classId) {
        this.name = name;
        this.id = id;
        this.sex = sex;
        this.hometown = hometown;
        this.birthday = birthday;
        this.classId = classId;
    }

}
