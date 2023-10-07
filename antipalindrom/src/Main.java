import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class VeryLong {
    private List<Integer> number;

    public VeryLong(String numStr) {
        number = new ArrayList<>();
        for (int i = numStr.length() - 1; i >= 0; i--) { // Видалити зайві нулі з початку числа
            number.add(numStr.charAt(i) - '0'); // Якщо після видалення нулів число порожнє, замінити його на "0" замінити його на "0"
        }
    }

    public VeryLong add(VeryLong other) {
        List<Integer> result = new ArrayList<>();
        int carry = 0; // # У додаванні чисел зправа на ліво, коли один розряд числа більший за розряд іншого числа, додавання цих двох розрядів може призвести до "переносу" - додаткової одиниці, яку потрібно додати до наступного меншого розряду
        for (int i = 0; i < Math.max(number.size(), other.number.size()) || carry != 0; i++) { // Перебір розрядів чисел з право на ліво
            int sum = carry;
            // Перевантаження оператора додавання
            if (i < number.size()) {
                sum += number.get(i);
            }
            if (i < other.number.size()) {
                sum += other.number.get(i);
            }
            carry = sum / 10;
            result.add(sum % 10);
        }
        // Форматоване виведення результату з урахуванням переносів розрядів, сума розрядів у зворотьому порядку
        Collections.reverse(result);
        VeryLong sum = new VeryLong("0");
        sum.number = result;
        return sum;
    }


    public VeryLong subtract(VeryLong other) { // Перевантаження оператора віднімання
        List<Integer> result = new ArrayList<>();
        int carry = 0; // Урахування переносу розрядів
        for (int i = 0; i < number.size(); i++) { // Ділення розрядів з ліва на право
            int diff = number.get(i) - carry;
            if (i < other.number.size()) {
                diff -= other.number.get(i);
            }
            if (diff < 0) {
                diff += 10;
                carry = 1;
            } else {
                carry = 0;
            }
            result.add(diff);
        }
        // Форматоване виведення результату з урахуванням переносів розрядів, різниця розрядів у зворотьому порядку
        Collections.reverse(result);
        VeryLong diff = new VeryLong("0");
        diff.number = result;
        return diff;
    }

    public VeryLong multiply(VeryLong other) { // Перевантаження оператора множення
        VeryLong result = new VeryLong("0");
        VeryLong tempResult = new VeryLong("0"); // Об'єкт використовується для тимчасового накопичення проміжних результатів під час операції множення
        for (int i = 0; i < other.number.size(); i++) { //перебір цифри першого числа та виконання операції множення на поточну цифру другого числа
            int carry = 0;
            for (int j = 0; j < number.size() || carry != 0; j++) { // Якщо індекс j знаходиться в межах розміру number, то поточна цифра першого числа додається до dobutok після множення на відповідну цифру другого числа
                int dobutok = carry;
                if (j < number.size()) {
                    dobutok += number.get(j) * other.number.get(i);
                }
                carry = dobutok / 10; // Визначається новий розряд для наступної ітераці
                tempResult.number.add(dobutok % 10); // Д
            }
            for (int k = 0; k < i; k++) { // Додає i нульових цифр до tempResult. Це необхідно для вирівнювання позицій під час додавання проміжних результатів
                tempResult.number.add(0, 0);
            }
            result = result.add(tempResult);
            tempResult.number.clear();
        }
        return result;
    }

    public VeryLong divide(VeryLong other) { // Перевантаження оператора ділення
        VeryLong dilennya = new VeryLong("0");
        VeryLong currentdilennya = this; // Створення копії об'єкта, для того щоб не змінювати сам об'єкт dilennya

        while (currentdilennya.isGreaterOrEqual(other)) {
            VeryLong tempResult = new VeryLong("1");
            VeryLong tempValue = other; // Створюється тимчасовий об'єкт tempValue іншого числа, яке множиться на 10 під час операції ділення
            while (currentdilennya.isGreaterOrEqual(tempValue.multiply(new VeryLong("10")))) { // Дозволяє знайти найбільший можливий множник tempValue для виконання операції ділення
                tempValue = tempValue.multiply(new VeryLong("10"));// Помножується на 10 для перевірки наступного розряду
                tempResult = tempResult.multiply(new VeryLong("10"));
            }
            while (currentdilennya.isGreaterOrEqual(tempValue)) { // У цьому циклі tempValue використовується для віднімання від currentdilennya. Після кожної ітерації tempResult додається до dilennya
                currentdilennya = currentdilennya.subtract(tempValue);
                dilennya = dilennya.add(tempResult);
            }
        }
        return dilennya;
    }

    private boolean isGreaterOrEqual(VeryLong other) { // Порівння розмірів двох об'єктів, якщо розмір поточного об'єкта більший = True, якщо ні = False
        if (number.size() != other.number.size()) {
            return number.size() > other.number.size();
        }
        for (int i = number.size() - 1; i >= 0; i--) { // Порівння розмірів поточного об'єкта за індексом, якщо поточний об'єкт більший = True, менший = False
            if (!number.get(i).equals(other.number.get(i))) {
                return number.get(i) > other.number.get(i);
            }
        }
        return true;
    }

    // Вкористовується для перетворення об'єкта класу VeryLong на рядок
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int digit : number) { // Кожна цифра додається до початку StringBuilder у зворотньому порядку
            result.insert(0, digit);
        }
        return result.toString();
    }
}

public class Main {
    public static void main(String[] args) {

        // Введення значень для обчислення

        String num1 = "100";
        String num2 = "0";
        System.out.println("First number: ");
        System.out.println(num1);
        System.out.println("Second number: ");
        System.out.println(num2);

        VeryLong a = new VeryLong(num1);
        VeryLong b = new VeryLong(num2);

        //Виклик відповідних методів за допомогою об'єктів класу VeryLong
        VeryLong sum = a.add(b);
        VeryLong diff = a.subtract(b);
        VeryLong dobutok = a.multiply(b);
        VeryLong dilennya = a.divide(b);

        // Виведення результатів операції
        System.out.println("Suma: " + sum);
        System.out.println("Riznytsya: " + diff);
        System.out.println("Dobutok: " + dobutok);
        System.out.println("Dilennya: " + dilennya);
    }
}