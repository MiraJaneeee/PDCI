package student.manager.utils;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;

public abstract class BaseDao {

    protected int update(String sql,Object... args) throws SQLException {
        Connection connection = JDBCTools.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql);
        if(args != null && args.length>0){
            for(int i=0; i<args.length; i++) {
                ps.setObject(i+1, args[i]);
            }
        }

        //执行sql
        int len = ps.executeUpdate();
        ps.close();
        if (connection.getAutoCommit()) {
            //回收
            JDBCTools.free();
        }
        return len;
    }

    /*
    通用的查询多个Javabean对象的方法，
     */
    protected <T> ArrayList<T> query(Class<T> clazz,String sql, Object... args) throws Exception {
        //        创建PreparedStatement对象，对sql预编译
        Connection connection = JDBCTools.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql);
        //设置?的值
        if(args != null && args.length>0){
            for(int i=0; i<args.length; i++) {
                ps.setObject(i+1, args[i]);
            }
        }

        ArrayList<T> list = new ArrayList<>();
        ResultSet res = ps.executeQuery();

        /*
        获取结果集的元数据对象。
         */
         ResultSetMetaData metaData = res.getMetaData();
        int columnCount = metaData.getColumnCount();//获取结果集列数

        //遍历结果集ResultSet，把查询结果中的一条一条记录，变成一个一个T 对象，放到list中。
        while(res.next()){
            //循环一次代表有一行，代表有一个T对象
            T t = clazz.newInstance();//要求这个类型必须有公共的无参构造

            //把这条记录的每一个单元格的值取出来，设置到t对象对应的属性中。
            for(int i=1; i<=columnCount; i++){
                //for循环一次，代表取某一行的1个单元格的值
                Object value = res.getObject(i);

                String columnName = metaData.getColumnLabel(i);//获取第i列的字段名或字段的别名
                Field field = clazz.getDeclaredField(columnName);
                field.setAccessible(true);

                field.set(t, value);
            }

            list.add(t);
        }

        res.close();
        ps.close();
        //这里检查下是否开启事务,开启不关闭连接,业务方法关闭!
        //没有开启事务的话,直接回收关闭即可!
        if (connection.getAutoCommit()) {
            //回收
            JDBCTools.free();
        }
        return list;
    }

    protected <T> T queryBean(Class<T> clazz,String sql, Object... args) throws Exception {
        ArrayList<T> list = query(clazz, sql, args);
        if(list == null || list.size() == 0){
            return null;
        }
        return list.get(0);
    }
}