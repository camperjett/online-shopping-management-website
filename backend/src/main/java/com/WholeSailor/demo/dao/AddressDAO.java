package com.WholeSailor.demo.dao;

import com.WholeSailor.demo.model.Address;
import com.WholeSailor.demo.model.Picx;
import com.WholeSailor.demo.model.TempModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Blob;
import java.sql.Connection;
import java.util.List;

@Repository
public class AddressDAO {
    @Autowired
    JdbcTemplate template;

    //get all address
    public List<Address> getAllAddress(Integer user_id) {
        String sql = "select * from user_address where user_id=?";
        List<Address> res = null;
        try {
            res = this.template.query(sql, new BeanPropertyRowMapper<>(Address.class), user_id);
        } catch (Exception e) {
            System.out.println(e);
        }

        return res;
    }

    //add address
    public String addAddress(Address address) {
        String sql = "insert into user_address(pincode, house_address, city, state, user_id, name) values(?, ?, ?, ?, ?, ?)";
        try {
            this.template.update(sql, address.getPincode(), address.getHouse_address(), address.getCity(), address.getState(), address.getUser_id(), address.getName());
            return "Address added!";
        } catch (Exception e) {
            System.out.println(e);
        }

        return "Some error occurred!";
    }

    //edit address
    public String editAddress(Address address) {
        String sql_pincode = "update user_address set pincode = ? where address_id=? and user_id=?";
        String sql_local_address = "update user_address set house_address = ? where address_id=? and user_id=?";
        String sql_city = "update user_address set city = ? where address_id=? and user_id=?";
        String sql_state = "update user_address set state = ? where address_id=? and user_id=?";
        String sql_name = "update user_address set name = ? where address_id=? and user_id=?";

        try {
            if (address.getPincode() != null) {
                this.template.update(sql_pincode, new Object[]{address.getPincode(), address.getAddress_id(), address.getUser_id()});
            }
            if (address.getHouse_address() != null) {
                this.template.update(sql_local_address, new Object[]{address.getHouse_address(), address.getAddress_id(), address.getUser_id()});
            }
            if (address.getCity() != null) {
                this.template.update(sql_city, new Object[]{address.getCity(), address.getAddress_id(), address.getUser_id()});
            }
            if (address.getState() != 0) {
                this.template.update(sql_state, new Object[]{address.getState(), address.getAddress_id(), address.getUser_id()});
            }
            if (address.getName() != null) {
                this.template.update(sql_name, new Object[]{address.getName(), address.getAddress_id(), address.getUser_id()});
            }
            return "Updated successfully!";
        } catch (Exception e) {
            System.out.println(e);
        }

        return "Some error occurred!";
    }

    //remove address given address_id
    public String removeAddress(Integer aid, Integer uid) {
        String sql = "delete from user_address where address_id = ? and user_id=?";

        try {
            this.template.update(sql, aid, uid);
            return "Deleted successfully";
        } catch (Exception e) {
            System.out.println(e);
        }

        return "Some error occured!";
    }

    public String addImg(String img) {
        String sq1 = "insert into image(bloby) values(?)";
        byte[] byteContent = img.getBytes();
        Connection con;
        try {
            con = this.template.getDataSource().getConnection();
            Blob blob = con.createBlob();//Where connection is the connection to db object.
            blob.setBytes(1, byteContent);
            this.template.update(sq1, blob);
            return "DONE";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "Some Eroror";
    }

    public Picx getImg(Integer id) {

        try {
            String query = "select * from Product_pics where product_id = ?";
            Picx res = this.template.queryForObject(query, new BeanPropertyRowMapper<>(Picx.class), id);
            System.out.println(res);
            Blob front = res.getBloby();
            byte[] bdata = front.getBytes(1, (int) front.length());
            String s = new String(bdata);
            res.setImage(s);
            res.setBloby(null);
            return res;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new Picx();

    }
}
