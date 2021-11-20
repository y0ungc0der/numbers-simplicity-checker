package ru.arina;

import java.math.BigInteger;
import java.util.*;

public class Main {

    public static void main(String args[]) {
        inv();
    }

    public static void inv() {
     /* BigInteger - это класс, хранящийся в пакете java.math.
    Он представляет целые числа произвольной длины. */
        BigInteger a = BigInteger.ZERO; // a = 0
        BigInteger n = BigInteger.ZERO; // n = 0

        // Считывание с консоли числа n
        System.out.print("Введите число n: ");
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNext()) {
            n = new BigInteger(scanner.next());
            System.out.println("Введенное n = " + n);
        }

        // Считывание с консоли числа a
        System.out.print("Введите основание a (2 <= a <= n-2): ");
        if (scanner.hasNext()) {
            a = new BigInteger(scanner.next());
            System.out.println("Введенное a = " + a);
        }

        if (((n.mod(BigInteger.valueOf(2)).compareTo(BigInteger.ZERO) != 0)) &&
                n.compareTo(BigInteger.valueOf(5)) != -1 && a.compareTo(BigInteger.valueOf(2)) != -1 &&
                (n.subtract(BigInteger.valueOf(2))).compareTo(a) != -1) {
            fer(a, n);
            csh(a, n);
            ram(a, n);
           // ef();
        }

        inv();

        return;
    }

    /* Тест Ферма */
    public static void fer(BigInteger a, BigInteger n) {

        System.out.println("\n" + "Тест Ферма: ");

        BigInteger r;
        r = a.modPow(n.subtract(BigInteger.ONE), n);

        if (r.compareTo(BigInteger.ONE) != 0) {
            System.out.println("r = " + r + " -> Число n составное. ");
            return;
        }

        System.out.println("r = 1 -> Число n, вероятно, простое. ");
    }

    /* Тест Соловея — Штрассена */
    public static void csh(BigInteger a, BigInteger n) {

        System.out.println("\n" + "Тест Соловея — Штрассена: ");

        BigInteger r;

        // System.out.println((n.subtract(BigInteger.ONE)).shiftRight(1));
        r = a.modPow((n.subtract(BigInteger.ONE)).shiftRight(1), n);

        if ((r.compareTo(BigInteger.ONE) != 0) && (r.compareTo(n.subtract(BigInteger.ONE)) != 0)) {
            System.out.println("r = " + r + " -> Число n составное. ");
            return;
        }

        if (r.compareTo(jac(a, n).mod(n)) != 0) {
            System.out.println("r = " + r + " != jac[a, n] = " + jac(a, n) + " -> Число n составное. ");
            return;
        }

        System.out.println("r = jac[a, n](mod n) = " + r + " -> Число n, вероятно, простое. ");
    }

    /* Тест Рабина-Миллера */
    public static void ram(BigInteger a, BigInteger n) {

        System.out.println("\n" + "Тест Рабина-Миллера: ");

        BigInteger y;
        BigInteger r = n.subtract(BigInteger.ONE);
        BigInteger s = BigInteger.ZERO;

        while ((r.mod(BigInteger.valueOf(2)).compareTo(BigInteger.ZERO) == 0)) {
            r = r.shiftRight(1);
            s = s.add(BigInteger.ONE);
        }

        y = a.modPow(r, n);

        if ((y.compareTo(BigInteger.ONE) != 0) && (y.compareTo(n.subtract(BigInteger.ONE)) != 0)) {

            while (s.compareTo((BigInteger.ONE)) != 0) {

                if ((y.compareTo((BigInteger.ONE)) == 0)) {

                    System.out.println("y = 1 -> Число n составное. " + "\n");
                    return;
                }

                if (y.compareTo(n.subtract(BigInteger.ONE)) == 0) break;

                y = y.modPow(BigInteger.valueOf(2), n);
                s = s.subtract(BigInteger.ONE);
            }

            if (y.compareTo(n.subtract(BigInteger.ONE)) != 0) {
                System.out.println("y = " + y + " != n - 1 = " + n.subtract(BigInteger.ONE) +
                        " -> Число n составное. " + "\n");
                return;
            }

        }
        System.out.println("r = 1 -> Число n, вероятно, простое. " + "\n");
    }

    /* Вычисление символа Якоби */
    public static BigInteger jac(BigInteger a, BigInteger n) {

        BigInteger g = BigInteger.ONE;
        BigInteger s = BigInteger.ONE;


        while (a.compareTo(BigInteger.ZERO) != 0) {
            if (a.compareTo(BigInteger.ONE) == 0)
                return g;
            else {
                BigInteger r = a;
                BigInteger k = BigInteger.ZERO;

                while ((r.mod(BigInteger.valueOf(2)).compareTo(BigInteger.ZERO) == 0)) {
                    r = r.shiftRight(1);
                    k = k.add(BigInteger.ONE);
                }

                if (k.mod(BigInteger.valueOf(2)).compareTo(BigInteger.ZERO) == 0)
                    s = BigInteger.valueOf(1);
                else if ((n.mod(BigInteger.valueOf(8)).compareTo(BigInteger.valueOf(1)) == 0) ||
                        (n.mod(BigInteger.valueOf(8)).compareTo(BigInteger.valueOf(7)) == 0))
                    s = BigInteger.valueOf(1);
                else if ((n.mod(BigInteger.valueOf(8)).compareTo(BigInteger.valueOf(3)) == 0) ||
                        (n.mod(BigInteger.valueOf(8)).compareTo(BigInteger.valueOf(5)) == 0))
                    s = BigInteger.valueOf(-1);

                if (r.compareTo(BigInteger.ONE) == 0) return (g.multiply(s));

                if ((n.mod(BigInteger.valueOf(4)).compareTo(BigInteger.valueOf(3)) == 0) &&
                        (r.mod(BigInteger.valueOf(4)).compareTo(BigInteger.valueOf(3)) == 0))
                    s = s.negate();

                g = g.multiply(s);
                a = n.mod(r);
                n = r;

            }
        }
        return (BigInteger.ZERO);
    }

    /* public static void ef() {

        BigInteger a = BigInteger.valueOf(2);
        BigInteger n = BigInteger.valueOf(100057);
        BigInteger t = n.subtract(BigInteger.valueOf(2));
        long time = 0;

        time = System.currentTimeMillis();
        while (a.compareTo(t) == -1) {
            //fer(a, n);
            //csh(a, n);
            ram(a, n);
            a = a.add(BigInteger.valueOf(1));
        }

        System.out.println("Число повторов  - " + a.subtract(BigInteger.valueOf(2)));
        time = System.currentTimeMillis() - time;
        System.out.println("Время работы  - " + time + " мс");
    } */
}