package com.fastcampus.ch3.diCopy4;


import com.google.common.reflect.ClassPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


@Component
class Car{
    @Resource
    Engine engine;
//    @Resource
    Door door;

    @Override
    public String toString() {
        return "Car{" +
                "engine=" + engine +
                ", door=" + door +
                '}';
    }
}
@Component class SportsCar extends Car{}
@Component class Truck extends Car{}
@Component class Engine{}
@Component class Door{}
class AppContext{
    Map map;
    AppContext(){
       map = new HashMap();
       //컨포넌트 붙은 클래스 map에 저장
       doComponentScan();
       doAutowired();
       doResource();
    }

    private void doResource() {//by Name
        try {
            for(Object obj: map.values()){
                for(Field field : obj.getClass().getDeclaredFields()){
                    if(field.getAnnotation(Resource.class) != null){
                        field.set(obj,getBean(field.getName())); // Car의 engine에 map에서 engine을 찾아 대입
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void doAutowired() {//by Type
        //map에 저장된 객체의 필드(iv)중에 @Autowired가 붙어 있으면
        //map에서 iv의 타입과 같은 객체를 찾아 연결해주기
        try {
            for(Object obj: map.values()){
                for(Field field : obj.getClass().getDeclaredFields()){
                    if(field.getAnnotation(Autowired.class) != null){
                        field.set(obj,getBean(field.getType())); // Car의 engine에 map에서 engine을 찾아 대입
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void doComponentScan() {
        //1. 패키지 내의 클래스 목록 가져오기
        //2. 반복문으로 클래스 하나씩 읽어와서 @Component 붙어있는지 확인
        //3. 붙어있으면 객체 생성해서 map에 저장
        try {
            ClassLoader classLoader = AppContext.class.getClassLoader();
            ClassPath classPath = ClassPath.from(classLoader);

            Set<ClassPath.ClassInfo> set = classPath.getTopLevelClasses("com.fastcampus.ch3.diCopy4");
            for(ClassPath.ClassInfo classInfo: set){
                Class clazz = classInfo.load();
                Component component = (Component) clazz.getAnnotation(Component.class);
                //Component 붙어있으면 map에 저장
                if(component!=null){
                    String id = StringUtils.uncapitalize(classInfo.getSimpleName());
                    map.put(id, clazz.newInstance());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    Object getBean(String key){
        return map.get(key);
    }
    Object getBean(Class clazz){
        for(Object obj : map.values()){
            if(clazz.isInstance(obj))
                return obj;
        }
        return null;
    }
}
public class Main4 {
    public static void main(String[] args) {
        //config.txt 파일을 읽어 맵으로 저장
        AppContext ac = new AppContext();

        Car car = (Car)ac.getBean("car");
        Engine engine = (Engine)ac.getBean("engine");
        Door door = (Door) ac.getBean(Door.class);
        //수동으로 연결하기
//        car.engine = engine;
//        car.door = door;
        System.out.println("car = " + car);
        System.out.println("engine = " + engine);
        System.out.println("door = " + door);
    }


}
