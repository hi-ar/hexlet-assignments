package exercise;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// BEGIN
@Component
public class CustomBeanPostProcessor implements BeanPostProcessor {
    Logger logger = LoggerFactory.getLogger("@Inspect class annotation logger");
    private Map<String, Class> markedBeans = new HashMap<>();
    private Map<String, String> annotationLevels = new HashMap();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        //проверить, отмечен ли бин аннотацией @Inspect
        boolean annotationPresent = bean.getClass().isAnnotationPresent(Inspect.class);
        //Если отмечен, нужно запомнить этот бин и нужный уровень логгирования
        if (annotationPresent) {
            logger.info("§§§§ BEFORE found annotation @Inspect in bean {}", beanName);
            markedBeans.put(beanName, bean.getClass());
            annotationLevels.put(beanName, bean.getClass().getAnnotation(Inspect.class).level());
            logger.info("§§§§ now beanNames contains {}, annoLevels contains {}", markedBeans, annotationLevels);
        }

//        //Пример Егора аннотация поля (не класса), которая сетает рандом число
//        Field[] declaredFields = bean.getClass().getDeclaredFields();
//        Arrays.stream(declaredFields).filter(field -> field.getAnnotation(Inspect.class) != null)
//                .forEach(field -> {
//                    field.setAccessible(true);
//                    field.set(bean, getRandomValue());
//                });
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        //В этом методе нужно обернуть отмеченные аннотацией @Inspect бины в прокси,
        //который позволит перехватывать имя метода и параметры, с которыми он был вызван
        // чтобы выводить в консоль лог с нужным уровнем.
        if (markedBeans.containsKey(beanName)) {
            logger.info("±±±±± AFTER init found marked bean: " + beanName);

            Class beanClass = bean.getClass();

            //на какой бин наш прокси должен быть похож, конфигурируем:
            Object proxiedBean = Proxy.newProxyInstance(
                    beanClass.getClassLoader(), //подделываем класс
                    beanClass.getInterfaces(), //подделываем интерфейсы
                    (proxy, method, args) -> { //как обрабатывать обращения к методам
                        String calledMethodName = method.getName();

                        String msg = String.format(
                                "±±±±± Was called method: %s() with arguments: %s", calledMethodName, Arrays.toString(args)
                        );
                        logger.info(msg); //выполняем задание - пишем лог
                        //контекст вызывал бин кальк (который проксировали) это его ответ
//                        return 1234; //sum?a=8&b=7 записывается в лог и возвращает 1234
                        return method.invoke(bean, args); //вызываем кальк и пересылаем его ответ (15)
                    }
            );
            return proxiedBean; //вместо бина, АфтерИнит возвращает прокси, который логирует обращения
        }
        return bean;  //если бин не помечен аннотацией, без изменений
    }
}
// END
