package com.example.michaelxiong.myapplication;

import java.io.Serializable;

/**
 * Created by princ on 11/11/2017.
 */

public class UUID extends Object implements Serializable, Comparable<UUID> {

    private long mostSigBits, leastSigBits;

    public UUID (long mostSigBits, long leastSigBits){
        this.leastSigBits = leastSigBits;
        this.mostSigBits = mostSigBits;
    }

    public long getMostSigBits() {
        return mostSigBits;
    }

    public long getLeastSigBits() {
        return leastSigBits;
    }

    @Override
    public int compareTo(UUID uuid) {
        UUID originalUUID = new UUID(mostSigBits, leastSigBits);
        String stringUUIDMain = originalUUID.toString();
        String stringUUIDComp = uuid.toString();
        if(stringUUIDMain.equals(stringUUIDComp)){
            return 0;
        }
        else if(stringUUIDMain.compareTo(stringUUIDComp) == 1)
        return 1;
        else{
            return -1;
        }
    }
}
