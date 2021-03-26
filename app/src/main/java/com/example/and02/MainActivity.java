package com.example.and02;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.and02.ui.common.KeepStateNavigator;
import com.example.and02.ui.home.HomeFragment;
import com.example.and02.ui.sport.SportFragment;
import com.example.and02.ui.team.SearchResultBottomFragment;
import com.example.and02.ui.team.TeamFragment;
import com.example.and02.ui.touring.TouringFragment;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.Navigator;
import androidx.navigation.NavigatorProvider;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity implements SearchResultBottomFragment.ItemClickListener{

  private FragmentManager fragmentManager = getSupportFragmentManager();
//  private HomeFragment    homeFragment = new HomeFragment();
//  private TeamFragment    teamFragment = new TeamFragment();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Fresco.initialize(this);

//    FragmentTransaction transaction = fragmentManager.beginTransaction();
//    transaction.replace(R.id.navigation_home, homeFragment).commitAllowingStateLoss();

    setContentView(R.layout.activity_main);

//    navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//      @Override
//      public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        BottomNavigationView navView = findViewById(R.id.nav_view);
//        switch (item.getItemId()) {
//          case R.id.navigation_home:
//            transaction.replace(R.id.nav_host_fragment, new HomeFragment()).commitAllowingStateLoss();
//            return true;
//          case R.id.navigation_team:
//            transaction.replace(R.id.nav_host_fragment, new TeamFragment()).commitAllowingStateLoss();
//            return true;
//          case R.id.navigation_sport:
//            return true;
//        }
//        return false;
//      }
//    });
    // Passing each menu ID as a set of Ids because each
    // menu should be considered as top level destinations.
    BottomNavigationView navView = findViewById(R.id.nav_view);
    AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
      R.id.navigation_home, R.id.navigation_team, R.id.navigation_sport, R.id.navigation_touring)
      .build();
    NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

    Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
    KeepStateNavigator navigator = new KeepStateNavigator(this, navHostFragment.getChildFragmentManager(), R.id.nav_host_fragment);
    NavigatorProvider navigatorProvider = navController.getNavigatorProvider();

    navigatorProvider.addNavigator(navigator);

//    navController.setNavigatorProvider(navigator);
//    navController.navigatorProvider += navigator

//    KeepStateNavigator navigator = new KeepStateNavigator(var10002, var10003, 1000130);
//    NavigatorProvider $this$plusAssign$iv = navController.getNavigatorProvider();
//    int $i$f$plusAssign = false;
//    $this$plusAssign$iv.addNavigator((Navigator)navigator);
//    navController.setGraph(1600001);



//    navController.navigatorProvider += navigator
    NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    NavigationUI.setupWithNavController(navView, navController);

//    navView.setupWithNavController(navController);

  }


  @Override
  public void onItemClick(String item) {
    Log.i("item activer", "click");
  }
}