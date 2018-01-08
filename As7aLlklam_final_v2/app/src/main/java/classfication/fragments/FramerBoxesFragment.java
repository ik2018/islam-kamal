package classfication.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.islamkamal.as7allklam.R;

/**
 * Created by islam kamal on 2/24/2017.
 */
public class FramerBoxesFragment extends Fragment {
    String type="المربعات الصعيدية";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.farmerboxes_fragment,container,false);
        return rootView;
    }
}
