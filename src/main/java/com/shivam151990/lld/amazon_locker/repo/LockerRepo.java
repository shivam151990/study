package com.shivam151990.lld.amazon_locker.repo;

import com.shivam151990.lld.amazon_locker.model.ItemPackage;
import com.shivam151990.lld.amazon_locker.model.Parcel;

public interface LockerRepo {
    void createLocker(int qty, Parcel size);

    String store(ItemPackage itemPackage);

    ItemPackage pickup(String code);
}
