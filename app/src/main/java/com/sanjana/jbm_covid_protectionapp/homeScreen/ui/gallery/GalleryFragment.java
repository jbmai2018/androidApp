package com.sanjana.jbm_covid_protectionapp.homeScreen.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sanjana.jbm_covid_protectionapp.R;
import com.sanjana.jbm_covid_protectionapp.homeScreen.ui.gallery.Adapters.GalleryImageAdapter;
import com.sanjana.jbm_covid_protectionapp.homeScreen.ui.gallery.ImageOnFullScreen.FullScreenImageActivity;
import com.sanjana.jbm_covid_protectionapp.homeScreen.ui.gallery.Interfaces.IRecyclerViewClickListener;

import java.io.File;


public class GalleryFragment extends Fragment {



    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;




    //private GalleryViewModel galleryViewModel;
//    GridView gridView;

//    private int count;
//    private Bitmap[] thumbnails;
//    private boolean[] thumbnailsselection;
//    private String[] arrPath;
//    private ImageAdapter imageAdapter;
//    ArrayList<String> f = new ArrayList<String>();// list of file paths
//    File[]            listFile;



    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
//        getFromSdcard();
//        GridView imagegrid = (GridView) root.findViewById(R.id.grid_view);
//        imageAdapter = new ImageAdapter(getActivity());
//        imagegrid.setAdapter(imageAdapter);




        recyclerView = (RecyclerView)root.findViewById(R.id.recyclerView);
        layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

//        Random random = new Random();
//        final String[] images = new String[10];
//        for (int i=0; i<images.length; i++){
//            images[i] = "http://picsum.photos/600?image=" + random.nextInt(1000+1);
//            Log.d("Path of image", images[i]);
//        }



        File folder = new File("/data/user/0/com.sanjana.jbm_covid_protectionapp/app_imageDir");
        final String[] images = folder.list();
        String temp = new String();

        if (images != null){
            for (int i =0; i< images.length; i++)
            {
                temp = images[i];
                images[i] = "/data/user/0/com.sanjana.jbm_covid_protectionapp/app_imageDir/" + temp;
//                Log.d("Path of image", images[i]);
            }
        }



//        int total = 0;
//        for (int i = 0; i< filenames.length; i++)
//        {
//            if (filenames[i].contains(".rbc"))
//            {
//                total++;
//            }
//        }

        final IRecyclerViewClickListener listener = new IRecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                //open Full Screen Activity With Image
                Intent i = new Intent(getActivity(), FullScreenImageActivity.class);
                i.putExtra("IMAGES", images);
                i.putExtra("POSITION", position);
                startActivity(i);
            }
        };

        GalleryImageAdapter galleryImageAdapter = new GalleryImageAdapter(getActivity(), images, listener);
        recyclerView.setAdapter(galleryImageAdapter);









        return root;
    }

//    public void getFromSdcard()
//    {
//        File file= new File("/data/user/0/com.sanjana.jbm_covid_protectionapp/app_imageDir");
//
//        if (file.isDirectory())
//        {
//            listFile = file.listFiles();
//
//
//            for (int i = 0; i < listFile.length; i++)
//            {
//
//                f.add(listFile[i].getAbsolutePath());
//
//            }
//        }
//    }

//    public class ImageAdapter extends BaseAdapter {
//        private LayoutInflater mInflater;
//
//        private Context context;
//        public ImageAdapter(Context context) {
//            this.context=context;
//            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        }
//
////        private LayoutInflater              inflater;
////        private List<ContactsContract.Data> items;
////        private Context context;
////        public ImageAdapter(Context context, List<ContactsContract.Data> items) {
////            this.context=context;
////            this.items = items;
////            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
////        }
//
//        public int getCount() {
//            return f.size();
//        }
//
//        public Object getItem(int position) {
//            return position;
//        }
//
//        public long getItemId(int position) {
//            return position;
//        }
//
//        public View getView(int position, View convertView, ViewGroup parent) {
//            ViewHolder holder;
//            if (convertView == null) {
//                holder = new ViewHolder();
//                convertView = mInflater.inflate(
//                        R.layout.galleryitem, null);
//                holder.imageview = (ImageView) convertView.findViewById(R.id.thumbImage);
//
//                convertView.setTag(holder);
//            }
//            else {
//                holder = (ViewHolder) convertView.getTag();
//            }
//
//
//            Bitmap myBitmap = BitmapFactory.decodeFile(f.get(position));
//            holder.imageview.setImageBitmap(myBitmap);
//            return convertView;
//        }
//    }
//    class ViewHolder {
//        ImageView imageview;
//
//    }
}