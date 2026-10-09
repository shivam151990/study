package com.shivam151990.lld.amazon_locker.service;

import com.shivam151990.lld.amazon_locker.model.ItemPackage;
import com.shivam151990.lld.amazon_locker.model.Parcel;
import com.shivam151990.lld.amazon_locker.repo.LockerRepo;

public class LockerService {

    private LockerRepo lockerRepo;

    public LockerService(LockerRepo lockerRepo) {
        this.lockerRepo = lockerRepo;
    }

    public void createLocker(int qty, Parcel size) {
        lockerRepo.createLocker(qty, size);
    }

    public String depositPackage(ItemPackage itemPackage) {
        return lockerRepo.store(itemPackage);
    }

    public void pickup(String code) {
        ItemPackage pickup = lockerRepo.pickup(code);
        System.out.println("Picked Up Package: " + pickup);
    }
}
