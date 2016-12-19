package com.example.wj.wordsapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.wj.wordsapp.word.Words;

import org.json.JSONException;

public class WordDetailActivity extends AppCompatActivity implements WordDetailFragment.OnFragmentInteractionListener{

    private static final String TAG = "myTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //如果是横屏的话直接退出
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }

        //    setContentView(R.layout.activity_word_detail);


        if (savedInstanceState == null) {
            WordDetailFragment detailFragment = new WordDetailFragment();
            detailFragment.setArguments(getIntent().getExtras());
            getFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, detailFragment)
                    .commit();
        }
    }

    @Override
    public void onWordDetailClick(String word) {
        ReadWordByYouDao wordByYouDao = new ReadWordByYouDao(word);
        wordByYouDao.start();
        try {
            wordByYouDao.join();
            Words.YouDaoWord youdaoWord = wordByYouDao.getYouDaoWord(wordByYouDao.getResultJson());
            Intent i = new Intent(WordDetailActivity.this, YouDaoAcitvity.class);
            i.putExtra("youdaoWord", youdaoWord);
            startActivity(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
