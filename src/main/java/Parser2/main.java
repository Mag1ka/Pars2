package Parser2;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class main {

    public static void main(String[] args) throws IOException {
        //Создаём нужные нам колекции
        ArrayList<String> vacancy = new ArrayList<>(); //Название вакансий
        ArrayList<String> links = new ArrayList<>(); //Ссылки на вакансии
        ArrayList<DateTime> point = new ArrayList<>(); //Список подходяших дат
        // дата введенная с клавиатуры
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите дату используя в формате ГГГГ-ММ-ДД:\n");
        DateTimeFormatter formatter = DateTimeFormat.forPattern("YYYY-MM-dd");
        DateTime sortingTime;
        while (true) {
            try {
                String strDate = sc.next();
                sortingTime = formatter.parseDateTime(strDate);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Введено неверное значение, повторите попытку:\n");

            }
        }
        //Второй вариант введения
        //System.out.println("Введите нужный день:"); //В одну строчку
        //int day = sc.nextInt();
        //System.out.println("Введите нужный месяц:");
        //int month = sc.nextInt();
        //System.out.println("Введите нужный год:");
        //int year = sc.nextInt();
        //DateTime sortingTime = new DateTime().withYear(year).withMonthOfYear(month).withDayOfMonth(day).withHourOfDay(0).withMinuteOfHour(0); //Переводим значения с клавиатуры в дату для сравнения


        Parser parser = new Parser(); //Работа со страницей
        boolean updated = true;
        for (int pageNumber = 1; pageNumber <= 704 && updated; pageNumber++) {
            Document page = parser.getPage(pageNumber);
            Element table = page.select("table[class=forumTable]").get(0); //Выбор таблицы
            //page.select("a").remove();
            ArrayList<DateTime> dates = new ArrayList<>();
            DateParser dateParser = new DateParser();  //Добавляем метод открытия страниц

            List<Element> postTime = table.select("td:nth-child(6)"); //Нужный столбец с датами
            postTime = postTime.subList(3, postTime.size()); //Делаем выборку данных (времени поста на форуме, за исключение первых 3 тем
            //Elements posted = postTime2.select("td[style=text-align:center]"); //Второй вариант поиска нужной колонки
            for (Element time : postTime) {
                dates.add(dateParser.parsDate(time.text()));
                //System.out.println(dates.get(dates.size() - 1));
            }

            for (DateTime sort : dates) {
                if (sort.isAfter(sortingTime)) {
                    point.add(sort);
                    //System.out.println(dateIndex);

                } else updated = false;
                if (sort.isBefore(sortingTime)) break;
            }
            List<Element> vacancyName = table.select("td:nth-child(2)"); //Добавляем нужные нам вакансии
            vacancyName = vacancyName.subList(3, vacancyName.size());
            if (vacancy.size() <= point.size()) {
                for (Element vac : vacancyName) {
                    if (vacancy.size() <= point.size() - 1) {
                        vacancy.add(vac.text());
                        //System.out.println(vac.text());
                    } else break;

                }
            }

            List<Element> links1 = table.select("td:nth-child(2)"); //Добавляем нужные нам ссылки
            links1 = links1.subList(3, links1.size());

            if (vacancy.size() <= point.size()) {
                for (Element lin : links1) {
                    if (links.size() <= point.size() - 1) {
                        links.add(lin.select("a").attr("href") + "\n");

                    } else break;

                }
            }
        }


        File file = new File("result.txt");
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        for (int i = 0; i <= links.size() - 1; i++)
            bw.write(vacancy.get(i) + " Ссылка на форум - " + links.get(i) + "\n");
        bw.close();
        System.out.println("Файл создан успешно");
        System.out.println("Строк времени" + point.size() + "\n" + "Строк вакансий" + vacancy.size() + "\n" + "Строк ссылок" + links.size());

    }

}




