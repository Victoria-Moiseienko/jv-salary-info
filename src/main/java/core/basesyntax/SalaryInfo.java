package core.basesyntax;

import core.basesyntax.exception.IllegalDateParametersException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SalaryInfo {
    /**
     * <p>Реализуйте метод getSalaryInfo(String[] names, String[] data,
     * String dateFrom, String dateTo)
     * вычисляющий зарплату сотрудников. На вход методу подаётся 2 массива и 2 даты,
     * определяющие период за который надо вычислить зарплату, первый массив содержит имена
     * сотрудников организации, второй массив информацию о рабочих часах и ставке. Формат данных
     * второго массива следующий: дата, имя сотрудника, количество отработанных часов,
     * ставка за 1 час. Метод должен вернуть отчёт за период, который передали в метод
     * (обе даты включительно) составленный по следующей форме: Отчёт за период
     * #дата_1# - #дата_2# Имя сотрудника - сумма заработанных средств за этот период
     * Создать пакет exception и в нём класс-ошибку IllegalDateParametersException. Сделать так,
     * чтобы метод getSalaryInfo выбрасывал IllegalDateParametersException,
     * если dateFrom > dateTo, с сообщнием "Wrong parameters"</p>
     *
     * <p>Пример ввода: date from = 01.04.2019 date to = 30.04.2019</p>
     *
     * <p>names:
     * Сергей
     * Андрей
     * София</p>
     *
     * <p>data:
     * 26.04.2019 Сергей 60 50
     * 26.04.2019 Андрей 3 200
     * 26.04.2019 Сергей 7 100
     * 26.04.2019 София 9 100
     * 26.04.2019 Сергей 11 50
     * 26.04.2019 Андрей 3 200
     * 26.04.2019 Сергей 7 100
     * 26.04.2019 София 9 100
     * 26.04.2019 Сергей 11 50</p>
     *
     * <p>Пример вывода:
     * Отчёт за период 01.04.2019  - 30.04.2019
     * Сергей - 1550
     * Андрей - 600
     * София - 900</p>
     */

    public String getSalaryInfo(String[] names, String[] data,
                                String dateFrom, String dateTo) throws Exception {
        checkInputDates(dateFrom, dateTo);

        Worker[] workers = new Worker[names.length];

        for (int i = 0; i < names.length; i++) {
            Worker worker = new Worker(names[i]);
            workers[i] = worker;
        }

        for (String singleData : data) {
            DataLineInfo dataLineInfo = new DataLineInfo(singleData);
            if (isNeedDate(dateFrom, dateTo, dataLineInfo.date)) {
                for (Worker worker : workers) {
                    if (worker.name.equals(dataLineInfo.name)) {
                        worker.salary += dataLineInfo.hours * dataLineInfo.payment;
                    }
                }
            }
        }

        String report = makeReport(dateFrom, dateTo, workers);
        return report;
    }

    private String makeReport(String dateFrom, String dateTo, Worker[] workers) {
        StringBuilder result = new StringBuilder();

        result.append("Отчёт за период ")
                .append(dateFrom)
                .append(" - ")
                .append(dateTo)
                .append("\n");

        for (Worker worker : workers) {
            result.append(worker.name).append(" - ").append(worker.salary).append("\n");
            ;
        }
        result.append("\n");

        return result.toString().replace("\n\n", "");
    }

    private void checkInputDates(String dateFrom, String dateTo) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        Date from = sdf.parse(dateFrom);
        Date to = sdf.parse(dateTo);

        if (from.compareTo(to) > 0) {
            throw new IllegalDateParametersException("Wrong parameters");
        }
    }

    private static boolean isNeedDate(String dateFrom, String dateTo,
                                      String currentDate) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        Date from = sdf.parse(dateFrom);
        Date to = sdf.parse(dateTo);
        Date current = sdf.parse(currentDate);

        if (current.compareTo(from) >= 0
                && current.compareTo(to) <= 0) {
            return true;
        }
        return false;
    }
}
