package com.thebaileybrew.retailinventory.data;

import android.net.Uri;
import android.provider.BaseColumns;

public final class InventoryContract {

    private InventoryContract() {}

    public static final class InventoryEntry implements BaseColumns {
        //Database Table Name
        public final static String TABLE_NAME = "inventory";
        //Type: INTEGER
        public final static String _ID = BaseColumns._ID;
        //Type: TEXT
        public final static String PRODUCT_NAME = "product";
        //Type: FLOAT
        public final static String PRODUCT_PRICE = "price";
        //Type: INTEGER
        public final static String PRODUCT_QTY = "quantity";
        //Type: INTEGER
        public final static String PRODUCT_CATEGORY = "category";
        //Type: INTEGER
        public final static String PRODUCT_SUPPLIER = "supplier";
        //Type: TEXT
        public final static String PRODUCT_SUPPLIER_PHONE = "phone";
        //Type: TEXT
        public final static String PRODUCT_SUPPLIER_ADDRESS = "address";

        //Content Provider constants
        public static final String CONTENT_AUTHORITY = "com.thebaileybrew.retailinventory";

        public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

        public static final String PATH_INVENTORY = "inventory";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_INVENTORY);



        //Possible category types
        public static final int CATEGORY_GAME = 0;
        public static final int CATEGORY_HARDWARE = 1;
        public static final int CATEGORY_ACCESSORY = 2;
        public static final int CATEGORY_POWER = 3;

        //Possible supplier types
        public static final int SUPPLIER_SONY = 0;
        public static final int SUPPLIER_MICROSOFT = 1;
        public static final int SUPPLIER_NINTENDO = 2;
    }
}
