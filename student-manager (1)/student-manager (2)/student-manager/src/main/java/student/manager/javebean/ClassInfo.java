package student.manager.javebean;

public class ClassInfo {
    // 班级名称、专业、班级开始时间
    private Integer classId;
    private String className;
    private String major;
    private String startTime;

    public ClassInfo() {
    }

    public ClassInfo(Integer classId, String className, String major, String startTime) {
        this.classId = classId;
        this.className = className;
        this.major = major;
        this.startTime = startTime;
    }

    public ClassInfo(String className, String major, String startTime) {
        this.className = className;
        this.major = major;
        this.startTime = startTime;
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

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return className;
    }


}
