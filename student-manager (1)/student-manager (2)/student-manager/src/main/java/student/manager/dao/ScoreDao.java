package student.manager.dao;

import student.manager.javebean.Score;
import student.manager.utils.BaseDao;

import java.util.List;

public class ScoreDao extends BaseDao {
    public Integer addScore(String id, String courseName, int score) {
        String sql = "insert into score(studentId, courseName, grade) values(?,?,?)";
        try {
            return update(sql, id, courseName, score);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Score> queryAllScores() {
        String sql = "select score.id,score.studentId,student.name studentName,score.grade,score.courseName from score left join student on score.studentId = student.id";
        try {
            return query(Score.class, sql);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Score> queryScoresWithParam(String name, String courseName) {
        String sql = "select score.id,score.studentId,student.name studentName,score.grade,score.courseName from score left join student on score.studentId = student.id where 1 = 1";
        if (name != null && !name.equals("")) {
            sql += " and student.name like '%" + name + "%'";
        }
        if (courseName != null && !courseName.equals("")) {
            sql += " and score.courseName like '%" + courseName + "%'";
        }
        try {
            return query(Score.class, sql);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Integer deleteScoreById(Integer id) {
        String sql = "delete from score where id=?";
        try {
            return update(sql, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Score> queryScoresWithStudentId(String id) {
        String sql = "select * from score where studentId = ?";
        try {
            return query(Score.class, sql, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Integer deleteScoreByStudentId(String id) {
        String sql = "delete from score where studentId = ?";
        try {
            return update(sql, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
