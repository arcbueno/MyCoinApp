package com.mycoin.augustobueno.mycoinapp.SuportClasses.Builder;

import android.widget.ProgressBar;
import android.widget.TextView;

import com.mycoin.augustobueno.mycoinapp.SuportClasses.MyAsyncTask;

public class Views {


    private final TextView view;
    private final ProgressBar bar;

    public TextView getView() {
        return view;
    }

    public ProgressBar getBar() {
        return bar;
    }

    private Views (Builder builder){

        this.view = builder.view;
        this.bar = builder.bar;
    }

    public static class Builder{


        private TextView view;
        private ProgressBar bar;

        public Builder view(final TextView view){
            this.view = view;
            return this;
        }
        public Builder bar(final ProgressBar bar){
            this.bar = bar;
            return this;
        }

        public Views build(){
            return new Views(this);
        }



    }

}
