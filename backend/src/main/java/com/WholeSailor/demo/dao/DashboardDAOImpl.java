package com.WholeSailor.demo.dao;

import com.WholeSailor.demo.model.*;
import com.WholeSailor.demo.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class DashboardDAOImpl implements DashboardDAO {
    @Autowired
    JdbcTemplate template;

    @Override
    public List<Dashboard> getDashboardInfoCustomer(User user) {
        String newSQl = "select u.username, c.first_name, c.last_name, c.gender, c.photo_url, c.email, c.phone_no from user u, customer c where u.id = ?  and u.id = c.user_id";
        String emailSql = "select * from user_email where user_id = ?";
        String contactSql = "select * from user_contact where user_id = ?";


        List<Email> emails = this.template.query(emailSql, new BeanPropertyRowMapper<>(Email.class), new Object[]{user.getId()});
        List<Contact> contacts = this.template.query(contactSql, new BeanPropertyRowMapper<>(Contact.class), new Object[]{user.getId()});
        List<Dashboard> d = this.template.query(newSQl, new BeanPropertyRowMapper<Dashboard>(Dashboard.class), new Object[]{user.getId()});
        System.out.println(emails);
        Dashboard dashboard = d.get(0);
        dashboard.setUser_id(user.getId());
        dashboard.setSecondary_contact_no(contacts);
        dashboard.setSecondary_emails(emails);
        return (List<Dashboard>) d;
    }

    @Override
    public List<Dashboard> getDashboardInfoShopkeeper(User user) {
        String newSQl = "select u.username, s.first_name, s.last_name, s.gender, s.photo_url, s.email, s.phone_no, s.license_image_url, s.license_no from user u, shopkeeper s where u.id = ?  and s.user_id = u.id";
        String emailSql = "select * from user_email where user_id = ?";
        String contactSql = "select * from user_contact where user_id = ?";


        //  String shopkeeperSql = "select s.first_name, s.last_name, s.gender, s.photo_url from shopkeeper where user_id = ?";

        List<Email> emails = this.template.query(emailSql, new BeanPropertyRowMapper<Email>(Email.class), new Object[]{user.getId()});
        List<Contact> contacts = this.template.query(contactSql, new BeanPropertyRowMapper<Contact>(Contact.class), new Object[]{user.getId()});
        List<Dashboard> d = this.template.query(newSQl, new BeanPropertyRowMapper<Dashboard>(Dashboard.class), new Object[]{user.getId()});
        //  Shopkeeper shopkeeper = this.template.queryForObject(customerSql, new Object[] {user.getId()}, new BeanPropertyRowMapper<Shopkeeper>(Shopkeeper.class));
        System.out.println(emails);
        Dashboard dashboard = d.get(0);
        dashboard.setUser_id(user.getId());
        dashboard.setSecondary_contact_no(contacts);
        dashboard.setSecondary_emails(emails);
        return (List<Dashboard>) d;

    }

    @Override
    public List<Dashboard> getDashboardInfoAdmin(User user) {
        String newSQl = "select u.username, c.first_name, c.last_name, c.gender, c.photo_url, c.email, c.phone_no from user u, admin c where u.id = ?  and u.id = c.user_id";
        String emailSql = "select * from user_email where user_id = ?";
        String contactSql = "select * from user_contact where user_id = ?";


        List<Email> emails = this.template.query(emailSql, new BeanPropertyRowMapper<>(Email.class), new Object[]{user.getId()});
        List<Contact> contacts = this.template.query(contactSql, new BeanPropertyRowMapper<>(Contact.class), new Object[]{user.getId()});
        List<Dashboard> d = this.template.query(newSQl, new BeanPropertyRowMapper<Dashboard>(Dashboard.class), new Object[]{user.getId()});
        System.out.println(emails);
        Dashboard dashboard = d.get(0);
        dashboard.setUser_id(user.getId());
        dashboard.setSecondary_contact_no(contacts);
        dashboard.setSecondary_emails(emails);
        return (List<Dashboard>) d;
    }

    @Override
    public String updateFirstName(Dashboard dashboard) {
        String sql = "update user set first_name = ? where id=?";
        this.template.update(sql, new Object[]{dashboard.getFirst_name(), dashboard.getUser_id()});
        return "First name updated!";
    }

    @Override
    public String updateLastName(Dashboard dashboard) {
        String sql = "update user set last_name = ? where id=?";
        this.template.update(sql, new Object[]{dashboard.getLast_name(), dashboard.getUser_id()});
        return "Last name updated!";
    }

    @Override
    public String addPhoneNo(Contact contact) {
        String sql = "insert into user_contact(contact_no, user_id) values(?, ?)";
        this.template.update(sql, new Object[]{contact.getContact_no(), contact.getUser_id()});
        return "Contact number added";
    }

    @Override
    public String addEmail(Email email) {
        System.out.println(email);
        String sql = "insert into user_email(email, user_id) values(?, ?)";
        this.template.update(sql, new Object[]{email.getEmail(), email.getUser_id()});
        return "Email added";
    }

    @Override
    public String deletePhoneNo(Integer ucid) {
        String sql = "delete from user_contact where ucid = ?";
        this.template.update(sql, ucid);
        return "Contact no. deleted!";
    }

    @Override
    public String deleteEmail(Integer ueid) {
        String sql = "delete from user_email where ueid = ?";
        this.template.update(sql, ueid);
        return "Email no. deleted!";
    }

    @Override
    public Integer getRole(Integer user_id) {
        String sql = "select role from user where id=?";
        Integer i = this.template.queryForObject(sql, new Object[]{user_id}, Integer.class);
        return i;
    }

    @Override
    public void updateFirstNameCustomer(Dashboard dashboard) {
        String sql = "update customer set first_name = ?";
        this.template.update(sql, new Object[]{dashboard.getFirst_name()});
    }

    @Override
    public void updateLastNameCustomer(Dashboard dashboard) {
        String sql = "update customer set last_name = ?";
        this.template.update(sql, new Object[]{dashboard.getLast_name()});
    }

    @Override
    public void updateGenderCustomer(Dashboard dashboard) {
        String sql = "update customer set gender = ?";
        this.template.update(sql, new Object[]{dashboard.getGender()});
    }

    @Override
    public void updatePhotoCustomer(Dashboard dashboard) {
        String sql = "update customer set photo_url = ?";
        this.template.update(sql, new Object[]{dashboard.getPhoto_url()});
    }

    @Override
    public void updateEmailCustomer(Dashboard dashboard) {
        String sql = "update customer set email = ?";
        this.template.update(sql, new Object[]{dashboard.getEmail()});
    }

    @Override
    public void updatePhoneNoCustomer(Dashboard dashboard) {
        String sql = "update customer set phone_no = ?";
        this.template.update(sql, new Object[]{dashboard.getPhone_no()});
    }

    @Override
    public void updateUsername(Dashboard dashboard) {
        String sql = "update user set username = ? where id=?";
        this.template.update(sql, new Object[]{dashboard.getUsername(), dashboard.getUser_id()});
    }

    @Override
    public void updateFirstNameShopkeeper(Dashboard dashboard) {
        String sql = "update shopkeeper set first_name = ?";
        this.template.update(sql, new Object[]{dashboard.getFirst_name()});
    }

    @Override
    public void updateLastNameShopkeeper(Dashboard dashboard) {
        String sql = "update shopkeeper set last_name = ?";
        this.template.update(sql, new Object[]{dashboard.getLast_name()});
    }

    @Override
    public void updateGenderShopkeeper(Dashboard dashboard) {
        String sql = "update shopkeeper set gender = ?";
        this.template.update(sql, new Object[]{dashboard.getGender()});
    }

    @Override
    public void updatePhotoShopkeeper(Dashboard dashboard) {
        String sql = "update shopkeeper set photo_url = ?";
        this.template.update(sql, new Object[]{dashboard.getPhoto_url()});
    }

    @Override
    public void updateEmailShopkeeper(Dashboard dashboard) {
        String sql = "update shopkeeper set email = ?";
        this.template.update(sql, new Object[]{dashboard.getEmail()});
    }

    @Override
    public void updatePhoneNoShopkeeper(Dashboard dashboard) {
        String sql = "update shopkeeper set phone_no = ?";
        this.template.update(sql, new Object[]{dashboard.getPhone_no()});
    }


    private String EmailToString(Email email) {
        return email.getEmail();
    }

    private String ContactToString(Contact contact) {
        return contact.getContact_no();
    }
}
