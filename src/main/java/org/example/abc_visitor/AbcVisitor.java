package org.example.abc_visitor;

import org.example.structs.MethodInfo;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;


public class AbcVisitor extends MethodVisitor {

    private final MethodInfo methodInfo;

    public AbcVisitor(int api, MethodInfo methodInfo) {
        super(api);
        this.methodInfo = methodInfo;
    }

    @Override
    public void visitMethodInsn(
            int opcode,
            String owner,
            String name,
            String desc,
            boolean itf
    ) {
        if ((opcode >= Opcodes.ISTORE && opcode <= Opcodes.ASTORE) || opcode  == Opcodes.IINC) {
            methodInfo.assignments++;
        }
    }
}
