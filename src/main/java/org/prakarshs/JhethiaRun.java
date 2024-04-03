package org.prakarshs;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class JhethiaRun {
    public static void main(String[] args) {
        System.out.println("*** Jai Jinendra ! Nashta Karke Jaana ! ***");
        Path sourceFilePath = FileSystems.getDefault().getPath(args[0]);
        if (Files.exists(sourceFilePath)) {
            new Jhethia().execute(sourceFilePath);
        }
        else {
            System.out.println("Cannot resolve args[0] [" + sourceFilePath + "] as a valid file path.");
        }
    }
}