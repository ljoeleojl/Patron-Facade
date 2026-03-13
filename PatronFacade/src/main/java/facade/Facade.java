/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package facade;

import subsystem.FileReader;
import subsystem.FileWriter;
import subsystem.FileEncryptor;
import java.io.IOException;

public class Facade {

    // Singleton instance
    private static Facade instance;

    // Subsystem references
    private final FileReader fileReader;
    private final FileWriter fileWriter;
    private final FileEncryptor fileEncryptor;

    // Private constructor (Singleton)
    private Facade() {
        this.fileReader    = new FileReader();
        this.fileWriter    = new FileWriter();
        this.fileEncryptor = new FileEncryptor();
    }

    // Global access point
    public static Facade getInstance() {
        if (instance == null) {
            instance = new Facade();
        }
        return instance;
    }

    /** Encrypts content and writes it to the file. */
    public void writeEncryptedFile(String filePath, String content) throws IOException {
        String encrypted = fileEncryptor.encrypt(content);
        fileWriter.write(filePath, encrypted);
        System.out.println("Archivo cifrado y escrito en: " + filePath);
    }

    /** Reads the file and decrypts its content. */
    public String readDecryptedFile(String filePath) throws IOException {
        String encrypted  = fileReader.read(filePath);
        String decrypted  = fileEncryptor.decrypt(encrypted);
        System.out.println("Archivo leido y descifrado desde: " + filePath);
        return decrypted;
    }

    /** Devuelve la clave AES en Base64 (delega al encriptador). */
    public String getEncodedKey() {
        return fileEncryptor.getEncodedKey();
    }
}
