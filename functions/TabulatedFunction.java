package functions;

public interface TabulatedFunction {
    // Методы работы с функцией

    //Левая и правая граница области определения функции
    double getLeftDomainBorder();
    double getRightDomainBorder();
    // Метод, возвращающий значение функции в точке x
    double getFunctionValue(double x);
    
    // Методы работы с точками

    int getPointsCount();// Метод, возвращающий количество точек
    FunctionPoint getPoint(int index);//Метод, возвращающий ссылку на объект по индекксу
    void setPoint(int index, FunctionPoint point)throws InappropriateFunctionPointException;// Метод, заменяющий точку по индексу на заданную
    double getPointX(int index);//Возвращает X точки с указанным индексом
    void setPointX(int index, double x)throws InappropriateFunctionPointException;// Метод, изменяющий абсциссу точки по индексу
    double getPointY(int index);
    void setPointY(int index, double y);
    
    // Методы изменения количества точек(удаление или добавление)
    void deletePoint(int index);
    void addPoint(FunctionPoint point)throws InappropriateFunctionPointException;
    void printTabulatedFunction();//вывод в консоль
}
