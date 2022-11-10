package com.WholeSailor.demo.service;

import com.WholeSailor.demo.dao.DashboardDAO;
import com.WholeSailor.demo.model.Contact;
import com.WholeSailor.demo.model.Dashboard;
import com.WholeSailor.demo.model.Email;
import com.WholeSailor.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {
    @Autowired
    DashboardDAO dashboardDAO;

    @Override
    public Dashboard getDashboardInfo(User user) {
        try {
            int role = this.dashboardDAO.getRole(user.getId());
            if (role == 1) {
                Dashboard dashboard = this.dashboardDAO.getDashboardInfoCustomer(user).get(0);
                dashboard.setRole(1);
                return dashboard;
            } else if (role == 2) {
                Dashboard dashboard = this.dashboardDAO.getDashboardInfoShopkeeper(user).get(0);
                dashboard.setRole(2);
                return dashboard;
            } else if (role == 3) {
                Dashboard dashboard = this.dashboardDAO.getDashboardInfoAdmin(user).get(0);
                return dashboard;
            }
            Dashboard dashboard = null;
            return dashboard;
        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }

    @Override
    public String updateDashboardInfo(Dashboard dashboard) {
        try {
            if (dashboard.getUsername() != null) {
                this.dashboardDAO.updateUsername(dashboard);
            }

            int role = this.dashboardDAO.getRole(dashboard.getUser_id());
            System.out.println(dashboard);
            if (role == 1) {
                if (dashboard.getFirst_name() != null) {
                    this.dashboardDAO.updateFirstNameCustomer(dashboard);
                }
                if (dashboard.getLast_name() != null) {
                    this.dashboardDAO.updateLastNameCustomer(dashboard);
                }
//                if (dashboard.getGender() != 0) {
                this.dashboardDAO.updateGenderCustomer(dashboard);
//                }
                if (dashboard.getPhoto_url() != null) {
                    this.dashboardDAO.updatePhotoCustomer(dashboard);
                }
                if (dashboard.getEmail() != null) {
                    this.dashboardDAO.updateEmailCustomer(dashboard);
                }
                if (dashboard.getPhone_no() != null) {
                    this.dashboardDAO.updatePhoneNoCustomer(dashboard);
                }
            } else if (role == 2) {
                if (dashboard.getFirst_name() != null) {
                    this.dashboardDAO.updateFirstNameShopkeeper(dashboard);
                }
                if (dashboard.getLast_name() != null) {
                    this.dashboardDAO.updateLastNameShopkeeper(dashboard);
                }
                if (dashboard.getGender() != 0) {
                    this.dashboardDAO.updateGenderShopkeeper(dashboard);
                }
                if (dashboard.getPhoto_url() != null) {
                    this.dashboardDAO.updatePhotoShopkeeper(dashboard);
                }
                if (dashboard.getEmail() != null) {
                    this.dashboardDAO.updateEmailShopkeeper(dashboard);
                }
                if (dashboard.getPhone_no() != null) {
                    this.dashboardDAO.updatePhoneNoShopkeeper(dashboard);
                }
            }


            return "Updated successfully!";
        } catch (Exception e) {
            System.out.println(e);
        }

        return "Some error occurred!";
    }

    @Override
    public String addEmail(Email email) {
        try {
            return this.dashboardDAO.addEmail(email);
        } catch (Exception e) {
            System.out.println(e);
        }
        return "Some error occurred!";
    }

    @Override
    public String addContact(Contact contact) {
        try {
            return this.dashboardDAO.addPhoneNo(contact);
        } catch (Exception e) {
            System.out.println(e);
        }
        return "Some error occurred!";
    }

    @Override
    public String removeEmail(Integer ueid) {
        try {
            return this.dashboardDAO.deleteEmail(ueid);
        } catch (Exception e) {
            System.out.println(e);
        }
        return "Some error occurred!";
    }

    @Override
    public String removeContact(Integer ucid) {
        try {
            return this.dashboardDAO.deletePhoneNo(ucid);
        } catch (Exception e) {
            System.out.println(e);
        }
        return "Some error occurred!";
    }
}
