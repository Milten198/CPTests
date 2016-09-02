package com.sam_chordas.android.androidflavors.Data;


import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.test.ProviderTestCase2;
import android.test.mock.MockContentResolver;

import com.sam_chordas.android.androidflavors.data.FlavorsContract;
import com.sam_chordas.android.androidflavors.data.FlavorsProvider;

public class FlavorsProviderTest extends ProviderTestCase2<FlavorsProvider> {

    private static MockContentResolver resolver;
    String LogTag = FlavorsProviderTest.class.getName();
    Uri uri = FlavorsContract.FlavorEntry.CONTENT_URI;

    public FlavorsProviderTest() {
        super(FlavorsProvider.class, FlavorsContract.CONTENT_AUTHORITY);
    }

    public void deleteDatabase() {
        mContext.deleteDatabase(FlavorsContract.FlavorEntry.TABLE_FLAVORS);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        deleteDatabase();
        resolver = this.getMockContentResolver();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testInsert() {
        insertRow();
    }

    public void testQuery() {

        Cursor cursor = insertRow();
        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
            int columnIndex = cursor.getColumnIndex(FlavorsContract.FlavorEntry.COLUMN_DESCRIPTION);
            String description = cursor.getString(columnIndex);
            buffer.append(description);
        }
        assertTrue("Wrong description", buffer.toString().equals("desc"));
    }

    public void testGetType() {
        String mimeType = resolver.getType(uri);
        if (mimeType != null) {
            assertTrue("Wrong mime type", mimeType.equals(FlavorsContract.FlavorEntry.CONTENT_DIR_TYPE));
        }
    }

    public void testDelete() {

        insertRow();
        int rowsAffected;
        rowsAffected = resolver.delete(uri, null, null);
        assertTrue("Incorrect count of rows affected", rowsAffected == 1);
    }

    public void testUpdate() {
        Cursor cursor = insertRow();
        Uri uri = FlavorsContract.FlavorEntry.CONTENT_URI;
        ContentValues contentValues = new ContentValues();
        contentValues.put(FlavorsContract.FlavorEntry.COLUMN_DESCRIPTION, "csed");
        int rowsAffected;

        rowsAffected = resolver.update(uri, contentValues, FlavorsContract.FlavorEntry.COLUMN_DESCRIPTION + " = ? ", new String[]{"desc"});
        assertTrue("Incorrect count of rows affected", rowsAffected == 1);
    }

    public Cursor insertRow() {

        ContentValues contentValues = FlavorsContract.databaseValues();
        resolver.insert(FlavorsContract.FlavorEntry.CONTENT_URI, contentValues);
        Cursor cursor = resolver.query(FlavorsContract.FlavorEntry.CONTENT_URI, null, null, null, null);
        assertTrue("Cursor count is wrong", cursor.getCount() == 1);

        return cursor;
    }


}
