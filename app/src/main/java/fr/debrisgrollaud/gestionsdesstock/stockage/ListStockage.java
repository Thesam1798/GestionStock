package fr.debrisgrollaud.gestionsdesstock.stockage;

import android.app.ListActivity;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fr.debrisgrollaud.gestionsdesstock.BDD.relation.Stockage;
import fr.debrisgrollaud.gestionsdesstock.ListAdapter;
import fr.debrisgrollaud.gestionsdesstock.MainActivity;
import fr.debrisgrollaud.gestionsdesstock.R;
import fr.debrisgrollaud.gestionsdesstock.stockage.ModifierStockage;

//List des stockage
public class ListStockage extends ListActivity {

    ArrayList<HashMap<String, Object>> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HashMap<String, Object> item;
        List<String> listOption = new ArrayList<>();
        ArrayList<Integer> allert = null;

        list.clear();

        MainActivity.BDD.open();

        Cursor cursor = MainActivity.BDD.select("stockage");

        if (cursor != null){

            while (cursor.moveToNext()) {
                String id = cursor.getString(0);
                String nom = cursor.getString(1);
                String lieu = cursor.getString(2);
                String date = cursor.getString(3);

                Stockage stockage = new Stockage(Integer.parseInt(id),nom,lieu,date);

                item = null;
                item = new HashMap<>();
                item.put("nom",stockage.getNom());
                item.put("nombres","TODO"); //TODO : Get all usage
                item.put("allert", null);
                item.put("instence", stockage);
                listOption.add((String) item.get("nom"));

                list.add(item);
                item = null;
            }

        }

        try {
            ListAdapter adaptateur = new ListAdapter(this, list, listOption);
            setListAdapter(adaptateur);
        } catch (Exception e) {
            Log.e("ERROR",e.getMessage());
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        if (list.isEmpty()) return;

        HashMap<String, Object> info = list.get(position);

        Stockage stockage = (Stockage) info.get("instence");

        if (stockage == null) return;

        HashMap<String, String> params = new HashMap<>();

        params.put("id", String.valueOf(stockage.getId()));
        params.put("nom", stockage.getNom());
        params.put("date", stockage.getDateAjout());
        params.put("lieu", stockage.getLieu());

        MainActivity.setActivity(ModifierStockage.class, params);
    }
}
