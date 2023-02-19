public class Implementor {
    public static void main(String ... args) {
        Person user = new Person.Builder()
                          .firstName("Ivan")
                          .lastName("Ivanov")
                          .middleName("Ivanovich")
                          .country("Russia")
                          .gender("male")
                          .age(30)
                          .build();
        user.showInfo();
    }
}