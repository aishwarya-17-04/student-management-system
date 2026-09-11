package model;

// ABSTRACTION: Abstract class Person defining a general entity. Cannot be instantiated directly.
public abstract class Person {
    // ENCAPSULATION: Private fields to restrict direct access to data.
    private String name;
    private int age;

    public Person(String name, int age) {
        // CORE KEYWORD: 'this' used to distinguish instance variables from constructor parameters.
        this.name = name;
        this.age = age;
    }

    // ENCAPSULATION: Public getters and setters for controlled access.
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

    // ABSTRACTION: Abstract method that forces subclasses to define their specific role.
    public abstract String getRole();

    public String display() {
        return "Name: " + name + "\nAge: " + age;
    }
}
