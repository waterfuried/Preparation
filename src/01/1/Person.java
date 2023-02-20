// 1. Создать builder для класса Person со следующими полями:
// String firstName, String lastName, String middleName, String country,
// String address, String phone, int age, String gender.
public class Person {
    private String firstName,
                   lastName,
                   middleName,
                   country,
                   address,
                   phone,
                   gender;
    private int age;

    public Person() {}

    public Person(Builder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.middleName = builder.middleName;
        this.country = builder.country;
        this.address = builder.address;
        this.phone = builder.phone;
        this.gender = builder.gender;
        this.age = builder.age;
    }

    // геттеры
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getMiddleName() { return middleName; }
    public String getCountry() { return country; }
    public String getAddress() { return address; }
    public String getPhone() { return phone; }
    public String getGender() { return gender; }
    public int getAge() { return age; }

    // сеттеры
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }
    public void setCountry(String country) { this.country = country; }
    public void setAddress(String address) { this.address = address; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setGender(String gender) { this.gender = gender; }
    public void setAge(int age) { this.age = age; }

    // методы, используемые для отображения свойств объекта
    private static boolean hasValue(String value) { return value != null && value.trim().length() > 0; }
    private static boolean hasValue(Integer value) { return value != null && value > 0; }

    // эти 2 метода имеют разные сигнатуры, но одинаковое тело;
    // к сожалению выводимые типы, появившиеся с Java 10,
    // не допускается использовать в качестве аргументов методов
    private void showProperty(String value, String prompt) {
        if (hasValue(value)) System.out.println((hasValue(prompt) ? prompt+": " : "") + value);
    }
    private void showProperty(Integer value, String prompt) {
        if (hasValue(value)) System.out.println((hasValue(prompt) ? prompt+": " : "") + value);
    }

    public void showInfo() {
        showProperty(firstName, "First name");
        showProperty(lastName, "Last name");
        showProperty(middleName, "Middle name");
        showProperty(gender, "Gender");
        showProperty(age, "Age");
        showProperty(country, "Country");
        showProperty(address, "Address");
        showProperty(phone, "Phone");
    }

    public static class Builder {
        private String firstName,
                       lastName,
                       middleName,
                       country,
                       address,
                       phone,
                       gender;
        private int age;

        public Builder() {}
        public Builder firstName(String name) { firstName = name; return this; }
        public Builder lastName(String name) { lastName = name; return this; }
        public Builder middleName(String name) { middleName = name; return this; }
        public Builder country(String country) { this.country = country; return this; }
        public Builder address(String address) { this.address = address; return this; }
        public Builder phone(String phone) { this.phone = phone; return this; }
        public Builder gender(String gender) { this.gender = gender; return this; }
        public Builder age(int age) { this.age = age; return this; }

        public Person build() { return new Person(this); }
    }
}