package com.shivam151990.lld.amazon_locker;

import com.shivam151990.lld.amazon_locker.model.ItemPackage;
import com.shivam151990.lld.amazon_locker.model.Parcel;
import com.shivam151990.lld.amazon_locker.repo.InMemoryLockerRepo;
import com.shivam151990.lld.amazon_locker.repo.LockerRepo;
import com.shivam151990.lld.amazon_locker.service.LockerService;

public class LockerRunner {

    public static void main(String[] args) {

        LockerRepo lockerRepo = new InMemoryLockerRepo(3);
        LockerService lockerService = new LockerService(lockerRepo);
        lockerService.createLocker(3, Parcel.SMALL);
        lockerService.createLocker(3, Parcel.MEDIUM);
        lockerService.createLocker(3, Parcel.LARGE);

        ItemPackage p1 = new ItemPackage("id1", Parcel.SMALL);
        ItemPackage p2 = new ItemPackage("id2", Parcel.MEDIUM);
        ItemPackage p3 = new ItemPackage("id3", Parcel.LARGE);

        String code1 = lockerService.depositPackage(p1);
        String code2 = lockerService.depositPackage(p2);

        lockerService.pickup(code1);
        lockerService.pickup(code2);
        lockerService.pickup(code2);


    }
}
