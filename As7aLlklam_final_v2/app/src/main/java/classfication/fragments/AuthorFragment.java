package classfication.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.islamkamal.as7allklam.R;

import java.util.ArrayList;

import writer.Writer_Details_Activity;

/**
 * Created by islam kamal on 2/24/2017.
 */
public class AuthorFragment extends Fragment {
    GridView gridView;
    ArrayList<CharactersAdapter>list;
    String type="الادباء";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final Context context=container.getContext();

        View authorView=inflater.inflate(R.layout.author_fragment,container,false);
        list=new ArrayList<>();

        list.add(0, new CharactersAdapter(1,"islam kamal",R.drawable.ic_discount));
        list.add(1, new CharactersAdapter(2,"khaled ali",R.drawable.ic_food));
        list.add(2, new CharactersAdapter(3,"ahmed othman",R.drawable.ic_movie));
        list.add(3, new CharactersAdapter(4,"sead ibrahim",R.drawable.ic_travel));
        list.add(4, new CharactersAdapter(5,"noha safwat",R.drawable.photo));
        list.add(5, new CharactersAdapter(6,"sandra nashat",R.drawable.profle));
        list.add(6, new CharactersAdapter(7,"salah sllem",R.drawable.share3));
        Log.d("test :", "1");
        gridView=(GridView)authorView.findViewById(R.id.polticalgridView);
        Log.d("test :","2");
        WriterCharactersAdapter adapter=new WriterCharactersAdapter(list);
        Log.d("test :","3");
        gridView.setAdapter(adapter);
        Log.d("test :", "4");
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("test :", "5");
                Intent intent = new Intent(context, Writer_Details_Activity.class);
                Log.d("test :", "6");
                startActivity(intent);
            }
        });
        Log.d("test :", "7");
        return authorView;
    }

    private class WriterCharactersAdapter extends BaseAdapter {

        ArrayList<CharactersAdapter> characters_list;
        public WriterCharactersAdapter(ArrayList<CharactersAdapter> list1){
            Log.d("test :","2-1");
            characters_list=new ArrayList<>();
            characters_list=list1;
            Log.d("test :","2-2");
        }

        @Override
        public int getCount() {
            return characters_list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
             Context context=convertView.getContext();
            Log.d("test :","2-3");
            LayoutInflater inflater=getActivity().getLayoutInflater();
            Log.d("test :","2-4");
            View characterView=inflater.inflate(R.layout.character_item, null);
            Log.d("test :","2-5");
            ImageView image=(ImageView)characterView.findViewById(R.id.charimageView2);
            TextView name=(TextView)characterView.findViewById(R.id.chartextView12);
            Log.d("test :","2-6");
            image.setImageResource(characters_list.get(position).getPicture());
            Log.d("test :", "2-7");
            name.setText(characters_list.get(position).getName());
            Log.d("test :", "2-8");
            return characterView;
        }
    }
}
