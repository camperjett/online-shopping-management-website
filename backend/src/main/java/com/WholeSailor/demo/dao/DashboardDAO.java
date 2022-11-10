package com.WholeSailor.demo.dao;

import com.WholeSailor.demo.model.Contact;
import com.WholeSailor.demo.model.Dashboard;
import com.WholeSailor.demo.model.Email;
import com.WholeSailor.demo.model.User;

import java.util.List;

public interface DashboardDAO {
    List<Dashboard> getDashboardInfoCustomer(User user);

    List<Dashboard> getDashboardInfoShopkeeper(User user);

    List<Dashboard> getDashboardInfoAdmin(User user);

    String updateFirstName(Dashboard dashboard);

    String updateLastName(Dashboard dashboard);

    String addPhoneNo(Contact contact);

    String addEmail(Email email);

    String deletePhoneNo(Integer ucid);

    String deleteEmail(Integer ueid);

    Integer getRole(Integer user_id);

    void updateFirstNameCustomer(Dashboard dashboard);

    void updateLastNameCustomer(Dashboard dashboard);

    void updateGenderCustomer(Dashboard dashboard);

    void updatePhotoCustomer(Dashboard dashboard);

    void updateEmailCustomer(Dashboard dashboard);

    void updatePhoneNoCustomer(Dashboard dashboard);

    void updateUsername(Dashboard dashboard);

    void updateFirstNameShopkeeper(Dashboard dashboard);

    void updateLastNameShopkeeper(Dashboard dashboard);

    void updateGenderShopkeeper(Dashboard dashboard);

    void updatePhotoShopkeeper(Dashboard dashboard);

    void updateEmailShopkeeper(Dashboard dashboard);

    void updatePhoneNoShopkeeper(Dashboard dashboard);
}
