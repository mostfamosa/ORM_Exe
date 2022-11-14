package ORM_EXE;

import java.time.LocalDate;

public class User {
    private int id;
    private String name;
    private double weight;
    private int age;
    private char grade;
    private LocalDate birthDate;
    private Boolean employed;
 //   private Item item;

    public User(int id, String name, double weight, int age, char grade, LocalDate birthDate, Boolean employed) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.age = age;
        this.grade = grade;
        this.birthDate = birthDate;
        this.employed = employed;
       // this.item=item;
    }

//    public Item getItem() {
//        return item;
//    }

//    public void setItem(Item item) {
//        this.item = item;
//    }

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

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public char getGrade() {
        return grade;
    }

    public void setGrade(char grade) {
        this.grade = grade;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Boolean getEmployed() {
        return employed;
    }

    public void setEmployed(Boolean employed) {
        this.employed = employed;
    }
}