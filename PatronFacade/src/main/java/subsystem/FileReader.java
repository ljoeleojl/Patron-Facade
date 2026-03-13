/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package subsystem;

import java.io.*;
import java.nio.file.*;

public class FileReader {

    public String read(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }
}
