package org.example;

import org.example.structs.ClassInfo;
import org.example.structs.MethodInfo;
import org.example.visitor.ClassPrinter;
import org.objectweb.asm.ClassReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
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
        int totalFields = 0;
        int totalMethods = 0;
        int totalAssignments = 0;
        int totalOverrideMethods = 0;
        int maxDepth = 0;
        int sumDepth = 0;

        Map<String, String> superMap = new HashMap<>();
        for (ClassInfo classInfo : allClasses) {
            superMap.put(classInfo.name, classInfo.superName);
        }

        for (ClassInfo classInfo : allClasses) {
            totalFields += classInfo.fields.size();
            totalMethods += classInfo.methods.size();
            for (MethodInfo methodInfo : classInfo.methods) {
                totalAssignments += methodInfo.assignments;
            }
            int depth = 0;
            String s = classInfo.superName;
            while (s != null && !s.equals("java/lang/Object")) {
                depth++;
                s = superMap.get(s);
            }
            if (depth >= maxDepth) {
                maxDepth = depth;
                sumDepth += depth;
            }
        }

    }
}
