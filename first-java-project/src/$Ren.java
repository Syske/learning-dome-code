public class $Ren {
    public static void main(String[] args) {
        // 赋值运算
        int a = 6;
        System.out.println(a);

        int b = 2;
        int c = 3;
        // 加法运算
        int d = b + c;
        System.out.println(d);
        // 减法运算
        int e = c - b;
        System.out.println(e);

        // 乘法运算
        int f = c * b;
        System.out.println(f);

        // 除法
        int g = a / c;
        System.out.println(g);
        // 复杂四则运算
        int h = a + c - a * 2 / c * (a + b) * 2;
        System.out.println(h);

        // a对b取余
        int j = c % b;
        System.out.println(j);

        if (a > 0) {
            System.out.println("a大于0");
        } else {
            System.out.println("a小于等于0");
        }

        byte bbb = 123;
        char ch = 123;
        long ls = 123;
        int s = 123;
        String str = "123";
        Double cs = new Double("123");
        a = 3;
        switch (a) {
            case 1:
                // 当a为1时，要执行的操作
                System.out.println("a的值是1");
//                break;
            case 2:
                // a为2时，要执行的操作
                System.out.println("a的值是2");
//                break;
            case 3:
                // a为3时，要执行的操作
                System.out.println("a的值是3");
//                break;
            default:
                // 默认值，即a不等于上面所有值是执行的操作
                System.out.println("a的值不在1、2、3中");
//                break;
        }
        a = 3;
        c = a > 0 ? a : 0;
        System.out.println("c = " + c);

        /*for (int i = 0; i < 1000; i++) {
            System.out.println("我是云中志");
        }
        int i = 0;
        for (; i < 1000;) {
            System.out.println("我是云中志");
            i++;
        }
        int k = 0;
        while (k < 1000) {
            System.out.println("我是云中志");
            i++;
        }*/

        int m = -3;
        do {
            System.out.println("我是云中志");
            m++;
        } while (m > 0);
        System.out.println("m的值:" + m);
    }
}
