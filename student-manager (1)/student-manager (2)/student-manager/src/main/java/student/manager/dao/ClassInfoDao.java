package student.manager.dao;

import student.manager.javebean.ClassInfo;
import student.manager.utils.BaseDao;

import java.util.List;

public class ClassInfoDao extends BaseDao {
    public List<ClassInfo> queryAllClassInfo() {
        String sql = "select * from classInfo";
        try {
            return query(ClassInfo.class, sql);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Integer addClassInfo(ClassInfo classInfo) {
        String sql = "insert into classInfo(className, major, startTime) values(?,?,?)";
        try {
            return update(sql, classInfo.getClassName(), classInfo.getMajor(), classInfo.getStartTime());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
