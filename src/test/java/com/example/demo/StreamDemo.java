package com.example.demo;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by v_zhangbing on 2017/6/11.
 * 学习下jdk8的流API, 看起来生产效率很高.
 */
public class StreamDemo {
    List<Person> persons = null;

    /**
     * 初始化一个集合
     */
    @Before
    public void init() {
        persons = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            Person person = new Person();
            person.setId(i);
            person.setAge(20 + i);
            person.setName("同学" + i);
            persons.add(person);
        }
    }


    @Test
    public void testStream() {
        //统计数量
        System.out.println(persons.stream().count());

        //筛选filter
        persons.stream()
                .filter(person -> person.getAge() > 20)
                .forEach(person -> System.out.println(person.toString()));

        //映射, 对流中的每个元素执行一个函数，使得元素转换成另一种类型输出, 得到另一个类型的流.
        List<String> names = persons.stream().map(person -> person.getName()).collect(Collectors.toList());
        names.forEach(s -> System.out.println(s));

        //合并流 flatMap: 对当前流执行一个函数, 函数里面要生产一个流, 然后把所有生产的流合成一个大流, 并返回.
        List<String> list = new ArrayList<String>();
        list.add("I am a boy");
        list.add("I love the girl");
        list.add("But the girl loves another girl");

        list.stream()
                .map(s -> s.split(" "))
                .flatMap(Arrays::stream)
                .forEach(System.out::println);


        // 是否匹配任一元素：anyMatch 返回true或false.  是否匹配所有元素：allMatch
        boolean match = persons.stream().anyMatch(person -> person.getAge() > 29);
        System.out.println("有人的年龄大于29么? " + match);

        // 归约 reduce, 归约是将集合中的所有元素(初始值作为第一个元素)经过指定运算，折叠成一个元素输出.
        // 本例作用是: 把所有元素相乘.
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        int value = numbers.stream().reduce(1, (integer, integer2) -> integer * integer2);
        System.out.println(value);


        // 数值流的使用, 采用reduce进行数值操作会涉及到基本数值类型和引用数值类型之间的装箱、拆箱操作，因此效率较低。当流操作为纯数值操作时，使用数值流能获得较高的效率。
        int sum = persons.stream().mapToInt(Person::getAge)
                .sum();
        System.out.println("数值流返回结果: " + sum);


    }


    public class Person {
        private int id;
        private String name;
        private int age;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
