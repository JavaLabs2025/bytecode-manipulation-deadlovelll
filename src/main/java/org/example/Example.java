package org.example;

import org.example.structs.ClassInfo;
import org.example.visitor.ClassPrinter;
import org.objectweb.asm.ClassReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Example {

    public static void main(String[] args) throws IOException {
        List<ClassInfo> allClasses = new ArrayList<>();

        try (JarFile sampleJar = new JarFile("src/main/resources/sample.jar")) {
            Enumeration<JarEntry> enumeration = sampleJar.entries();
            while (enumeration.hasMoreElements()) {
                JarEntry entry = enumeration.nextElement();
                if (entry.getName().endsWith(".class")) {
                    ClassPrinter cp = new ClassPrinter(allClasses);
                    InputStream inputStream = sampleJar.getInputStream(entry);
                    ClassReader reader = new ClassReader(inputStream);
                    reader.accept(cp, 0);
                }
            }
        }
        getStats(allClasses);
    }

    private static void getStats(List<ClassInfo> allClasses) {
        System.out.println("Classes count: " + allClasses.size());
    }
}
