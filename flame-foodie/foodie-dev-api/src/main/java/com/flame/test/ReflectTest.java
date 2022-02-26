package com.flame.test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射test
 *  反射的四种方式：
 *     1.知道具体类的情况下可以使用： Class alunbarClass = TargetObject.class;
 *     2.通过 Class.forName()传入类的路径获取：Class alunbarClass1 = Class.forName("cn.javaguide.TargetObject");
 *     3.通过对象实例instance.getClass()获取：
 *      TargetObject o = new TargetObject();
 *      Class alunbarClass2 = o.getClass();
 *     4.通过类加载器xxxClassLoader.loadClass()传入类路径获取:Class clazz = ClassLoader.loadClass("cn.javaguide.TargetObject");
 * @author laihuibo
 */
public class ReflectTest {

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {

        // 获取TargetObject类的Class对象并且创建TargetObject类实例
        Class<?> targetClass = Class.forName("com.flame.test.TargetObject");
        TargetObject targerObject = (TargetObject) targetClass.newInstance();

        // 获取所有类中所有定义的方法
        Method[] methods = targetClass.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println("方法名称："+method.getName());
        }

        // 获取指定方法并调用
        Method publicMethods = targetClass.getDeclaredMethod("publicMethod", String.class);
        publicMethods.invoke(targerObject, "laihuibo");

        Field declaredField = targetClass.getDeclaredField("value");
        //为了对类中的参数进行修改我们取消安全检查
        declaredField.setAccessible(true);
        declaredField.set(targerObject, "xiaoming");

        // 调用 private 方法
        Method privateMethod = targetClass.getDeclaredMethod("privateMethod");
        //为了调用private方法我们取消安全检查
        privateMethod.setAccessible(true);
        privateMethod.invoke(targerObject);
    }
}
