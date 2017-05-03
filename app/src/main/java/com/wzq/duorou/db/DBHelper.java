package com.wzq.duorou.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.wzq.duorou.chat.model.DisturbDao;
import com.wzq.duorou.chat.model.InviteMessgeDao;
import com.wzq.duorou.chat.model.TopUserDao;
import com.wzq.duorou.chat.model.UserDao;
import com.wzq.duorou.mvp.modle.BreedDao;
import com.wzq.duorou.mvp.modle.NewsDao;

/**
 * Created by wzq on 2017/3/13.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "djk";
    public static final int version = 1;
    public static final String CACHE = "cache";
    public static final String ID = "_id";
    public static final String URL = "url";
    public static final String DATA = "data";
    public static final String TIME = "time";


    private static final int DATABASE_VERSION = 6;
    private static DBHelper instance;

    private static final String USERNAME_TABLE_CREATE = "CREATE TABLE "
            + UserDao.TABLE_NAME + " ("
            + UserDao.COLUMN_NAME_NICK + " TEXT, "
            + UserDao.COLUMN_NAME_AVATAR + " TEXT, "
            + UserDao.COLUMN_NAME_ID + " TEXT PRIMARY KEY);";

    private static final String INIVTE_MESSAGE_TABLE_CREATE = "CREATE TABLE "
            + InviteMessgeDao.TABLE_NAME + " ("
            + InviteMessgeDao.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + InviteMessgeDao.COLUMN_NAME_FROM + " TEXT, "
            + InviteMessgeDao.COLUMN_NAME_GROUP_ID + " TEXT, "
            + InviteMessgeDao.COLUMN_NAME_GROUP_Name + " TEXT, "
            + InviteMessgeDao.COLUMN_NAME_REASON + " TEXT, "
            + InviteMessgeDao.COLUMN_NAME_STATUS + " INTEGER, "
            + InviteMessgeDao.COLUMN_NAME_ISINVITEFROMME + " INTEGER, "
            + InviteMessgeDao.COLUMN_NAME_UNREAD_MSG_COUNT + " INTEGER, "
            + InviteMessgeDao.COLUMN_NAME_TIME + " TEXT, "
            + InviteMessgeDao.COLUMN_NAME_GROUPINVITER + " TEXT); ";

    private static final String ROBOT_TABLE_CREATE = "CREATE TABLE "
            + UserDao.ROBOT_TABLE_NAME + " ("
            + UserDao.ROBOT_COLUMN_NAME_ID + " TEXT PRIMARY KEY, "
            + UserDao.ROBOT_COLUMN_NAME_NICK + " TEXT, "
            + UserDao.ROBOT_COLUMN_NAME_AVATAR + " TEXT);";

    private static final String CREATE_PREF_TABLE = "CREATE TABLE "
            + UserDao.PREF_TABLE_NAME + " ("
            + UserDao.COLUMN_NAME_DISABLED_GROUPS + " TEXT, "
            + UserDao.COLUMN_NAME_DISABLED_IDS + " TEXT);";


    private static final String CREATE_NEWS_TABLE = "CREATE TABLE "
            + NewsDao.TABLE_NAME + " ("
            + NewsDao.NEWS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NewsDao.NEWS_IMAGE + " TEXT, "
            + NewsDao.NEWS_TITLE + " TEXT, "
            + NewsDao.NEWS_PING + " TEXT, "
            + NewsDao.NEWS_TIME + " TEXT, "
            + NewsDao.NEWS_WHO + " TEXT, "
            + NewsDao.NEWS_LOCATION + " TEXT); ";

    private static final String CREATE_BREED_TABLE = "CREATE TABLE "
            + BreedDao.TABLE_NAME + " ("
            + BreedDao.BREED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + BreedDao.BREED_TITLE + " TEXT, "
            + BreedDao.BREED_IMAGE + " TEXT, "
            + BreedDao.BREED_TIME + " TEXT, "
            + BreedDao.BREED_LIKE_COUNT + " INTEGER, "
            + BreedDao.BREED_SHARE_FROM + " TEXT, "
            + BreedDao.BREED_PATH + " TEXT, "
            + BreedDao.BREED_REID + " INTEGER, "
            + BreedDao.BREED_LOCATION + " TEXT); ";

    /**
     * 会话置顶
     */
    private static final String TOP_TABLE_CREATE = "CREATE TABLE "
            + TopUserDao.TABLE_NAME + " ("
            + TopUserDao.COLUMN_NAME_ID + " TEXT PRIMARY KEY, "
            + TopUserDao.COLUMN_NAME_IS_GOUP + " TEXT, "
            + TopUserDao.COLUMN_NAME_TIME + " TEXT);";

    /**
     * 消息免打扰
     */
    private static final String DISTURB_TABLE_CREATE = "CREATE TABLE "
            + DisturbDao.TABLE_NAME + " ("
            + DisturbDao.USER_ID + " TEXT PRIMARY KEY);";

    public static DBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DBHelper(context.getApplicationContext());
        }
        return instance;
    }

    public DBHelper(Context context) {
        super(context, DBNAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS "
                + CACHE + " ("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + URL + " TEXT, "
                + TIME + " TEXT, "
                + DATA + " TEXT)";
        db.execSQL(sql);
        db.execSQL(USERNAME_TABLE_CREATE);
        db.execSQL(INIVTE_MESSAGE_TABLE_CREATE);
        db.execSQL(CREATE_PREF_TABLE);
        db.execSQL(ROBOT_TABLE_CREATE);
        db.execSQL(CREATE_NEWS_TABLE);
        db.execSQL(CREATE_BREED_TABLE);
        db.execSQL(TOP_TABLE_CREATE);
        db.execSQL(DISTURB_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + UserDao.TABLE_NAME + " ADD COLUMN " +
                    UserDao.COLUMN_NAME_AVATAR + " TEXT ;");
        }

        if (oldVersion < 3) {
            db.execSQL(CREATE_PREF_TABLE);
        }
        if (oldVersion < 4) {
            db.execSQL(ROBOT_TABLE_CREATE);
        }
        if (oldVersion < 5) {
            db.execSQL("ALTER TABLE " + InviteMessgeDao.TABLE_NAME + " ADD COLUMN " +
                    InviteMessgeDao.COLUMN_NAME_UNREAD_MSG_COUNT + " INTEGER ;");
        }
        if (oldVersion < 6) {
            db.execSQL("ALTER TABLE " + InviteMessgeDao.TABLE_NAME + " ADD COLUMN " +
                    InviteMessgeDao.COLUMN_NAME_GROUPINVITER + " TEXT;");
        }
    }

    public void closeDB() {
        if (instance != null) {
            try {
                SQLiteDatabase db = instance.getWritableDatabase();
                db.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            instance = null;
        }
    }
}
