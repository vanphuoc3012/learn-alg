package org.example.keywordextraction.common;

public class Word implements Comparable<Word>{
    private String word;
    private int tf;
    private int df;
    private double tfIdf;

    public Word(String word) {
        this.word = word;
        this.df = 1;
    }
    public void setDf(int df, int N) {
        this.df = df;
        tfIdf = tf * Math.log(Double.valueOf(N) / df);
    }

    public String getWord() {
        return word;
    }

    public int getTf() {
        return tf;
    }

    public int getDf() {
        return df;
    }

    public double getTfIdf() {
        return tfIdf;
    }

    public Word merge(Word other) {
        if (this.word.equals(other.word)) {
            this.tf += other.tf;
            this.df += other.df;
        }
        return this;
    }

    public void addTf() {this.tf++;}

    @Override
    public int compareTo(Word o) {
        return Double.compare(o.tfIdf, this.tfIdf);
    }


}
