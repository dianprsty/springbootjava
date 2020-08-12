package com.dian.Emoney.Backend.Mapper;


import com.dian.Emoney.Backend.Model.Topup;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface TransaksiMapper {
    String meminjam = "INSERT INTO transaksi (id_user, id_buku, status, waktu_dipinjam, waktu_dikembalikan) VALUES(#{id_user},#{id_buku}, #{status}, #{waktu_dipinjam}, #{waktu_dikembalikan});";
    String mengganti = "UPDATE transaksi SET id_buku=#{id_buku} WHERE id_transaksi=#{id_transaksi};";
    String kembalikan = "UPDATE transaksi SET status=#{status}, waktu_dikembalikan=#{waktu_dikembalikan} WHERE id_transaksi=#{id_transaksi};";
    String findAll = "SELECT*FROM transaksi";

    @Insert(meminjam)
    int meminjam(Topup transaksi);

    @Update(mengganti)
    int mengganti(Topup transaksi);

    @Update(kembalikan)
    int kembalikan(Topup transaksi);

    @Select(findAll)
    @Results({
            @Result(column = "id_transaksi", property = "id_transaksi"),
            @Result(column = "id_user", property = "id_user"),
            @Result(column = "status", property = "status")
    })
    List<Topup> findAll();
}
