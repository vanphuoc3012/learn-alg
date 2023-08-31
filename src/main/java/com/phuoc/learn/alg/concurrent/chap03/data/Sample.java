package com.phuoc.learn.alg.concurrent.chap03.data;

public abstract class Sample {

    /**
     * Method that returns the tag or class of the example
     *
     * @return The tag or class of examples
     */
    public abstract String getTag();

    /**
     * Method that return the values of the attributes of the example as an array of doubles
     *
     * @return The values of the attributes of the example
     */
    public abstract double[] getExample();
}
