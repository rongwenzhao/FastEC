package com.example.compiler;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleAnnotationValueVisitor7;

/**
 * 一个Visitor就是对应注解所注解的类或方法或属性所传入的值
 * Created by rongwenzhao on 2017/11/23.
 */

public final class AppRegisterVisitor extends SimpleAnnotationValueVisitor7<Void, Void> {

    private Filer mFiler = null;
    private TypeMirror mTypeMirror = null;
    private String mPackageName = null;

    public void setmFiler(Filer filer) {
        this.mFiler = filer;
    }

    @Override
    public Void visitString(String s, Void p) {
        mPackageName = s;
        return super.visitString(s, p);
    }

    /**
     * 访问类类型的
     *
     * @param t
     * @param p
     * @return
     */

    @Override
    public Void visitType(TypeMirror t, Void p) {
        mTypeMirror = t;
        generateJavaCode();
        return p;
    }

    /**
     * com.squareup.javapoet 生成java代码
     */
    private void generateJavaCode() {
        final TypeSpec targetActivity =
                TypeSpec.classBuilder("AppRegister")
                        .addModifiers(Modifier.PUBLIC)
                        .addModifiers(Modifier.FINAL)
                        .superclass(TypeName.get(mTypeMirror))
                        .build();
        final JavaFile javaFile = JavaFile.builder(mPackageName + ".wxapi", targetActivity)
                .addFileComment("微信广播接收器")//文件注释
                .build();
        try {
            javaFile.writeTo(mFiler);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
