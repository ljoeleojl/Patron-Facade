/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package subsystem;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;
import java.util.Base64;

public class FileEncryptor {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORM = "AES/CBC/PKCS5Padding";
    private static final int    KEY_SIZE  = 128; // bits
    private static final int    IV_SIZE   = 16;  // bytes

    private final SecretKey secretKey;

    public FileEncryptor() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
            keyGen.init(KEY_SIZE, new SecureRandom());
            this.secretKey = keyGen.generateKey();
        } catch (Exception e) {
            throw new RuntimeException("Error al generar la clave AES", e);
        }
    }

    /**
     * Cifra el contenido con AES/CBC/PKCS5Padding.
     * El IV aleatorio (16 bytes) se antepone al texto cifrado.
     * Formato: Base64(IV + ciphertext)
     */
    public String encrypt(String content) {
        try {
            byte[] iv = new byte[IV_SIZE];
            new SecureRandom().nextBytes(iv);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            Cipher cipher = Cipher.getInstance(TRANSFORM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
            byte[] cipherBytes = cipher.doFinal(content.getBytes("UTF-8"));

            // Prepend IV so decrypt can recover it
            byte[] ivPlusCipher = new byte[IV_SIZE + cipherBytes.length];
            System.arraycopy(iv,          0, ivPlusCipher, 0,       IV_SIZE);
            System.arraycopy(cipherBytes, 0, ivPlusCipher, IV_SIZE, cipherBytes.length);

            return Base64.getEncoder().encodeToString(ivPlusCipher);

        } catch (Exception e) {
            throw new RuntimeException("Error al cifrar el contenido", e);
        }
    }

    /**
     * Descifra un texto previamente cifrado con encrypt().
     * Extrae el IV de los primeros 16 bytes y descifra el resto.
     */
    public String decrypt(String encryptedContent) {
        try {
            byte[] ivPlusCipher = Base64.getDecoder().decode(encryptedContent);

            byte[] iv = new byte[IV_SIZE];
            System.arraycopy(ivPlusCipher, 0, iv, 0, IV_SIZE);

            int cipherLen = ivPlusCipher.length - IV_SIZE;
            byte[] cipherBytes = new byte[cipherLen];
            System.arraycopy(ivPlusCipher, IV_SIZE, cipherBytes, 0, cipherLen);

            Cipher cipher = Cipher.getInstance(TRANSFORM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));

            return new String(cipher.doFinal(cipherBytes), "UTF-8");

        } catch (Exception e) {
            throw new RuntimeException("Error al descifrar el contenido", e);
        }
    }

    /** Devuelve la clave AES en Base64 (útil para diagnóstico). */
    public String getEncodedKey() {
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }
}
