package com.example.iot_lab4_20191641.fragmentos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
public class fragmentLigas extends Fragment {

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ligas, container, false);

        recyclerView = view.findViewById(R.id.ligas);
        btnFetchLigas = view.findViewById(R.id.button2);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        btnFetchLigas.setOnClickListener(v -> fetchLigas()); // se le consultó a chatgpt porque tenía errores en mi código anterior y no encontraba la forma de solucionarlo

        return view;
    }

    private void fetchLigas() {
        ApiRetrofit1 apiService = ApiRetrofit.getRetrofitInstance().create(ApiRetrofit1.class);
        Call<Ligas> call = apiService.getAllLeagues();

        call.enqueue(new Callback<Ligas>() {
            @Override
            public void onResponse(Call<Ligas> call, Response<Ligas> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getLeagues() != null) {
                    List<Liga> ligaList = response.body().getLeagues();

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
}