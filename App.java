import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Animal {
    String name;
    String type;
    List<String> commands;

    public Animal(String name, String type) {
        this.name = name;
        this.type = type;
        this.commands = new ArrayList<>();
    }

    public void addCommand(String command) {
        commands.add(command);
    }

    public void displayCommands() {
        System.out.println("Команда для " + name + ":");
        for (String command : commands) {
            System.out.println(command);
        }
    }
}

class AnimalRegistry implements AutoCloseable {
    List<Animal> animals;
    private int count;

    public AnimalRegistry() {
        animals = new ArrayList<>();
        count = 0;
    }

    public void addAnimal(Animal animal) {
        animals.add(animal);
        count++;
    }

    public void listAnimals() {
        System.out.println("Список всех животных:");
        for (Animal animal : animals) {
            System.out.println(animal.name + " - " + animal.type);
        }
    }

    public void teachAnimal(String animalName, String command) {
        for (Animal animal : animals) {
            if (animal.name.equals(animalName)) {
                animal.addCommand(command);
                System.out.println("Команда добавлена для " + animalName);
                return;
            }
        }
        System.out.println("Животное с именем " + animalName + " не найдено.");
    }

    @Override
    public void close() throws Exception {
        if (count > 0) {
            throw new Exception("AnimalRegistry не был использован или был остановлен");
        }
    }
}

public class App {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in);
             AnimalRegistry animalRegistry = new AnimalRegistry()) {

            while (true) {
                displayMenu();

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.print("Введите имя животного: ");
                        String name = scanner.nextLine();
                        System.out.print("Введите тип животного: ");
                        String type = scanner.nextLine();
                        Animal animal = new Animal(name, type);
                        animalRegistry.addAnimal(animal);
                        break;
                    case 2:
                        animalRegistry.listAnimals();
                        break;
                    case 3:
                        System.out.print("Введите имя животного: ");
                        String animalName = scanner.nextLine();
                        for (Animal a : animalRegistry.animals) {
                            if (a.name.equals(animalName)) {
                                a.displayCommands();
                                break;
                            }
                        }
                        break;
                    case 4:
                        System.out.print("Введите имя животного: ");
                        String animalNameForTeaching = scanner.nextLine();
                        System.out.print("Команда для изучения: ");
                        String command = scanner.nextLine();
                        animalRegistry.teachAnimal(animalNameForTeaching, command);
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Неверный выбор, повторите.");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void displayMenu() {
        System.out.println("\n==== Animal Registry ====");
        System.out.println("1. Добавить животное");
        System.out.println("2. Список всех животных");
        System.out.println("3. Команды животных");
        System.out.println("4. Изучить новую команду");
        System.out.println("0. Выход");
        System.out.print("Выберите пункт меню: ");
    }
}
