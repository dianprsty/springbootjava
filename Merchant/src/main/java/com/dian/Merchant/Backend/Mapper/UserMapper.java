package com.dian.Merchant.Backend.Mapper;


import com.dian.Merchant.Backend.Model.MerchantUser;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {
    String addUser = "INSERT INTO muser (musername,password,role) VALUES(#{username},#{password}, #{role});";
    String login = "SELECT*FROM muser WHERE musername=#{username} AND password=#{password}";
    String updateStatus = "UPDATE muser SET logstatus=1 WHERE musername=#{username};";
    String logout = "SELECT*FROM muser WHERE musername=#{username}";


    @Insert(addUser)
    int addUser(MerchantUser user);

    @Select(login)
    @Results({
            @Result(column = "mid", property = "id_user"),
            @Result(column = "musername", property = "username"),
            @Result(column = "password", property = "password"),
            @Result(column = "role", property = "role"),
            @Result(column = "logstatus", property = "logstatus")
    })
    List<MerchantUser> login(MerchantUser user);

    @Update(updateStatus)
    int updateStatus(MerchantUser user);

    @Select(logout)
    @Results({
            @Result(column = "mid", property = "id_user"),
            @Result(column = "musername", property = "username"),
            @Result(column = "password", property = "password"),
            @Result(column = "role", property = "role"),
            @Result(column = "logstatus", property = "logstatus")
    })
    List<MerchantUser> logout(MerchantUser user);

}
