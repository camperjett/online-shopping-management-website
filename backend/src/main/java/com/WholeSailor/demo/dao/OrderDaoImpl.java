package com.WholeSailor.demo.dao;

import com.WholeSailor.demo.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao {
    @Autowired
    JdbcTemplate template;

    @Autowired
    ProductDAO productDAO;

    @Override
    public Integer insertOrder(Order order) {
        System.out.println(order);
        String sql = "insert into orders(status, date, user_id, area_id, address_id, transaction_id) values (?, ?, ?, ?, ?, ?)";
        return template.update(sql, new Object[]{order.getStatus(), order.getDate(), order.getUser_id(), order.getArea_id(), order.getAddress_id(), order.getTransaction_id()});
    }

    @Override
    public List<extendedOrder> getAllOrder(Integer user_id) {
        String sql = "select * from orders where user_id = ? ORDER BY date DESC";
        List<extendedOrder> extOrdList = new ArrayList<>();
        List<Order> ord = template.query(sql, new BeanPropertyRowMapper<>(Order.class), user_id);
        //System.out.println(ord.get(0).getOrder_id());
        for (int i = 0; i < ord.size(); i++) {
            extendedOrder temp = new extendedOrder();
            temp.setPl_order(ord.get(i));
            //System.out.println(ord.get(0).getOrder_id());
            int tid = ord.get(i).getTransaction_id();
            int oid = ord.get(i).getOrder_id();
            //System.out.println(ord.get(0).getOrder_id());
            String sql1 = "SELECT amount FROM transaction WHERE transaction_id = ?";
            int price = template.queryForObject(sql1, Integer.class, tid);
            //System.out.println(ord.get(0).getOrder_id());
            temp.setTotal_price(price);
            String sql2 = "SELECT product_id FROM order_details WHERE order_id = ?";
            List<Integer> pid = template.queryForList(sql2, Integer.class, oid);
            //System.out.println(ord.get(0).getOrder_id());
            Product pd = productDAO.getByID(pid.get(0));
            //System.out.println(ord.get(0).getOrder_id());
            temp.setOrd_prod(pd);
            extOrdList.add(temp);
        }

        return extOrdList;
    }

    @Override
    public List<Order> getOrderById(Integer order_id) {
        String sql = "select * from orders where order_id = ?";
        return template.query(sql, new BeanPropertyRowMapper<>(Order.class), new Object[]{order_id});
    }

    @Override
    public List<Order> getOrderByArea(Integer area_id) {
        String sql = "select * from orders where area_id = ?";
        return template.query(sql, new BeanPropertyRowMapper<>(Order.class), new Object[]{area_id});
    }

    @Override
    public Integer updateOrderStatus(Integer order_id, Order order) {
        String sql = "update orders set status = ? where order_id = ?";
        return template.update(sql, new Object[]{order.getStatus(), order_id});
    }

    public List<Order> getOrder(Order order) {
        String sql = "select * from orders where transaction_id = ? ";
        return template.query(sql, new BeanPropertyRowMapper<>(Order.class), new Object[]{order.getTransaction_id()});
    }

    public List<Order> getAllOrders() {
        String sql = "select * from orders";
        return template.query(sql, new BeanPropertyRowMapper<>(Order.class));
    }

    @Override
    public List<extendedOrder> getOrdersByStatus(Integer user_id, int stat) {
        String sql = "select * from orders where user_id = ? and status = ? ORDER BY date DESC";
        List<extendedOrder> extOrdList = new ArrayList<>();
        List<Order> ord = template.query(sql, new BeanPropertyRowMapper<>(Order.class), user_id, stat);
        //System.out.println(ord.get(0).getOrder_id());
        for (int i = 0; i < ord.size(); i++) {
            extendedOrder temp = new extendedOrder();
            temp.setPl_order(ord.get(i));
            //System.out.println(ord.get(0).getOrder_id());
            int tid = ord.get(i).getTransaction_id();
            int oid = ord.get(i).getOrder_id();
            //System.out.println(ord.get(0).getOrder_id());
            String sql1 = "SELECT amount FROM transaction WHERE transaction_id = ?";
            int price = template.queryForObject(sql1, Integer.class, tid);
            //System.out.println(ord.get(0).getOrder_id());
            temp.setTotal_price(price);
            String sql2 = "SELECT product_id FROM order_details WHERE order_id = ?";
            List<Integer> pid = template.queryForList(sql2, Integer.class, oid);
            //System.out.println(ord.get(0).getOrder_id());
            Product pd = productDAO.getByID(pid.get(0));
            //System.out.println(ord.get(0).getOrder_id());
            temp.setOrd_prod(pd);
            extOrdList.add(temp);
        }

        return extOrdList;
    }

    @Override
    public List<extendedOrder> getAllOrderAdminByStatus() {
        List<extendedOrder> extOrdList = new ArrayList<>();
        try {
            String sql = "select * from orders ORDER BY date DESC";
            List<Order> ord = template.query(sql, new BeanPropertyRowMapper<>(Order.class));
            //System.out.println(ord.get(0).getOrder_id());
            for (int i = 0; i < ord.size(); i++) {
                extendedOrder temp = new extendedOrder();
                temp.setPl_order(ord.get(i));
                //System.out.println(ord.get(0).getOrder_id());
                int tid = ord.get(i).getTransaction_id();
                int oid = ord.get(i).getOrder_id();
                //System.out.println(ord.get(0).getOrder_id());
                String sql1 = "SELECT amount FROM transaction WHERE transaction_id = ?";
                int price = template.queryForObject(sql1, Integer.class, tid);
                //System.out.println(ord.get(0).getOrder_id());
                temp.setTotal_price(price);
                String sql2 = "SELECT product_id FROM order_details WHERE order_id = ?";
                List<Integer> pid = template.queryForList(sql2, Integer.class, oid);
                System.out.println(pid);
                String sql3 = "SELECT * FROM Product WHERE product_id = ?";
                Product pd = template.queryForObject(sql3, new BeanPropertyRowMapper<>(Product.class), pid.get(0));
                System.out.println(pd);
                temp.setOrd_prod(pd);
                extOrdList.add(temp);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return extOrdList;
    }

    @Override
    public boolean cancelOrder(Integer order_id, Integer user_id) {
        String sql1 = "SELECT * FROM order_details WHERE order_id = ?";
        System.out.println("hello-3");
        List<orderDetails> ordDet = template.query(sql1, new BeanPropertyRowMapper<>(orderDetails.class), order_id);
        System.out.println(ordDet);
        String sql = "UPDATE orders SET status = ? WHERE order_id = ? AND user_id = ?";
        //template.update(sql, new Object[]{3, order_id, user_id});
        if (template.update(sql, new Object[]{3, order_id, user_id}) > 0) {
            System.out.println("hello-4");
            String sql2 = "UPDATE Product SET stock = stock+? WHERE product_id = ?";
            for (int i = 0; i < ordDet.size(); i++) {
                template.update(sql2, ordDet.get(i).getQuantity(), ordDet.get(i).getProduct_id());
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteOrder(Integer order_id) {
        String sql = "DELETE FROM orders WHERE order_id = ?";
        if (template.update(sql, new Object[]{order_id}) > 0) return true;
        return false;
    }

    @Override
    public List<Order> SearchFilter(Integer x, Integer y) {
        String sql = "SELECT * FROM orders WHERE user_id = ? AND status = ?";
        return template.query(sql, new BeanPropertyRowMapper<>(Order.class), new Object[]{x, y});
    }

    @Override
    public List orderDetails(Integer order_id, Integer user_id) {
        // order
        String sql1 = "SELECT * FROM orders WHERE order_id = ? AND user_id = ?";
        Order ord = template.queryForObject(sql1, new BeanPropertyRowMapper<>(Order.class), new Object[]{order_id, user_id});
        // address
        Integer adr_id = ord.getAddress_id();
        String sql2 = "SELECT * FROM user_address WHERE address_id = ?";
        Address address = template.queryForObject(sql2, new BeanPropertyRowMapper<>(Address.class), new Object[]{adr_id});
        // transaction
        Integer tid = ord.getTransaction_id();
        String sql3 = "SELECT * FROM transaction WHERE transaction_id = ?";
        transaction tran = template.queryForObject(sql3, new BeanPropertyRowMapper<>(transaction.class), tid);
        // final price is in transaction
        // list of products + quantity
        String sql4 = "SELECT * FROM order_details WHERE order_id = ?";
        List<orderDetails> orDet = template.query(sql4, new BeanPropertyRowMapper<>(orderDetails.class), order_id);
        System.out.println(orDet.get(0).getOrder_id());
        List<product_quantity> prdQuan = new ArrayList<>();
        String sql5 = "SELECT * FROM Product WHERE product_id = ?";
        for (int i = 0; i < orDet.size(); i++) {
            System.out.println("hello-4");
            product_quantity temp = new product_quantity();
            Product temp_prod = template.queryForObject(sql5, new BeanPropertyRowMapper<>(Product.class), orDet.get(i).getProduct_id());
            temp.setProduct(temp_prod);
            temp.setQuantity(orDet.get(i).getQuantity());
            prdQuan.add(temp);
        }

        List res = new ArrayList<>();
        res.add(ord);
        res.add(address);
        res.add(tran);
        res.add(prdQuan);
        return res;
    }

    @Override
    @Transactional
    public int placeByUser(int user_id, List<productForOrder> Prod, transaction tran, int ad_id) {
        int amount = 0;
        String qr = "SELECT MRP FROM Product WHERE product_id = ?";
        String qr1 = "SELECT stock FROM Product WHERE product_id = ?";
        for (int i = 0; i < Prod.size(); i++) {
            Integer stock = template.queryForObject(qr1, Integer.class, Prod.get(i).getProduct_id());
            if (stock < Prod.get(i).getQuantity()) return -2;
        }
        String qr2 = "UPDATE Product SET stock = stock-? WHERE product_id = ?";
        for (int i = 0; i < Prod.size(); i++) {
            int price = template.queryForObject(qr, Integer.class, Prod.get(i).getProduct_id());
            amount = amount + (price * Prod.get(i).getQuantity());
            template.update(qr2, Prod.get(i).getQuantity(), Prod.get(i).getProduct_id());
        }
        // create a transaction id
        String sql1 = "INSERT INTO transaction(date_of_tran, amount, user_id, mode) VALUES(NOW(), ?, ?, ?)";

        if ((template.update(sql1, amount, user_id, tran.getMode())) <= 0) return 0;
        String sql2 = "SELECT LAST_INSERT_ID()";
        int tid = template.queryForObject(sql2, Integer.class);
        // create an entry in order table using tid from above
        String sql3 = "INSERT INTO orders(status, date, user_id, address_id, transaction_id) VALUES(?, NOW(), ?, ?, ?)";
        if ((template.update(sql3, 0, user_id, ad_id, tid)) <= 0) return 0;
        int oid = template.queryForObject(sql2, Integer.class);
//        System.out.println(oid);
        // 0 - in waiting
        // entry in order details
        String sql4 = "INSERT INTO order_details(order_id, product_id, final_price, quantity) VALUES(?, ?, ?, ?)";
        for (int i = 0; i < Prod.size(); i++) {
            int price_prod = template.queryForObject(qr, Integer.class, Prod.get(i).getProduct_id());
            int price = Prod.get(i).getQuantity() * price_prod;
            if ((template.update(sql4, new Object[]{oid, Prod.get(i).getProduct_id(), price, Prod.get(i).getQuantity()})) <= 0)
                return 0;
        }
        return oid;
    }
}