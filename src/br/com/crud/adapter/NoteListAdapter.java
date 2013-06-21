package br.com.crud.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.crud.R;
import br.com.crud.model.Note;

public class NoteListAdapter extends BaseAdapter {

	private Context context;
	private List<Note> notas;

	public NoteListAdapter(Context context, List<Note> notas) {
		this.context = context;
		this.notas = notas;
	}

	@Override
	public int getCount() {
		return notas.size();
	}

	@Override
	public Object getItem(int position) {
		return notas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Note note = notas.get(position);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.note_line_table, null);
		TextView nota = (TextView) view.findViewById(R.id.note_text);
		nota.setText(note.getNote());
		TextView id = (TextView) view.findViewById(R.id.id_text);
		Long indice = note.getId(); 
		id.setText(indice.toString());
		return view;
	}
}
