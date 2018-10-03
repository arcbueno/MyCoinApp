package com.mycoin.augustobueno.mycoinapp.SuportClasses.Builder;

public class Names {


    private final String finalURL;
    private final StringBuilder sb;
    private final String method;

    public String getFinalURL() {
        return finalURL;
    }

    public StringBuilder getSb() {
        return sb;
    }

    public String getMethod() {
        return method;
    }

    private Names (Builder builder){

        this.finalURL = builder.finalURL;
        this.sb = builder.sb;
        this.method = builder.method;

    }

    public static class Builder{


        private String finalURL;
        private StringBuilder sb;
        private String method;


        public Builder finalURL(final String finalURL){
            this.finalURL = finalURL;
            return this;
        }
        public Builder sb (final StringBuilder sb){
            this.sb = sb;
            return this;
        }
        public Builder method(final String method){
            this.method=method;
            return this;
        }
        public Names build(){
            return new Names(this);
        }



    }
}
