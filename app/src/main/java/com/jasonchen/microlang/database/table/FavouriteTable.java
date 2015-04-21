package com.jasonchen.microlang.database.table;

/**
 * jasonchen
 * 2015/04/10
 */
public class FavouriteTable {

    public static final String TABLE_NAME = "favourite_table";
    //support multi user,so primary key can't be message id
    public static final String ID = "_id";
    //support mulit user
    public static final String ACCOUNTID = "accountid";

    public static final String TIMELINEDATA = "timelinedata";

    public static final String PAGE = "page";


    public static class FavouriteDataTable {

        public static final String TABLE_NAME = "favourite_data_table";
        //support multi user,so primary key can't be message id
        public static final String ID = "_id";
        //support mulit user
        public static final String ACCOUNTID = "accountid";
        //message id
        public static final String MBLOGID = "mblogid";

        public static final String JSONDATA = "json";

    }

}
