import java.sql.*;
import java.text.*;

import com.sun.org.slf4j.internal.*;

/*
1. Задача про кинотеатр.
    У фильма, который идет в кинотеатре, есть
        - название,
        - длительность (пусть будет 60, 90 или 120 минут),
        - цена билета (в разное время и дни может быть разной),
        - время начала сеанса (один фильм может быть показан несколько раз в разное время
          и с разной ценой билета).
    Есть информация о купленных билетах (номер билета, на какой сеанс).

Задания:
    Составить грамотную нормализованную схему хранения этих данных в БД.
    Внести в нее 4–5 фильмов, расписание на один день и несколько проданных билетов.

    Сделать запросы, считающие и выводящие в понятном виде:
       а) ошибки в расписании (фильмы накладываются друг на друга),
          отсортированные по возрастанию времени.
          Выводить надо колонки «фильм 1», «время начала», «длительность»,
          «фильм 2», «время начала», «длительность»;
       б) перерывы 30 минут и более между фильмами — выводить по уменьшению длительности перерыва.
          Колонки - «фильм 1», «время начала», «длительность»,
          «время начала второго фильма», «длительность перерыва»;
       в) список фильмов, для каждого — с указанием
            - общего числа посетителей за все время,
            - среднего числа зрителей за сеанс и общей суммы сборов по каждому фильму
              (отсортировать по убыванию прибыли).
          Внизу таблицы должна быть строчка «итого», содержащая данные по всем фильмам сразу;
       г) число посетителей и кассовые сборы, сгруппированные по времени начала фильма:
            - с 9 до 15,
            - с 15 до 18,
            - с 18 до 21,
            - с 21 до 00:00
            (сколько посетителей пришло с 9 до 15 часов и т.д.).
*/
public class MovieTheater {
    public static void main(String[] args) {
        try {
            // для корректной работы проекта
            // - нужно подключить библиотеку SQLite
            // - добавить для Maven зависимость в pom-документ/файл с настройками/конфигурацию
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:MovieTheater.db");
            Statement st = connection.createStatement();

            getTimeTable();
            getBreaks();
            printTaskName("Задача №3 - общая и сеансовая статистика посещаемости - не реализована", false);
            printTaskName("Посещения и сборы", false);
            int[] times = new int[] { 9, 15, 18, 21, 24};
            for (int i = 0; i < times.length - 1; i++)
                getStats(times[i], times[i+1]);

            st.close();
            connection.close();
        }
        catch (ClassNotFoundException ex) { logger.error(ex.getMessage()); }
        catch (SQLException ex) { logger.error("База данных кинотеатра не найдена!"); }
    }

    final static int TASK1 = 0, TASK2 = 1, TASK3 = 2, TASK4 = 3;

    final static String[][] HEADER_COLUMN = new String[][] {
            { "Фильм_", "Время начала", "Длительность" },
            { "Время начала 2-го фильма", "Длительность перерыва"},
            {},
            { "Время начала", "Зрители", "Сборы" }
    };

    static int[] MAX_LENGTH = new int[HEADER_COLUMN[TASK1].length];

    // поскольку табуляция имеет "плавающую" длину,
    // иногда она может состоять из 1 пробельного символа,
    // что не всегда удобно при чтении
    final static String SEPARATOR = "   ";

    // общая часть SQL-запросов в задачах 1 и 2
    final static String SQL_ACCESS_TIMETABLE =
            "FROM films " +
            "LEFT JOIN sessions " +
            "ON sessions.film_id = films.id "+
            "ORDER BY showtime";

    final static Logger logger = LoggerFactory.getLogger(MovieTheater.class);

    static Connection connection;

    // задача 4
    static void getStats(int start, int end) {
        try (PreparedStatement ps = connection
                .prepareStatement(
                        "SELECT strftime('%H:%M', showtime), count(tickets.id), sum(price) "+
                            "FROM tickets " +
                            "LEFT JOIN sessions " +
                            "ON sessions.id = tickets.session_id " +
                            "WHERE (strftime('%H', showtime) >= ?) and (strftime('%H', showtime) < ?) " +
                            //"where (HOUR(showtime) >= ?) and (HOUR(showtime) <= ?) " +
                            "GROUP BY strftime('%H:%M', showtime)")) {
            ps.setString(1, leadZero(start));
            ps.setString(2, leadZero(end));
            ResultSet rs = ps.executeQuery();

            int cnt = 0, i;
            for (i = 0; i < HEADER_COLUMN[TASK4].length; i++)
                MAX_LENGTH[i] = HEADER_COLUMN[TASK4][i].length();
            StringBuilder header = new StringBuilder(start + "-" + end + ":\n");
            System.out.println(header.append(addHeaderColumns(TASK4, 0)));

            while (rs.next()) {
                System.out.print(SEPARATOR);
                for (i = 0; i < rs.getMetaData().getColumnCount(); i++)
                    System.out.print(
                            adjustToLength(rs.getString( i+1),MAX_LENGTH[i],0)+SEPARATOR);
                System.out.println();
                cnt++;
            }
            if (cnt == 0) System.out.println(SEPARATOR+"ПУСТО");
        } catch (SQLException ex) { logger.error(ex.getMessage()); }
    }

