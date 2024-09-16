package com.chenx.thinking.diff;

public class ExampleWrapper {
    private Example example;

    public Example getExample() {
        return example;
    }

    public void setExample(Example example) {
        this.example = example;
    }

    @Override
    public String toString() {
        return "ExampleWrapper{" +
                "example=" + example +
                '}';
    }
}
