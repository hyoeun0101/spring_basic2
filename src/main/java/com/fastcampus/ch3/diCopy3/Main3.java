package com.fastcampus.ch3.diCopy3;



import com.google.common.reflect.ClassPath;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Component;


@Component
class Car{}
@Component class SportsCar extends Car{}
@Component class Truck extends Car{}
@Component class Engine{}
class AppContext{
    Map map;
    AppContext(){
       map = new HashMap();
       //컨포넌트 붙은 클래스 map에 저장
       doComponentScan();
    }

    private void doComponentScan() {
        //1. 패키지 내의 클래스 목록 가져오기
        //2. 반복문으로 클래스 하나씩 읽어와서 @Component 붙어있는지 확인
        //3. 붙어있으면 객체 생성해서 map에 저장
        try {
            ClassLoader classLoader = AppContext.class.getClassLoader();
            ClassPath classPath = ClassPath.from(classLoader);

            Set<ClassPath.ClassInfo> set = classPath.getTopLevelClasses("com.fastcampus.ch3.diCopy3");
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
}
public class Main3 {
    public static void main(String[] args) {
        //config.txt 파일을 읽어 맵으로 저장
        AppContext ac = new AppContext();

        Car car = (Car)ac.getBean("car");
        Engine engine = (Engine)ac.getBean("engine");
        System.out.println("car = " + car);
        System.out.println("engine = " + engine);
    }


}
