package com.dian.Emoney.Backend.Mapper;


import com.dian.Emoney.Backend.Model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {
    String addUser = "INSERT INTO euser (eusername, password,email) VALUES(#{username},#{password}, #{email});";
    String login = "SELECT*FROM euser WHERE eusername=#{username} AND password=#{password}";
    String updateStatus = "UPDATE euser SET logstatus=#{logstatus} WHERE eusername=#{username};";
    String logout = "SELECT*FROM euser WHERE eusername=#{username}";
    String topup = "SELECT*FROM euser WHERE eusid=#{id_user} AND eusername=#{username} AND logstatus=1";
    String updateBalance = "UPDATE euser SET balance=#{balance} WHERE eusid=#{id_user};";
    String confirm = "SELECT*FROM euser WHERE eusername=#{username} AND logstatus=1";
    String findById = "SELECT*FROM euser WHERE eusid=#{id_user}";


    @Insert(addUser)
    int addUser(User user);

    @Select(login)
    @Results({
            @Result(column = "euserid", property = "id_user"),
            @Result(column = "eusername", property = "username"),
            @Result(column = "password", property = "password"),
            @Result(column = "balance", property = "balance"),
            @Result(column = "logstatus", property = "logstatus")
    })
    List<User> login(User user);

    @Update(updateStatus)
    int updateStatus(User user);

    @Select(logout)
    @Results({
            @Result(column = "euserid", property = "id_user"),
            @Result(column = "eusername", property = "username"),
            @Result(column = "password", property = "password"),
            @Result(column = "balance", property = "balance"),
            @Result(column = "logstatus", property = "logstatus")
    })
    List<User> logout(User user);

    @Select(topup)
    @Results({
            @Result(column = "eusid", property = "id_user"),
            @Result(column = "username", property = "username"),
            @Result(column = "password", property = "password"),
            @Result(column = "status", property = "status"),
            @Result(column = "balance", property = "balance")
    })
    List<User> topup(User user);

    @Update(updateBalance)
    int updateBalance(User user);

    @Select(confirm)
    @Results({
            @Result(column = "eusid", property = "id_user"),
            @Result(column = "username", property = "username"),
            @Result(column = "password", property = "password"),
            @Result(column = "status", property = "status"),
            @Result(column = "balance", property = "balance")
    })
    List<User> confirm(User user);

    @Select(findById)
    @Results({
            @Result(column = "eusid", property = "id_user"),
            @Result(column = "username", property = "username"),
            @Result(column = "password", property = "password"),
            @Result(column = "status", property = "status"),
            @Result(column = "balance", property = "balance")
    })
    User findById(Long id);


}