    // задача 1
    static void getTimeTable() {
        int cnt = checkTimeTable();
        if (cnt == 0) return;
        // NB:
        // при последовательной обработке любого результата выполнения запроса
        // происходит перемещение указателя вперед по результирующему набору,
        // драйвера не всех СУБД поддерживают перемещение по нему назад.
        // набор почему-то не имеет "осязаемой" формы хранения, сохранение копии
        // результата ничего не дает - перемещение по копии вызывает перемещение
        // и по оригинальному результирующему набору, поэтому для повторного прохода
        // по нему запрос нужно выполнить снова - при стандартном наличии кэша
        // запросов проблемой это не является
        printTaskName("Расписание", true);
        try (PreparedStatement ps = connection
                .prepareStatement("SELECT title, showtime, duration " + SQL_ACCESS_TIMETABLE)) {

            // сформировать заголовок...
            int i;
            for (i = 0; i < HEADER_COLUMN[TASK1].length; i++)
                MAX_LENGTH[i] = HEADER_COLUMN[TASK1][i].length();

            ResultSet rs = ps.executeQuery();
            while (rs.next())
                for (i = 0; i < HEADER_COLUMN[TASK1].length; i++)
                    if (rs.getString(i + 1).length() > MAX_LENGTH[i])
                        MAX_LENGTH[i] = rs.getString(i + 1).length();

            StringBuilder header = new StringBuilder(addHeaderColumns(TASK1, 1));
            if (cnt > 1)
                header.append(addHeaderColumns(TASK1, 2));
            System.out.println(header);

            // ...под ним вывести результаты
            i = 0;
            rs = ps.executeQuery();
            rs.next();
            while (i < cnt) {
                displayFilmSession(rs, true);
                if (++i < cnt) {
                    rs.next();
                    displayFilmSession(rs, false);
                }
                System.out.println();
            }
        } catch (SQLException ex) { logger.error(ex.getMessage()); }
    }

    // задача 2
    static void getBreaks() {
        int cnt = checkTimeTable();
        if (cnt == 0) return;

        printTaskName("Перерывы между фильмами", false);
        try (PreparedStatement ps = connection
                .prepareStatement("SELECT title, showtime, duration " + SQL_ACCESS_TIMETABLE)) {

            // оставшиеся после первой задачи длины элементов заголовка действительны и здесь,
            // число знаков в длительности перерыва (минуты) не имеет значения,
            // поскольку это последнее значение в строке, но по смыслу - не более 3 (16 часов);
            // перечитывать данные для формирования заголовка нет необходимости
            System.out.println(addHeaderColumns(TASK1, 1) + addHeaderColumns(TASK2, 0));
            int i = 0;
            ResultSet rs = ps.executeQuery();
            rs.next();
            while (i < cnt) {
                displayFilmSession(rs, true);
                String firstTime = rs.getString("showtime");
                int duration = rs.getInt("duration");
                if (++i < cnt) {
                    rs.next();
                    displayBreaks(rs, firstTime, duration);
                }
                System.out.println();
            }
        } catch (SQLException ex) { logger.error(ex.getMessage()); }

    }

    /*
     вспомогательные методы
     */
    static String leadZero(int value) { return (value < 10 ? "0" : "") + value; }

    static void printTaskName(String name, boolean first) {
        if (!first) System.out.println();
        // как ни странно, но метод repeat для строк был введен только с Java 11
        System.out.println(name);
        for (int i = 0; i < name.length(); i++) System.out.print('-');
        System.out.println();
    }

    static String adjustToLength(String value, int length, int substIdx) {
        StringBuilder sb =
                new StringBuilder(value.endsWith("_")
                        ? value.substring(0, value.length()-1)+substIdx
                        : value);
        while (sb.length() < length) sb.append(" ");
        return sb.toString();
    }

    static String addHeaderColumns(int task, int index) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < HEADER_COLUMN[task].length; i++)
            sb.append(SEPARATOR).append(adjustToLength(HEADER_COLUMN[task][i], MAX_LENGTH[i], index));
        return sb.toString();
    }

    static void displayFilmSession(ResultSet rs, boolean firstPart) throws SQLException {
        if (firstPart) System.out.print(SEPARATOR);
        for (int j = 0; j < rs.getMetaData().getColumnCount(); j++)
            System.out.print(adjustToLength(rs.getString(j+1),MAX_LENGTH[j],0)+SEPARATOR);
    }

    static long getTime(String datetime) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try { return fmt.parse(datetime).getTime(); }
        catch (ParseException ex) {
            ex.printStackTrace();
            return 0L;
        }
    }

    static void displayBreaks(ResultSet rs, String firstTime, int duration) throws SQLException {
        String secondTime = rs.getString("showtime");
        System.out.print(
                adjustToLength(secondTime,Math.max(MAX_LENGTH[1],HEADER_COLUMN[TASK2][0].length()),0)
                        + SEPARATOR + (((getTime(secondTime)-getTime(firstTime))/1000/60)-duration));
    }

    static int checkTimeTable() {
        int cnt = 0;
        try (PreparedStatement ps = connection
                .prepareStatement("SELECT COUNT(*) " + SQL_ACCESS_TIMETABLE)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) cnt = rs.getInt(1);
        } catch (SQLException ex) { logger.error(ex.getMessage()); }
        return cnt;
    }
}