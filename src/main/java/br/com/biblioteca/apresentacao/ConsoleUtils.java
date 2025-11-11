package br.com.biblioteca.apresentacao;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleUtils {

    private static final Scanner scanner = new Scanner(System.in);

    public static int lerOpcao() {
        while (true) {
            try {
                int opcao = scanner.nextInt();
                scanner.nextLine();
                return opcao;
            } catch (InputMismatchException e) {
                System.out.print("Entrada inválida. Digite apenas números: ");
                scanner.nextLine();
            }
        }
    }

    public static Long lerLong() {
        while (true) {
            try {
                Long id = scanner.nextLong();
                scanner.nextLine();
                return id;
            } catch (InputMismatchException e) {
                System.out.print("Entrada inválida. Digite apenas números (ID): ");
                scanner.nextLine();
            }
        }
    }

    public static Double lerDouble() {
         while (true) {
            try {
                String input = scanner.nextLine();
                input = input.replace(',', '.');
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.print("Entrada inválida. Digite um valor numérico (ex: 199.90): ");
            }
        }
    }

    public static String lerString() {
        return scanner.nextLine();
    }
    
    public static void fecharScanner() {
        scanner.close();
    }
}
