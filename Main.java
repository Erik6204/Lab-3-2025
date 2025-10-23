import functions.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Создание функции y=2x+1 на определенном интервале.");
        try {
            // Тест 1: Создание функции и вывод значений
            System.out.println("Вариант 1: ArrayTabulatedFunction ");
            TabulatedFunction func = new ArrayTabulatedFunction(0, 10, 6);
            
            // Устанавливаем значения функции y = 2x+1
            for (int i = 0; i < func.getPointsCount(); i++) {
                double x = func.getPointX(i);
                func.setPointY(i, 2*x+1);
            }
            
            // Выводим значения функции в различных точках
            System.out.println("\n Вычисление значений функции в разных точках:");
            double[] testPoints = {-1, 0, 2, 4, 6.6, 8, 10, 12};
            for (double testPoint : testPoints) {
                double y = func.getFunctionValue(testPoint);
                System.out.print("   f(" + testPoint + ") = ");
                if (Double.isNaN(y)) {
                    System.out.println("не определена (вне области определения)");
                } else {
                    System.out.println(y);
                }
            }

            System.out.println("\n Границы области определения:");
            System.out.println("   Левая граница: " + func.getLeftDomainBorder());
            System.out.println("   Правая граница: " + func.getRightDomainBorder());
            
            System.out.println("\n Исходные точки функции:");
            for (int i = 0; i < func.getPointsCount(); i++) {
                FunctionPoint point = func.getPoint(i);
                System.out.println("   Точка " + i + ":(" + point.getX() + " " + point.getY() + ")");
            }

            System.out.println("\n2. Изменение точек функции:");
            // Изменяем первую точку (индекс 0)
            System.out.println("   Изменение первой точки (индекс 0) на (0,100):");
            try {
                func.setPoint(0, new FunctionPoint(0, 100));
            } catch (InappropriateFunctionPointException e) {
                System.out.println("   Ошибка при изменении точки: " + e.getMessage());
            }
            
            for (int i = 0; i < func.getPointsCount(); i++) {
                FunctionPoint point = func.getPoint(i);
                System.out.println("   Точка " + i + ":(" + point.getX() + " " + point.getY() + ")");
            }
            
            // Добавление точки
            System.out.println("\n3. Добавление новой точки (6.6, 9.9):");
            System.out.println("   Количество точек до добавления: " + func.getPointsCount());
            try {
                func.addPoint(new FunctionPoint(6.6, 9.9));
                System.out.println("   Количество точек после добавления: " + func.getPointsCount());
            } catch (InappropriateFunctionPointException e) {
                System.out.println("   Ошибка при добавлении точки: " + e.getMessage());
            }
            
            System.out.println("\n Новые точки функции после добавления:");
            for (int i = 0; i < func.getPointsCount(); i++) {
                FunctionPoint point = func.getPoint(i);
                System.out.println("   Точка " + i + ":(" + point.getX() + " " + point.getY() + ")");
            }
            
            // Удаляем точку из середины (индекс 3)
            System.out.println("\n4. Удаляем точку с индексом 3:");
            try {
                func.deletePoint(3);
            } catch (Exception e) {
                System.out.println("   Ошибка при удалении точки: " + e.getMessage());
            }
            
            for (int i = 0; i < func.getPointsCount(); i++) {
                FunctionPoint point = func.getPoint(i);
                System.out.println("   Точка " + i + ":(" + point.getX() + " " + point.getY() + ")");
            }

            // Тестирование исключений для ArrayTabulatedFunction
            System.out.println("\n=== ПРОВЕРКА ИСКЛЮЧЕНИЙ ДЛЯ ArrayTabulatedFunction ===");
            
            try {//Проверка на доступ к определенной координате по индексу(здесь 100)
                System.out.println("\n Пытаемся получить доступ к координате по индексу 100");
                func.getPoint(100);
            } catch (FunctionPointIndexOutOfBoundsException e) {
                System.out.println("Поймано исключение: " + e.getMessage());
            }
            try {//Проверка на нарушение порядке x
                System.out.println("\n Пытаемся в абциссу точки с индексом 2 вставить 10");
                func.setPointX(2, 10); // Нарушение порядка - x=10 больше следующей точки
            } catch (InappropriateFunctionPointException e) {
                System.out.println("Поймано исключение: " + e.getMessage());
            }
            
            // Создаем точку для теста повторного добавления
            FunctionPoint testPoint1 = new FunctionPoint(6.6, 15.0);
            try {
                System.out.println("\n Пытаемся создать точку с x=6.6,y=15");
                func.addPoint(testPoint1); // Точка с x=6.6 уже существует
            } catch (InappropriateFunctionPointException e) {
                System.out.println("Поймано исключение: " + e.getMessage());
            }
            
            // Тест 2: LinkedListTabulatedFunction
            System.out.println("\nВариант 2: LinkedListTabulatedFunction ");
            TabulatedFunction linkedFunc = new LinkedListTabulatedFunction(0, 10, 6);
            
            // Заполняем значениями
            for (int i = 0; i < linkedFunc.getPointsCount(); i++) {
                double x = linkedFunc.getPointX(i);
                linkedFunc.setPointY(i, 2*x +1);
            }
            
            System.out.println("\n Исходные точки функции (y = 2x+1):");
            for (int i = 0; i < linkedFunc.getPointsCount(); i++) {
                FunctionPoint point = linkedFunc.getPoint(i);
                System.out.println("   Точка " + i + ":(" + point.getX() + " " + point.getY() + ")");
            }

            // ДОБАВЛЕНЫ ПРОВЕРКИ МЕТОДОВ ДЛЯ LinkedListTabulatedFunction
            System.out.println("\n=== ПРОВЕРКА МЕТОДОВ ДЛЯ LinkedListTabulatedFunction ===");
            
            // Вычисление значений функции в различных точках
            System.out.println("\n Вычисление значений функции в разных точках:");
            for (double testPoint : testPoints) {
                double y = linkedFunc.getFunctionValue(testPoint);
                System.out.print("   f(" + testPoint + ") = ");
                if (Double.isNaN(y)) {
                    System.out.println("не определена (вне области определения)");
                } else {
                    System.out.println(y);
                }
            }

            System.out.println("\n Границы области определения:");
            System.out.println("   Левая граница: " + linkedFunc.getLeftDomainBorder());
            System.out.println("   Правая граница: " + linkedFunc.getRightDomainBorder());

            System.out.println("\n2. Изменение точек функции:");
            // Изменяем первую точку (индекс 0)
            System.out.println("   Изменение первой точки (индекс 0) на (0,100):");
            try {
                linkedFunc.setPoint(0, new FunctionPoint(0, 100));
            } catch (InappropriateFunctionPointException e) {
                System.out.println("   Ошибка при изменении точки: " + e.getMessage());
            }
            
            for (int i = 0; i < linkedFunc.getPointsCount(); i++) {
                FunctionPoint point = linkedFunc.getPoint(i);
                System.out.println("   Точка " + i + ":(" + point.getX() + " " + point.getY() + ")");
            }
            
            // Добавление точки
            System.out.println("\n3. Добавление новой точки (6.6, 9.9):");
            System.out.println("   Количество точек до добавления: " + linkedFunc.getPointsCount());
            try {
                linkedFunc.addPoint(new FunctionPoint(6.6, 9.9));
                System.out.println("   Количество точек после добавления: " + linkedFunc.getPointsCount());
            } catch (InappropriateFunctionPointException e) {
                System.out.println("   Ошибка при добавлении точки: " + e.getMessage());
            }
            
            System.out.println("\n Новые точки функции после добавления:");
            for (int i = 0; i < linkedFunc.getPointsCount(); i++) {
                FunctionPoint point = linkedFunc.getPoint(i);
                System.out.println("   Точка " + i + ":(" + point.getX() + " " + point.getY() + ")");
            }
            
            // Удаляем точку из середины (индекс 3)
            System.out.println("\n4. Удаляем точку с индексом 3:");
            try {
                linkedFunc.deletePoint(3);
            } catch (Exception e) {
                System.out.println("   Ошибка при удалении точки: " + e.getMessage());
            }
            
            for (int i = 0; i < linkedFunc.getPointsCount(); i++) {
                FunctionPoint point = linkedFunc.getPoint(i);
                System.out.println("   Точка " + i + ":(" + point.getX() + " " + point.getY() + ")");
            }
            
            // Тестирование исключений для LinkedListTabulatedFunction
            System.out.println("\n=== ПРОВЕРКА ИСКЛЮЧЕНИЙ ДЛЯ LinkedListTabulatedFunction ===");
            
            try {//Проверка на доступ к определенной координате по индексу(здесь 100)
                System.out.println("\n Пытаемся получить доступ к координате по индексу 100");
                linkedFunc.getPoint(100);
            } catch (FunctionPointIndexOutOfBoundsException e) {
                System.out.println("Поймано исключение: " + e.getMessage());
            }
            try {//Проверка на нарушение порядке x
                System.out.println("\n Пытаемся в абциссу точки с индексом 2 вставить 10");
                linkedFunc.setPointX(2, 10); // Нарушение порядка
            } catch (InappropriateFunctionPointException e) {
                System.out.println("Поймано исключение: " + e.getMessage());
            }
            // Создаем точку для теста повторного добавления
            FunctionPoint testPoint2 = new FunctionPoint(4.0, 20.0);
            try {//Проверка на повторяющиеся элементы
                System.out.println("\n Пытаемся создать точку с x=4,y=20");
                linkedFunc.addPoint(testPoint2); // Точка с x=4.0 уже существует
            } catch (InappropriateFunctionPointException e) {
                System.out.println("Поймано исключение: " + e.getMessage());
            }
            
        } catch (Exception e) {
            System.out.println("Общая ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }
}