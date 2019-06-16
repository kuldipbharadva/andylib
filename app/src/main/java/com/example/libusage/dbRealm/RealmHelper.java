package com.example.libusage.dbRealm;

import android.content.Context;

import java.util.ArrayList;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class RealmHelper {

    private static Realm mRealm;

    public static Realm getRealmInstance(Context context) {
        RealmConfiguration config = new RealmConfiguration
                .Builder(context)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
        mRealm = Realm.getInstance(config);
        mRealm.setAutoRefresh(true);
        return mRealm;
    }

    /**
     * this function get all record from give model class of realm db
     */
    public static RealmResults getAllRecords(Class aClass) {
        return mRealm.allObjects(aClass);
    }

    /**
     * this function give you record from Realm db. If you pass null in 'where' then return all records.
     * you can also use this function to check record is available or not in given model class of realm.
     */
    public static RealmResults getWhere(Class aClass, ArrayList<String> wheres, ArrayList<Object> val, Case aCase) {
        RealmQuery query = mRealm.where(aClass);
        if (aCase == null) {
            aCase = Case.INSENSITIVE;
        }
        if (wheres != null) {
            for (int i = 0; i < wheres.size(); i++) {
                if (val.get(i) instanceof String) {
                    query.equalTo(wheres.get(i), (String) val.get(i), aCase);
                } else if (val.get(i) instanceof Integer) {
                    query.equalTo(wheres.get(i), (Integer) val.get(i));
                } else if (val.get(i) instanceof Double) {
                    query.equalTo(wheres.get(i), (Double) val.get(i));
                } else if (val.get(i) instanceof Float) {
                    query.equalTo(wheres.get(i), (Float) val.get(i));
                } else if (val.get(i) instanceof Boolean) {
                    query.equalTo(wheres.get(i), (Boolean) val.get(i));
                }
            }
        }
        return query.findAll();
    }

    /**
     * this function check is record exist in Realm db or not from given model class
     */
    public static boolean isExist(Class aClass, String where, Object val, Case aCase) {
        RealmObject realmObject = null;
        if (aCase == null) aCase = Case.INSENSITIVE;
        if (where != null && val != null) {
            if (val instanceof String) {
                realmObject = mRealm.where(aClass).equalTo(where, (String) val, aCase).findFirst();
            } else if (val instanceof Integer) {
                realmObject = mRealm.where(aClass).equalTo(where, (Integer) val).findFirst();
            } else if (val instanceof Double) {
                realmObject = mRealm.where(aClass).equalTo(where, (Double) val).findFirst();
            } else if (val instanceof Float) {
                realmObject = mRealm.where(aClass).equalTo(where, (Float) val).findFirst();
            } else if (val instanceof Boolean) {
                realmObject = mRealm.where(aClass).equalTo(where, (Boolean) val).findFirst();
            }
        }
        return realmObject != null;
    }

    public static boolean isExist(Class aClass, ArrayList<String> wheres, ArrayList<Object> val, Case aCase) {
        RealmObject realmObject;
        RealmQuery query = mRealm.where(aClass);
        if (aCase == null) aCase = Case.INSENSITIVE;
        if (wheres != null) {
            for (int i = 0; i < wheres.size(); i++) {
                if (val.get(i) instanceof String) {
                    query.equalTo(wheres.get(i), (String) val.get(i), aCase);
                } else if (val.get(i) instanceof Integer) {
                    query.equalTo(wheres.get(i), (Integer) val.get(i));
                } else if (val.get(i) instanceof Double) {
                    query.equalTo(wheres.get(i), (Double) val.get(i));
                } else if (val.get(i) instanceof Float) {
                    query.equalTo(wheres.get(i), (Float) val.get(i));
                } else if (val.get(i) instanceof Boolean) {
                    query.equalTo(wheres.get(i), (Boolean) val.get(i));
                }
            }
        }
        realmObject = query.findFirst();
        return realmObject != null;
    }

    /**
     * this function delete all record from given class of realm model
     */
    public static void deleteRealm(final Class aClass) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @SuppressWarnings("unchecked")
            @Override
            public void execute(Realm realm) {
                realm.clear(aClass);
            }
        });
    }

    /**
     * this function delete selected record base on give where condition.
     * you can only check single column/id/where and value at a time using this function
     */
    public static void deleteRealm(Class aClass, String where, Object val, Case aCase) {
        mRealm.beginTransaction();
        if (aCase == null) aCase = Case.INSENSITIVE;
        RealmResults results = null;
        if (where != null && val != null) {
            if (val instanceof String) {
                results = mRealm.where(aClass).equalTo(where, String.valueOf(val), aCase).findAll();
            } else if (val instanceof Integer) {
                results = mRealm.where(aClass).equalTo(where, (Integer) val).findAll();
            } else if (val instanceof Double) {
                results = mRealm.where(aClass).equalTo(where, (Double) val).findAll();
            } else if (val instanceof Float) {
                results = mRealm.where(aClass).equalTo(where, (Float) val).findAll();
            } else if (val instanceof Boolean) {
                results = mRealm.where(aClass).equalTo(where, (Boolean) val).findAll();
            }
        }
        if (results != null && !results.isEmpty()) {
            for (int i = 0; i < results.size(); i++) {
                results.get(i).removeFromRealm();
            }
        }
        mRealm.commitTransaction();
    }

    /**
     * this function delete selected record only
     */
    public static void deleteRealm(Class aClass, ArrayList<String> wheres, ArrayList<Object> val, Case aCase) {
        mRealm.beginTransaction();
        if (aCase == null) aCase = Case.INSENSITIVE;
        RealmQuery query = mRealm.where(aClass);
        RealmResults results;
        if (wheres != null) {
            for (int i = 0; i < wheres.size(); i++) {
                if (val.get(i) instanceof String) {
                    query.equalTo(wheres.get(i), (String) val.get(i), aCase);
                } else if (val.get(i) instanceof Integer) {
                    query.equalTo(wheres.get(i), (Integer) val.get(i));
                } else if (val.get(i) instanceof Double) {
                    query.equalTo(wheres.get(i), (Double) val.get(i));
                } else if (val.get(i) instanceof Float) {
                    query.equalTo(wheres.get(i), (Float) val.get(i));
                } else if (val.get(i) instanceof Boolean) {
                    query.equalTo(wheres.get(i), (Boolean) val.get(i));
                }
            }
        }
        results = query.findAll();
        if (results != null && !results.isEmpty()) {
            for (int i = 0; i < results.size(); i++) {
                results.get(i).removeFromRealm();
            }
        }
        mRealm.commitTransaction();
    }
}
