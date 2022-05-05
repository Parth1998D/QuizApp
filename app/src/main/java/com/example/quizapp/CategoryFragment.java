package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.quizapp.Common.Common;
import com.example.quizapp.Interface.ItemClickListener;
import com.example.quizapp.Model.Category;
import com.example.quizapp.ViewHolder.CategoryViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class CategoryFragment extends Fragment {

    View myFragment;
    RecyclerView listCategory;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Category, CategoryViewHolder> adapter;
    FirebaseDatabase database;
    DatabaseReference categories;
    public  static CategoryFragment newInstance()
    {
        CategoryFragment categoryFragment=new CategoryFragment();
        return categoryFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database=FirebaseDatabase.getInstance();
        categories=database.getReference("Category");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment= inflater.inflate(R.layout.fragment_category,container,false);
        listCategory=(RecyclerView)myFragment.findViewById(R.id.listCategory);
        listCategory.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(container.getContext());
        listCategory.setLayoutManager(layoutManager);
        loadCategories();
        return myFragment;
    }
    private void loadCategories(){
      // FirebaseRecyclerOptions<Category> options=new FirebaseRecyclerOptions.Builder<Category>().setQuery(categories,Category.class).build();
        adapter=new FirebaseRecyclerAdapter<Category, CategoryViewHolder>(
                Category.class,
                R.layout.category_layout,
                CategoryViewHolder.class,
                categories)
        {
            @Override
            protected void populateViewHolder(CategoryViewHolder categoryViewHolder, final Category category, int i) {
                categoryViewHolder.category_name.setText(category.getName());
                Picasso.with(getActivity()).load(category.getImage()).into(categoryViewHolder.category_image);
                categoryViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent startGame=new Intent(getActivity(),Start.class);
                        Common.categoryId=adapter.getRef(position).getKey();
                        Common.categoryName=category.getName();
                        Common.categoryBg=category.getBg();
                        startActivity(startGame);
                    }
                });
            }
        };
        adapter.notifyDataSetChanged();
        listCategory.setAdapter(adapter);
    }
}
