package classfication.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.islamkamal.as7allklam.R;

/**
 * Created by islam kamal on 2/23/2017.
 */
public class PolticalFragment extends Fragment {
   // GridView gridView;
   // public static ArrayList<CharactersAdapter>list;
   // int type=0;
     Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context=container.getContext();
        View polticalView=inflater.inflate(R.layout.poltical_fragment,container,false);
         Intent intent=new Intent(context, MapsActivity.class);
        startActivity(intent);
       /*
        list=new ArrayList<>();

        list.add(0, new CharactersAdapter("islam kamal",R.drawable.ic_discount));
        list.add(1, new CharactersAdapter("khaled ali",R.drawable.ic_food));
        list.add(2, new CharactersAdapter("ahmed othman",R.drawable.ic_movie));
        list.add(3, new CharactersAdapter("sead ibrahim",R.drawable.ic_travel));
        list.add(4, new CharactersAdapter("noha safwat",R.drawable.photo));
        list.add(5, new CharactersAdapter("sandra nashat",R.drawable.profle));
        list.add(6, new CharactersAdapter("salah sllem",R.drawable.share3));

        gridView=(GridView)polticalView.findViewById(R.id.polticalgridView);
        type=2; // النوع السياسى
        String url="http://islamkamal.890m.com/twitter/writerProfile.php?type="+type+"&op=1";
        Log.d("Poltical :",url);
        new MyAsyncTack().execute(url);
        adapter=new WriterCharactersAdapter(list);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, Writer_Details_Activity.class);
                Bundle bun=new Bundle();
                bun.putInt("writer_id",list.get(position).getId());
                intent.putExtras(bun);
                startActivity(intent);
            }
        });
        */
        return polticalView;
    }
    /*
    private class WriterCharactersAdapter extends BaseAdapter {

        ArrayList<CharactersAdapter>characters_list;
        public WriterCharactersAdapter(ArrayList<CharactersAdapter> list1){
            characters_list=new ArrayList<>();
            characters_list=list1;
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
            LayoutInflater inflater=getActivity().getLayoutInflater();
            View characterView=inflater.inflate(R.layout.character_item, null);
            ImageView image=(ImageView)characterView.findViewById(R.id.charimageView2);
            TextView name=(TextView)characterView.findViewById(R.id.chartextView12);
           // Picasso.with(context).load(list.get(position).getPicture()).into(image);
            image.setImageResource(characters_list.get(position).getPicture());
            name.setText(characters_list.get(position).getName());
            return characterView;
        }
    }

    //upload data to server
    public class MyAsyncTack extends AsyncTask<String,String,String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected void onProgressUpdate(String... values) {
            Log.d("Poltical :","3-1");
            try {
                Log.d("Poltical :","3-2");

                JSONObject json ;
                Log.d("Poltical :","3-3");
                json = new JSONObject(values[0]);
                Log.d("Poltical :","3-4");
                try {
                    if (json.getString("msg") == null) {
                        Log.d("Poltical :","3-5");
                        return;
                    }
                } catch (JSONException e1) {
                    Log.d("Poltical :","3-6");
                    e1.printStackTrace();
                }
                if (json.getString("msg").equalsIgnoreCase("has data")) {
                    Log.d("Poltical :","3-7");
                    list.clear();
                    JSONArray tweets = new JSONArray(json.getString("info"));
                    Log.d("Poltical :","3-8");
                    for (int i = 0; i < tweets.length(); i++) {
                        JSONObject oneTweet = tweets.getJSONObject(i);
                        list.add(i, new CharactersAdapter(oneTweet.getInt("writer_id"), oneTweet.getString("name"), oneTweet.getInt("picture")));

                      Log.d("Poltical :",list.get(i).getName());

                    }
                    Log.d("Poltical :","3-9");
                } else if (json.getString("msg").equalsIgnoreCase("no tweet")) {
                    Log.d("Poltical :","3-10");
                    Log.d("Poltical :","empty query for poltical");
                }

            } catch (JSONException e) {
                Log.d("Poltical :","3-11");
                e.printStackTrace();
            }

        }

        @Override
        protected String doInBackground(String... params) {
            Log.d("Poltical :","1");
            try {
                String newData;
                URL url = new URL(params[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setConnectTimeout(30000);
                Log.d("Poltical :", "2");
                //get response data
                try {
                    Log.d("Poltical :","3");
                    InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                    //convert stream to string
                    Operations op = new Operations(getActivity());
                    newData = op.convertInputToString(inputStream);
                    // send o display data
                    publishProgress(newData);
                    Log.d("Poltical :", "4");
                } finally {
                    Log.d("Poltical :","5");
                    urlConnection.disconnect();
                }

            } catch (Exception e) {
            }
            Log.d("Poltical :","19");
            return null;
        }


    }
    */

}
