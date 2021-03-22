public class VarObject {
    int globalVarInt; // 全局变量
    char globalVarChar; // 全局
    byte globalVarByte; // 全局
    long globalVarLong; // 全局
    short globalVarShort; // 全局
    float globalVarFloat; // 全局
    double globalVarDouble; // 全局
    boolean globalVarBoolean; // 全局


    /**
     * 装箱
     */
    private void function2() {
        Character ch = 'c';
        Integer i = 12;
        Byte b = 12;
        Short s = 123;
        Long aLong = 123213L;

        System.out.println("Character ch = " + ch);
        System.out.println("Integer i = " + i);
        System.out.println("Byte b = " + b);
        System.out.println("Short s = " + s);
        System.out.println("Long aLong = " + aLong);
        // 手动装箱
        Integer integer0 = new Integer(12);
        System.out.println("Integer integer0 = " + integer0);
    }

    /**
     * 拆箱
     */
    private void function3() {
        Integer i = new Integer(16);
        int i2 = i;
        int i3 = i.intValue(); // 不知道这个算不算手动拆箱
        System.out.println("int i2 = " + i2);
        System.out.println("int i3 = " + i3);
    }


    public void function() {
        int localVarInt = 1; // 局部变量，必须初始化，否则会报编译错误
        System.out.println(localVarInt);
        System.out.println("全局变量globalVarInt的初始值：" + globalVarInt);
        System.out.println("全局变量globalVarChar的初始值：" + globalVarChar);
        System.out.println("全局变量globalVarByte的初始值：" + globalVarByte);
        System.out.println("全局变量globalVarLong的初始值：" + globalVarLong);
        System.out.println("全局变量globalVarShort的初始值：" + globalVarShort);
        System.out.println("全局变量globalVarFloat的初始值：" + globalVarFloat);
        System.out.println("全局变量globalVarDouble的初始值：" + globalVarDouble);
        System.out.println("全局变量globalVarBoolean的初始值：" + globalVarBoolean);
    }

    public static void main(String[] args) {
        new VarObject().function2();
    }
}
