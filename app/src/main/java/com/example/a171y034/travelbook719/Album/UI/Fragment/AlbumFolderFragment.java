package com.example.a171y034.travelbook719.Album.UI.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.a171y034.travelbook719.Album.Adapter.AlbumFolderAdapter;
import com.example.a171y034.travelbook719.Album.Entity.AlbumFolderInfo;
import com.example.a171y034.travelbook719.Album.ImageLoader.ImageLoaderFactory;
import com.example.a171y034.travelbook719.Album.ImageLoader.ImageLoaderWrapper;
import com.example.a171y034.travelbook719.Album.UI.Fragment.base.BaseFragment;
import com.example.a171y034.travelbook719.Album.View.AlbumView;
import com.example.a171y034.travelbook719.R;

import java.io.Serializable;
import java.util.List;

/**
 * アルバムディレクトリページ
 *
 * @author Clock
 */
public class AlbumFolderFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    private static final String ARG_PARAM1 = "param1";

    private AlbumView mAlbumView;
    /**
     * アルバムディレクトリリスト
     */
    private List<AlbumFolderInfo> mAlbumFolderInfoList;
    private ListView mFolderListView;

    public AlbumFolderFragment() {
        // Required empty public constructor
    }

    /**
     * @param albumFolderInfoList アルバムディレクトリリスト
     * @return
     */
    public static AlbumFolderFragment newInstance(List<AlbumFolderInfo> albumFolderInfoList) {
        AlbumFolderFragment fragment = new AlbumFolderFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, (Serializable) albumFolderInfoList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAlbumFolderInfoList = (List<AlbumFolderInfo>) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_album_directory, container, false);
        mFolderListView = (ListView) rootView.findViewById(R.id.list_album);
        ImageLoaderWrapper loaderWrapper = ImageLoaderFactory.getLoader();
        AlbumFolderAdapter albumFolderAdapter = new AlbumFolderAdapter(mAlbumFolderInfoList, loaderWrapper);
        mFolderListView.setAdapter(albumFolderAdapter);
        mFolderListView.setOnItemClickListener(this);
        return rootView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AlbumView) {
            mAlbumView = (AlbumView) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mAlbumView = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent == mFolderListView) {
            if (mAlbumView != null) {
                AlbumFolderInfo albumFolderInfo = mAlbumFolderInfoList.get(position);
                mAlbumView.switchAlbumFolder(albumFolderInfo);
            }
        }
    }

}
