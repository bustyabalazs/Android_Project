package com.example.android_project;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@SuppressWarnings({"unchecked", "deprecation"})
public final class ProfileDAO_Impl implements ProfileDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Profile> __insertionAdapterOfProfile;

  private final EntityInsertionAdapter<RestaurantTable> __insertionAdapterOfRestaurantTable;

  private final EntityInsertionAdapter<RestaurantImages> __insertionAdapterOfRestaurantImages;

  private final EntityDeletionOrUpdateAdapter<RestaurantTable> __deletionAdapterOfRestaurantTable;

  private final EntityDeletionOrUpdateAdapter<RestaurantImages> __deletionAdapterOfRestaurantImages;

  private final EntityDeletionOrUpdateAdapter<Profile> __updateAdapterOfProfile;

  public ProfileDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfProfile = new EntityInsertionAdapter<Profile>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR IGNORE INTO `profile_table` (`id`,`name`,`picture`,`address`,`phone`,`email`) VALUES (?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Profile value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        if (value.getPicture() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getPicture());
        }
        if (value.getAddress() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getAddress());
        }
        if (value.getPhone() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getPhone());
        }
        if (value.getEmail() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getEmail());
        }
      }
    };
    this.__insertionAdapterOfRestaurantTable = new EntityInsertionAdapter<RestaurantTable>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR IGNORE INTO `restaurants_table` (`id`,`name`,`address`,`city`,`state`,`area`,`postal_code`,`country`,`phone`,`lat`,`lng`,`price`,`reserve_url`,`mobile_reserve_url`,`image_url`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, RestaurantTable value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        if (value.getAddress() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getAddress());
        }
        if (value.getCity() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getCity());
        }
        if (value.getState() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getState());
        }
        if (value.getArea() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getArea());
        }
        if (value.getPostal_code() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getPostal_code());
        }
        if (value.getCountry() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getCountry());
        }
        if (value.getPhone() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getPhone());
        }
        stmt.bindDouble(10, value.getLat());
        stmt.bindDouble(11, value.getLng());
        stmt.bindDouble(12, value.getPrice());
        if (value.getReserve_url() == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, value.getReserve_url());
        }
        if (value.getMobile_reserve_url() == null) {
          stmt.bindNull(14);
        } else {
          stmt.bindString(14, value.getMobile_reserve_url());
        }
        if (value.getImage_url() == null) {
          stmt.bindNull(15);
        } else {
          stmt.bindString(15, value.getImage_url());
        }
      }
    };
    this.__insertionAdapterOfRestaurantImages = new EntityInsertionAdapter<RestaurantImages>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR IGNORE INTO `restaurant_images` (`id`,`restaurant_id`,`image_url`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, RestaurantImages value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getRestaurant_id());
        if (value.getImage_url() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getImage_url());
        }
      }
    };
    this.__deletionAdapterOfRestaurantTable = new EntityDeletionOrUpdateAdapter<RestaurantTable>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `restaurants_table` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, RestaurantTable value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__deletionAdapterOfRestaurantImages = new EntityDeletionOrUpdateAdapter<RestaurantImages>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `restaurant_images` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, RestaurantImages value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfProfile = new EntityDeletionOrUpdateAdapter<Profile>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `profile_table` SET `id` = ?,`name` = ?,`picture` = ?,`address` = ?,`phone` = ?,`email` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Profile value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        if (value.getPicture() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getPicture());
        }
        if (value.getAddress() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getAddress());
        }
        if (value.getPhone() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getPhone());
        }
        if (value.getEmail() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getEmail());
        }
        stmt.bindLong(7, value.getId());
      }
    };
  }

  @Override
  public Object addProfile(final Profile profile, final Continuation<? super Unit> p1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfProfile.insert(profile);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, p1);
  }

  @Override
  public Object addRestaurant(final RestaurantTable restaurant,
      final Continuation<? super Unit> p1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfRestaurantTable.insert(restaurant);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, p1);
  }

  @Override
  public Object addImage(final RestaurantImages restaurantImage,
      final Continuation<? super Unit> p1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfRestaurantImages.insert(restaurantImage);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, p1);
  }

  @Override
  public Object deleteRestaurant(final RestaurantTable restaurant,
      final Continuation<? super Unit> p1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfRestaurantTable.handle(restaurant);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, p1);
  }

  @Override
  public Object deleteImage(final RestaurantImages restaurantImage,
      final Continuation<? super Unit> p1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfRestaurantImages.handle(restaurantImage);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, p1);
  }

  @Override
  public Object updateProfile(final Profile profile, final Continuation<? super Unit> p1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfProfile.handle(profile);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, p1);
  }

  @Override
  public LiveData<Profile> readAllData() {
    final String _sql = "SELECT * FROM profile_table WHERE id=1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"profile_table"}, false, new Callable<Profile>() {
      @Override
      public Profile call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPicture = CursorUtil.getColumnIndexOrThrow(_cursor, "picture");
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final Profile _result;
          if(_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpPicture;
            _tmpPicture = _cursor.getString(_cursorIndexOfPicture);
            final String _tmpAddress;
            _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            final String _tmpPhone;
            _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            final String _tmpEmail;
            _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            _result = new Profile(_tmpId,_tmpName,_tmpPicture,_tmpAddress,_tmpPhone,_tmpEmail);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<RestaurantImages>> readImages(final int id) {
    final String _sql = "SELECT * FROM restaurant_images WHERE restaurant_id=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return __db.getInvalidationTracker().createLiveData(new String[]{"restaurant_images"}, false, new Callable<List<RestaurantImages>>() {
      @Override
      public List<RestaurantImages> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfRestaurantId = CursorUtil.getColumnIndexOrThrow(_cursor, "restaurant_id");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "image_url");
          final List<RestaurantImages> _result = new ArrayList<RestaurantImages>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final RestaurantImages _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpRestaurant_id;
            _tmpRestaurant_id = _cursor.getInt(_cursorIndexOfRestaurantId);
            final String _tmpImage_url;
            _tmpImage_url = _cursor.getString(_cursorIndexOfImageUrl);
            _item = new RestaurantImages(_tmpId,_tmpRestaurant_id,_tmpImage_url);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<RestaurantTable>> readRestaurants() {
    final String _sql = "SELECT * FROM restaurants_table";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"restaurants_table"}, false, new Callable<List<RestaurantTable>>() {
      @Override
      public List<RestaurantTable> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfCity = CursorUtil.getColumnIndexOrThrow(_cursor, "city");
          final int _cursorIndexOfState = CursorUtil.getColumnIndexOrThrow(_cursor, "state");
          final int _cursorIndexOfArea = CursorUtil.getColumnIndexOrThrow(_cursor, "area");
          final int _cursorIndexOfPostalCode = CursorUtil.getColumnIndexOrThrow(_cursor, "postal_code");
          final int _cursorIndexOfCountry = CursorUtil.getColumnIndexOrThrow(_cursor, "country");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
          final int _cursorIndexOfLat = CursorUtil.getColumnIndexOrThrow(_cursor, "lat");
          final int _cursorIndexOfLng = CursorUtil.getColumnIndexOrThrow(_cursor, "lng");
          final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "price");
          final int _cursorIndexOfReserveUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "reserve_url");
          final int _cursorIndexOfMobileReserveUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "mobile_reserve_url");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "image_url");
          final List<RestaurantTable> _result = new ArrayList<RestaurantTable>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final RestaurantTable _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpAddress;
            _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            final String _tmpCity;
            _tmpCity = _cursor.getString(_cursorIndexOfCity);
            final String _tmpState;
            _tmpState = _cursor.getString(_cursorIndexOfState);
            final String _tmpArea;
            _tmpArea = _cursor.getString(_cursorIndexOfArea);
            final String _tmpPostal_code;
            _tmpPostal_code = _cursor.getString(_cursorIndexOfPostalCode);
            final String _tmpCountry;
            _tmpCountry = _cursor.getString(_cursorIndexOfCountry);
            final String _tmpPhone;
            _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            final float _tmpLat;
            _tmpLat = _cursor.getFloat(_cursorIndexOfLat);
            final float _tmpLng;
            _tmpLng = _cursor.getFloat(_cursorIndexOfLng);
            final float _tmpPrice;
            _tmpPrice = _cursor.getFloat(_cursorIndexOfPrice);
            final String _tmpReserve_url;
            _tmpReserve_url = _cursor.getString(_cursorIndexOfReserveUrl);
            final String _tmpMobile_reserve_url;
            _tmpMobile_reserve_url = _cursor.getString(_cursorIndexOfMobileReserveUrl);
            final String _tmpImage_url;
            _tmpImage_url = _cursor.getString(_cursorIndexOfImageUrl);
            _item = new RestaurantTable(_tmpId,_tmpName,_tmpAddress,_tmpCity,_tmpState,_tmpArea,_tmpPostal_code,_tmpCountry,_tmpPhone,_tmpLat,_tmpLng,_tmpPrice,_tmpReserve_url,_tmpMobile_reserve_url,_tmpImage_url);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }
}
