package com.thebaileybrew.retailinventory.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public final class InventoryContract {

    private InventoryContract() {}

    //Content Provider constants
    public static final String CONTENT_AUTHORITY = "com.thebaileybrew.retailinventory";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_INVENTORY = "inventory";
    public static final String PATH_GAMES = "games";

    public static final class InventoryEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_INVENTORY).build();
        public final static String TABLE_NAME = "gameinventory";
        public final static String _ID = BaseColumns._ID;
        public final static String PRODUCT_NAME = "product";
        public final static String PRODUCT_PRICE = "price";
        public final static String PRODUCT_QTY = "quantity";
        public final static String PRODUCT_SYSTEM = "system";

        public static Uri buildInventoryUri(long id) {
            return ContentUris.withAppendedId(BASE_CONTENT_URI, id);
        }
        //MIME types
        public static final String CONTENT_LIST_TYPE =
                "vnd.android.cursor.dir" + "/" + CONTENT_AUTHORITY + "/" + PATH_INVENTORY;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item" + "/" + CONTENT_AUTHORITY + "/" + PATH_INVENTORY;

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

    public static final class GameEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_GAMES).build();
        //Database Table Name
        public final static String TABLE_NAME = "gamedetails";
        public final static String _ID = BaseColumns._ID;
        public final static String GAME_NAME = "game";
        public final static String GAME_DEV = "developer";
        public final static String GAME_RELEASE = "releasedate";
        public final static String GAME_SYSTEM = "system";
        public final static String GAME_DEV_HOME = "developercountry";
        public final static String GAME_DEV_HQ_CITY = "developercity";

        public static Uri buildGameUri(long id) {
            return ContentUris.withAppendedId(BASE_CONTENT_URI, id);
        }
        //MIME types
        public static final String CONTENT_LIST_TYPE =
                "vnd.android.cursor.dir" + "/" + CONTENT_AUTHORITY + "/" + PATH_GAMES;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item" + "/" + CONTENT_AUTHORITY + "/" + PATH_GAMES;
    }
}
