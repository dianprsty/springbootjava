package com.dian.Merchant.Backend.Mapper;


import com.dian.Merchant.Backend.Model.MerchantUser;
import com.dian.Merchant.Backend.Model.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface ProductMapper {
    String create = "INSERT INTO PRODUCT (name,stock,price) VALUES(#{name},#{stock},#{price});";
    String update = "UPDATE PRODUCT SET stock = #{stock}, price = #{price}   WHERE name=#{name} OR pid=#{pid};";
    String findByName = "SELECT * FROM PRODUCT WHERE name=#{name}";
    String delete = "DELETE FROM PRODUCT WHERE pid = #{pid}";
    String findById = "SELECT * FROM PRODUCT WHERE pid=#{pid}";
    String findProductById = "SELECT * FROM PRODUCT WHERE pid=#{pid}";
    String findAllProduct = "SELECT * FROM PRODUCT";
    String updateAfterTransaction = "UPDATE PRODUCT SET stock = #{stock} WHERE name=#{name} OR pid=#{pid};";

    @Insert(create)
    int create(Product product);

    @Update(update)
    int update(Product product);

    @Delete(delete)
    int delete(Product product);

    @Select(findByName)
    @Results({
            @Result(column = "pid", property = "pid"),
            @Result(column = "name", property = "name"),
            @Result(column = "price", property = "price"),
            @Result(column = "stock", property = "stock")
    })
    List<Product>findByName(Product product);

    @Select(findById)
    @Results({
            @Result(column = "pid", property = "pid"),
            @Result(column = "name", property = "name"),
            @Result(column = "price", property = "price"),
            @Result(column = "stock", property = "stock")
    })
    Product findById(Product product);

    @Select(findProductById)
    @Results({
            @Result(column = "pid", property = "pid"),
            @Result(column = "name", property = "name"),
            @Result(column = "price", property = "price"),
            @Result(column = "stock", property = "stock")
    })
    List<Product>findProductById(Product product);

    @Select(findAllProduct)
    @Results({
            @Result(column = "pid", property = "pid"),
            @Result(column = "name", property = "name"),
            @Result(column = "price", property = "price"),
            @Result(column = "stock", property = "stock")
    })
    List<Product>  findAllProduct();

    @Update(updateAfterTransaction)
    int updateAfterTransaction(Product product);
}
