import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneBook {

    //Регулярные выражения и паттерны, которые будут использоваться для проверки
    private static final String regexName = "^[а-яА-ЯёЁ]+";
    private static final Pattern patternName = Pattern.compile(regexName);
    private static final String regexPhone = "^79\\d{9}";
    private static final Pattern patternPhone = Pattern.compile(regexPhone);

    TreeMap<String, String> phoneBook = new TreeMap<>();

    public void addContact(String phone, String name) {

        boolean correctAll = correctName(name) && correctPhone(phone);

        //Если формат ввода номера и имени корректный и в поиске по номеру и по имени ничего не находится
        if (correctAll && getContactByName(name).equals(new TreeSet<>()) && getContactByPhone(phone).isEmpty()) {
            phoneBook.put(name, phone);
        }
        //Если такое имя уже есть, добавляем номер через запятую
        else if (!getContactByName(name).equals(new TreeSet<>())) {
            String phoneInBook = phoneBook.get(name);
            phoneInBook += ", " + phone;
            phoneBook.replace(name, phoneInBook);
        }
        //Если такой номер уже есть, перезаписываем контакт с новым именем
        else if (!getContactByPhone(phone).isEmpty()) {
            for (String key : phoneBook.keySet()) {
                if (phoneBook.get(key).equals(phone)) {
                    phoneBook.remove(key);
                }
            }
            phoneBook.put(name, phone);
        }
    }

    //Проверка имени на соответствие регулярному выражению
    public boolean correctName(String name) {

        Matcher matcher = patternName.matcher(name.trim());
        return matcher.matches();
    }

    //Проверка номера на соответствие регулярному выражению
    public boolean correctPhone(String phone) {

        Matcher matcher = patternPhone.matcher(phone.trim());
        return matcher.matches();
    }

    public String getContactByPhone(String phone) {
        String result = "";

        for (String key : phoneBook.keySet()) {
            if (phoneBook.get(key).contains(phone)) {
                result = key + " - " + phone;
            }
        }

        return result;
    }

    public Set<String> getContactByName(String name) {
        TreeSet<String> contacts = new TreeSet<>();

        for (String key : phoneBook.keySet()) {
            if (key.contains(name)) {
                contacts.add(key + " - " + phoneBook.get(key));
            }
        }
        return contacts;
    }

    public Set<String> getAllContacts() {
        TreeSet<String> allContacts = new TreeSet<>();

        for (String key : phoneBook.keySet()) {
            allContacts.add(key + " - " + phoneBook.get(key));
        }

        return allContacts;
    }
}