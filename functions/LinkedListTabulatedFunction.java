package functions;

public class LinkedListTabulatedFunction implements TabulatedFunction {

    private static class ListElement{
        private FunctionPoint point;
        private  ListElement prev, next;
        private ListElement(FunctionPoint point){
            this.point = point;
        }
    }

    private final ListElement head;
    private int size;

    //Конструкто по умолчанию
    public LinkedListTabulatedFunction() {
        head = new ListElement(null);
        head.prev = head;
        head.next = head;
        size = 0;
    }


    public LinkedListTabulatedFunction(double leftX, double rightX, int pointsCount){
        this();
        if (leftX >= rightX){
            throw new IllegalArgumentException("Левая граница области определения должна быть меньше правой");
        }
        if (pointsCount < 2){
            throw new IllegalArgumentException("Количество точек табулирования не может быть меньше 2");
        }
        double step = (rightX - leftX) / (pointsCount - 1);
        for(int i = 0; i < pointsCount; i++){
            ListElement node = addNodeToTail();
            node.point = new FunctionPoint(leftX, 0);
            leftX += step;
        }
    }

    public LinkedListTabulatedFunction(double leftX, double rightX, double[] values) {
        this();
        if (leftX >= rightX) {
            throw new IllegalArgumentException("Левая граница области определения должна быть меньше правой");
        }
        if (values.length < 2) {
            throw new IllegalArgumentException("Количество точек табулирования не может быть меньше 2");
        }
        double step = (rightX - leftX) / (values.length - 1);
        for (int i = 0; i < values.length; i++) {
            ListElement node = addNodeToTail();
            node.point = new FunctionPoint(leftX, values[i]);
            leftX += step;
        }
    }

    //Левая граница области определения функции
    public double getLeftDomainBorder() {
        if (size == 0) throw new IllegalStateException("Функция не содержит точек");
        return head.next.point.getX();
    }
    //правая
    public double getRightDomainBorder()  {
        if (size == 0) throw new IllegalStateException("Функция не содержит точек");
        return head.prev.point.getX();
    }

    // Метод, возвращающий количество точек
    public int getPointsCount() {
        return size;
    }

    //Метод, возвращающий ссылку на объект по индексу
    public FunctionPoint getPoint(int index) {
        if (index < 0 || index >= size) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        // Возвращаем копию точки, чтобы предотвратить несанкционированный доступ к точке
        return new FunctionPoint(getNodeByIndex(index).point);
    }

    // Метод, заменяющий точку на заданную
    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException{
        if (index < 0 || index >= size) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        ListElement node = getNodeByIndex(index);

        double x = point.getX();
        if ((index > 0 && x <= node.prev.point.getX()) || (index < size - 1 && x >= node.next.point.getX())) {
            throw new InappropriateFunctionPointException("X координата точки нарушает упорядоченность");
        }

        node.point = new FunctionPoint(point);
    }

    //Возвращает X точки с указанным индексом
    public double getPointX(int index) {
        return getPoint(index).getX();
    }

    // Метод, изменяющий значение абсциссы точки с указанным номером
    public void setPointX(int index, double x)throws InappropriateFunctionPointException {
        if (index < 0 || index >= size) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        ListElement node = getNodeByIndex(index);
        if ((index > 0 && x <= node.prev.point.getX()) || (index < size - 1 && x >= node.next.point.getX())) {
            throw new InappropriateFunctionPointException("X координата точки нарушает упорядоченность");
        }
        node.point.setX(x);
    }

    // Возвращает Y точки с указанным индексом
    public double getPointY(int index) {
        return getPoint(index).getY();
    }

    // Метод, изменяющий значение ординаты точки с указанным номером
    public void setPointY(int index, double y) {
        if (index < 0 || index >= size) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        getNodeByIndex(index).point.setY(y);
    }

    // добавление точки
    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        double x = point.getX();

        if (size == 0) {
            addNodeToTail().point = new FunctionPoint(point);
            size++;
            return;
        }

        // Проверка на существующую точку с таким X
        ListElement current = head.next;
        while (current != head) {
            if (Math.abs(current.point.getX() - x) < 1e-10) {
                throw new InappropriateFunctionPointException("Точка с X=" + point.getX() + " уже существует");
            }
            current = current.next;
        }

        if (x < getLeftDomainBorder()) {
            ListElement node = addNodeByIndex(0); // добавляем в начало
            node.point = new FunctionPoint(point);
            return;
        }

        if (x > getRightDomainBorder()) {
            addNodeToTail().point = new FunctionPoint(point);
            return;
        }

        // Иначе вставляем в нужное место
        ListElement curr = head.next;
        int index = 0;
        while (curr != head && curr.point.getX() < x) {
            curr = curr.next;
            index++;
        }

        if (curr != head && Math.abs(curr.point.getX() - x) < 1e-10) {
            throw new InappropriateFunctionPointException("Точка с X=" + point.getX() + " уже существует");
        }
        ListElement node = addNodeByIndex(index);
        node.point = new FunctionPoint(point);
    }

    //Удаление точки
    public void deletePoint(int index) {
        if (size < 3) throw new IllegalStateException("Невозможно удалить точку: количество точек должно быть не менее 3");
        if (index < 0 || index >= size) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        deleteNodeByIndex(index);
    }

    // Метод, возвращающий значение функции в точке х
    public double getFunctionValue(double x) {
        if (x < getLeftDomainBorder() || x > getRightDomainBorder()) {
            return Double.NaN;
        }
        ListElement current = head.next;
        while (current != head && current.next != head) {
            double x1 = current.point.getX();
            double y1 = current.point.getY();
            double x2 = current.next.point.getX();
            double y2 = current.next.point.getY();

            if (x >= x1 && x <= x2) {
                return y1 + (x - x1) * (y2 - y1) / (x2 - x1);
            }
            current = current.next;
        }
        return Double.NaN;
    }

    //возвращает ссылку на объект элемента списка по его индексу
    private ListElement getNodeByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        ListElement current;

        if (index < size / 2) {
            current = head.next;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = head.prev;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    //Добавление точки в конец и возвращение ссылки на этот объект
    private ListElement addNodeToTail() {
        ListElement newNode = new ListElement(null);
        ListElement last = head.prev;

        last.next = newNode;
        newNode.prev = last;
        newNode.next = head;
        head.prev = newNode;

        size++;
        return newNode;
    }

    //Добавление элемента в список по индексу и возвращение ссылки на объект этого элемента
    private ListElement addNodeByIndex(int index) {
        if (index == size) return addNodeToTail();
        ListElement nextNode = getNodeByIndex(index);
        ListElement newNode = new ListElement(null);

        newNode.next = nextNode;
        newNode.prev = nextNode.prev;
        nextNode.prev.next = newNode;
        nextNode.prev = newNode;

        size++;
        return newNode;
    }

    //удаление элемента в списке по индексу
    private ListElement deleteNodeByIndex(int index) {
        ListElement node = getNodeByIndex(index);
        node.prev.next = node.next;
        node.next.prev = node.prev;
        size--;
        return node;
    }

    //Метод ввыода
    public void printTabulatedFunction() {
        for (int i = 0; i < size; i++) {
            System.out.println("x = " + getPointX(i) + ", y = " + getPointY(i));
        }
    }
}