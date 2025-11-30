package org.example.util;

import org.example.structs.ClassInfo;
import org.example.structs.MethodInfo;

import java.util.List;

public class OverrideDetector {

    private final List<ClassInfo> classes;

    public OverrideDetector(List<ClassInfo> classes) {
        this.classes = classes;
    }

    public boolean isOverride(ClassInfo cls, MethodInfo method) {
        if (checkSuperclass(cls.superName, method)) return true;

        for (String iface : cls.interfaces) {
            if (checkInterface(iface, method)) return true;
        }

        return false;
    }

    private boolean checkSuperclass(String parent, MethodInfo method) {
        while (parent != null && !parent.equals("java/lang/Object")) {
            ClassInfo parentClass = findClass(parent);
            if (parentClass == null) return false;

            if (containsMethod(parentClass, method)) {
                return true;
            }

            parent = parentClass.superName;
        }
        return false;
    }

    private boolean checkInterface(String iface, MethodInfo method) {
        ClassInfo ifaceClass = findClass(iface);
        if (ifaceClass == null) return false;

        return containsMethod(ifaceClass, method);
    }

    private boolean containsMethod(ClassInfo cls, MethodInfo m) {
        return cls.methods.stream()
                .anyMatch(pm -> pm.name.equals(m.name) && pm.desc.equals(m.desc));
    }

    private ClassInfo findClass(String internalName) {
        return classes.stream()
                .filter(c -> c.name.equals(internalName))
                .findFirst()
                .orElse(null);
    }
}
