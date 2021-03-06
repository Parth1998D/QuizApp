package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.quizapp.Model.QuestionScore;
import com.example.quizapp.ViewHolder.ScoreDetailViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ScoreDetail extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference question_score;

    RecyclerView scoreList;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<QuestionScore, ScoreDetailViewHolder> adapter;

    String viewUser="";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_detail);

        database=FirebaseDatabase.getInstance();
        question_score=database.getReference("Question_Score");

        scoreList=(RecyclerView)findViewById(R.id.scoreList);
        scoreList.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        scoreList.setLayoutManager(layoutManager);
        TextView un=(TextView)findViewById(R.id.un);

        if(getIntent()!=null)
            viewUser=getIntent().getStringExtra("viewUser");
        if(!viewUser.isEmpty())
            loadScoreDetail(viewUser);
        un.setText(viewUser);
    }

    private void loadScoreDetail(String viewUser)
    {
        adapter=new FirebaseRecyclerAdapter<QuestionScore, ScoreDetailViewHolder>(
                QuestionScore.class,
                R.layout.score_detail_layout,
                ScoreDetailViewHolder.class,
                question_score.orderByChild("user").equalTo(viewUser))
        {
            @Override
            protected void populateViewHolder(ScoreDetailViewHolder scoreDetailViewHolder, QuestionScore questionScore, int i)
            {
                scoreDetailViewHolder.txt_name.setText(questionScore.getCategoryName());
                scoreDetailViewHolder.txt_score.setText(questionScore.getScore());
            }
        };
        adapter.notifyDataSetChanged();
        scoreList.setAdapter(adapter);
    }

}
