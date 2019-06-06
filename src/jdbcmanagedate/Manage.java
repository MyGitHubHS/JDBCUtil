package jdbcmanagedate;

import org.omg.CORBA.PUBLIC_MEMBER;

import java.sql.*;

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

    public void insert(int id,String username,String userpwd){

        connection=this.getConnection();
        String sql="insert into user value(?,?,?)";
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            preparedStatement.setString(2,username);
            preparedStatement.setString(3,userpwd);
            count=preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            prompt(count);
            close(connection,preparedStatement,resultSet);
        }
    }

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

    public void update(String sql ,int id ,String userpwd){
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,userpwd);
            preparedStatement.setInt(2,id);
            count=preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(connection,preparedStatement,resultSet);
            prompt(count);
        }
    }
    public void updatePwdById(int id ,String userpwd){
        connection=this.getConnection();
        String sql="update user set password=? where id=?";
       update(sql,id,userpwd);
        close(connection,preparedStatement,resultSet);
    }

    public void updateNameById(int id,String username){
        connection=this.getConnection();
        String sql="update user set username=? where id=?";
        update(sql,id,username);
        close(connection,preparedStatement,resultSet);
    }

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

    public void selectById(){
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


