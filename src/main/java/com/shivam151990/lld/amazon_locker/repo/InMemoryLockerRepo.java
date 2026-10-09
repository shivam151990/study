package com.shivam151990.lld.amazon_locker.repo;

import com.shivam151990.lld.amazon_locker.exception.NoStorageSpaceException;
import com.shivam151990.lld.amazon_locker.exception.StorageException;
import com.shivam151990.lld.amazon_locker.model.ItemPackage;
import com.shivam151990.lld.amazon_locker.model.Parcel;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InMemoryLockerRepo implements LockerRepo {

    private Map<Parcel, Integer> availableLockers;
    private int maxSizePerCategory;
    private Map<String, ItemPackage> packageMp;

    public InMemoryLockerRepo(int maxSizePerCategory) {
        this.availableLockers = new HashMap<>();
        this.packageMp = new HashMap<>();
        this.maxSizePerCategory = maxSizePerCategory;
    }

    @Override
    public void createLocker(int qty, Parcel size) {
        if (qty > maxSizePerCategory) {
            throw new StorageException("Cannot create lockers more than the max capacity");
        }
        availableLockers.put(size, qty);
    }

    @Override
    public String store(ItemPackage itemPackage) {
        if (itemPackage.size().getValue() <= Parcel.SMALL.getValue()
                && availableLockers.get(Parcel.SMALL) > 0) {
            availableLockers.put(Parcel.SMALL, availableLockers.get(Parcel.SMALL) - 1);
        } else if (itemPackage.size().getValue() <= Parcel.MEDIUM.getValue()
                && availableLockers.get(Parcel.MEDIUM) > 0)
            availableLockers.put(Parcel.MEDIUM, availableLockers.get(Parcel.MEDIUM) - 1);
        else if (itemPackage.size().getValue() <= Parcel.LARGE.getValue()
                && availableLockers.get(Parcel.LARGE) > 0) {
            availableLockers.put(Parcel.LARGE, availableLockers.get(Parcel.LARGE) - 1);
        } else {
            throw new NoStorageSpaceException("Space full");
        }
        String code = UUID.randomUUID().toString().replace("-", "").substring(0, 6);
        packageMp.put(code, itemPackage);
        return code;
    }

    @Override
    public ItemPackage pickup(String code) {
        if (!packageMp.containsKey(code)) {
            throw new RuntimeException("Wrong Code / No package please try again");
        }
        ItemPackage removedPackage = packageMp.remove(code);
        availableLockers.put(removedPackage.size(), availableLockers.get(removedPackage.size()) + 1);
        return removedPackage;
    }
}
