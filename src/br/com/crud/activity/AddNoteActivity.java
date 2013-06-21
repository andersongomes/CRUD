package br.com.crud.activity;

import br.com.crud.R;
import br.com.crud.dao.NotesDAO;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddNoteActivity extends Activity {

	private NotesDAO dao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_note);

		dao = new NotesDAO(this);
		dao.open();

		Button saveButton = (Button) findViewById(R.id.save_note_button);
		Button editButton = (Button) findViewById(R.id.update_button);
		final EditText oldNote = (EditText) findViewById(R.id.old_note_input);
		final EditText newNote = (EditText) findViewById(R.id.new_note_input);
		final EditText noteText = (EditText) findViewById(R.id.note_text);

		saveButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String note = noteText.getEditableText().toString();
				dao.create(note);
				finish();
				setContentView(R.layout.main);
			}
		});

		editButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String passNote = oldNote.getEditableText().toString();
				String futureNote = newNote.getEditableText().toString();
				dao.update(passNote, futureNote);
				finish();
				setContentView(R.layout.main);
			}
		});
	}

	@Override
	protected void onPause() {
		dao.close();
		super.onPause();
	}

	@Override
	protected void onResume() {
		dao.open();
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_add, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == R.id.main) {
			Intent intent = new Intent(this, ListNotesActivity.class);
			startActivity(intent);
		}
		if (item.getItemId() == R.id.delete_note) {
			Intent intent = new Intent(this, DeleteNoteActivity.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}

}
