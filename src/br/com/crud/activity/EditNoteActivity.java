package br.com.crud.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import br.com.crud.R;
import br.com.crud.dao.NotesDAO;
import br.com.crud.model.Note;
import br.com.crud.util.CustomSQLiteOpenHelper;

public class EditNoteActivity extends Activity {
	static final int RESULT_SALVAR = 1;
	static final int RESULT_EXCLUIR = 2;

	// Campos texto
	private EditText nota;
	private Long id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_note);
		nota = (EditText) findViewById(R.id.note_text);
		id = null;

		Bundle extras = getIntent().getExtras();
		// Se for pra editar, recupera os valores
		if (extras != null) {
			id = extras.getLong(CustomSQLiteOpenHelper.COLUMN_ID);
			if (id != null) {
				// É uma edição, busca o carro
				Note note = searchNote(id);
				nota.setText(note.getNote());
			}
		}

		ImageButton btCancelar = (ImageButton) findViewById(R.id.btCancelar);
		btCancelar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setResult(RESULT_CANCELED);
				// Fecha a tela
				finish();
			}
		});

		ImageButton btSalvar = (ImageButton) findViewById(R.id.btSalvar);
		btSalvar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				salvar();
			}

		});

		ImageButton btDeletar = (ImageButton) findViewById(R.id.btDeletar);
		if (id == null) {
			btDeletar.setVisibility(View.INVISIBLE);
		} else {
			btDeletar.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					excluir();
				}

			});
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		setResult(RESULT_CANCELED);
		finish();
	}

	protected void salvar() {
		Note note = new Note();
		NotesDAO dao = new NotesDAO(this);
		dao.open();
		if (id != null) {
			note.setId(id);
			note.setNote(nota.getText().toString());
			// Salvar
			dao.updateNote(note);
			finish();
		}
		
		note.setNote(nota.getText().toString());
		// Salvar
		dao.create(note.getNote());
		// OK
		setResult(RESULT_OK, new Intent());
		// Fecha a Tela
		dao.close();
		finish();
	}


	protected Note searchNote(Long id) {
		NotesDAO dao = new NotesDAO(this);
		dao.open();
		Note note = dao.searchById(id);
		dao.close();
		return note;
	}

	protected void excluir() {
		if (id != null) {
			NotesDAO dao = new NotesDAO(this);
			dao.open();
			Note note = dao.searchById(id);
			dao.delete(note);
			dao.close();
		}	
	}

}
