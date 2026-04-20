



class Animal {
    String name;
    String environment; // where the animal lives

    void eat() {
        System.out.println(name + " is eating in the " + environment);
    }
}

public class Test {
    public static void main(String[] args) {

        Animal fish = new Animal();

        fish.name = "Goldfish";

        fish.environment = "water";

        fish.eat();



        Animal bird = new Animal();

        bird.name = "Parrot";

        bird.environment = "air";

        bird.eat();
    }
}
