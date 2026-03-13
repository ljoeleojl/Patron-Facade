/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package subsystem;

import java.io.*;
import java.nio.file.*;

public class FileWriter {

    public void write(String filePath, String content) throws IOException {
        Files.write(Paths.get(filePath), content.getBytes());
    }
}
