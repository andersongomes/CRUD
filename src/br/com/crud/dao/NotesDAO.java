package br.com.crud.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.crud.model.Note;
import br.com.crud.util.CustomSQLiteOpenHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class NotesDAO {

	private SQLiteDatabase database;
	private String[] columns = { CustomSQLiteOpenHelper.COLUMN_ID,
			CustomSQLiteOpenHelper.COLUMN_NOTES };
	private CustomSQLiteOpenHelper sqliteOpenHelper;

	public NotesDAO(Context context) {
		sqliteOpenHelper = new CustomSQLiteOpenHelper(context);
	}

	public void open() throws SQLException {
		database = sqliteOpenHelper.getWritableDatabase();
	}

	public void close() {
		sqliteOpenHelper.close();
	}

	public Note create(String note) {
		ContentValues values = new ContentValues();
		values.put(CustomSQLiteOpenHelper.COLUMN_NOTES, note);
		long insertId = database.insert(CustomSQLiteOpenHelper.TABLE_NOTES,
				null, values);
		Cursor cursor = database.query(CustomSQLiteOpenHelper.TABLE_NOTES,
				columns, CustomSQLiteOpenHelper.COLUMN_ID + " = " + insertId,
				null, null, null, null);
		cursor.moveToFirst();
		Note newNote = new Note();
		newNote.setId(cursor.getLong(0));
		newNote.setNote(cursor.getString(1));
		cursor.close();

		return newNote;
	}

	public void delete(Note note) {
		long id = note.getId();
		database.delete(CustomSQLiteOpenHelper.TABLE_NOTES,
				CustomSQLiteOpenHelper.COLUMN_ID + " = " + id, null);
	}

	// public int delete (String table, String whereClause, String[] whereArgs)
	public void deleteByNote(String note) {
		database.delete(CustomSQLiteOpenHelper.TABLE_NOTES,
				CustomSQLiteOpenHelper.COLUMN_NOTES + " LIKE '" + note + "'",
				null);
	}

	// public int updateWithOnConflict (String table, ContentValues values,
	// String whereClause, String[] whereArgs, int conflictAlgorithm)
	public void update(String oldNote, String newNote) {
		ContentValues args = new ContentValues();
		args.put(CustomSQLiteOpenHelper.COLUMN_NOTES, newNote);
		database.update(
				CustomSQLiteOpenHelper.TABLE_NOTES,
				args,
				CustomSQLiteOpenHelper.COLUMN_NOTES + " LIKE '" + oldNote + "'",
				null);
	}
	
	public void updateNote(Note note) {
		ContentValues args = new ContentValues();
		args.put(CustomSQLiteOpenHelper.COLUMN_NOTES, note.getNote());
		database.update(
				CustomSQLiteOpenHelper.TABLE_NOTES,
				args,
				CustomSQLiteOpenHelper.COLUMN_ID + " LIKE '" + note.getId() + "'",
				null);
	}

	public List<Note> getAll() {
		List<Note> notes = new ArrayList<Note>();

		Cursor cursor = database.query(CustomSQLiteOpenHelper.TABLE_NOTES,
				columns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Note note = new Note();
			note.setId(cursor.getLong(0));
			note.setNote(cursor.getString(1));
			notes.add(note);
			cursor.moveToNext();
		}
		cursor.close();

		return notes;
	}
	
	public Note searchById(Long id){
		Cursor cursor = database.query(CustomSQLiteOpenHelper.TABLE_NOTES, new String[] {"_id", "note"}, CustomSQLiteOpenHelper.COLUMN_ID + " = " + id , null, null, null, null);
		if(cursor.getCount() > 0){
			Note note = new Note();
			cursor.moveToFirst();
			note.setId(cursor.getLong(0));
			note.setNote(cursor.getString(1));
			return note;
		}
		return null;
	}
	
	public Note searchByNote(String note){
		Cursor cursor = database.query(CustomSQLiteOpenHelper.TABLE_NOTES, new String[] {"_id", "note"},  CustomSQLiteOpenHelper.COLUMN_NOTES + " = ?",  new String[]{note}, null, null, null);
		if(cursor.getCount() > 0){
			Note nota = new Note();
			cursor.moveToFirst();
			nota.setId(cursor.getLong(0));
			nota.setNote(cursor.getString(1));
			return nota;
		}
		return null;
	}

}
