/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client;

import facade.Facade;

public class Client {

    public static void main(String[] args) throws Exception {
        String filePath        = "test.txt";
        String originalContent = "¡Hola, patrón Facade en Java!";

        Facade facade = Facade.getInstance();

        System.out.println("=== Patron Facade + AES/CBC/PKCS5Padding ===");
        System.out.println("Clave AES (Base64): " + facade.getEncodedKey());

        facade.writeEncryptedFile(filePath, originalContent);

        String recovered = facade.readDecryptedFile(filePath);

        System.out.println("\nContenido original : " + originalContent);
        System.out.println("Contenido recuperado: " + recovered);
        System.out.println("Coinciden? " + originalContent.equals(recovered));
        System.out.println();
    }
}
