package es.upm.dit.adsw.ejemplosbbdd;

import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Note;
import android.app.ListActivity;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends ListActivity {
	private PlanetasDbAdapter dbAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		dbAdapter = new PlanetasDbAdapter(this);
		dbAdapter.open();
		fillView0(dbAdapter.selectAll());
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		dbAdapter.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		try {
			switch (item.getItemId()) {
			case R.id.item1: {
				cargaInicial();
				fillView0(dbAdapter.selectAll());
				return true;
			}

			case R.id.item2: {
				fillView3(dbAdapter.selectAZ());
				return true;
			}

			case R.id.item3: {
				fillView3(dbAdapter.selectPattern("M"));
				return true;
			}
			
			case R.id.item4: {
				Planeta planeta = dbAdapter.selectNombre("Tierra");
				if (planeta == null)
					return false;
				dbAdapter.deletePlaneta(planeta);
				fillView0(dbAdapter.selectAll());				
				return true;
			}
			
			case R.id.item5: {
				dbAdapter.deleteAll();
				fillView3(dbAdapter.selectAll());
				return true;
			}
			
			case R.id.item6: {
				fillView1(dbAdapter.selectAll());
				return true;
			}
			
			case R.id.item7: {
				fillView2(dbAdapter.selectAll());
				return true;
			}
			
			case R.id.item8: {
				fillView3(dbAdapter.selectAll());
				return true;
			}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	protected void onListItemClick(ListView listView, View view, int position,
			long id) {
		super.onListItemClick(listView, view, position, id);
		ListAdapter listAdapter = listView.getAdapter();
		CursorAdapter cursorAdapter = (CursorAdapter) listAdapter;
		Cursor cursor = cursorAdapter.getCursor();
		cursor.moveToPosition(position);
		String nombre = cursor.getString(cursor
				.getColumnIndex(PlanetasDbAdapter.COL_NOMBRE));
		String name = cursor.getString(cursor
				.getColumnIndex(PlanetasDbAdapter.COL_NAME));

		String msg = String.format("nombre: %s%nname: %s", nombre, name);
		Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
		toast.show();
	}

	private void cargaInicial() {
		dbAdapter.insertPlaneta(new Planeta(0, "Tierra", "Earth", 6378,
				149597890, 24, 365, 0, 9.98));
		// http://www.espacioprofundo.com.ar/verarticulo/Datos_sobre_nuestro_sistema_solar.html
		dbAdapter.insertPlaneta(new Planeta(0, "Mercurio", "Mercury", 2440,
				57910000d, 1404, 87.97, 179, 2.78));
		dbAdapter.insertPlaneta(new Planeta(0, "Venus", "Venus", 6052,
				108200000d, 243, 224.7, 482, 8.87));
		dbAdapter.insertPlaneta(new Planeta(0, "Marte", "Mars", 3397, 227940000d,
				24.62, 686.98, -63, 3.72));
		dbAdapter.insertPlaneta(new Planeta(0, "Júpiter", "Jupiter", 71492,
				778330000d, 9.84, 11.86 * 365, -120, 22.88));
		dbAdapter.insertPlaneta(new Planeta(0, "Saturno", "Saturn", 60268,
				1429400000d, 10.23, 29.46 * 365, -125, 9.05));
		dbAdapter.insertPlaneta(new Planeta(0, "Urano", "Uranus", 25559,
				2870990000d, 17.9, 84.01 * 365, -210, 7.77));
		dbAdapter.insertPlaneta(new Planeta(0, "Neptuno", "Neptune", 24746,
				4504300000d, 16.11, 164.8 * 365, -200, 11));
		dbAdapter.insertPlaneta(new Planeta(0, "Plutón", "Pluto", 1160,
				5913520000d, 153, 248.54 * 365, -230, 0.4));
	}
	
	private void fillView0(Cursor cursor) {
		startManagingCursor(cursor);

		String[] columns = { PlanetasDbAdapter.COL_NOMBRE };
		int[] to = new int[] { android.R.id.text1 };

		SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_list_item_1, cursor, columns, to);
		setListAdapter(cursorAdapter);
	}

	private void fillView1(Cursor cursor) {
		startManagingCursor(cursor);

		String[] from = new String[] { PlanetasDbAdapter.COL_NOMBRE,
				PlanetasDbAdapter.COL_NAME, PlanetasDbAdapter.COL_RADIO,
				PlanetasDbAdapter.COL_DISTANCIA, PlanetasDbAdapter.COL_DIA,
				PlanetasDbAdapter.COL_ANNO, PlanetasDbAdapter.COL_T,
				PlanetasDbAdapter.COL_G };
		int[] to = new int[] { R.id.planeta_nombre, R.id.planeta_name,
				R.id.planeta_radio, R.id.planeta_distancia, R.id.planeta_dia,
				R.id.planeta_anno, R.id.planeta_temperatura,
				R.id.planeta_gravedad };

		SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this,
				R.layout.row1, cursor, from, to);
		setListAdapter(cursorAdapter);
	}

	private void fillView2(Cursor cursor) {
		startManagingCursor(cursor);

		String[] from = new String[] { PlanetasDbAdapter.COL_NOMBRE,
				PlanetasDbAdapter.COL_DIA, PlanetasDbAdapter.COL_ANNO };
		int[] to = new int[] { R.id.planeta_nombre, R.id.planeta_dia,
				R.id.planeta_anno };

		SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this,
				R.layout.row2, cursor, from, to);
		setListAdapter(cursorAdapter);
	}

	private void fillView3(Cursor cursor) {
		startManagingCursor(cursor);

		String[] from = new String[] { PlanetasDbAdapter.COL_NOMBRE,
				PlanetasDbAdapter.COL_DIA, PlanetasDbAdapter.COL_ANNO };
		int[] to = new int[] { R.id.planeta_nombre, R.id.planeta_dia,
				R.id.planeta_anno };

		SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this,
				R.layout.row3, cursor, from, to);
		setListAdapter(cursorAdapter);
	}
}
