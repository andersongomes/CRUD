package br.com.crud.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import br.com.crud.R;
import br.com.crud.dao.NotesDAO;

public class DeleteNoteActivity extends Activity {

	private NotesDAO dao;
	Button button;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.delete_note);
		
		button = (Button) findViewById(R.id.segunda_listagem_button);
		
		button.setOnClickListener(new View.OnClickListener() {
			 
			@Override
			public void onClick(View arg0) {
			 
				Intent trocatela = new
				Intent(DeleteNoteActivity.this, ListNotesActivity.class);
				DeleteNoteActivity.this.startActivity(trocatela);
				DeleteNoteActivity.this.finish();
			}
		});
		
		dao = new NotesDAO(this);
		dao.open();

		Button deleteButton = (Button) findViewById(R.id.delete_note_button);
		final EditText noteText = (EditText) findViewById(R.id.delete_input);

		deleteButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String note = noteText.getEditableText().toString();
				dao.deleteByNote(note);
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
		getMenuInflater().inflate(R.menu.menu_delete, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == R.id.add_note) {
			Intent intent = new Intent(this, AddNoteActivity.class);
			startActivity(intent);
		}
		if (item.getItemId() == R.id.main) {
			Intent intent = new Intent(this, ListNotesActivity.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}

}
