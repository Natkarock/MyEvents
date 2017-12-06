package com.natateam.myevents.db;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Created by macbook on 07/07/ 15.
 */
public final class RealmAutoIncrement {

    private Map<Class<? extends RealmObject>, AtomicInteger> modelMap = new HashMap<>();
    private static RealmAutoIncrement autoIncrementMap;
    private Class<? extends RealmObject> mObj;
    private Realm realmMain;


    private RealmAutoIncrement(Class<? extends RealmObject> obj,Realm realm) {
        mObj = obj;
        realmMain = realm;
        modelMap.put(obj, new AtomicInteger(getLastIdFromModel(mObj)));
    }

    private int getLastIdFromModel(Class<? extends RealmObject> clazz) {

        String primaryKeyColumnName = "id";
        Number lastId = realmMain.where(clazz).max(primaryKeyColumnName);
        return lastId == null ? 0 : lastId.intValue();
    }

    public Integer getNextIdFromModel() {

        if (isValidMethodCall()) {

            AtomicInteger modelId = modelMap.get(mObj);

            if (modelId == null) {
                return 0;
            }
            return modelId.incrementAndGet();
        }
        return 0;
    }

    private boolean isValidMethodCall() {

        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

        for (StackTraceElement stackTraceElement : stackTraceElements) {

            if (stackTraceElement.getMethodName().equals("newInstance")) {
                return false;
            }
        }
        return true;
    }

    public static RealmAutoIncrement getInstance(Class<? extends RealmObject> obj,Realm realm) {

        if (autoIncrementMap == null) {
            autoIncrementMap = new RealmAutoIncrement(obj,realm);
        }
        return autoIncrementMap;
    }
}
