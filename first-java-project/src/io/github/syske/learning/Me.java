package io.github.syske.learning;

class Me {
    private String name;
    private String age;

    int height;
    double weight;

    protected double money;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    private class Test {
        // 我是内部类
    }
}
