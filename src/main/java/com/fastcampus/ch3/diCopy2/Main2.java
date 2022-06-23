package com.fastcampus.ch3.diCopy2;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

class Car{}
class SportsCar extends Car{}
class Truck extends Car{}
class Engine{}
class AppContext{
    Map map;
    AppContext(){
        try {
            Properties p = new Properties();
            p.load(new FileReader("config.txt"));
            map = new HashMap(p);
            for(Object key: map.keySet()){
                Class clazz = Class.forName((String)map.get(key));
                map.put(key,clazz.newInstance());
                //map에 저장
            }
            
        } catch (Exception e) {}
    }

    Object getBean(String key){
        return map.get(key);
    }
}
public class Main2 {
    public static void main(String[] args) throws Exception {
        //config.txt 파일을 읽어 맵으로 저장
        AppContext ac = new AppContext();

        Car car = (Car)ac.getBean("car");
        Engine engine = (Engine)ac.getBean("engine");
        System.out.println("car = " + car);
        System.out.println("engine = " + engine);
    }


}
