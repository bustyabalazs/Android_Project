package com.example.android_project;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class ProfileDatabase_Impl extends ProfileDatabase {
  private volatile ProfileDAO _profileDAO;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `profile_table` (`id` INTEGER NOT NULL, `name` TEXT NOT NULL, `picture` TEXT NOT NULL, `address` TEXT NOT NULL, `phone` TEXT NOT NULL, `email` TEXT NOT NULL, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `restaurants_table` (`id` INTEGER NOT NULL, `name` TEXT NOT NULL, `address` TEXT NOT NULL, `city` TEXT NOT NULL, `state` TEXT NOT NULL, `area` TEXT NOT NULL, `postal_code` TEXT NOT NULL, `country` TEXT NOT NULL, `phone` TEXT NOT NULL, `lat` REAL NOT NULL, `lng` REAL NOT NULL, `price` REAL NOT NULL, `reserve_url` TEXT NOT NULL, `mobile_reserve_url` TEXT NOT NULL, `image_url` TEXT NOT NULL, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `restaurant_images` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `restaurant_id` INTEGER NOT NULL, `image_url` TEXT NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '0426984912ae0ce21c47e1fe771b900a')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `profile_table`");
        _db.execSQL("DROP TABLE IF EXISTS `restaurants_table`");
        _db.execSQL("DROP TABLE IF EXISTS `restaurant_images`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsProfileTable = new HashMap<String, TableInfo.Column>(6);
        _columnsProfileTable.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProfileTable.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProfileTable.put("picture", new TableInfo.Column("picture", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProfileTable.put("address", new TableInfo.Column("address", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProfileTable.put("phone", new TableInfo.Column("phone", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProfileTable.put("email", new TableInfo.Column("email", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysProfileTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesProfileTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoProfileTable = new TableInfo("profile_table", _columnsProfileTable, _foreignKeysProfileTable, _indicesProfileTable);
        final TableInfo _existingProfileTable = TableInfo.read(_db, "profile_table");
        if (! _infoProfileTable.equals(_existingProfileTable)) {
          return new RoomOpenHelper.ValidationResult(false, "profile_table(com.example.android_project.Profile).\n"
                  + " Expected:\n" + _infoProfileTable + "\n"
                  + " Found:\n" + _existingProfileTable);
        }
        final HashMap<String, TableInfo.Column> _columnsRestaurantsTable = new HashMap<String, TableInfo.Column>(15);
        _columnsRestaurantsTable.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRestaurantsTable.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRestaurantsTable.put("address", new TableInfo.Column("address", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRestaurantsTable.put("city", new TableInfo.Column("city", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRestaurantsTable.put("state", new TableInfo.Column("state", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRestaurantsTable.put("area", new TableInfo.Column("area", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRestaurantsTable.put("postal_code", new TableInfo.Column("postal_code", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRestaurantsTable.put("country", new TableInfo.Column("country", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRestaurantsTable.put("phone", new TableInfo.Column("phone", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRestaurantsTable.put("lat", new TableInfo.Column("lat", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRestaurantsTable.put("lng", new TableInfo.Column("lng", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRestaurantsTable.put("price", new TableInfo.Column("price", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRestaurantsTable.put("reserve_url", new TableInfo.Column("reserve_url", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRestaurantsTable.put("mobile_reserve_url", new TableInfo.Column("mobile_reserve_url", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRestaurantsTable.put("image_url", new TableInfo.Column("image_url", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysRestaurantsTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesRestaurantsTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoRestaurantsTable = new TableInfo("restaurants_table", _columnsRestaurantsTable, _foreignKeysRestaurantsTable, _indicesRestaurantsTable);
        final TableInfo _existingRestaurantsTable = TableInfo.read(_db, "restaurants_table");
        if (! _infoRestaurantsTable.equals(_existingRestaurantsTable)) {
          return new RoomOpenHelper.ValidationResult(false, "restaurants_table(com.example.android_project.RestaurantTable).\n"
                  + " Expected:\n" + _infoRestaurantsTable + "\n"
                  + " Found:\n" + _existingRestaurantsTable);
        }
        final HashMap<String, TableInfo.Column> _columnsRestaurantImages = new HashMap<String, TableInfo.Column>(3);
        _columnsRestaurantImages.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRestaurantImages.put("restaurant_id", new TableInfo.Column("restaurant_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRestaurantImages.put("image_url", new TableInfo.Column("image_url", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysRestaurantImages = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesRestaurantImages = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoRestaurantImages = new TableInfo("restaurant_images", _columnsRestaurantImages, _foreignKeysRestaurantImages, _indicesRestaurantImages);
        final TableInfo _existingRestaurantImages = TableInfo.read(_db, "restaurant_images");
        if (! _infoRestaurantImages.equals(_existingRestaurantImages)) {
          return new RoomOpenHelper.ValidationResult(false, "restaurant_images(com.example.android_project.RestaurantImages).\n"
                  + " Expected:\n" + _infoRestaurantImages + "\n"
                  + " Found:\n" + _existingRestaurantImages);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "0426984912ae0ce21c47e1fe771b900a", "60d55cabf65b442f01c87310a02db493");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "profile_table","restaurants_table","restaurant_images");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `profile_table`");
      _db.execSQL("DELETE FROM `restaurants_table`");
      _db.execSQL("DELETE FROM `restaurant_images`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public ProfileDAO profileDao() {
    if (_profileDAO != null) {
      return _profileDAO;
    } else {
      synchronized(this) {
        if(_profileDAO == null) {
          _profileDAO = new ProfileDAO_Impl(this);
        }
        return _profileDAO;
      }
    }
  }
}
