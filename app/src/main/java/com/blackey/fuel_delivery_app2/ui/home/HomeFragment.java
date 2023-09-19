package com.blackey.fuel_delivery_app2.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.blackey.fuel_delivery_app2.NearbyFuelst;
import com.blackey.fuel_delivery_app2.OrderFuel;
import com.blackey.fuel_delivery_app2.R;
import com.blackey.fuel_delivery_app2.checkprice;

public class HomeFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //navigate Nearby Fuel type ui
        LinearLayout map1 = root.findViewById(R.id.linearLayout4);
        map1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),NearbyFuelst.class));
            }
        });
        //navigate orderFuel ui
        LinearLayout map = root.findViewById(R.id.linearLayout);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), OrderFuel.class));
            }
        });

        Button btn = root.findViewById(R.id.button7);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),checkprice.class));
            }
        });



//        ImageView navigationImageView = root.findViewById(R.id.imageView13);
//        navigationImageView.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(),orderFuel.class);
//                startActivity(intent);
//            }
//        });
//        public void order(View Object view;
//        view){
//            startActivity(new Intent(this, orderFuel.class));
//        }

        return root;
    }

}
