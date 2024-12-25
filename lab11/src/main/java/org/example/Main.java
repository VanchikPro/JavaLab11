package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        // загрузка данных
        ObjectMapper mapper = new ObjectMapper();
        List<Person> people = mapper.readValue(new File("src/main/resources/data.json"),
                new TypeReference<>() {
                });

        // реализация лямбда-выражений
        demonstrateLambdas(people);

        // применение Stream API
        demonstrateStreamAPI(people);
    }

    // метод для лямбд
    private static void demonstrateLambdas(List<Person> people) {
        // фильтрация по возрасту
        Predicate<Person> isYoung = p -> p.getAge() < 30;
        System.out.println("Жителей младше 30 лет: " + people.stream().filter(isYoung).count());

        // преобразование в строку
        Function<Person, String> toJobString = p -> "Работа: " + p.getJob();
        people.stream().map(toJobString).limit(5).forEach(System.out::println);

        // вывод объекта
        Consumer<Person> printPerson = System.out::println;
        people.stream().limit(5).forEach(printPerson);

        // создание нового пустого объекта
        Supplier<Person> newPersonSupplier = Person::new;
        System.out.println("\nНовый объект: " + newPersonSupplier.get());

        // самая высокая зарплата
        Comparator<Person> salaryComparator = Comparator.comparingInt(Person::getSalary);
        Person highestSalary = people.stream().max(salaryComparator).orElse(null);
        System.out.println("\nЖитель с самой высокой зарплата: " + highestSalary);

        // сложение зарплаты двух людей
        BiFunction<Person, Person, Integer> sumSalary = (p1, p2) -> p1.getSalary() + p2.getSalary();
        System.out.println("\nСумма зарплат первых двух: " + sumSalary.apply(people.get(0), people.get(1)));
    }

    // метод для Stream API
    private static void demonstrateStreamAPI(List<Person> people) {
        // фильтрация
        System.out.println("\nЖители младше 25 лет:");
        people.stream().filter(p -> p.getAge() < 25).limit(5).forEach(System.out::println);

        // сортировка
        System.out.println("\nСортировка по зарплате по возрастанию:");
        people.stream().sorted(Comparator.comparingInt(Person::getSalary)).limit(5).forEach(System.out::println);

        // ограничение
        System.out.println("\nПервые 5 жителей:");
        people.stream().limit(5).forEach(System.out::println);

        // преобразование
        System.out.println("\nПреобразование в список профессий:");
        people.stream().map(Person::getJob).distinct().forEach(System.out::println);

        // топ 10 зарплат младше 25 лет в городе София
        System.out.println("\nТоп 10 зарплат младше 25 лет в городе София:");
        people.stream()
                .filter(p -> p.getAge() < 25 && "София".equals(p.getCity()))
                .sorted(Comparator.comparingInt(Person::getSalary).reversed())
                .limit(10)
                .forEach(System.out::println);

        // количество программистов с зарплатой выше 50 тыс.
        long count = people.stream()
                .filter(p -> p.getSalary() > 50000 && "Программист".equals(p.getJob()))
                .count();
        System.out.println("\nКоличество программистов с зарплатой > 50 тыс.: " + count);

        // максимальная зарплата в городе София, в возрасте от 25 до 40
        int maxSalary = people.stream()
                .filter(p -> "София".equals(p.getCity()) && p.getAge() >= 25 && p.getAge() <= 40)
                .mapToInt(Person::getSalary)
                .max()
                .orElse(0);
        System.out.println("\nМаксимальная зарплата в городе София в возрасте от 25 до 40): " + maxSalary);

        // минимальный возраст для зарплат > 100 тыс. в городе София
        int minAge = people.stream()
                .filter(p -> p.getSalary() > 100000 && "София".equals(p.getCity()))
                .mapToInt(Person::getAge)
                .min()
                .orElse(0);
        System.out.println("\nМинимальный возраст в городе София с зарплатой > 100 тыс.: " + minAge);

        // группировки по профессиям и по городам с макс. зп
        System.out.println("\nГруппировка по профессиям:");
        Map<String, Long> groupByJob = people.stream()
                .collect(Collectors.groupingBy(Person::getJob, Collectors.counting()));
        groupByJob.forEach((job, count1) -> System.out.println(job + ": " + count1));

        System.out.println("\nГруппировка по городам с максимальной зарплатой:");
        Map<String, Optional<Person>> maxSalaryByCity = people.stream().limit(5)
                .collect(Collectors.groupingBy(Person::getCity,
                        Collectors.maxBy(Comparator.comparingInt(Person::getSalary))));
        maxSalaryByCity.forEach((city, person) -> System.out.println(city + ": " + person.orElse(null)));
    }
}