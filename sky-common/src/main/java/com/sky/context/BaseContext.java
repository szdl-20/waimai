package com.sky.context;

public class BaseContext {

    public static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    public static Long getCurrentId() {
        return threadLocal.get();
    }

    public static void removeCurrentId() {
        threadLocal.remove();
    }
    // public static ThreadLocal<Long> th = new ThreadLocal<>();
    // public static void setCurrentId(Long id){
    //     th.set(id);

    // }
    // public static Long getCurrentId(){
    //     return th.get();
    // }
    // public static void removeCurrentId(){
    //     th.remove();
    // }


}
