package com.zzz.myemergencyclientnew.base;

public class InstanceFactory {
    public static <T> T getInstance(Class clazz) {
        try {
            return (T) create(clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object create(Class mClass) throws IllegalAccessException, InstantiationException {
        switch (mClass.getSimpleName()) {
//            case "AdvisePresenter": return  new AdvisePresenter();
//            case "ArticlePresenter": return  new ArticlePresenter();
//            case "HomePresenter": return  new HomePresenter();
//            case "LoginPresenter": return  new LoginPresenter();
//            case "UserPresenter": return  new UserPresenter();
            default: return mClass.newInstance();
        }
    }
}
