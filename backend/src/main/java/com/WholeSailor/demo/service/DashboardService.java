package com.WholeSailor.demo.service;

import com.WholeSailor.demo.model.Contact;
import com.WholeSailor.demo.model.Dashboard;
import com.WholeSailor.demo.model.Email;
import com.WholeSailor.demo.model.User;

import java.util.List;

public interface DashboardService {
    public Dashboard getDashboardInfo(User user);

    String updateDashboardInfo(Dashboard dashboard);

    String addEmail(Email email);

    String addContact(Contact contact);

    String removeEmail(Integer ueid);

    String removeContact(Integer ucid);
}
