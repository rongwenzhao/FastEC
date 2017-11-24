package com.example.compiler;

import com.example.annotations.AppRegisterGenerator;
import com.example.annotations.EntryGenerator;
import com.example.annotations.PayEntryGenerator;
import com.google.auto.service.AutoService;

import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.AnnotationValueVisitor;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

/**
 * Created by rongwenzhao on 2017/11/23.
 */

/**
 * AbstractProcessor，如果项目类型是application或androidLib，是找不到AbstractProcessor这个类的，
 * 此处是java的lib，所以可以找到使用
 */

@SuppressWarnings("unused")
@AutoService(Processor.class)
public class LatteProcessor extends AbstractProcessor {

    /**
     * 返回此Processor所关注的annotation类型
     *
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        final Set<String> types = new LinkedHashSet<>();
        final Set<Class<? extends Annotation>> supportedAnnotations = getSupportedAnnotations();
        for (Class<? extends Annotation> annotation : supportedAnnotations) {
            types.add(annotation.getCanonicalName());//annotation.getCanonicalName()返回规范名
        }
        return types;
    }

    /**
     * 将该processor所关注的annotation添加到set中
     *
     * @return
     */
    private Set<Class<? extends Annotation>> getSupportedAnnotations() {
        final Set<Class<? extends Annotation>> annotations = new LinkedHashSet<>();
        annotations.add(EntryGenerator.class);
        annotations.add(PayEntryGenerator.class);
        annotations.add(AppRegisterGenerator.class);
        return annotations;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        generateEntryCode(roundEnv);
        generatePayEntryCode(roundEnv);
        generateAppRegisterEntryCode(roundEnv);
        return true;
    }

    /**
     * 调试方式见：http://blog.csdn.net/zhangteng22/article/details/54946270
     * 整个流程调试一次之后，细节你就会清楚了。项目中使用时，直接扣出相应代码即可。
     *
     * @param env
     * @param annotation
     * @param visitor
     */
    private void scan(RoundEnvironment env,
                      Class<? extends Annotation> annotation,
                      AnnotationValueVisitor visitor) {

        for (Element typeElement : env.getElementsAnnotatedWith(annotation)) {
            //typeElement是使用了对应注解的类
            //annotationMirrors存放该类上的注解集合，每一个AnnotationMirror对应了一个注解
            final List<? extends AnnotationMirror> annotationMirrors =
                    typeElement.getAnnotationMirrors();

            for (AnnotationMirror annotationMirror : annotationMirrors) {
                //elementValues存放着对应注解中的元素信息。此处，指自定义的package信息和templateClass信息
                final Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues =
                        annotationMirror.getElementValues();

                for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry
                        : elementValues.entrySet()) {
                    //annotation中每个元素调用accept方法时，都会调用对应类型的在visitor中的方法
                    //比如，String类型的，调用visitString方法，类类型的，调用visitType方法
                    entry.getValue().accept(visitor, null);
                }
            }
        }

    }

    /**
     * 生成Entry对应java文件的方法
     *
     * @param env
     */
    private void generateEntryCode(RoundEnvironment env) {
        final EntryVisitor entryVisitor = new EntryVisitor();
        entryVisitor.setmFiler(processingEnv.getFiler());
        scan(env, EntryGenerator.class, entryVisitor);
    }

    /**
     * 生成PayEntry对应java文件的方法
     *
     * @param env
     */
    private void generatePayEntryCode(RoundEnvironment env) {
        final PayEntryVisitor payEntryVisitor = new PayEntryVisitor();
        payEntryVisitor.setmFiler(processingEnv.getFiler());
        scan(env, PayEntryGenerator.class, payEntryVisitor);
    }

    /**
     * 生成AppRegister对应java文件的方法
     *
     * @param env
     */
    private void generateAppRegisterEntryCode(RoundEnvironment env) {
        final AppRegisterVisitor appRegisterVisitor = new AppRegisterVisitor();
        appRegisterVisitor.setmFiler(processingEnv.getFiler());
        scan(env, AppRegisterGenerator.class, appRegisterVisitor);
    }
}
