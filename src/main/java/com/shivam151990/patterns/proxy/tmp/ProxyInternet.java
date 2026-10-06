package com.shivam151990.patterns.proxy.tmp;

import com.shivam151990.patterns.proxy.internet.InternetAccess;

public class ProxyInternet implements InternetAccess {

    private OfficeInternet internet;
    private String role;

    public ProxyInternet(OfficeInternet internet) {
        this.internet = internet;
    }

    @Override
    public void grantInternetAccess() {

    }
}
