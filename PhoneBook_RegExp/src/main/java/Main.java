import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static final String regexCommand = "^LIST";
    public static final Pattern pattern = Pattern.compile(regexCommand);

    public static void main(String[] args) throws IOException {

        PhoneBook phoneBook = new PhoneBook();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {

            System.out.println("Введите номер, имя или команду:");
            String inputFirst = reader.readLine();

            Matcher matcher = pattern.matcher(inputFirst);

            if (inputFirst.equals("0")) {
                break;
            }

            if (matcher.matches()) {
                System.out.println(phoneBook.getAllContacts());
                continue;
            }
            //Если первый ввод - имя
            if (phoneBook.correctName(inputFirst) && phoneBook.getContactByName(inputFirst).equals(new TreeSet<>())) {
                System.out.println("Такого имени в телефонной книге нет.");
                System.out.println("Введите номер телефона для абонента \"" + inputFirst + "\":");

                String inputSecond = reader.readLine();
                phoneBook.addContact(inputFirst, inputSecond);
                if (phoneBook.correctPhone(inputSecond)) {
                    phoneBook.addContact(inputSecond, inputFirst);
                    System.out.println("Контакт сохранен!\n");
                }
                else {
                    System.out.println("Неверный формат ввода");
                }
                continue;

            }
            //Если введенное имя уже есть
            else if (!phoneBook.getContactByName(inputFirst).equals(new TreeSet<>())) {
                System.out.println("Такое имя уже есть в телефонной книге.");
                System.out.println("Введите номер телефона для абонента \"" + inputFirst + "\":");

                String inputSecond = reader.readLine();
                phoneBook.addContact(inputFirst, inputSecond);
                if (phoneBook.correctPhone(inputSecond)) {
                    phoneBook.addContact(inputSecond, inputFirst);
                    System.out.println("Контакт сохранен!\n");
                }
                else {
                    System.out.println("Неверный формат ввода");
                }
            }
            //Если первый ввод - телефон
            if (phoneBook.correctPhone(inputFirst) && phoneBook.getContactByPhone(inputFirst).isEmpty()) {
                System.out.println("Такого номера нет в телефонной книге.");
                System.out.println("Введите имя абонента для номера \"" + inputFirst + "\":");

                String inputSecond = reader.readLine();

                if (phoneBook.correctName(inputSecond)) {
                    phoneBook.addContact(inputFirst, inputSecond);
                    System.out.println("Контакт сохранен!\n");
                }
                else {
                    System.out.println("Неверный формат ввода");
                }
            }
            //Если телефон уже есть в телефонной книге
            else if (!phoneBook.getContactByPhone(inputFirst).isEmpty()) {
                System.out.println("Такой номер есть в телефонной книге и принадлежит контакту \"" +
                        phoneBook.getContactByPhone(inputFirst).substring(0, phoneBook.getContactByPhone(inputFirst).indexOf(" ")) + "\"");
                System.out.println("Если вы хотите изменить имя абонента, введите его. Если хотите вернуться в начало, напишите START");
                String inputSecond = reader.readLine();

                if (phoneBook.correctName(inputSecond)) {
                    phoneBook.addContact(inputFirst, inputSecond);
                    System.out.println("Контакт сохранен!\n");
                }
                else {
                    System.out.println("Неверный формат ввода");
                }
            }

        }
    }
}
