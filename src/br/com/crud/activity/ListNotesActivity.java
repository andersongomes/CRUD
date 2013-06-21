package br.com.crud.activity;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import br.com.crud.R;
import br.com.crud.adapter.NoteListAdapter;
import br.com.crud.dao.NotesDAO;
import br.com.crud.model.Note;
import br.com.crud.util.CustomSQLiteOpenHelper;

public class ListNotesActivity extends ListActivity {

	public List<Note> notas;
	public NotesDAO dao;
	protected static final int INSERIR_EDITAR = 1;
	protected static final int BUSCAR = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dao = new NotesDAO(this);
		dao.open();
		atualizarLista();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//getMenuInflater().inflate(R.menu.menu_list, menu);
		super.onCreateOptionsMenu(menu);
		menu.add(0, INSERIR_EDITAR, 0, "Inserir Novo").setIcon(R.drawable.novo);
		//menu.add(0, BUSCAR, 0, "Buscar").setIcon(R.drawable.pesquisar);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == R.id.add_note) {
			Intent intent = new Intent(this, AddNoteActivity.class);
			startActivity(intent);
		}
		if (item.getItemId() == R.id.delete_note) {
			Intent intent = new Intent(this, DeleteNoteActivity.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}

	protected void atualizarLista() {
		// Pega a lista de carros e exibe na tela
		notas = dao.getAll();
		// Lista customizada
		setListAdapter(new NoteListAdapter(this, notas));
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
			case INSERIR_EDITAR:
				// Abre a tela com o formulário para adicionar
				startActivityForResult(new Intent(this, AddNoteActivity.class),
						INSERIR_EDITAR);
				break;
			case BUSCAR:
				// Abre a tela para buscar a nota pelo nota
				startActivity(new Intent(this, SearchNoteActivity.class));
				break;
		}
		return true;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		editNote(position);
	}

	protected void editNote(int position) {
		//Recupera a nota selecionada
		Note note = notas.get(position);
		//Cria a intent para abrir a tela
		Intent it = new Intent(this, EditNoteActivity.class);
		//Passa o ID da nota como parâmetro
		it.putExtra(CustomSQLiteOpenHelper.COLUMN_ID, note.getId());
		//Abre a tela de edição
		startActivityForResult(it, INSERIR_EDITAR);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//Quando a activity EditNoteActivity retornar, seja se foi para adicionar, atualizar ou excluir
		//Vamos atualizar a lista
		if(resultCode == RESULT_OK){
			atualizarLista();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// fecha o banco
		dao.close();
	}

}
