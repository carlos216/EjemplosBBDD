package es.upm.dit.adsw.ejemplosbbdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PlanetasDbAdapter {
	private static final String DATABASE_NAME = "SistemaSolar";
	private static final String DATABASE_TABLE = "planetas";
	private static final int DATABASE_VERSION = 2;

	public static final String COL_ID = "_id";
	public static final String COL_NOMBRE = "nombre"; // nombre en espanol
	public static final String COL_NAME = "name"; // nombre en ingles
	public static final String COL_RADIO = "radio"; // radio en Km
	public static final String COL_DISTANCIA = "distancia"; // distancia media
															// al Sol en Km
	public static final String COL_DIA = "dia"; // periodo rotacion sobre su eje
												// en horas
	public static final String COL_ANNO = "anno"; // orbita alrededor del sol en
													// dias
	public static final String COL_T = "temp"; // temperatura media superficial
												// en grados
	public static final String COL_G = "g"; // gravedad superficial en el
											// ecuador em m/s2

	private static final String TAG = "PlanetasDbAdapter";
	private DatabaseHelper dbHelper;
	private SQLiteDatabase db;

	private static final String DATABASE_CREATE = String.format(
			"CREATE TABLE %s (" 
	        + "%s INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ "%s TEXT," 
	        + "%s TEXT," 
			+ "%s REAL," 
	        + "%s REAL,"
			+ "%s REAL," 
	        + "%s REAL," 
			+ "%s REAL," 
	        + "%s REAL" 
			+ ");",
			DATABASE_TABLE, 
			COL_ID, 
			COL_NOMBRE, 
			COL_NAME, 
			COL_RADIO,
			COL_DISTANCIA, 
			COL_DIA, 
			COL_ANNO, 
			COL_T, 
			COL_G);

	private final Context context;

	private static class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}
	}

	public PlanetasDbAdapter(Context context) {
		this.context = context;
	}

	public PlanetasDbAdapter open() throws SQLException {
		dbHelper = new DatabaseHelper(context);
		db = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		dbHelper.close();
	}

	public long insertPlaneta(Planeta planeta) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(COL_NOMBRE, planeta.getNombre());
		initialValues.put(COL_NAME, planeta.getName());
		initialValues.put(COL_RADIO, planeta.getRadio());
		initialValues.put(COL_DISTANCIA, planeta.getDistancia());
		initialValues.put(COL_DIA, planeta.getDia());
		initialValues.put(COL_ANNO, planeta.getAnno());
		initialValues.put(COL_T, planeta.getTemperatura());
		initialValues.put(COL_G, planeta.getGravedad());

		return db.insert(DATABASE_TABLE, null, initialValues);
	}

	public int deletePlaneta(Planeta planeta) {
		if (planeta == null)
			return 0;
		String table = DATABASE_TABLE;
		String whereClause = COL_ID + "= ?";
		String[] whereArgs = { String.valueOf(planeta.getId()) };
		int n = db.delete(table, whereClause, whereArgs);
		return n;
	}

	public Cursor selectAll() {
		String table = DATABASE_TABLE;
		String[] columns = { COL_ID, COL_NOMBRE, COL_NAME, COL_RADIO,
				COL_DISTANCIA, COL_DIA, COL_ANNO, COL_T, COL_G };
		String selection = null;
		String[] selectionArgs = null;
		String groupBy = null;
		String having = null;
		String orderBy = null;
		Cursor cursor = db.query(table, columns, selection, selectionArgs,
				groupBy, having, orderBy);
		return cursor;
	}

	public Planeta selectNombre(String nombre) throws SQLException {
		try {
			String table = DATABASE_TABLE;
			String[] columns = { COL_ID, COL_NOMBRE, COL_NAME, COL_RADIO,
					COL_DISTANCIA, COL_DIA, COL_ANNO, COL_T, COL_G };
			String selection = COL_NOMBRE + "= ?";
			String[] selectionArgs = { nombre };
			String groupBy = null;
			String having = null;
			String orderBy = null;
			Cursor cursor = db.query(table, columns, selection, selectionArgs,
					groupBy, having, orderBy);

			cursor.moveToFirst();
			long id = cursor.getLong(cursor.getColumnIndex(COL_ID));
//			String nombre = cursor.getString(cursor.getColumnIndex(COL_NOMBRE));
			String name = cursor.getString(cursor.getColumnIndex(COL_NAME));
			double radio = cursor.getDouble(cursor.getColumnIndex(COL_RADIO));
			double distancia = cursor.getDouble(cursor
					.getColumnIndex(COL_DISTANCIA));
			double dia = cursor.getDouble(cursor.getColumnIndex(COL_DIA));
			double anno = cursor.getDouble(cursor.getColumnIndex(COL_ANNO));
			double temperatura = cursor.getDouble(cursor.getColumnIndex(COL_T));
			double gravedad = cursor.getDouble(cursor.getColumnIndex(COL_G));
			return new Planeta(id, nombre, name, radio, distancia, dia, anno,
					temperatura, gravedad);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public int updatePlaneta(Planeta planeta) {
		ContentValues values = new ContentValues();
		values.put(COL_NOMBRE, planeta.getNombre());
		values.put(COL_NAME, planeta.getName());
		values.put(COL_RADIO, planeta.getRadio());
		values.put(COL_DISTANCIA, planeta.getDistancia());
		values.put(COL_DIA, planeta.getDia());
		values.put(COL_ANNO, planeta.getAnno());
		values.put(COL_T, planeta.getTemperatura());
		values.put(COL_G, planeta.getGravedad());

		String table = DATABASE_TABLE;
		String selection = COL_ID + "= ?";
		String[] selectionArgs = { String.valueOf(planeta.getId()) };
		int n = db.update(table, values, selection, selectionArgs);
		return n;
	}

	public void deleteAll() {
		String sql = String.format("DELETE FROM %s;", DATABASE_TABLE);
		db.execSQL(sql);
	}

	public Cursor selectAZ() {
		String table = DATABASE_TABLE;
		String[] columns = { COL_ID, COL_NOMBRE, COL_NAME, COL_RADIO,
				COL_DISTANCIA, COL_DIA, COL_ANNO, COL_T, COL_G };
		String selection = null;
		String[] selectionArgs = null;
		String groupBy = null;
		String having = null;
		String orderBy = COL_NOMBRE + " ASC";
		Cursor cursor = db.query(table, columns, selection, selectionArgs,
				groupBy, having, orderBy);
		return cursor;
	}

	public Cursor selectPattern(String pattern) {
		String table = DATABASE_TABLE;
		String[] columns = { COL_ID, COL_NOMBRE, COL_NAME, COL_RADIO,
				COL_DISTANCIA, COL_DIA, COL_ANNO, COL_T, COL_G };
		String selection = COL_NOMBRE + " LIKE ?";
		String[] selectionArgs = { pattern + "%" };
		String groupBy = null;
		String having = null;
		String orderBy = COL_NOMBRE + " ASC";
		Cursor cursor = db.query(table, columns, selection, selectionArgs,
				groupBy, having, orderBy);
		return cursor;
	}
}
