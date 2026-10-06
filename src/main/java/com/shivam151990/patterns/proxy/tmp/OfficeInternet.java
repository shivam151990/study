package com.shivam151990.patterns.proxy.tmp;

import com.shivam151990.patterns.proxy.internet.InternetAccess;

public class OfficeInternet implements InternetAccess {

    @Override
    public void grantInternetAccess() {
        System.out.println("Internet Access Granted");
    }
}
