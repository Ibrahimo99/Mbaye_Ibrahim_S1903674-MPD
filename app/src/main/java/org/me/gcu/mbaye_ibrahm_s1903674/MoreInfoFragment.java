package org.me.gcu.mbaye_ibrahm_s1903674;
//  Ibrahim Mbaye S1903674

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import java.io.Serializable;
import java.util.List;

public class MoreInfoFragment extends Fragment {

    TextView magnitudeController;
    TextView depthController;
    TextView nearestController;
    List<EarthQuakeItem> list;
    public MoreInfoFragment() {
    }

    public static MoreInfoFragment newInstance(List<EarthQuakeItem> lists) {
        MoreInfoFragment f = new MoreInfoFragment();
        Bundle args = new Bundle();
        args.putSerializable("EarthQuakeItems", (Serializable) lists);
        f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_more_info, container, false);
        magnitudeController = view.findViewById(R.id.magnitude);
        depthController = view.findViewById(R.id.depth);
        nearestController = view.findViewById(R.id.nearest);
        Bundle bundle = getArguments();
        Serializable items = bundle.getSerializable("EarthQuakeItems");

        list = (List<EarthQuakeItem>) items;
        //System.out.println("MyList "+list.size());
        String magnitude =  getMaximumEarthQuake(4);  //for magnitude
        String depth =  getMaximumEarthQuake(3); //for depth
        magnitudeController.setText(magnitude);
        depthController.setText(depth);
        nearestController.setText(getNearestEarthQuake());
        return view;

    }


    public String getMaximumEarthQuake(int position) {
        String value = "";
        double value_double = 0;
        double max = 0;
        String description = "";
        String desc="";

        for (int i = 0; i < list.size(); i++) {
            description = list.get(i).getDescription();
            value = list.get(i).getDescription().split(";")[position];
            //System.out.println("value"+value);
            value =value.replaceAll("[^0-9.]", "");
            value_double = Double.parseDouble(value);

            if (value_double > max) {
                max = value_double;
                desc = description;
            }

        }
        return desc;
    }

    public String getNearestEarthQuake() {
        LatLng Glasgow = new LatLng(55.864237,-4.251806);
        LatLng current;
        double distance=0;
        double min_distance=0;
        String description = "";
        String descOutput="";

        //assume minimum distance is first distance value
        current = new LatLng(Double.parseDouble(list.get(0).getLatitude()),Double.parseDouble(list.get(0).getLongitude()));
        min_distance = SphericalUtil.computeDistanceBetween(Glasgow, current);
        //System.out.println("minDistanceInitial "+min_distance);
        //loop through to compare distance values in list
        for (int i = 0; i < list.size(); i++) {
            description = list.get(i).getDescription();
            current = new LatLng(Double.parseDouble(list.get(i).getLatitude()),Double.parseDouble(list.get(i).getLongitude()));
            distance = SphericalUtil.computeDistanceBetween(Glasgow, current);
            if (min_distance > distance) {
                min_distance = distance;
                //System.out.println("distance "+min_distance+" "+distance);
                descOutput = description ;
                //System.out.println("Description "+descOutput+"distance "+min_distance);
            }

        }
        return descOutput;
    }

}