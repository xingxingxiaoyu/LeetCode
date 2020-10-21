package art.bytecode.yuan;


import art.genericty.Addresser;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class TestMain {

    public static void main(String[] args) {
        System.out.println(System.getProperty("sun.arch.data.model"));

        Test test = new Test();
        B b = new B();

        System.out.println("替换前");
        System.out.println(test.a.selfIntroduction());

        TKt.replaceObject(test, "a", b);

        System.out.println("强转前");
        System.out.println(test.a.selfIntroduction());
        System.out.println(test.a.testField);
        System.out.println(test.a.getClass());

        System.out.println("强转后");
        Object obb = test.a;
        System.out.println(((B) obb).selfIntroduction());
        System.out.println(((B) obb).testField);

        TKt.getOffset();
    }
}

final class A {

    String selfIntroduction() {
        return "I'm A " + getClass().getName();
    }

    String testField = "I'm A field";
}

class B {

    String selfIntroduction() {
        return "I'm B " + getClass().getName();
    }

    //    int i = 1;
    Object object = new Object();
    String testField2 = "I'm B field22222222222";
    String testField = "I'm B field";

}

class Test {
    public final A a = new A();
}


class TKt {
    private static Unsafe unsafe;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void replaceObject(Object obj, String fieldName, Object newField) {

        try {
            Field nameField = Test.class.getDeclaredField(fieldName);
            long fieldOffset = unsafe.objectFieldOffset(nameField);
//            System.out.println(fieldOffset);
            unsafe.putObject(obj, fieldOffset, newField);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

    }

    public static void getOffset() {
        C c = new C();
//        byte[] bytes = new byte[1024 * 1024 * 1024];
//        byte[] bytes2 = new byte[1024 * 1024 * 1024];
//        byte[] bytes3 = new byte[1024 * 1024 * 1024];
//        byte[] bytes4 = new byte[1024 * 1024 * 1024];
        Field[] declaredFields = C.class.getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            long fieldOffset = unsafe.objectFieldOffset(declaredFields[i]);
            System.out.println(declaredFields[i].getName() + " " + fieldOffset);
            try {
                System.out.println("Addess: " + Long.toHexString(Addresser.addressOf(declaredFields[i].get(c))));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

class C {
    boolean valueBoolean;
    byte valueByte;
    short valueShort;
    int i;
    long valueLong;
    double d;
    float f;
    String s = "";
    int[] ints = new int[10];
    Object object = new Object();
    String s2 = "11";

}
