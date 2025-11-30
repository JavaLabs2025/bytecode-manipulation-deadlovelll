package org.example.util;

import org.example.structs.ClassInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InheritanceDepthCalculator {

    private final Map<String, String> superMap = new HashMap<>();

    public InheritanceDepthCalculator(List<ClassInfo> allClasses) {
        for (ClassInfo classInfo : allClasses) {
            superMap.put(classInfo.name, classInfo.superName);
        }
    }

    public int getDepth(ClassInfo cls) {
        int depth = 0;
        String parent = cls.superName;

        while (parent != null && !parent.equals("java/lang/Object")) {
            depth++;
            parent = superMap.get(parent);
        }

        return depth;
    }

    public int getMaxDepth(List<ClassInfo> classes) {
        int max = 0;
        for (ClassInfo cls : classes) {
            int d = getDepth(cls);
            if (d > max) max = d;
        }
        return max;
    }

    public int getAverageDepth(List<ClassInfo> classes) {
        if (classes.isEmpty()) return 0;

        int sum = 0;
        for (ClassInfo cls : classes) {
            sum += getDepth(cls);
        }
        return sum / classes.size();
    }
}
