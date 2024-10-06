package com.example.iot_lab4_20191641.fragmentos;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.hardware.SensorEvent;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AlertDialog;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.iot_lab4_20191641.R;
import com.example.iot_lab4_20191641.adapter.LigaAdapter;
import com.example.iot_lab4_20191641.api.ApiRetrofit;
import com.example.iot_lab4_20191641.api.ApiRetrofit1;
import com.example.iot_lab4_20191641.dto.Ligas;
import com.example.iot_lab4_20191641.model.Liga;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragmentLigas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragmentLigas extends Fragment implements SensorEventListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragmentLigas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragmentLigas.
     */
    // TODO: Rename and change types and number of parameters
    public static fragmentLigas newInstance(String param1, String param2) {
        fragmentLigas fragment = new fragmentLigas();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    private RecyclerView recyclerView;
    private LigaAdapter ligaAdapter;
    private Button btnFetchLigas;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private boolean isAccelerometerAvailable;
    private float lastX, lastY, lastZ;
    private List<Liga> ligaList;
    private EditText buscarPais;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ligas, container, false);

        recyclerView = view.findViewById(R.id.ligas);
        btnFetchLigas = view.findViewById(R.id.button2);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        btnFetchLigas.setOnClickListener(v -> fetchLigas()); // se le consultó a chatgpt porque tenía errores en mi código anterior y no encontraba la forma de solucionarlo

        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            isAccelerometerAvailable = accelerometer != null;
        } else {
            isAccelerometerAvailable = false;
        }

        return view;
    }
    //acá le pregunté a chatgpt por qué no mostraba la lista cuando ponía el pais en el editText pero no logró darme una solución
    //creo que debí hacer otro model y adapter para adaptarlo el json que entrega la consulta de las ligas de un pais.
    private void fetchLigas() {
        ApiRetrofit1 apiService = ApiRetrofit.getRetrofitInstance().create(ApiRetrofit1.class);

        String pais = buscarPais.getText().toString().trim();
        Call<Ligas> call;

        if (pais.isEmpty()) {
            call = apiService.getAllLeagues();
        } else {
            call = apiService.getLeaguesByCountry(pais); //como daba error si ponía LigasParticular debido a que falta más implementación lo dejé así.
        }

        call.enqueue(new Callback<Ligas>() {
            @Override
            public void onResponse(Call<Ligas> call, Response<Ligas> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getLeagues() != null) {
                    ligaList = response.body().getLeagues();

                    if (ligaList != null && !ligaList.isEmpty()) {
                        ligaAdapter = new LigaAdapter(getContext(), ligaList);
                        recyclerView.setAdapter(ligaAdapter);
                    } else {
                        Toast.makeText(getContext(), "No se encontraron ligas", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Error al obtener ligas", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Ligas> call, Throwable t) {
                Toast.makeText(getContext(), "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API Error", t.getMessage());
            }
        });
    }

    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        float deltaX = x - lastX;
        float deltaY = y - lastY;
        float deltaZ = z - lastZ;
        lastX = x;
        lastY = y;
        lastZ = z;

        float acceleration = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);

        if (acceleration > SensorManager.SENSOR_DELAY_GAME) {
            mostrarDialogConfirmacion();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    private void mostrarDialogConfirmacion() {
        new AlertDialog.Builder(getContext())
                .setTitle("Eliminar últimos resultados")
                .setPositiveButton("Eliminar", (dialog, which) -> eliminarUltimosResultados())
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void eliminarUltimosResultados() {
            ligaAdapter.notifyItemRemoved(ligaList.size());
    }
}