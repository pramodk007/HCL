/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.heartcarelite.utility;


import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

class AssetSQLiteOpenHelper implements SupportSQLiteOpenHelper {
    private final OpenHelper mDelegate;

    AssetSQLiteOpenHelper(Context context, String name,
                          SQLiteDatabase.CursorFactory factory, int version,
                          DatabaseErrorHandler errorHandler,
                          Callback callback) {
        mDelegate = createDelegate(context, name, factory, version, errorHandler, callback);
    }

    private OpenHelper createDelegate(Context context, String name,
                                      SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler,
                                      final Callback callback) {
        return new OpenHelper(context, name, factory, version, errorHandler) {
            @Override
            public final void onCreate(SQLiteDatabase sqLiteDatabase) {
                mWrappedDb = new FrameworkSQLiteDatabase(sqLiteDatabase);
                callback.onCreate(mWrappedDb);
            }

            @Override
            public final void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
                callback.onUpgrade(getWrappedDb(sqLiteDatabase), oldVersion, newVersion);
            }

            @Override
            public final void onConfigure(SQLiteDatabase db) {
                callback.onConfigure(getWrappedDb(db));
            }

            @Override
            public final void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                callback.onDowngrade(getWrappedDb(db), oldVersion, newVersion);
            }

            @Override
            public void onOpen(SQLiteDatabase db) {
                callback.onOpen(getWrappedDb(db));
            }
        };
    }

    @Override
    public String getDatabaseName() {
        return mDelegate.getDatabaseName();
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void setWriteAheadLoggingEnabled(boolean enabled) {
        mDelegate.setWriteAheadLoggingEnabled(enabled);
    }

    @Override
    public SupportSQLiteDatabase getWritableDatabase() {
        return mDelegate.getWritableSupportDatabase();
    }

    @Override
    public SupportSQLiteDatabase getReadableDatabase() {
        return mDelegate.getReadableSupportDatabase();
    }

    @Override
    public void close() {
        mDelegate.close();
    }

    abstract static class OpenHelper extends SQLiteAssetHelper {

        FrameworkSQLiteDatabase mWrappedDb;

        OpenHelper(Context context, String name,
                   SQLiteDatabase.CursorFactory factory, int version,
                   DatabaseErrorHandler errorHandler) {
            super(context, name, null, factory, version, errorHandler);
        }

        SupportSQLiteDatabase getWritableSupportDatabase() {
            SQLiteDatabase db = super.getWritableDatabase();
            return getWrappedDb(db);
        }

        SupportSQLiteDatabase getReadableSupportDatabase() {
            SQLiteDatabase db = super.getReadableDatabase();
            return getWrappedDb(db);
        }

        FrameworkSQLiteDatabase getWrappedDb(SQLiteDatabase sqLiteDatabase) {
            if (mWrappedDb == null) {
                mWrappedDb = new FrameworkSQLiteDatabase(sqLiteDatabase);
            }
            return mWrappedDb;
        }

        @Override
        public synchronized void close() {
            super.close();
            mWrappedDb = null;
        }
    }
}
