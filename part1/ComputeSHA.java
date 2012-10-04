// Copyright 2012
// Author: Mark Vismonte and Logan Chang
// Date: 10/4/2012
// ComputeSHA.java - <insert description here>

import java.io.*;
import java.security.*;


public class ComputeSHA {
  public static void main(String[] args) {
    // Verify that there is an input file
    if (args.length < 1) {
      System.out.println("You specify an input file");
    }

    // Declare variables.
    MessageDigest md;
    File file;
    FileInputStream fstream;
    byte[] fileData;

    try {
      // Obtain the MessageDigest instance and open the file/file stream.
      md = MessageDigest.getInstance("SHA-1");
      file = new File(args[0]);
      fstream = new FileInputStream(file);

      // Create a byte array for the data and read the file in.
      fileData = new byte[(int)file.length()];
      fstream.read(fileData);
      
      // Find the SHA-1 of the file data and print out each byte.
      for (byte b: md.digest(fileData)) {
        String hex = Integer.toHexString(0xFF & b);
        if (hex.length() == 1) {
          System.out.print('0');
        }
        System.out.printf(hex);
      }
      System.out.println();
    } catch (FileNotFoundException e) {
      System.out.println("The file was not found.");
      System.exit(1);
    } catch (IOException e) {
      System.out.println("There was a problem opening the file");
      System.exit(1);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
  }
}