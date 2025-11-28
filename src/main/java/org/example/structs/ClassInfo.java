package org.example.structs;

import java.util.List;
import java.util.ArrayList;

public class ClassInfo {
    public String name;
    public String superName;
    public List<String> fields = new ArrayList<>();
    public List<MethodInfo> methods = new ArrayList<>();
}
