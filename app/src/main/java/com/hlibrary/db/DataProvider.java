package com.hlibrary.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.j256.ormlite.android.apptools.OpenHelperManager;

public abstract class DataProvider extends ContentProvider {

    public static final class Constant {
        private static final String PACKAGENAME = "cn.itcast.providers.personprovider";
        // 表1
        private static final String TB_PERSON = "person";
        private static final int TB_PERSONS_CODE = 0x0001;
        private static final int TB_PERSON_CODE = 0x0002;
        // 表2
        private static final String TB_STUDENT = "student";
        private static final int TB_STUDENTS_CODE = 0x0003;
        private static final int TB_STUDENT_CODE = 0x0004;
    }

    private DatabaseHelper dbOpenHelper;
    private static final UriMatcher MATCHER = new UriMatcher(
            UriMatcher.NO_MATCH);

    static {
        MATCHER.addURI(Constant.PACKAGENAME, Constant.TB_PERSON,
                Constant.TB_PERSONS_CODE);
        MATCHER.addURI(Constant.PACKAGENAME, Constant.TB_PERSON + "/#",
                Constant.TB_PERSON_CODE);
        MATCHER.addURI(Constant.PACKAGENAME, Constant.TB_STUDENT,
                Constant.TB_STUDENTS_CODE);
        MATCHER.addURI(Constant.PACKAGENAME, Constant.TB_STUDENT + "/#",
                Constant.TB_STUDENT_CODE);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        int count = 0;
        switch (MATCHER.match(uri)) {
            case Constant.TB_PERSONS_CODE:
                count = db.delete(Constant.TB_PERSON, selection, selectionArgs);
                return count;

            case Constant.TB_PERSON_CODE: {
                long id = ContentUris.parseId(uri);
                String where = "id=" + id;
                if (selection != null && !"".equals(selection)) {
                    where = selection + " and " + where;
                }
                count = db.delete(Constant.TB_PERSON, where, selectionArgs);
            }
            return count;
            case Constant.TB_STUDENTS_CODE:
                count = db.delete("student", selection, selectionArgs);
                return count;
            case Constant.TB_STUDENT_CODE: {
                long id = ContentUris.parseId(uri);
                String where = "id=" + id;
                if (selection != null && !"".equals(selection)) {
                    where = selection + " and " + where;
                }
                count = db.delete("student", where, selectionArgs);
            }
            return count;
            default:
                throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
        }
    }

    @Override
    public String getType(Uri uri) {// ���ص�ǰ�������ݵ�mimeType
        switch (MATCHER.match(uri)) {
            case Constant.TB_PERSONS_CODE:
                return "vnd.android.cursor.dir/" + Constant.TB_PERSON;

            case Constant.TB_PERSON_CODE:
                return "vnd.android.cursor.item/" + Constant.TB_PERSON;
            case Constant.TB_STUDENTS_CODE:
                return "vnd.android.cursor.dir/" + Constant.TB_STUDENT;

            case Constant.TB_STUDENT_CODE:
                return "vnd.android.cursor.item/" + Constant.TB_STUDENT;

            default:
                throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {// /person
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        switch (MATCHER.match(uri)) {
            case Constant.TB_PERSON_CODE:
            case Constant.TB_PERSONS_CODE: {
                long rowid = db.insert("person", null, values);
                Uri insertUri = ContentUris.withAppendedId(uri, rowid);// �õ���������¼��Uri
                this.getContext().getContentResolver().notifyChange(uri, null);
                return insertUri;
            }
            case Constant.TB_STUDENT_CODE:
            case Constant.TB_STUDENTS_CODE: {
                long rowid = db.insert("student", null, values);
                Uri insertUri = ContentUris.withAppendedId(uri, rowid);// �õ���������¼��Uri
                this.getContext().getContentResolver().notifyChange(uri, null);
                return insertUri;
            }
            default:
                throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
        }
    }

    public abstract Class<? extends DatabaseHelper> getDatabaseClass();

    @Override
    public boolean onCreate() {
        dbOpenHelper = OpenHelperManager.getHelper(getContext(), getDatabaseClass());
        return true;
    }

    // ��ѯperson���е����м�¼ /person
    // ��ѯperson����ָ��id�ļ�¼ /person/10
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        switch (MATCHER.match(uri)) {
            case Constant.TB_PERSONS_CODE:
                return db.query("person", projection, selection, selectionArgs,
                        null, null, sortOrder);

            case Constant.TB_PERSON_CODE: {
                long id = ContentUris.parseId(uri);
                String where = "id=" + id;
                if (selection != null && !"".equals(selection)) {
                    where = selection + " and " + where;
                }
                return db.query("person", projection, where, selectionArgs, null,
                        null, sortOrder);
            }

            case Constant.TB_STUDENTS_CODE:
                return db.query("student", projection, selection, selectionArgs,
                        null, null, sortOrder);

            case Constant.TB_STUDENT_CODE: {
                long id = ContentUris.parseId(uri);
                String where = "id=" + id;
                if (selection != null && !"".equals(selection)) {
                    where = selection + " and " + where;
                }
                return db.query("student", projection, where, selectionArgs, null,
                        null, sortOrder);
            }
            default:
                throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        int count = 0;
        switch (MATCHER.match(uri)) {
            case Constant.TB_PERSONS_CODE:
                count = db.update("person", values, selection, selectionArgs);
                return count;

            case Constant.TB_PERSON_CODE:
                long id = ContentUris.parseId(uri);
                String where = "id=" + id;
                if (selection != null && !"".equals(selection)) {
                    where = selection + " and " + where;
                }
                count = db.update("person", values, where, selectionArgs);
                return count;

            default:
                throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
        }
    }

}
