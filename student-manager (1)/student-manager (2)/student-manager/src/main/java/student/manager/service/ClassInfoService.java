package student.manager.service;

import student.manager.dao.ClassInfoDao;
import student.manager.javebean.ClassInfo;

import java.util.ArrayList;
import java.util.List;

public class ClassInfoService {
    private static  ClassInfoService classInfoService = null;

    public static ClassInfoService getInstance() {
        if (classInfoService == null) {
            synchronized (ClassInfoService.class) {
                if (classInfoService == null) {
                    classInfoService = new ClassInfoService();
                }
            }
        }
        return classInfoService;
    }
    private ClassInfoDao classInfoDao = new ClassInfoDao();

    public List<ClassInfo> queryAllClassInfo() {
        return classInfoDao.queryAllClassInfo();
    }

    public Integer addClassInfo(ClassInfo classInfo) {
         return classInfoDao.addClassInfo(classInfo);
    }
}
