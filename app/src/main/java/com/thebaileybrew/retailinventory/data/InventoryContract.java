package com.thebaileybrew.retailinventory.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class InventoryContract {

    private InventoryContract() {}

    public static final class InventoryEntry implements BaseColumns {
        //Database Table Name
        public final static String TABLE_NAME = "gameinventory";
        //Type: INTEGER
        public final static String _ID = BaseColumns._ID;
        //Type: TEXT
        public final static String PRODUCT_NAME = "product";
        //Type: FLOAT
        public final static String PRODUCT_PRICE = "price";
        //Type: INTEGER
        public final static String PRODUCT_QTY = "quantity";
        //Type: INTEGER
        public final static String PRODUCT_SYSTEM = "system";


        //Content Provider constants
        public static final String CONTENT_AUTHORITY = "com.thebaileybrew.retailinventory";

        public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

        public static final String PATH_INVENTORY = "inventory";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_INVENTORY);

        //MIME types
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INVENTORY;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INVENTORY;



        //Possible category types
        public static final int SYSTEM_PS3 = 0;
        public static final int SYSTEM_PS4 = 1;
        public static final int SYSTEM_XBOXONE = 2;
        public static final int SYSTEM_N3DS = 3;
        public static final int SYSTEM_NSWITCH = 4;
        public static final int SYSTEM_UNKNOWN = 5;

        public static boolean isValidSystem(int system) {
            if (system == SYSTEM_PS3
                    || system == SYSTEM_PS4
                    || system == SYSTEM_XBOXONE
                    || system == SYSTEM_N3DS
                    || system == SYSTEM_NSWITCH) {
                return true;
            } else return false;
        }


    }
}
