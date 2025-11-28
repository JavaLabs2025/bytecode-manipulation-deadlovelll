package org.example.visitor;

import org.example.abc_visitor.AbcVisitor;
import org.example.structs.MethodInfo;
import org.objectweb.asm.*;

import java.util.List;

import static org.objectweb.asm.Opcodes.ASM8;
import org.example.structs.ClassInfo;

public class ClassPrinter extends ClassVisitor {

    private ClassInfo currentClass;
    private List<ClassInfo> allClasses;
    private ClassInfo classInfo;

    public ClassPrinter(List<ClassInfo> allClasses) {
        super(ASM8);
        this.allClasses = allClasses;
    }

    public void visit(
            int version,
            int access,
            String name,
            String signature,
            String superName,
            String[] interfaces
    ) {
        classInfo = new ClassInfo();
        classInfo.name = name;
        classInfo.superName = superName;
    }

    public void visitSource(
            String source,
            String debug
    ) {
    }

    public void visitOuterClass(
            String owner,
            String name,
            String desc
    ) {
    }

    public AnnotationVisitor visitAnnotation(
            String desc,
            boolean visible
    ) {
        return null;
    }

    public void visitAttribute(Attribute attr) {
    }

    public void visitInnerClass(
            String name,
            String outerName,
            String innerName,
            int access
    ) {
    }

    public FieldVisitor visitField(
            int access,
            String name,
            String desc,
            String signature,
            Object value
    ) {
        currentClass.fields.add(name);
        return super.visitField(access, name, desc, signature, value);
    }

    public MethodVisitor visitMethod(
            int access,
            String name,
            String desc,
            String signature,
            String[] exceptions
    ) {
        MethodInfo methodInfo = new MethodInfo();
        methodInfo.name = name;
        currentClass.methods.add(methodInfo);
        return new AbcVisitor(Opcodes.ASM9, methodInfo);
    }

    public void visitEnd() {allClasses.add(currentClass);}
}

