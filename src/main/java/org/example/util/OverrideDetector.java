package org.example.util;

import org.example.structs.ClassInfo;
import org.example.structs.MethodInfo;

import java.util.List;

public class OverrideGetter {

    public int get(
            List<ClassInfo> allClasses,
            ClassInfo classInfo,
            int totalAssignments,
            int totalOverrideMethods
    ) {
        for (MethodInfo m : classInfo.methods) {
            totalAssignments += m.assignments;
            boolean isOverride = false;
            String parent = classInfo.superName;
            while (parent != null && !parent.equals("java/lang/Object")) {
                String finalParent = parent;
                ClassInfo parentClass = allClasses.stream()
                        .filter(c -> c.name.equals(finalParent))
                        .findFirst().orElse(null);
                if (parentClass != null) {
                    if (parentClass.methods.stream()
                            .anyMatch(pm -> pm.name.equals(m.name) && pm.desc.equals(m.desc))) {
                        isOverride = true;
                        break;
                    }
                    parent = parentClass.superName;
                } else break;
            }

            for (String iface : classInfo.interfaces) {
                ClassInfo ifaceClass = allClasses.stream().filter(c -> c.name.equals(iface)).findFirst().orElse(null);
                if (ifaceClass != null && ifaceClass.methods.stream()
                        .anyMatch(pm -> pm.name.equals(m.name) && pm.desc.equals(m.desc))) {
                    isOverride = true;
                    break;
                }
            }

            if (isOverride) totalOverrideMethods++;
        }
        return totalOverrideMethods;
    }

    private boolean overrideInterfaceCheck(
            List<ClassInfo> allClasses,
            ClassInfo classInfo
    ) {
        boolean isOverride = false;
        for (String iface : classInfo.interfaces) {
            ClassInfo ifaceClass = allClasses.stream().filter(
                    c -> c.name.equals(iface)
            ).findFirst().orElse(null);
            if (ifaceClass != null && ifaceClass.methods.stream().anyMatch(pm -> pm.name.equals(m.name) && pm.desc.equals(m.desc))) {
                isOverride = true;
                return isOverride;
            }
        }
        return null;
    }
}
