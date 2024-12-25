package org.example;

public class Person {
    private String job;
    private int salary;
    private int id;
    private String city;
    private int year;
    private int age;

    // геттеры и сеттеры
    public String getJob() { return job; }
    public void setJob(String job) { this.job = job; }

    public int getSalary() { return salary; }
    public void setSalary(int salary) { this.salary = salary; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    @Override
    public String toString() {
        return "Person{" +
                "job='" + job + '\'' +
                ", salary=" + salary +
                ", id=" + id +
                ", city='" + city + '\'' +
                ", year=" + year +
                ", age=" + age +
                '}';
    }
}