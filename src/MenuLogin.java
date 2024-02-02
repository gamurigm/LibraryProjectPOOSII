package biblioteca.Model;

import java.util.Scanner;

/***  @author gamur */

public class MenuLogin {
    
    public static void mainMenu(ListaLibros listaLibros) {
        
        Scanner scanner = new Scanner(System.in);
       
        System.out.println("Bienvenido a la Biblioteca");
        System.out.println("1. Ingresar como Administrador");
        System.out.println("2. Ingresar como Usuario");
        System.out.print("Seleccione una opcion: ");

        int opcion = scanner.nextInt();

        switch (opcion) {
            case 1 -> {
          
                User admin = Login.iniciarSesionAdmin();
                if (admin != null) {
                    AdminMenu adminMenu = new AdminMenu(listaLibros);
                   
                    adminMenu.mostrarMenu();
                }
            }

            case 2 -> {
               
                    UserMenu userMenu = new UserMenu(listaLibros);
                    
                    userMenu.menuPrincipal(); 
                
            }

            default -> System.out.println("NO Valido!");
        }
    }   
}
