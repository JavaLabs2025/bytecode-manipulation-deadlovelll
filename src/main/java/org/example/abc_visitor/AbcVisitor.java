package org.example.abc_visitor;

import org.example.structs.MethodInfo;
import org.objectweb.asm.MethodVisitor;


public class AbcVisitor extends MethodVisitor {

    private final MethodInfo methodInfo;

    public AbcVisitor(int api, MethodInfo methodInfo) {
        super(api);
        this.methodInfo = methodInfo;
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        super.visitMethodInsn(opcode, owner, name, desc, itf);
    }
}
