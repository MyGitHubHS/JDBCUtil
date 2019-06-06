package jdbcmanagedate;

import org.omg.CORBA.PUBLIC_MEMBER;

import java.sql.*;

/**
 * 对数据库的一些简单操作
 */
public class Manage {
    private String url="jdbc:mysql://localhost:3306/web01?serverTimezone=UTC";
    private String  user="root";
    private String pwd="root";
    int count=0;
    private Connection connection=null;
    private PreparedStatement preparedStatement=null;
    private ResultSet resultSet=null;
    public Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, pwd);
            return connection;
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (SQLException E){
            E.printStackTrace();
        }
        return null;
    }
    public void close( Connection connection,PreparedStatement preparedStatement,ResultSet resultSet){
        try {
            if(connection!=null){
                connection.close();
            }
            if (preparedStatement!=null){
                preparedStatement.close();
            }
            if (resultSet!=null){
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void prompt(int count){
        if (count!=0){
            System.out.println("操作成功！");
        }else {
            System.out.println("操作失败！");
        }
    }

    /**
     * 传入对应数据进行添加
     * @param id
     * @param userName
     * @param userPwd
     */
    public void insert(int id,String userName,String userPwd){

        connection=this.getConnection();
        String sql="insert into user value(?,?,?)";
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            preparedStatement.setString(2,userName);
            preparedStatement.setString(3,userPwd);
            count=preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            prompt(count);
            close(connection,preparedStatement,resultSet);
        }
    }

    /**
     * 通过传入的id值进行删除操作
     * @param id
     */
    public void deleteById(int id){
        connection=this.getConnection();
        String sql="delete from user where id=?";
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            count=preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection,preparedStatement,resultSet);
            prompt(count);
        }
    }

    public void update(String sql ,int id ,String userPwd){
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,userPwd);
            preparedStatement.setInt(2,id);
            count=preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(connection,preparedStatement,resultSet);
            prompt(count);
        }
    }

    /**
     * 修改对应id的密码
     * @param id
     * @param userPwd
     */
    public void updatePwdById(int id ,String userPwd){
        connection=this.getConnection();
        String sql="update user set password=? where id=?";
       update(sql,id,userPwd);
        close(connection,preparedStatement,resultSet);
    }


    /**
     * 修改对应id的用户名
     * @param id
     * @param username
     */
    public void updateNameById(int id,String userName){
        connection=this.getConnection();
        String sql="update user set username=? where id=?";
        update(sql,id,userName);
        close(connection,preparedStatement,resultSet);
    }

    /**
     * 查询对应id的信息并输出
     * @param id
     */
    public void selectById(int id){
        connection=this.getConnection();

         String  sql="select*from user where id=?";

        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            resultSet=preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet.next()){
                    System.out.println("该用户的信息为：  "+"id："+resultSet.getInt(1)+" ，名字："
                            +resultSet.getString(2)+"，密码："+resultSet.getString(3));
                }else {
                    System.out.println("ID不存在，请输入正确ID");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            close(connection,preparedStatement,resultSet);
        }
    }

    /**
     * 查询该表里所有的数据
     */
    public void select(){
        connection=this.getConnection();
        String sql="select*from user";
        try {
            preparedStatement=connection.prepareStatement(sql);
            resultSet=preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            while (resultSet.next()){
                System.out.println("id："+resultSet.getInt(1)+" ，名字："
                        +resultSet.getString(2)+"，密码："+resultSet.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    }


