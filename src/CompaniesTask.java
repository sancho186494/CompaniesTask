import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CompaniesTask {

    private static List<Company> listOfCompanies;
    private static String inputDate;
    private static Currency inputCurrency;
    private static LocalDate currentDate = LocalDate.now();
    private static AtomicInteger stocksSumm = new AtomicInteger();
    private static String datePattern;


    public static void main(String[] args) {

        System.out.println("Укажите адрес файла Json:");
        listOfCompanies = parseJsonAndGetList();
        assert listOfCompanies != null;
        System.out.println("Укажите дату:");
        inputDate = readSystemIn();
        System.out.println("Укажите валюту:");
        inputCurrency = Currency.getInstance(readSystemIn());

        //установка паттерна даты в зависимости от введенного значения
        String[] temp = inputDate.split("\\W");
        if (temp[2].length() == 2) {
            inputDate = temp[0] + "." + temp[1] + "." + temp[2];
            datePattern = "dd.MM.yy";
        } else {
            inputDate = temp[0] + "." + temp[1] + "." + temp[2];
            datePattern = "dd.MM.yyyy";
        }
        
        //Задание 1: Вывести все имеющиеся компании в формате «Краткое название» – «Дата основания 17/01/98»
        System.out.println("\n-------------Список компаний---------------");
        listOfCompanies.forEach(company ->
                System.out.println(String.format("%s - дата основания %s",
                        company.getCompanyName(),
                        company.getRegistrationDate())));

        //Задание 2: Вывести все ценные бумаги (их код, дату истечения и полное название организации-владельца),
        //           которые просрочены на текущий день, а также посчитать суммарное число всех таких бумаг
        System.out.println("\n-------------Список недействительных акций на текущую дату---------------");
        listOfCompanies.forEach(company -> company.getStocks().forEach(stock -> {
            if (stock.getValidityDateObj().isBefore(currentDate)) {
                System.out.println(String.format("%s | Действительны до: %s | Владелец: %s",
                        stock.getCode(),
                        stock.getValidity(),
                        stock.getOwner().getCompanyName()));
                stocksSumm.addAndGet(stock.getCount());
            }
        }));
        System.out.println("\nИтого: " + stocksSumm + " акций");

        //Задание 3: На запрос пользователя в виде даты «ДД.ММ.ГГГГ», «ДД.ММ,ГГ», «ДД/ММ/ГГГГ» и «ДД/ММ/ГГ» вывести
        //           название и дату создания всех организаций, основанных после введенной даты
        System.out.println("\n-------------Список компаний, основанных после указанной даты---------------");
        String finalDateLine = inputDate;
        listOfCompanies.forEach(company -> {
            if (company.getRegistrationDateObj().isAfter(LocalDate.from(DateTimeFormatter.ofPattern(datePattern).parse(finalDateLine))))
                System.out.println(company.getCompanyName() + " " + company.getRegistrationDate());
        });

        //Задание 4: На запрос пользователя в виде кода валюты, например EU, USD, RUB и пр. выводить id и
        //           коды ценных бумаг, использующих заданную валюту
        System.out.println("\n-------------Акции, использующие указанную валюту---------------");
        listOfCompanies.forEach(company -> {
            company.getStocks().forEach(stock -> {
                if (stock.getCurrency().getCurrencyCode().equals(inputCurrency.getCurrencyCode()))
                    System.out.println(stock.getId() + " | " + stock.getCode() + " | "
                            + stock.getCurrency().getCurrencyCode() + " | " + stock.getStockName());
            });
        });
    }

    //ввод с клавиатуры
    public static String readSystemIn() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String string = null;
        try {
            string = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return string;
    }

    //парсинг файла и получение списка компаний
    public static List<Company> parseJsonAndGetList() {
        //Создание списка потенциальных адресов файла в случае ввода относительного пути вида "file.txt" или "/file.txt"
        //и соответствующий перебор значений. В случае неудовлетворительного результата, требуется запустить программу
        // заново.
        //Также предусмотрена работа в Windows (в адресе используется обратная косая черта "\")
        String fileAdress = readSystemIn();
        List<String> adressesList = new ArrayList<>();
        adressesList.add(fileAdress);
        adressesList.add(System.getProperty("user.dir") + "/" + fileAdress);
        adressesList.add(System.getProperty("user.dir") + "\\" + fileAdress);
        adressesList.add(System.getProperty("user.dir") + fileAdress);

        //чтение файла с помощью парсера Jakson
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node;
        int count = 0;
        while (true) {
            try {
                node = objectMapper.readTree(new File(adressesList.get(count)));
                break;
            } catch (IOException e) {
                count++;
                if (count > 5) {
                    e.printStackTrace();
                    return null;
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Адрес файла не распознан / файл не найден, требуется повторный запуск");
                return null;
            }
        }

        //Итерирование узлов, занесение полученных данных с список
        assert node != null;
        List<Company> listOfCompanies = new ArrayList<>();
        Iterator<Map.Entry<String, JsonNode>> iterNodes = node.fields();
        iterNodes.forEachRemaining(pair -> {
            try {
                listOfCompanies.add(objectMapper.readValue(pair.getValue().toString(), Company.class));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });

        return listOfCompanies;
    }
}
