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

    public ClassPrinter(List<ClassInfo> allClasses) {
        super(ASM8);
        this.allClasses = allClasses;
    }

    @Override
    public void visit(
            int version,
            int access,
            String name,
            String signature,
            String superName,
            String[] interfaces
    ) {
        currentClass = new ClassInfo();
        currentClass.name = name;
        currentClass.superName = superName;
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public void visitSource(
            String source,
            String debug
    ) {
        super.visitSource(source, debug);
    }

    @Override
    public void visitOuterClass(
            String owner,
            String name,
            String desc
    ) {
        super.visitOuterClass(owner, name, desc);
    }

    @Override
    public AnnotationVisitor visitAnnotation(
            String desc,
            boolean visible
    ) {
        return super.visitAnnotation(desc, visible);
    }

    @Override
    public void visitAttribute(Attribute attr) {
        super.visitAttribute(attr);
    }

    @Override
    public void visitInnerClass(
            String name,
            String outerName,
            String innerName,
            int access
    ) {
        super.visitInnerClass(name, outerName, innerName, access);
    }

    @Override
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

    @Override
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

    @Override
    public void visitEnd() {
        allClasses.add(currentClass);
        super.visitEnd();
    }
}

