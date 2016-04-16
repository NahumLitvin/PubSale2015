package com.pubsale.app.fragments;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.example.pubsale2015.R;
import com.pubsale.client.PubServiceClient;
import com.pubsale.dto.CategoryDTO;

import java.util.List;

/**
 * Created by Nahum on 01/04/2016.
 */
public class CategoriesFragment extends Fragment {

    private Spinner category;
    private ArrayAdapter<CategoryDTO> categoryAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.categories_fragment, container, false);
        category = (Spinner) view.findViewById(R.id.sp_category_fragment);
        new GetCategories().execute();
        return view;
    }

    public CategoryDTO getSelectedCategory() {
        return (CategoryDTO) category.getSelectedItem();
    }

    public void setOnItemSelectedListener(AdapterView.OnItemSelectedListener onSelected) {
        category.setOnItemSelectedListener(onSelected);
    }




    private class GetCategories extends AsyncTask<Void, Void, List<CategoryDTO>> {

        @Override
        protected List<CategoryDTO> doInBackground(Void... voids) {
            return PubServiceClient.getInstance().GetCategories();
        }

        @Override
        protected void onPostExecute(List<CategoryDTO> response) {
            categoryAdapter = new ArrayAdapter<CategoryDTO>(getActivity().getApplicationContext(),
                    android.R.layout.simple_spinner_item, response);
            category.setAdapter(categoryAdapter);
        }
    }
}