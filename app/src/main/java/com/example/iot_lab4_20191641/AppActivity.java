package com.example.iot_lab4_20191641;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.iot_lab4_20191641.fragmentos.FragmentPosiciones;
import com.example.iot_lab4_20191641.fragmentos.FragmentResultados;
import com.example.iot_lab4_20191641.fragmentos.fragmentLigas;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AppActivity extends AppCompatActivity {

    private final Fragment fragmentLigas = new fragmentLigas();
    private final Fragment fragmentPosiciones = new FragmentPosiciones();
    private final Fragment fragmentResultados = new FragmentResultados();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_app);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment, fragmentLigas)
                    .commit();
        }

        bottomNavigationView.setOnItemSelectedListener(this::onNavigationItemSelected);

    }

    private boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;

        if (item.getItemId() == R.id.ligas){
            selectedFragment = fragmentLigas;

            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, selectedFragment).commit();
            return true;
        }

        if (item.getItemId() == R.id.posiciones){
            selectedFragment = fragmentPosiciones;

            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, selectedFragment).commit();
            return true;
        }

        if (item.getItemId() == R.id.resultados){
            selectedFragment = fragmentResultados;

            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, selectedFragment).commit();
            return true;
        }

        return false;
    }
}